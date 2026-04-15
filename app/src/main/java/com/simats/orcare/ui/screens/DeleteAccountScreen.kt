package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.simats.orcare.data.UserPreferences
import com.simats.orcare.ui.components.*
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.viewmodel.AuthState
import com.simats.orcare.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

// Steps in the delete flow
private enum class DeleteStep { CREDENTIALS, OTP, SUCCESS }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAccountScreen(navController: NavController) {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(
            context.applicationContext as android.app.Application,
            userPreferences
        )
    )

    var step by remember { mutableStateOf(DeleteStep.CREDENTIALS) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var otp by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showContent by remember { mutableStateOf(false) }

    val deleteOtpState by viewModel.deleteOtpState.collectAsState()
    val deleteConfirmState by viewModel.deleteConfirmState.collectAsState()

    // OTP request response
    LaunchedEffect(deleteOtpState) {
        when (val s = deleteOtpState) {
            is AuthState.Success -> {
                errorMessage = null
                step = DeleteStep.OTP
            }
            is AuthState.Error -> errorMessage = s.message
            else -> {}
        }
    }

    // Delete confirm response
    LaunchedEffect(deleteConfirmState) {
        when (val s = deleteConfirmState) {
            is AuthState.Success -> {
                errorMessage = null
                step = DeleteStep.SUCCESS
            }
            is AuthState.Error -> errorMessage = s.message
            else -> {}
        }
    }

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
            // Top bar
            Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                if (step != DeleteStep.SUCCESS) {
                    IconButton(onClick = {
                        if (step == DeleteStep.OTP) step = DeleteStep.CREDENTIALS
                        else navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                }
                Text(
                    text = when (step) {
                        DeleteStep.CREDENTIALS -> "Delete Account"
                        DeleteStep.OTP -> "Verify Identity"
                        DeleteStep.SUCCESS -> "Account Deleted"
                    },
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary,
                        letterSpacing = (-0.5).sp
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn(tween(600)) + slideInVertically(initialOffsetY = { 30 })
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Icon
                    Surface(
                        modifier = Modifier.size(88.dp),
                        shape = CircleShape,
                        color = if (step == DeleteStep.SUCCESS) Color(0xFF43A047).copy(alpha = 0.1f)
                                else ErrorRed.copy(alpha = 0.1f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = when (step) {
                                    DeleteStep.SUCCESS -> Icons.Rounded.CheckCircle
                                    DeleteStep.OTP -> Icons.Rounded.MarkEmailRead
                                    else -> Icons.Rounded.PersonRemove
                                },
                                contentDescription = null,
                                tint = if (step == DeleteStep.SUCCESS) Color(0xFF43A047) else ErrorRed,
                                modifier = Modifier.size(44.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = when (step) {
                            DeleteStep.CREDENTIALS -> "Close Your Account"
                            DeleteStep.OTP -> "Check Your Email"
                            DeleteStep.SUCCESS -> "Account Deleted"
                        },
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary,
                            letterSpacing = (-0.5).sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (step) {
                            DeleteStep.CREDENTIALS ->
                                "Enter your account email and password to confirm your identity before deletion."
                            DeleteStep.OTP ->
                                "A 6-digit verification code has been sent to $email. Enter it below to permanently delete your account."
                            DeleteStep.SUCCESS ->
                                "Your account and all associated data have been permanently removed from our servers."
                        },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondary,
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Error banner
                    AnimatedVisibility(visible = errorMessage != null) {
                        Surface(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            shape = RoundedCornerShape(14.dp),
                            color = ErrorRed.copy(alpha = 0.1f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Rounded.ErrorOutline, null, tint = ErrorRed, modifier = Modifier.size(18.dp))
                                Text(
                                    errorMessage ?: "",
                                    style = MaterialTheme.typography.bodySmall.copy(color = ErrorRed),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }

                    // Step: Credentials
                    if (step == DeleteStep.CREDENTIALS) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(28.dp),
                            color = Color.White.copy(alpha = 0.9f),
                            shadowElevation = 8.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                ORCareTextField(
                                    value = email,
                                    onValueChange = { email = it; errorMessage = null },
                                    label = "Email Address",
                                    imageVector = Icons.Rounded.Email,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                                )

                                ORCareTextField(
                                    value = password,
                                    onValueChange = { password = it; errorMessage = null },
                                    label = "Password",
                                    imageVector = Icons.Rounded.Lock,
                                    visualTransformation = if (passwordVisible) VisualTransformation.None
                                                           else PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    trailingIcon = {
                                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                            Icon(
                                                imageVector = if (passwordVisible) Icons.Rounded.VisibilityOff
                                                              else Icons.Rounded.Visibility,
                                                contentDescription = null,
                                                tint = TextMuted
                                            )
                                        }
                                    }
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                ORCareButton(
                                    text = "Send Verification Code",
                                    isLoading = deleteOtpState is AuthState.Loading,
                                    containerColor = ErrorRed,
                                    onClick = {
                                        errorMessage = null
                                        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                            errorMessage = "Enter a valid email address"
                                        } else if (password.isBlank()) {
                                            errorMessage = "Password is required"
                                        } else {
                                            viewModel.requestDeleteOtp(email.trim(), password)
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Warning box
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            color = ErrorRed.copy(alpha = 0.06f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.2f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    "⚠️ This will permanently delete:",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold, color = ErrorRed
                                    )
                                )
                                listOf(
                                    "Your account and profile data",
                                    "All chat history and AI conversations",
                                    "Learning progress and quiz results",
                                    "All credentials from our database"
                                ).forEach { item ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text("•", color = ErrorRed, fontWeight = FontWeight.Bold)
                                        Text(item, style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary))
                                    }
                                }
                            }
                        }
                    }

                    // Step: OTP
                    if (step == DeleteStep.OTP) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(28.dp),
                            color = Color.White.copy(alpha = 0.9f),
                            shadowElevation = 8.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Enter 6-Digit Code",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold, color = TextPrimary
                                    )
                                )

                                OutlinedTextField(
                                    value = otp,
                                    onValueChange = { if (it.length <= 6 && it.all { c -> c.isDigit() }) { otp = it; errorMessage = null } },
                                    modifier = Modifier.fillMaxWidth(),
                                    textStyle = MaterialTheme.typography.headlineMedium.copy(
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 12.sp,
                                        color = ErrorRed
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = ErrorRed,
                                        unfocusedBorderColor = ErrorRed.copy(alpha = 0.4f),
                                        focusedContainerColor = Color(0xFFFFF5F5),
                                        unfocusedContainerColor = Color(0xFFFFF5F5)
                                    ),
                                    placeholder = {
                                        Text(
                                            "······",
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.headlineMedium.copy(
                                                color = TextMuted, letterSpacing = 12.sp
                                            )
                                        )
                                    }
                                )

                                Text(
                                    "Code expires in 10 minutes",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted),
                                    textAlign = TextAlign.Center
                                )

                                ORCareButton(
                                    text = "Permanently Delete Account",
                                    isLoading = deleteConfirmState is AuthState.Loading,
                                    containerColor = ErrorRed,
                                    onClick = {
                                        errorMessage = null
                                        if (otp.length < 6) {
                                            errorMessage = "Enter the 6-digit code"
                                        } else {
                                            viewModel.confirmDeleteAccount(email.trim(), otp) {
                                                // handled by LaunchedEffect above
                                            }
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                TextButton(onClick = {
                                    otp = ""
                                    errorMessage = null
                                    viewModel.requestDeleteOtp(email.trim(), password)
                                }) {
                                    Text("Resend Code", color = TextMuted)
                                }
                            }
                        }
                    }

                    // Step: Success
                    if (step == DeleteStep.SUCCESS) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(28.dp),
                            color = Color.White.copy(alpha = 0.9f),
                            shadowElevation = 8.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    "All your data has been permanently removed from our servers and all devices.",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextSecondary, textAlign = TextAlign.Center, lineHeight = 22.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                ORCareButton(
                                    text = "Go to Login",
                                    onClick = {
                                        navController.navigate("sign_in") {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            SIMATSFooter()
        }
    }
}
