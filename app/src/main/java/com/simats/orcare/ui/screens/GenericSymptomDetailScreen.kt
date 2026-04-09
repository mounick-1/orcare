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
import androidx.compose.material.icons.filled.Check
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
import com.simats.orcare.data.SymptomRepository
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericSymptomDetailScreen(navController: NavController, symptomName: String?) {
    val symptomDetail = symptomName?.let { SymptomRepository.symptomDetailsMap[it.lowercase()] }
    val title = symptomName ?: "Symptom Detail"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = TextPrimary)) },
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
            if (symptomDetail != null) {
                Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                
                // Icon
                Surface(modifier = Modifier.size(100.dp), shape = CircleShape, color = PrimaryCoral.copy(alpha=0.1f)) {
                    Box(contentAlignment = Alignment.Center) { Text(symptomDetail.icon, fontSize = 50.sp) }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // 1. What is happening?
                HardenedSymptomCard(title = "What is happening?", content = symptomDetail.whatIsHappening)
                Spacer(modifier = Modifier.height(16.dp))

                // 2. What people notice
                HardenedSymptomCard(title = "What people notice", content = symptomDetail.whatPeopleNotice)
                Spacer(modifier = Modifier.height(16.dp))
                
                // 3. Possible reasons
                HardenedSymptomCard(title = "Possible reasons") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        symptomDetail.possibleReasons.forEach { reason ->
                            Row(verticalAlignment = Alignment.Top) {
                                Text("•", style = MaterialTheme.typography.bodyLarge.copy(color = PrimaryCoral, fontWeight = FontWeight.Bold))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(reason, style = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // 4. What you can do (Self-care)
                HardenedSymptomCard(title = "What can you do?") {
                     Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        symptomDetail.whatToDo.forEach { item ->
                             Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = PrimaryCoral, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(item, style = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary, fontWeight = FontWeight.SemiBold))
                             }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                
                // 5. When to see a dentist (Warning)
                Card(
                    modifier = Modifier.fillMaxWidth(), 
                    shape = RoundedCornerShape(20.dp), 
                    colors = CardDefaults.cardColors(containerColor = ErrorRed.copy(alpha = 0.1f)), 
                    border = BorderStroke(1.dp, ErrorRed.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "When to see a dentist",
                            style = MaterialTheme.typography.labelLarge.copy(color = ErrorRed, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = symptomDetail.whenToSeeDentist, 
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = ErrorRed, 
                                fontWeight = FontWeight.Medium, 
                                lineHeight = 22.sp
                            ), 
                            textAlign = TextAlign.Center
                        )
                    }
                }
                
                // Ask AI Button
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp)) {
                    ORCareButton(
                        text = "Ask AI about this",
                        onClick = { navController.navigate("chatbot/$title") },
                        modifier = Modifier.fillMaxWidth().height(56.dp)
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        } else {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                 Text("Symptom details not found.")
             }
        }
        }
    }
}
