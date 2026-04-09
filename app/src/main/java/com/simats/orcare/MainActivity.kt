package com.simats.orcare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.simats.orcare.data.UserPreferences
import com.simats.orcare.navigation.AppNavigation
import com.simats.orcare.ui.components.ORCareBottomNavigation
import com.simats.orcare.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // enableEdgeToEdge must be called before setContent
        enableEdgeToEdge()
        
        setContent {
            ORCareTheme {
                // Root surface should match the theme's background
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ORCareApp()
                }
            }
        }
    }
}

@Composable
fun ORCareApp() {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val currentLanguage by userPreferences.language.collectAsState(initial = "English")
    val savedServerIp by userPreferences.serverIp.collectAsState(initial = null)
    val authToken by userPreferences.authToken.collectAsState(initial = null)

    // Sync Auth Token to RetrofitClient
    LaunchedEffect(authToken) {
        com.simats.orcare.data.api.RetrofitClient.setToken(authToken)
    }

    val strings: ORCareStrings = when (currentLanguage) {
        "Tamil" -> TamilStrings()
        "Telugu" -> TeluguStrings()
        "Hindi" -> HindiStrings()
        else -> EnglishStrings()
    }

    CompositionLocalProvider(LocalORCareStrings provides strings) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        
        val bottomBarRoutes = listOf("home", "learning_center", "oral_disease", "profile")
        val showBottomBar = currentRoute in bottomBarRoutes

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AppNavigation(
                navController = navController, 
                modifier = Modifier.fillMaxSize()
            )
            
            if (showBottomBar) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    ORCareBottomNavigation(navController = navController)
                }
            }
        }
    }
}
