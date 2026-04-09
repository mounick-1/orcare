package com.simats.orcare.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.simats.orcare.data.UserPreferences
import com.simats.orcare.data.api.RetrofitClient
import com.simats.orcare.data.model.*
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



class AuthViewModel(
    application: Application,
    private val userPreferences: UserPreferences
) : AndroidViewModel(application) {

    private val apiService get() = RetrofitClient.getInstance()

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _registerState.asStateFlow()

    private val _otpState = MutableStateFlow<AuthState>(AuthState.Idle)
    val otpState: StateFlow<AuthState> = _otpState.asStateFlow()

    private val _forgotPasswordState = MutableStateFlow<AuthState>(AuthState.Idle)
    val forgotPasswordState: StateFlow<AuthState> = _forgotPasswordState.asStateFlow()

    private val _resetPasswordState = MutableStateFlow<AuthState>(AuthState.Idle)
    val resetPasswordState: StateFlow<AuthState> = _resetPasswordState.asStateFlow()

    private val _profileUpdateState = MutableStateFlow<AuthState>(AuthState.Idle)
    val profileUpdateState: StateFlow<AuthState> = _profileUpdateState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            try {
                val response = apiService.login(LoginRequest(email, password))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && !body.error) {
                        userPreferences.saveAuthToken(body.token)
                        body.user.let { user ->
                            userPreferences.saveUserName(user.name)
                            userPreferences.saveUserEmail(user.email)
                            if (user.district != null) userPreferences.saveDistrict(user.district)
                            if (user.state != null) userPreferences.saveState(user.state)
                            if (user.language != null) userPreferences.saveLanguage(user.language)
                            if (user.profileImage != null) userPreferences.saveProfileImageUri(user.profileImage)
                            if (user.profileImageIndex != null) userPreferences.saveProfileImageIndex(user.profileImageIndex)
                        }
                        _loginState.value = AuthState.Success(body.message)
                    } else {
                        _loginState.value = AuthState.Error(body?.message ?: "Login failed")
                    }
                } else {
                    _loginState.value = AuthState.Error("Login failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun register(name: String, email: String, password: String, age: Int, gender: String, language: String? = "English") {
        viewModelScope.launch {
            _registerState.value = AuthState.Loading
            try {
                val response = apiService.register(RegisterRequest(name, email, password, age, gender, language))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && !body.error) {
                        _registerState.value = AuthState.Success(body.message)
                    } else {
                        _registerState.value = AuthState.Error(body?.message ?: "Registration failed")
                    }
                } else {
                    _registerState.value = AuthState.Error("Registration failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _registerState.value = AuthState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun verifyOtp(email: String, otp: String, type: String) {
        viewModelScope.launch {
            _otpState.value = AuthState.Loading
            try {
                val response = apiService.verifyOtp(VerifyOtpRequest(email, otp, type))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && !body.error) {
                        _otpState.value = AuthState.Success(body.message)
                        if (body.token != null) {
                            userPreferences.saveAuthToken(body.token)
                        }
                    } else {
                        _otpState.value = AuthState.Error(body?.message ?: "OTP Verification failed")
                    }
                } else {
                    _otpState.value = AuthState.Error("OTP Verification failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _otpState.value = AuthState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun resendOtp(email: String, type: String) {
        viewModelScope.launch {
            try {
                val response = apiService.resendOtp(mapOf("email" to email, "type" to type))
                if (!response.isSuccessful) {
                    Log.e("AuthViewModel", "Resend OTP failed: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Resend OTP error", e)
            }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            _forgotPasswordState.value = AuthState.Loading
            try {
                val response = apiService.forgotPassword(ForgotPasswordRequest(email))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && !body.error) {
                        _forgotPasswordState.value = AuthState.Success(body.message)
                    } else {
                        _forgotPasswordState.value = AuthState.Error(body?.message ?: "Request failed")
                    }
                } else {
                    _forgotPasswordState.value = AuthState.Error("Request failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _forgotPasswordState.value = AuthState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun resetPassword(email: String, otp: String, newPassword: String) {
        viewModelScope.launch {
            _resetPasswordState.value = AuthState.Loading
            try {
                val response = apiService.resetPassword(ResetPasswordRequest(email, otp, newPassword))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && !body.error) {
                        _resetPasswordState.value = AuthState.Success(body.message)
                    } else {
                        _resetPasswordState.value = AuthState.Error(body?.message ?: "Reset failed")
                    }
                } else {
                    _resetPasswordState.value = AuthState.Error("Reset failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _resetPasswordState.value = AuthState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun updateProfile(
        name: String? = null,
        email: String? = null,
        language: String? = null,
        district: String? = null,
        state: String? = null,
        image: String? = null,
        profileImageIndex: Int? = null,
    ) {
        viewModelScope.launch {
            _profileUpdateState.value = AuthState.Loading
            try {
                // Here, we would ideally read userId from preferences or pass it
                // For now, let's create UpdateProfileRequest with temporary dummy userId
                val request = UpdateProfileRequest(
                    userId = null,
                    name = name,
                    email = email,
                    language = language,
                    district = district,
                    state = state,
                    profileImage = image,
                    profileImageIndex = profileImageIndex
                )
                
                val response = apiService.updateProfile(request)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.success) {
                        _profileUpdateState.value = AuthState.Success(body.message)
                        body.user?.let { user ->
                            userPreferences.saveUserName(user.name)
                            userPreferences.saveUserEmail(user.email)
                            userPreferences.saveDistrict(user.district ?: "")
                            userPreferences.saveState(user.state ?: "")
                            userPreferences.saveLanguage(user.language ?: "English")
                            userPreferences.saveProfileImageUri(user.profileImage)
                            userPreferences.saveProfileImageIndex(user.profileImageIndex ?: 0)
                        }
                    } else {
                        _profileUpdateState.value = AuthState.Error(body?.message ?: "Update failed")
                    }
                } else {
                    _profileUpdateState.value = AuthState.Error("Update failed: ${response.message()}")
                }
            } catch (e: Exception) {
                _profileUpdateState.value = AuthState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun resetState() {
        _loginState.value = AuthState.Idle
        _registerState.value = AuthState.Idle
        _otpState.value = AuthState.Idle
        _forgotPasswordState.value = AuthState.Idle
        _resetPasswordState.value = AuthState.Idle
        _profileUpdateState.value = AuthState.Idle
    }

    fun logout() {
        viewModelScope.launch {
            userPreferences.clearAuthToken()
            resetState()
        }
    }

    fun deleteAccount(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.deleteProfile()
                if (response.isSuccessful) {
                    userPreferences.clearAuthToken()
                    resetState()
                    onSuccess()
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Failed to delete account", e)
            }
        }
    }


    class Factory(
        private val application: Application,
        private val userPreferences: UserPreferences
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(application, userPreferences) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
