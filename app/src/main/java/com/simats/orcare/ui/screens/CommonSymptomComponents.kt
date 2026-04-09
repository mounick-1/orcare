package com.simats.orcare.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.orcare.ui.theme.*
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import com.simats.orcare.ui.components.*

@Composable
fun HardenedSymptomCard(title: String, content: String) {
    HardenedSymptomCard(title) { Text(content, style = MaterialTheme.typography.bodyLarge.copy(color = TextSecondary, lineHeight = 24.sp)) }
}

@Composable
fun HardenedSymptomCard(title: String, content: @Composable () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = PrimaryCoral.copy(alpha = 0.05f))) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = TextPrimary))
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun HardenedStepItem(number: Int, text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Surface(modifier = Modifier.size(24.dp), shape = CircleShape, color = PrimaryCoral) {
            Box(contentAlignment = Alignment.Center) { Text(number.toString(), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold) }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary, lineHeight = 20.sp))
    }
}

@Composable
fun HardenedFoodChip(text: String) {
    Surface(shape = RoundedCornerShape(12.dp), color = Color.White, border = BorderStroke(1.dp, PrimaryCoral.copy(alpha = 0.3f))) {
        Text(text, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), style = MaterialTheme.typography.bodySmall.copy(color = PrimaryCoral, fontWeight = FontWeight.Bold))
    }
}
