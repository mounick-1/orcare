package com.simats.orcare.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderRepository(private val context: Context) {
    private val gson = Gson()

    companion object {
        val REMINDERS_KEY = stringPreferencesKey("reminders_list")
    }

    // Default reminders if none exist
    private val defaultReminders = listOf(
        Reminder(id = 1, title = "Morning Brushing", time = "07:00", isEnabled = true, iconName = "Brush", colorHex = "#3B82F6"), // Blue
        Reminder(id = 2, title = "Night Brushing", time = "22:00", isEnabled = true, iconName = "NightsStay", colorHex = "#1E293B"), // Slate
        Reminder(id = 3, title = "Flossing", time = "22:15", isEnabled = true, iconName = "CleaningServices", colorHex = "#10B981"), // Green
        Reminder(id = 4, title = "Mouthwash (Morning)", time = "07:30", isEnabled = true, iconName = "WaterDrop", colorHex = "#0EA5E9"), // Sky
        Reminder(id = 5, title = "Mouthwash (Evening)", time = "20:30", isEnabled = true, iconName = "WaterDrop", colorHex = "#6366F1"), // Indigo
        Reminder(id = 6, title = "Tongue Cleaning", time = "07:15", isEnabled = true, iconName = "Face", colorHex = "#F59E0B"), // Amber
        Reminder(id = 7, title = "Gum Massage", time = "07:20", isEnabled = true, iconName = "TouchApp", colorHex = "#EC4899"), // Pink
        Reminder(id = 8, title = "Water Intake", time = "13:00", isEnabled = true, iconName = "LocalDrink", colorHex = "#06B6D4"), // Cyan
        Reminder(id = 9, title = "Sugar-Free Rinse", time = "14:00", isEnabled = true, iconName = "Restaurant", colorHex = "#8B5CF6"), // Violet
        Reminder(id = 10, title = "Weekly Check-up", time = "10:00", isEnabled = true, iconName = "Event", colorHex = "#EF4444") // Red
    )

    val reminders: Flow<List<Reminder>> = context.dataStore.data
        .map { preferences ->
            val json = preferences[REMINDERS_KEY]
            if (json.isNullOrBlank()) {
                defaultReminders
            } else {
                try {
                    val type = object : TypeToken<List<Reminder>>() {}.type
                    gson.fromJson(json, type)
                } catch (_: Exception) {
                    defaultReminders
                }
            }
        }

    suspend fun updateReminder(updatedReminder: Reminder) {
        context.dataStore.edit { preferences ->
            val currentJson = preferences[REMINDERS_KEY]
            val currentList = if (currentJson.isNullOrBlank()) {
                defaultReminders
            } else {
                try {
                    val type = object : TypeToken<List<Reminder>>() {}.type
                    gson.fromJson(currentJson, type)
                } catch (_: Exception) {
                    defaultReminders
                }
            }.toMutableList()

            val index = currentList.indexOfFirst { it.id == updatedReminder.id }
            if (index != -1) {
                currentList[index] = updatedReminder
            } else {
                currentList.add(updatedReminder)
            }
            preferences[REMINDERS_KEY] = gson.toJson(currentList)
        }
    }
}
