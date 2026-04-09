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
fun BleedingGumsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Bleeding gums", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = TextPrimary)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        GradientBackground {
            Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Surface(modifier = Modifier.size(120.dp), shape = CircleShape, color = PrimaryCoral.copy(alpha = 0.1f)) {
                Box(contentAlignment = Alignment.Center) { Text("💉", fontSize = 60.sp) }
            }
            Spacer(modifier = Modifier.height(32.dp))
            HardenedSymptomCard(title = "Possible Reason", content = "Bleeding gums often indicate gum disease, improper brushing technique, or vitamin deficiency.")
            Spacer(modifier = Modifier.height(16.dp))
            HardenedSymptomCard(title = "Do This at Home") {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    HardenedStepItem(1, "Brush gently with soft-bristled toothbrush")
                    HardenedStepItem(2, "Use antibacterial mouthwash")
                    HardenedStepItem(3, "Floss carefully between teeth")
                    HardenedStepItem(4, "Apply gauze with gentle pressure if bleeding")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HardenedSymptomCard(title = "Avoid These Foods") {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    HardenedFoodChip("Hard brushing")
                    HardenedFoodChip("Smoking or tobacco")
                    HardenedFoodChip("Alcohol-based mouthwash")
                    HardenedFoodChip("Spicy foods")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.1f)), border = BorderStroke(1.dp, ErrorRed.copy(alpha = 0.3f))) {
                Text("Visit dentist if bleeding continues for more than a week or gums are very swollen.", modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.bodyMedium.copy(color = ErrorRed, fontWeight = FontWeight.Bold, lineHeight = 22.sp), textAlign = TextAlign.Center)
            }
            Spacer(modifier = Modifier.height(40.dp))

            // Ask AI Button (Moved from Bottom Bar)
            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
                ORCareButton(
                    text = "Ask AI about this symptom",
                    onClick = { navController.navigate("chatbot/BleedingGums") },
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                )
            }
            // Add extra space for global nav bar
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
}
