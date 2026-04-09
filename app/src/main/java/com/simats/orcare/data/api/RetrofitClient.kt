package com.simats.orcare.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // ✅ Your backend URL (MAKE SURE THIS MATCHES YOUR SERVER)
    private const val BASE_URL = "http://192.168.1.5:3000/"

    private var authToken: String? = null

    @Volatile
    private var instance: ApiService? = null

    // ✅ Set token if needed
    fun setToken(token: String?) {
        authToken = token
        instance = null
    }

    // ✅ Logging (for debugging API calls)
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // ✅ HTTP Client
    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                authToken?.let { token ->
                    if (token.isNotBlank()) {
                        requestBuilder.addHeader("Authorization", "Bearer $token")
                    }
                }

                chain.proceed(requestBuilder.build())
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // ✅ Get API instance
    fun getInstance(): ApiService {
        return instance ?: synchronized(this) {
            instance ?: buildApi().also { instance = it }
        }
    }

    // ✅ Retrofit Builder
    private fun buildApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL) // 🔥 FIXED (NO dynamic logic)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createHttpClient())
            .build()
            .create(ApiService::class.java)
    }
}