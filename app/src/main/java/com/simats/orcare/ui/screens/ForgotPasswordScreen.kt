package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.simats.orcare.data.UserPreferences
import com.simats.orcare.ui.viewmodel.AuthState
import com.simats.orcare.ui.viewmodel.AuthViewModel
import com.simats.orcare.ui.components.*
import com.simats.orcare.ui.theme.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    val context = LocalContext.current
    val strings = LocalORCareStrings.current
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(context.applicationContext as android.app.Application, UserPreferences(context))
    )
    
    var email by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var step by remember { mutableIntStateOf(1) } // 1: Email, 2: OTP & New PW
    
    var showContent by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    val forgotPasswordState by viewModel.forgotPasswordState.collectAsState()
    val resetPasswordState by viewModel.resetPasswordState.collectAsState()

    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
    }

    LaunchedEffect(forgotPasswordState) {
        val state = forgotPasswordState
        if (state is AuthState.Success) {
            step = 2
            viewModel.resetState()
        } else if (state is AuthState.Error) {
            isError = true
            errorMessage = state.message
        }
    }

    LaunchedEffect(resetPasswordState) {
        val state = resetPasswordState
        if (state is AuthState.Success) {
            navController.popBackStack()
            viewModel.resetState()
        } else if (state is AuthState.Error) {
            isError = true
            errorMessage = state.message
        }
    }

    AnimatedAuthBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { if (step == 2) step = 1 else navController.popBackStack() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, 
                        contentDescription = "Back", 
                        tint = TextPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(horizontal = 28.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(1000)) + slideInVertically(initialOffsetY = { 30 })
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            modifier = Modifier.size(100.dp),
                            shape = CircleShape,
                            color = PrimaryOrange.copy(alpha = 0.1f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (step == 1) Icons.Default.Email else Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = PrimaryOrange,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Text(
                            text = if (step == 1) strings.forgotPassword else "New Credentials", 
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary,
                                letterSpacing = (-1).sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (step == 1) strings.enterEmailToReset else "Verify your identity and set a new password", 
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondary,
                                letterSpacing = 0.2.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(48.dp))
                        
                        // Premium Reset Card
                        Surface(
                            modifier = Modifier.fillMaxWidth().animateContentSize(),
                            shape = RoundedCornerShape(32.dp),
                            color = Color.White.copy(alpha = 0.9f),
                            shadowElevation = 8.dp,
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(28.dp), verticalArrangement = Arrangement.spacedBy(20.dp)) {
                                if (step == 1) {
                                    ORCareTextField(
                                        value = email, 
                                        onValueChange = { email = it; isError = false }, 
                                        label = strings.email, 
                                        imageVector = Icons.Default.Email, 
                                        isError = isError
                                    )
                                    
                                    ORCareButton(
                                        text = strings.sendOtp, 
                                        isLoading = forgotPasswordState is AuthState.Loading, 
                                        onClick = {
                                            if (email.isBlank()) { isError = true; errorMessage = "Email is required" }
                                            else viewModel.forgotPassword(email)
                                        }
                                    )
                                } else {
                                    ORCareTextField(
                                        value = otpCode, 
                                        onValueChange = { if (it.length <= 6) otpCode = it; isError = false }, 
                                        label = "OTP Code", 
                                        imageVector = Icons.Default.Lock, 
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        isError = isError
                                    )
                                    
                                    ORCareTextField(
                                        value = newPassword, 
                                        onValueChange = { newPassword = it; isError = false }, 
                                        label = "New Password", 
                                        imageVector = Icons.Default.Lock, 
                                        visualTransformation = PasswordVisualTransformation(), 
                                        isError = isError
                                    )
                                    
                                    ORCareButton(
                                        text = "Reset Password", 
                                        isLoading = resetPasswordState is AuthState.Loading, 
                                        onClick = {
                                            if (otpCode.length != 6) { isError = true; errorMessage = "Enter valid 6-digit OTP" }
                                            else if (newPassword.isBlank()) { isError = true; errorMessage = "Password required" }
                                            else viewModel.resetPassword(email, otpCode, newPassword)
                                        }
                                    )
                                }
                                
                                if (isError) {
                                    Text(
                                        text = errorMessage, 
                                        color = MaterialTheme.colorScheme.error, 
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        if (step == 1) {
                            Text(
                                text = strings.backToLogin, 
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = PrimaryOrange, 
                                    fontWeight = FontWeight.ExtraBold
                                ), 
                                modifier = Modifier
                                    .clickable { navController.popBackStack() }
                                    .padding(8.dp)
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
