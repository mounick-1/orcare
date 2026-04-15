package com.simats.orcare.data.api

import com.simats.orcare.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/auth/verify-otp")
    suspend fun verifyOtp(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse>

    @POST("api/auth/resend-otp")
    suspend fun resendOtp(@Body request: Map<String, String>): Response<Map<String, String>>

    @POST("api/auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @POST("api/auth/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ResetPasswordResponse>

    @retrofit2.http.PUT("api/users/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<UpdateProfileResponse>

    @retrofit2.http.DELETE("api/users/profile")
    suspend fun deleteProfile(): Response<Map<String, Any>>

    @POST("api/auth/request-delete-otp")
    suspend fun requestDeleteOtp(@Body request: RequestDeleteOtpRequest): Response<RequestDeleteOtpResponse>

    @POST("api/auth/confirm-delete-account")
    suspend fun confirmDeleteAccount(@Body request: ConfirmDeleteAccountRequest): Response<ConfirmDeleteAccountResponse>


    @retrofit2.http.GET("api/content/diseases")
    suspend fun getDiseases(): Response<List<DiseaseDto>>

    @POST("api/chat/save")
    suspend fun saveChatHistory(@Body request: ChatHistoryRequest): Response<ChatHistoryResponse>

    @retrofit2.http.GET("api/chat/history")
    suspend fun getChatHistory(): Response<List<RemoteChatSession>>

    @retrofit2.http.GET("api/content/learning")
    suspend fun getLearning(): Response<List<LearningCategoryDto>>

    @POST("api/content/feedback")
    suspend fun submitFeedback(@Body request: Map<String, String>): Response<Map<String, Any>>

    @POST("api/chat/ask")
    suspend fun askChatbot(@Body request: Map<String, String>): Response<Map<String, Any>>
}
