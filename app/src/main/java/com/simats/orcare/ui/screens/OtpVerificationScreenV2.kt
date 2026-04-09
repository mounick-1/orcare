package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.ui.viewmodel.AuthState
import com.simats.orcare.ui.viewmodel.AuthViewModel
import com.simats.orcare.ui.components.*
import com.simats.orcare.ui.theme.*
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreenV2(
    navController: NavController,
    email: String,
    viewModel: AuthViewModel
) {
    val strings = LocalORCareStrings.current
    var emailOtp by remember { mutableStateOf("") }
    val otpState by viewModel.otpState.collectAsState()
    var isSuccess by remember { mutableStateOf(false) }
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
    }

    LaunchedEffect(otpState) {
        val state = otpState
        if (state is AuthState.Success) {
            isSuccess = true
            delay(1500)
            navController.navigate("onboarding") {
                popUpTo(0) { inclusive = true }
            }
            viewModel.resetState()
        }
    }

    androidx.activity.compose.BackHandler {
        navController.popBackStack()
    }

    AnimatedAuthBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
                Text(
                    text = strings.verification,
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
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(400)) + slideInVertically(initialOffsetY = { 30 })
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ORCareGlassCard(
                            modifier = Modifier.size(100.dp),
                            shape = CircleShape,
                            elevation = 4.dp
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                Icon(
                                    imageVector = if (isSuccess) Icons.Rounded.CheckCircle else Icons.Rounded.VpnKey,
                                    contentDescription = null,
                                    tint = if (isSuccess) SuccessGreen else PrimaryOrange,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Text(
                            text = if (isSuccess) strings.verified else strings.otpVerification,
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary,
                                letterSpacing = (-1).sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (isSuccess) 
                                "Your identity has been confirmed. Redirecting..." 
                            else 
                                "We've sent a 6-digit code to\n$email",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondary,
                                letterSpacing = 0.2.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(48.dp))
                        
                        if (!isSuccess) {
                            ORCareGlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(32.dp),
                                elevation = 12.dp
                            ) {
                                Column(
                                    modifier = Modifier.padding(28.dp),
                                    verticalArrangement = Arrangement.spacedBy(24.dp)
                                ) {
                                    ORCareTextField(
                                        value = emailOtp,
                                        onValueChange = { 
                                            if (it.length <= 6) emailOtp = it 
                                        },
                                        label = "Verification Code",
                                        imageVector = Icons.Rounded.Dialpad,
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Number
                                        ),
                                        isError = otpState is AuthState.Error
                                    )
                                    
                                    if (otpState is AuthState.Error) {
                                        Text(
                                            text = (otpState as AuthState.Error).message,
                                            color = ErrorRed,
                                            style = MaterialTheme.typography.bodySmall,
                                            modifier = Modifier.padding(horizontal = 4.dp)
                                        )
                                    }
                                    
                                    ORCareButton(
                                        text = "Verify Code",
                                        isLoading = otpState is AuthState.Loading,
                                        enabled = emailOtp.length == 6,
                                        onClick = {
                                            viewModel.verifyOtp(email, emailOtp, "email")
                                        },
                                        modifier = Modifier.fillMaxWidth()
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
                                    text = "Didn't receive the code?",
                                    style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Resend",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = PrimaryOrange,
                                        fontWeight = FontWeight.ExtraBold
                                    ),
                                    modifier = Modifier.clickable { 
                                        viewModel.resendOtp(email, "email")
                                    }
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                        
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}
