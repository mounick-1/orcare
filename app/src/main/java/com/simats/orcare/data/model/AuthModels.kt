package com.simats.orcare.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String?,
    val token: String,
    val user: UserDto,
    val error: Boolean = false
)

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val age: Int,
    val gender: String,
    val language: String? = "English",
)


data class RegisterResponse(
    val message: String?,
    val user: UserDto?,
    val error: Boolean = false
)

data class UserDto(
    @SerializedName("_id") val id: String,
    val name: String,
    val email: String,
    val age: Int? = null,
    val gender: String? = null,
    val district: String? = null,
    val state: String? = null,
    @SerializedName("profileImageUri") val profileImage: String? = null, // URL or Base64
    val language: String? = "English",
    val profileImageIndex: Int? = 0,
    val isEmailVerified: Boolean = false,
)

data class VerifyOtpRequest(
    val email: String,
    val otp: String,
    val type: String // e.g., "registration" or "password_reset"
)

data class VerifyOtpResponse(
    val message: String?,
    val token: String? = null,
    val error: Boolean = false
)

data class ForgotPasswordRequest(
    val email: String
)

data class ForgotPasswordResponse(
    val message: String?,
    val error: Boolean = false
)

data class ResetPasswordRequest(
    val email: String,
    val otp: String,
    val newPassword: String
)

data class ResetPasswordResponse(
    val message: String?,
    val error: Boolean = false
)

data class UpdateProfileRequest(
    val userId: String? = null,
    val name: String? = null,
    val email: String? = null,
    val district: String? = null,
    val state: String? = null,
    @SerializedName("profileImageUri") val profileImage: String? = null,
    val profileImageIndex: Int? = null,
    val language: String? = null
)

data class UpdateProfileResponse(
    val message: String?,
    val user: UserDto?,
    val success: Boolean
)

