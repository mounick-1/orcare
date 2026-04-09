package com.simats.orcare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.simats.orcare.ui.theme.TextPrimary
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.simats.orcare.ui.components.*

@Composable
fun TipCard(index: Int) {
    val tips = listOf(
        Triple("Brush twice a day", Icons.Rounded.Brush, Color(0xFF60A5FA)), // Blue
        Triple("Floss daily", Icons.Rounded.CleaningServices, Color(0xFF34D399)), // Green
        Triple("Limit sugary foods", Icons.Rounded.NoFood, Color(0xFFF87171)), // Red
        Triple("Visit dentist regularly", Icons.Rounded.MedicalServices, Color(0xFF818CF8)), // Indigo
        Triple("Use fluoride toothpaste", Icons.Rounded.Science, Color(0xFFF472B6)), // Pink
        Triple("Change toothbrush often", Icons.Rounded.Refresh, Color(0xFFFBBF24)), // Amber
        Triple("Drink more water", Icons.Rounded.WaterDrop, Color(0xFF60A5FA)), // Blue
        Triple("Don't smoke", Icons.Rounded.SmokeFree, Color(0xFF9CA3AF)) // Gray
    )

    val (text, icon, color) = tips.getOrElse(index) { Triple("Tip #${index + 1}", Icons.Rounded.Lightbulb, Color.Gray) }
    
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        modifier = Modifier.width(280.dp).height(100.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                 modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.2f), CircleShape),
                 contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
