package com.simats.orcare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacySecurityScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Privacy & Security",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        GradientBackground {
            Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header Shield
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = CircleShape,
                    color = PrimaryCoral.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Rounded.Shield,
                            contentDescription = null,
                            tint = PrimaryCoral,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            Text(
                text = "Your data is secure with us",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            PrivacySection(
                title = "Data Encryption",
                content = "All your personal health data is encrypted end-to-end using industry-standard AES-256 encryption. We ensure that your information is accessible only to you."
            )

            PrivacySection(
                title = "Information Collection",
                content = "We only collect data necessary to provide you with personalized dental health insights. This includes symptom checks, reminder preferences, and basic profile information."
            )

            PrivacySection(
                title = "Third-Party Sharing",
                content = "We do not sell your personal data to third parties. Your data is used solely for improving the ORCare experience and providing medical recommendations."
            )

            PrivacySection(
                title = "Account Deletion",
                content = "You have the right to request the deletion of your account and all associated data at any time."
            )

            OutlinedButton(
                onClick = { navController.navigate("privacy_policy") },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryCoral)
            ) {
                Text(text = "View Full Privacy Policy", color = PrimaryCoral, fontWeight = FontWeight.Bold)
            }

            val context = androidx.compose.ui.platform.LocalContext.current
            val userPreferences = androidx.compose.runtime.remember { com.simats.orcare.data.UserPreferences(context) }
            val viewModel: com.simats.orcare.ui.viewmodel.AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                factory = com.simats.orcare.ui.viewmodel.AuthViewModel.Factory(context.applicationContext as android.app.Application, userPreferences)
            )

            Button(
                onClick = { 
                    viewModel.deleteAccount {
                        navController.navigate("sign_in") {
                            popUpTo(0)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.1f), contentColor = Color.Red),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
            ) {
                Text(text = "Delete Account Permanently", fontWeight = FontWeight.Bold)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
}

@Composable
fun PrivacySection(title: String, content: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Rounded.Lock,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
        }
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondary,
                lineHeight = 22.sp
            ),
            modifier = Modifier.padding(start = 24.dp)
        )
    }
}
