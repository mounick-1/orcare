package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.simats.orcare.data.UserPreferences
import com.simats.orcare.ui.components.*
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.viewmodel.AuthState
import com.simats.orcare.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val strings = LocalORCareStrings.current
    val userPreferences = remember { UserPreferences(context) }
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(context.applicationContext as android.app.Application, userPreferences)
    )
    
    val scope = rememberCoroutineScope()
    val profileUpdateState by viewModel.profileUpdateState.collectAsState()
    
    // Form State
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var stateName by remember { mutableStateOf("") }
    var selectedImageIndex by remember { mutableIntStateOf(0) }
    
    val profileImages = listOf(
        Icons.Rounded.Person,
        Icons.Rounded.Face,
        Icons.Rounded.AccountCircle,
        Icons.Rounded.EmojiPeople,
        Icons.Rounded.SentimentSatisfied
    )

    // Load existing data
    LaunchedEffect(Unit) {
        scope.launch {
            userPreferences.userName.collect { name = it }
        }
        scope.launch {
            userPreferences.userEmail.collect { email = it }
        }
        scope.launch {
            userPreferences.district.collect { district = it }
        }
        scope.launch {
            userPreferences.state.collect { stateName = it }
        }
        scope.launch {
            userPreferences.profileImageIndex.collect { selectedImageIndex = it }
        }
    }

    LaunchedEffect(profileUpdateState) {
        if (profileUpdateState is AuthState.Success) {
            navController.popBackStack()
            viewModel.resetState()
        }
    }

    GradientBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(strings.profile, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                
                // Profile Image Selection
                Box(contentAlignment = Alignment.BottomEnd) {
                    Surface(
                        modifier = Modifier.size(120.dp),
                        shape = CircleShape,
                        color = SurfaceWhite,
                        shadowElevation = 8.dp,
                        border = BorderStroke(2.dp, PrimaryOrange)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = profileImages[selectedImageIndex],
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = PrimaryOrange
                            )
                        }
                    }
                    
                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = CircleShape,
                        color = PrimaryOrange,
                        shadowElevation = 4.dp
                    ) {
                        IconButton(onClick = { 
                            selectedImageIndex = (selectedImageIndex + 1) % profileImages.size
                        }) {
                            Icon(Icons.Default.Refresh, null, tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(40.dp))
                
                ORCareCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        ORCareTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = strings.fullName,
                            imageVector = Icons.Default.Person
                        )
                        
                        ORCareTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = strings.email,
                            imageVector = Icons.Default.Email,
                            enabled = false // Usually email shouldn't be edited easily
                        )
                        
                        ORCareTextField(
                            value = district,
                            onValueChange = { district = it },
                            label = "District",
                            imageVector = Icons.Default.LocationOn
                        )
                        
                        ORCareTextField(
                            value = stateName,
                            onValueChange = { stateName = it },
                            label = "State",
                            imageVector = Icons.Default.Map
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(40.dp))
                
                ORCareButton(
                    text = strings.saveChanges,
                    isLoading = profileUpdateState is AuthState.Loading,
                    onClick = {
                        viewModel.updateProfile(
                            name = name,
                            district = district,
                            state = stateName,
                            profileImageIndex = selectedImageIndex
                        )
                    }
                )
                
                if (profileUpdateState is AuthState.Error) {
                    Text(
                        text = (profileUpdateState as AuthState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
