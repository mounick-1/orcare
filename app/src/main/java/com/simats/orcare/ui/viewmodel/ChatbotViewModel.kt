package com.simats.orcare.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.simats.orcare.data.ChatMessage
import com.simats.orcare.data.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class ChatbotViewModel(application: Application) : AndroidViewModel(application) {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _generatingMessage = MutableStateFlow<String?>(null)
    val generatingMessage: StateFlow<String?> = _generatingMessage.asStateFlow()

    // Conversation history sent to backend on every request
    private val history = mutableListOf<Map<String, Any>>()

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
            val responseText = withContext(Dispatchers.IO) { callBackend(query) }

            if (!responseText.isNullOrBlank()) {
                // Update history with the user turn and model response
                history.add(mapOf("role" to "user", "parts" to listOf(mapOf("text" to query))))
                history.add(mapOf("role" to "model", "parts" to listOf(mapOf("text" to responseText))))
                addMessageLocally(responseText.trim(), isFromUser = false)
            } else {
                addMessageLocally("I couldn't generate a response. Please try again.", false)
            }
        } catch (e: Exception) {
            addMessageLocally(friendlyError(e), false)
        } finally {
            _generatingMessage.value = null
        }
    }

    private suspend fun callBackend(message: String): String? {
        val request: Map<String, Any> = mapOf("message" to message, "history" to history)
        val response = RetrofitClient.getInstance().askChatbot(request)
        if (response.isSuccessful) {
            return response.body()?.get("text") as? String
        }
        throw Exception("HTTP ${response.code()}: ${response.errorBody()?.string()}")
    }

    private fun friendlyError(e: Exception): String {
        val msg = e.message ?: ""
        return when {
            e is UnknownHostException -> "No internet connection. Please check your Wi-Fi or mobile data."
            msg.contains("401") || msg.contains("403") -> "Authentication error. Please log in again."
            msg.contains("429") -> "Too many requests. Please try again later."
            msg.contains("500") -> "Server error. Please try again in a moment."
            msg.contains("connect", ignoreCase = true) ||
            msg.contains("timeout", ignoreCase = true) -> "Connection timed out. Check your internet."
            else -> "Something went wrong. Please try again."
        }
    }
}
