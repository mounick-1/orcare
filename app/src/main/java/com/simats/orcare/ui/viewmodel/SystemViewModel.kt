package com.simats.orcare.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simats.orcare.data.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.orcare.ui.components.*

sealed class UIState {
    object Idle : UIState()
    object Loading : UIState()
    data class Success(val message: String) : UIState()
    data class Error(val message: String) : UIState()
}

class SystemViewModel : ViewModel() {
    private val apiService = RetrofitClient.getInstance()

    private val _feedbackState = MutableStateFlow<UIState>(UIState.Idle)
    val feedbackState: StateFlow<UIState> = _feedbackState.asStateFlow()

    fun submitFeedback(name: String, email: String, message: String) {
        viewModelScope.launch {
            _feedbackState.value = UIState.Loading
            try {
                val response = apiService.submitFeedback(mapOf(
                    "name" to name,
                    "email" to email,
                    "message" to message
                ))
                if (response.isSuccessful) {
                    _feedbackState.value = UIState.Success("Feedback submitted successfully!")
                } else {
                    _feedbackState.value = UIState.Error("Failed to submit feedback: ${response.message()}")
                }
            } catch (e: Exception) {
                _feedbackState.value = UIState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun resetFeedbackState() {
        _feedbackState.value = UIState.Idle
    }
}
