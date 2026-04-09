package com.simats.orcare.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule

import androidx.compose.material.icons.rounded.*

data class Reminder(
    val id: Int,
    val title: String,
    val time: String,
    val iconName: String = "Schedule",
    val colorHex: String = "#9CA3AF", // Gray-400
    val isEnabled: Boolean = true
) {
    // Helper to get Color from Hex
    val color: Color
        get() = try {
            Color(android.graphics.Color.parseColor(colorHex))
        } catch (e: Exception) {
            Color.Gray
        }

    // Helper to get icon from name (Safer than reflection)
    val icon: ImageVector
        get() = when (iconName) {
            "Brush" -> Icons.Rounded.Brush
            "NightsStay" -> Icons.Rounded.NightsStay
            "CleaningServices" -> Icons.Rounded.CleaningServices
            "WaterDrop" -> Icons.Rounded.WaterDrop
            "Face" -> Icons.Rounded.Face
            "TouchApp" -> Icons.Rounded.TouchApp
            "LocalDrink" -> Icons.Rounded.LocalDrink
            "Restaurant" -> Icons.Rounded.Restaurant
            "Event" -> Icons.Rounded.Event
            else -> Icons.Rounded.Schedule
        }
}
