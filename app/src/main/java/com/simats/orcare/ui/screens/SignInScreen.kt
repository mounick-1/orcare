package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.simats.orcare.ui.components.*
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.viewmodel.AuthState
import com.simats.orcare.ui.viewmodel.AuthViewModel
import androidx.compose.ui.platform.LocalContext
import com.simats.orcare.data.UserPreferences
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

@Composable
fun SignInScreen(navController: NavController) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val strings = LocalORCareStrings.current
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(context.applicationContext as android.app.Application, UserPreferences(context))
    )
    var showContent by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
        if (userPreferences.authToken.first() != null) {
            navController.navigate("home") {
                popUpTo(0) { inclusive = true }
            }
        }
    }
    
    LaunchedEffect(loginState) {
        when (val state = loginState) {
            is AuthState.Success -> {
                navController.navigate("onboarding") { popUpTo(0) }
                viewModel.resetState()
            }
            is AuthState.Error -> {
                isError = true
                errorMessage = state.message
            }
            else -> {}
        }
    }
    
    var logoClickCount by remember { mutableStateOf(0) }
    var showServerDialog by remember { mutableStateOf(false) }
    var customIp by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val savedServerIp by userPreferences.serverIp.collectAsState(initial = "")
    
    LaunchedEffect(savedServerIp) {
        if (customIp.isEmpty()) customIp = savedServerIp ?: ""
    }

    if (showServerDialog) {
        androidx.compose.ui.window.Dialog(onDismissRequest = { showServerDialog = false }) {
            Surface(shape = RoundedCornerShape(28.dp), color = Color.White, tonalElevation = 8.dp) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Server Settings", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                    Spacer(modifier = Modifier.height(16.dp))
                    ORCareTextField(value = customIp, onValueChange = { customIp = it }, label = "Host IP or URL", imageVector = Icons.Default.Settings)
                    Spacer(modifier = Modifier.height(24.dp))
                    ORCareButton(text = "Save Configuration", onClick = {
                        scope.launch { userPreferences.saveServerIp(customIp); showServerDialog = false }
                    })
                }
            }
        }
    }

    AnimatedAuthBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(1000)) + slideInVertically(initialOffsetY = { 30 })
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ORCareLogo(
                            scale = 1.0f, 
                            showText = true,
                            modifier = Modifier.clickable(
                                interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
                                indication = null
                            ) { 
                                logoClickCount++
                                if (logoClickCount >= 3) showServerDialog = true
                            }
                        )

                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Text(
                            text = strings.welcomeBack, 
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary,
                                letterSpacing = (-1).sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = strings.signInToContinue, 
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondary,
                                letterSpacing = 0.2.sp
                            )
                        )
                        
                        Spacer(modifier = Modifier.height(48.dp))
                        
                        // Glassmorphic Form Card
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(32.dp),
                            color = Color.White.copy(alpha = 0.9f),
                            shadowElevation = 8.dp,
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(28.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                                ORCareTextField(
                                    value = email, 
                                    onValueChange = { email = it; isError = false }, 
                                    label = strings.email, 
                                    imageVector = Icons.Default.Email, 
                                    isError = isError
                                )
                                
                                Column {
                                    ORCareTextField(
                                        value = password, 
                                        onValueChange = { password = it; isError = false }, 
                                        label = strings.password, 
                                        imageVector = Icons.Default.Lock, 
                                        visualTransformation = PasswordVisualTransformation(), 
                                        isError = isError
                                    )
                                    
                                    Spacer(modifier = Modifier.height(12.dp))
                                    
                                    Text(
                                        text = strings.forgotPassword,
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            color = PrimaryOrange, 
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier
                                            .align(Alignment.End)
                                            .clickable { navController.navigate("forgot_password") }
                                            .padding(4.dp)
                                    )
                                }
                                
                                if (isError && errorMessage.isNotEmpty()) {
                                    Text(
                                        text = errorMessage, 
                                        color = MaterialTheme.colorScheme.error, 
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                ORCareButton(
                                    text = strings.login, 
                                    isLoading = loginState is AuthState.Loading, 
                                    onClick = {
                                        if (email.isBlank() || password.isBlank()) { 
                                            isError = true; errorMessage = "Please enter both email and password" 
                                        } else {
                                            viewModel.login(email, password)
                                        }
                                    }
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = strings.dontHaveAccount, 
                                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = strings.signUp, 
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = PrimaryOrange, 
                                    fontWeight = FontWeight.ExtraBold
                                ), 
                                modifier = Modifier.clickable { navController.navigate("sign_up") }
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
            
            SIMATSFooter()
        }
    }
}

