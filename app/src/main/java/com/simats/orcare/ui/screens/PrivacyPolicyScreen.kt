package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
    }

    AnimatedAuthBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
                
                Text(
                    text = "Privacy Policy",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary,
                        letterSpacing = (-0.5).sp
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(28.dp)
            ) {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(800)) + slideInVertically(initialOffsetY = { 20 })
                ) {
                    Column {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(32.dp),
                            color = Color.White.copy(alpha = 0.9f),
                            shadowElevation = 4.dp,
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text(
                                    text = "ORCare Privacy",
                                    style = MaterialTheme.typography.headlineSmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = PrimaryOrange
                                    )
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Last updated: March 2025",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = TextMuted,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Welcome to ORCare. Your privacy is our priority. We are committed to protecting your data while providing world-class healthcare support.",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextSecondary,
                                        lineHeight = 22.sp,
                                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                            PolicyCard(
                                title = "1. Information We Collect",
                                icon = Icons.Rounded.Info
                            ) {
                                PolicySubSection(
                                    "1.1 Personal Information",
                                    listOf("Name & Contact", "Phone number (OTP)", "Email address", "User role", "Location")
                                )
                                PolicySubSection(
                                    "1.2 Health Data",
                                    listOf("Appointment history", "Medical usage", "Selected services", "Booking details")
                                )
                            }

                            PolicyCard(
                                title = "2. Usage & Security",
                                icon = Icons.Rounded.Security
                            ) {
                                PolicySectionContent("We use your data to manage appointments, connect you with nearby services, and improve app performance. All data is transferred securely via HTTPS and stored with restricted access.")
                            }

                            PolicyCard(
                                title = "3. Your Rights",
                                icon = Icons.Rounded.AdminPanelSettings
                            ) {
                                PolicySectionContent("You have the right to access, update, and request deletion of your data at any time via the Account Deletion screen or by contacting our support team.")
                            }

                            PolicyCard(
                                title = "4. Contact Us",
                                icon = Icons.Rounded.Email
                            ) {
                                PolicySectionContent("Have questions? Reach out to us at support@orcare.com for any privacy-related concerns.")
                            }
                        }

                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
            
            SIMATSFooter()
        }
    }
}

@Composable
fun PolicyCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = Color.White.copy(alpha = 0.8f),
        border = BorderStroke(1.dp, GrayBorder.copy(alpha = 0.2f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = CircleShape,
                    color = PrimaryOrange.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, null, tint = PrimaryOrange, modifier = Modifier.size(20.dp))
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun PolicySectionContent(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = TextSecondary,
            lineHeight = 22.sp
        )
    )
}

@Composable
fun PolicySubSection(title: String, items: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        )
        items.forEach { item ->
            Row(modifier = Modifier.padding(start = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(4.dp).background(AccentMint, CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextSecondary
                    )
                )
            }
        }
    }
}
