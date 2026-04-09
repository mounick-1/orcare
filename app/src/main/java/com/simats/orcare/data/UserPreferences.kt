package com.simats.orcare.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UserPreferences(private val context: Context) {
    companion object {
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val COMPLETED_MODULES_KEY = stringPreferencesKey("completed_modules")
        
        // Profile Data
        val PROFILE_IMAGE_INDEX_KEY = androidx.datastore.preferences.core.intPreferencesKey("profile_image_index")
        val PROFILE_IMAGE_URI_KEY = stringPreferencesKey("profile_image_uri")
        val DISTRICT_KEY = stringPreferencesKey("district")
        val STATE_KEY = stringPreferencesKey("state")
        val ONBOARDING_COMPLETED_KEY = androidx.datastore.preferences.core.booleanPreferencesKey("onboarding_completed")
        
        // Auth Data
        val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        val SERVER_IP_KEY = stringPreferencesKey("server_ip")
    }

    private val gson = Gson()
    
    // Language Settings
    val language: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LANGUAGE_KEY] ?: "English"
        }

    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    // Profile Data Accessors
    val profileImageIndex: Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[PROFILE_IMAGE_INDEX_KEY] ?: 0 // Default index 0
        }

    suspend fun saveProfileImageIndex(index: Int) {
        context.dataStore.edit { preferences ->
            preferences[PROFILE_IMAGE_INDEX_KEY] = index
        }
    }

    val profileImageUri: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PROFILE_IMAGE_URI_KEY]
        }

    suspend fun saveProfileImageUri(uri: String?) {
        context.dataStore.edit { preferences ->
            if (uri != null) {
                preferences[PROFILE_IMAGE_URI_KEY] = uri
            } else {
                preferences.remove(PROFILE_IMAGE_URI_KEY)
            }
        }
    }

    val district: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[DISTRICT_KEY] ?: ""
        }

    suspend fun saveDistrict(district: String) {
        context.dataStore.edit { preferences ->
            preferences[DISTRICT_KEY] = district
        }
    }

    val state: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[STATE_KEY] ?: ""
        }

    suspend fun saveState(state: String) {
        context.dataStore.edit { preferences ->
            preferences[STATE_KEY] = state
        }
    }

    // Auth Token for Persistent Login
    val authToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }

    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    val userName: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_NAME_KEY] ?: "User"
        }

    suspend fun saveUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    val userEmail: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[USER_EMAIL_KEY] ?: ""
        }

    suspend fun saveUserEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = email
        }
    }

    
    suspend fun clearAuthToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
        }
    }

    val serverIp: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[SERVER_IP_KEY]
        }

    suspend fun saveServerIp(ip: String) {
        context.dataStore.edit { preferences ->
            preferences[SERVER_IP_KEY] = ip
        }
    }

    val onboardingCompleted: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] ?: false
        }

    suspend fun saveOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] = completed
        }
    }
}
