package com.simats.orcare.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.simats.orcare.BuildConfig
import com.simats.orcare.data.ChatMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit
import java.net.UnknownHostException

class ChatbotViewModel(application: Application) : AndroidViewModel(application) {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _generatingMessage = MutableStateFlow<String?>(null)
    val generatingMessage: StateFlow<String?> = _generatingMessage.asStateFlow()

    private val gson = Gson()

    private val http = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Full conversation history sent to Gemini on every request
    private val history = mutableListOf<Map<String, Any>>()

    private val systemPrompt =
        "You are ORCare AI, a friendly oral health assistant built into the ORCare app. " +
        "Help users understand oral diseases, symptoms, home remedies, and when to see a dentist. " +
        "Be concise and warm. Use **bold** for key terms. " +
        "Always recommend a dentist for serious or persistent issues."

    // Gemini 1.5 Flash via direct REST API — no SDK, no version issues
    private val modelId = "gemini-1.5-flash"
    private val endpoint
        get() = "https://generativelanguage.googleapis.com/v1beta/models/$modelId:generateContent?key=${BuildConfig.API_KEY}"

    init { startNewSession() }

    fun startNewSession() {
        _messages.value = emptyList()
        history.clear()
    }

    fun sendMessage(text: String) {
        if (_generatingMessage.value != null) return
        viewModelScope.launch {
            addMessageLocally(text, isFromUser = true)
            processResponse(text)
        }
    }

    fun sendSymptomQuery(symptom: String) {
        if (_generatingMessage.value != null) return
        viewModelScope.launch {
            val q = "I have a concern about: $symptom. What are the common causes, symptoms, and home remedies?"
            addMessageLocally(q, isFromUser = true)
            processResponse(q)
        }
    }

    private fun addMessageLocally(text: String, isFromUser: Boolean) {
        _messages.value = _messages.value + ChatMessage(text = text, isFromUser = isFromUser)
    }

    private suspend fun processResponse(query: String) {
        _generatingMessage.value = "..."
        try {
            // Add user turn to history
            history.add(userPart(if (history.isEmpty()) "$systemPrompt\n\n$query" else query))

            val responseText = withContext(Dispatchers.IO) { callGemini() }

            if (!responseText.isNullOrBlank()) {
                history.add(modelPart(responseText))   // keep AI turn in history
                addMessageLocally(responseText.trim(), isFromUser = false)
            } else {
                history.removeLast()
                addMessageLocally("I couldn't generate a response. Please try again.", false)
            }
        } catch (e: Exception) {
            Log.e("ChatbotVM", "Gemini error", e)
            if (history.isNotEmpty()) history.removeLast()
            addMessageLocally(friendlyError(e), false)
        } finally {
            _generatingMessage.value = null
        }
    }

    // ── HTTP call (runs on IO dispatcher) ───────────────────────────────────
    private fun callGemini(): String? {
        val body = gson.toJson(
            mapOf(
                "contents" to history,
                "generationConfig" to mapOf(
                    "temperature" to 0.75,
                    "maxOutputTokens" to 900
                )
            )
        )

        val request = Request.Builder()
            .url(endpoint)
            .post(body.toRequestBody("application/json".toMediaType()))
            .build()

        http.newCall(request).execute().use { response ->
            val raw = response.body?.string() ?: ""
            Log.d("ChatbotVM", "HTTP ${response.code}: ${raw.take(500)}")
            if (!response.isSuccessful) throw Exception("HTTP ${response.code}: $raw")
            return parseText(raw)
        }
    }

    // ── Parse response JSON ──────────────────────────────────────────────────
    @Suppress("UNCHECKED_CAST")
    private fun parseText(json: String): String? {
        return try {
            val root = gson.fromJson(json, Map::class.java) as Map<String, Any>
            val candidates = root["candidates"] as? List<*> ?: return null
            val content = (candidates[0] as? Map<*, *>)?.get("content") as? Map<*, *> ?: return null
            val parts = content["parts"] as? List<*> ?: return null
            (parts[0] as? Map<*, *>)?.get("text") as? String
        } catch (e: Exception) {
            Log.e("ChatbotVM", "Parse error: ${e.message}")
            null
        }
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private fun userPart(text: String) = mapOf(
        "role" to "user",
        "parts" to listOf(mapOf("text" to text))
    )

    private fun modelPart(text: String) = mapOf(
        "role" to "model",
        "parts" to listOf(mapOf("text" to text))
    )

    private fun friendlyError(e: Exception): String {
        val msg = e.message ?: ""
        return when {
            e is UnknownHostException -> "📡 No internet connection. Please check your Wi-Fi or mobile data."
            msg.contains("400") -> "⚠️ Model not available. Try again or contact support.\n(400 Bad Request)"
            msg.contains("401") || msg.contains("403") -> "⚠️ Invalid API key. Check your configuration."
            msg.contains("429") -> "⚠️ Quota reached. Please try again later."
            msg.contains("connect", ignoreCase = true) ||
            msg.contains("timeout", ignoreCase = true) ||
            msg.contains("network", ignoreCase = true) -> "📡 Connection timed out. Check your internet."
            else -> "⚠️ ${e.javaClass.simpleName}: ${msg.take(120)}"
        }
    }
}
