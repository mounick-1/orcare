package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteAccountScreen(navController: NavController) {
    val strings = LocalORCareStrings.current
    val scope = rememberCoroutineScope()
    var showContent by remember { mutableStateOf(false) }
    
    val context = androidx.compose.ui.platform.LocalContext.current
    val userPreferences = remember { com.simats.orcare.data.UserPreferences(context) }
    
    // Form state
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    
    // Validation state
    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

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
            Box(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Back",
                        tint = TextPrimary
                    )
                }
                Text(
                    text = "Account Deletion",
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
                    .padding(horizontal = 24.dp),
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
                            color = ErrorRed.copy(alpha = 0.1f)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Rounded.PersonRemove,
                                    contentDescription = null,
                                    tint = ErrorRed,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Text(
                            text = "Close Your Account",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary,
                                letterSpacing = (-1).sp,
                                textAlign = TextAlign.Center
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "We're sorry to see you go. Please complete the form below to initiate account deletion.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondary,
                                letterSpacing = 0.2.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(40.dp))
                        
                        if (isSuccess) {
                            DeletionSuccessCard {
                                navController.navigate("sign_in") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        } else {
                            // Premium Form Card
                            Surface(
                                modifier = Modifier.fillMaxWidth().animateContentSize(),
                                shape = RoundedCornerShape(32.dp),
                                color = Color.White.copy(alpha = 0.9f),
                                shadowElevation = 12.dp,
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(28.dp),
                                    verticalArrangement = Arrangement.spacedBy(20.dp)
                                ) {
                                    ORCareTextField(
                                        value = fullName,
                                        onValueChange = { fullName = it; nameError = null },
                                        label = strings.fullName,
                                        imageVector = Icons.Rounded.Person,
                                        isError = nameError != null
                                    )
                                    if (nameError != null) ErrorText(nameError!!)
                                    
                                    ORCareTextField(
                                        value = email,
                                        onValueChange = { email = it; emailError = null },
                                        label = strings.email,
                                        imageVector = Icons.Rounded.Email,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                        isError = emailError != null
                                    )
                                    if (emailError != null) ErrorText(emailError!!)
                                    
                                    ORCareTextField(
                                        value = phoneNumber,
                                        onValueChange = { phoneNumber = it; phoneError = null },
                                        label = "Phone Number",
                                        imageVector = Icons.Rounded.Phone,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        isError = phoneError != null
                                    )
                                    if (phoneError != null) ErrorText(phoneError!!)
                                    
                                    OutlinedTextField(
                                        value = reason,
                                        onValueChange = { reason = it },
                                        label = { Text("Reason for leaving (Optional)") },
                                        modifier = Modifier.fillMaxWidth().height(120.dp),
                                        shape = RoundedCornerShape(20.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedBorderColor = GrayBorder.copy(alpha = 0.5f),
                                            focusedBorderColor = PrimaryOrange,
                                            focusedLabelColor = PrimaryOrange,
                                            unfocusedLabelColor = TextMuted,
                                            focusedContainerColor = SurfaceWhite.copy(alpha = 0.5f),
                                            unfocusedContainerColor = SurfaceWhite.copy(alpha = 0.3f)
                                        )
                                    )
                                    
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    ORCareButton(
                                        text = "Delete My Account",
                                        isLoading = isDeleting,
                                        containerColor = ErrorRed,
                                        onClick = {
                                            if (validateForm(fullName, email, phoneNumber, { nameError = it }, { emailError = it }, { phoneError = it })) {
                                                showConfirmationDialog = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
            
            SIMATSFooter()
        }
    }
    
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { 
                Text(
                    "Delete Permanently?", 
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold)
                ) 
            },
            text = { 
                Text(
                    "This action is irreversible. All your data including health history and appointments will be permanently removed.",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                ) 
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmationDialog = false
                        isDeleting = true
                        scope.launch {
                            delay(2000)
                            userPreferences.clearAuthToken()
                            isDeleting = false
                            isSuccess = true
                        }
                    }
                ) {
                    Text("YES, DELETE", color = ErrorRed, fontWeight = FontWeight.ExtraBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmationDialog = false }) {
                    Text("CANCEL", color = TextSecondary, fontWeight = FontWeight.Bold)
                }
            },
            shape = RoundedCornerShape(32.dp),
            containerColor = Color.White
        )
    }
}

@Composable
fun ErrorText(text: String) {
    Text(
        text = text,
        color = ErrorRed,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier.padding(start = 4.dp, top = (-16).dp)
    )
}


@Composable
fun DeletionSuccessCard(onClose: () -> Unit) {
    ORCareCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = null,
                tint = Color(0xFF43A047),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Request Submitted",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Your deletion request has been submitted. Our team will process it within 48 hours. You will be logged out now.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
            )
            Spacer(modifier = Modifier.height(24.dp))
            ORCareButton(text = "Go to Login", onClick = onClose)
        }
    }
}

private fun validateForm(
    name: String,
    email: String,
    phone: String,
    setNameError: (String?) -> Unit,
    setEmailError: (String?) -> Unit,
    setPhoneError: (String?) -> Unit
): Boolean {
    var isValid = true
    
    if (name.isBlank()) {
        setNameError("Name is required")
        isValid = false
    }
    
    if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        setEmailError("Enter a valid email address")
        isValid = false
    }
    
    if (phone.isBlank()) {
        setPhoneError("Phone number is required")
        isValid = false
    }
    
    return isValid
}
