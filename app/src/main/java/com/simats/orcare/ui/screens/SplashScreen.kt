package com.simats.orcare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.simats.orcare.ui.components.ORCareLogo
import com.simats.orcare.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.simats.orcare.ui.components.*

@Composable
fun SplashScreen(navController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val userPreferences = remember { com.simats.orcare.data.UserPreferences(context) }

    LaunchedEffect(Unit) {
        // Wait for branding to be seen clearly
        delay(1500) 
        
        // Move to language selection as the starting point of the flow
        navController.navigate("language_selection") {
            popUpTo("splash") { inclusive = true }
            launchSingleTop = true
        }
    }

    GradientBackground {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ORCareLogo(scale = 1.6f, showText = true)
        }
    }
}

