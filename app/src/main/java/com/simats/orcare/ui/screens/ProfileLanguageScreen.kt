package com.simats.orcare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simats.orcare.ui.viewmodel.AuthViewModel
import com.simats.orcare.ui.components.ORCareButton
import kotlinx.coroutines.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLanguageScreen(navController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(context.applicationContext as android.app.Application, com.simats.orcare.data.UserPreferences(context))
    )
    val scope = rememberCoroutineScope()
    val userPreferences = remember { com.simats.orcare.data.UserPreferences(context) }
    val strings = LocalORCareStrings.current
    
    var selectedLanguage by remember { mutableStateOf("English") }

    data class LanguageItem(val code: String, val name: String, val native: String)
    val languages = listOf(
        LanguageItem("English", "English", "English"),
        LanguageItem("Tamil", "Tamil", "தமிழ்"),
        LanguageItem("Telugu", "Telugu", "తెలుగు"),
        LanguageItem("Hindi", "Hindi", "हिन्दी")
    )

    LaunchedEffect(Unit) {
        userPreferences.language.collect { selectedLanguage = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = strings.language,
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
                            contentDescription = strings.skip,
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            languages.forEach { lang ->
                LanguageOptionCard(
                    language = lang.name,
                    nativeName = lang.native,
                    isSelected = selectedLanguage == lang.code,
                    onClick = { selectedLanguage = lang.code }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            ORCareButton(
                text = strings.saveChanges,
                onClick = {
                    scope.launch {
                        authViewModel.updateProfile(language = selectedLanguage)
                        userPreferences.saveLanguage(selectedLanguage)
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}
}

@Composable
fun LanguageOptionCard(
    language: String,
    nativeName: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) PrimaryCoral.copy(alpha = 0.05f) else SurfaceWhite
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) PrimaryCoral else SunsetPeach
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = language,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) PrimaryCoral else TextPrimary
                    )
                )
                if (nativeName != language) {
                    Text(
                        text = nativeName,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (isSelected) PrimaryCoral.copy(alpha = 0.7f) else TextSecondary
                        )
                    )
                }
            }
            
            if (isSelected) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = PrimaryCoral,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
