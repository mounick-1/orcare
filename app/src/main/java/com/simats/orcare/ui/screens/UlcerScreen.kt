package com.simats.orcare.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UlcerScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Ulcer", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = SlateDark)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = SurfaceWhite)
            )
        },

        containerColor = SurfaceWhite
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Surface(modifier = Modifier.size(120.dp), shape = CircleShape, color = Color(0xFFE0F2FE)) {
                Box(contentAlignment = Alignment.Center) { Text("😷", fontSize = 60.sp) }
            }
            Spacer(modifier = Modifier.height(32.dp))
            HardenedSymptomCard(title = "Possible Reason", content = "Mouth ulcers can be caused by minor injuries, stress, vitamin deficiency, or certain foods.")
            Spacer(modifier = Modifier.height(16.dp))
            HardenedSymptomCard(title = "Do This at Home") {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    HardenedStepItem(1, "Rinse with salt water 3 times daily")
                    HardenedStepItem(2, "Apply honey on the ulcer")
                    HardenedStepItem(3, "Avoid touching or irritating the area")
                    HardenedStepItem(4, "Use soft-bristled brush around ulcer")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HardenedSymptomCard(title = "Avoid These Foods") {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    HardenedFoodChip("Spicy and acidic foods")
                    HardenedFoodChip("Salty snacks")
                    HardenedFoodChip("Hard and crunchy foods")
                    HardenedFoodChip("Hot beverages")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2)), border = BorderStroke(1.dp, Color(0xFFFECACA))) {
                Text("See dentist if ulcer lasts for more than 2 weeks or is very large and painful.", modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFEF4444), fontWeight = FontWeight.Bold, lineHeight = 22.sp), textAlign = TextAlign.Center)
            }
            // Ask AI Button (Moved from Bottom Bar)
            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                Button(
                    onClick = { navController.navigate("chatbot/Ulcer") },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Icon(Icons.AutoMirrored.Rounded.Chat, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Ask AI about this symptom", fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}
