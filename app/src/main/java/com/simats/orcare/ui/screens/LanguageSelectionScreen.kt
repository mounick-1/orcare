package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.data.UserPreferences
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay

@Composable
fun LanguageSelectionScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPreferences = remember { UserPreferences(context) }
    val strings = LocalORCareStrings.current
    
    var selectedLanguage by remember { mutableStateOf<String?>(null) }
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showContent = true
    }

    val languages = listOf(
        LanguageOption("English", "English", "English", "🇺🇸"),
        LanguageOption("Tamil", "Tamil", "தமிழ்", "🇮🇳"),
        LanguageOption("Hindi", "Hindi", "हिन्दी", "🇮🇳"),
        LanguageOption("Telugu", "Telugu", "తెలుగు", "🇮🇳")
    )

    AnimatedAuthBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(1000)) + expandVertically(tween(1000, easing = EaseOut))
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ORCareLogo(scale = 1.0f, showText = true)
                        
                        Spacer(modifier = Modifier.height(40.dp))
                        
                        Text(
                            text = strings.chooseLanguage,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary,
                                letterSpacing = (-1).sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Select your preferred language to personalize your ORCare experience.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondary,
                                lineHeight = 22.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }

                
                Spacer(modifier = Modifier.height(48.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    itemsIndexed(languages) { index, language ->
                        var isVisible by remember { mutableStateOf(false) }
                        LaunchedEffect(Unit) {
                            delay(200L + (index * 100L))
                            isVisible = true
                        }
                        
                        AnimatedVisibility(
                            visible = isVisible,
                            enter = fadeIn(tween(600)) + slideInVertically(
                                initialOffsetY = { 30 },
                                animationSpec = tween(600, easing = EaseOut)
                            )
                        ) {
                            LanguageCard(
                                language = language,
                                isSelected = selectedLanguage == language.id,
                                onSelect = { selectedLanguage = language.id }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedVisibility(
                    visible = selectedLanguage != null,
                    enter = fadeIn() + slideInVertically { 20 },
                    exit = fadeOut() + slideOutVertically { 20 }
                ) {
                    ORCareButton(
                        text = strings.continueLabel,
                        onClick = {
                            selectedLanguage?.let {
                                scope.launch {
                                    userPreferences.saveLanguage(it)
                                    val token = userPreferences.authToken.first()
                                    val destination = if (token != null) "home" else "sign_in"
                                    
                                    navController.navigate(destination) {
                                        popUpTo("language_selection") { inclusive = true }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }
            }
            
            SIMATSFooter()
        }
    }
}

@Composable
fun LanguageCard(
    language: LanguageOption,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "Scale"
    )

    val elevation by animateDpAsState(
        targetValue = if (isSelected) 16.dp else 4.dp,
        label = "Elevation"
    )

    ORCareGlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .scale(scale)
            .clickable { onSelect() },
        shape = RoundedCornerShape(32.dp),
        border = if (isSelected) BorderStroke(2.dp, PrimaryOrange) else BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
        elevation = elevation
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .padding(8.dp)
                        .background(PrimaryOrange, CircleShape)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            if (isSelected) PrimaryOrange.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.5f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = language.flag,
                        fontSize = 32.sp
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = language.nativeName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isSelected) PrimaryOrange else TextPrimary,
                        letterSpacing = 0.5.sp
                    )
                )
                
                Text(
                    text = language.name,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = if (isSelected) PrimaryOrange.copy(alpha = 0.7f) else TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}


data class LanguageOption(
    val id: String,
    val name: String,
    val nativeName: String,
    val flag: String = "🏳️"
)
