package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.simats.orcare.ui.components.*
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.viewmodel.AuthState
import com.simats.orcare.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import androidx.compose.ui.platform.LocalContext
import com.simats.orcare.data.UserPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

@Composable
fun SignUpScreen(navController: NavController) {
    val context = LocalContext.current
    val strings = LocalORCareStrings.current
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModel.Factory(context.applicationContext as android.app.Application, UserPreferences(context))
    )
    var showContent by remember { mutableStateOf(false) }
    
    var fullName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    
    val registerState by viewModel.registerState.collectAsState()
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val userPreferences = remember { UserPreferences(context) }
    val currentLanguage by userPreferences.language.collectAsState(initial = "English")

    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
        if (userPreferences.authToken.first() != null) {
            navController.navigate("home") { popUpTo(0) { inclusive = true } }
        }
    }
    
    LaunchedEffect(registerState) {
        if (registerState is AuthState.Success) {
            navController.navigate("otp_verification?email=${email}")
            viewModel.resetState()
        } else if (registerState is AuthState.Error) {
            isError = true
            errorMessage = (registerState as AuthState.Error).message
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
                    .fillMaxSize()
                    .padding(horizontal = 28.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                
                AnimatedVisibility(
                    visible = showContent,
                    enter = fadeIn(tween(1000)) + slideInVertically(initialOffsetY = { 30 })
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ORCareLogo(scale = 0.9f, showText = true)
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Text(
                            text = strings.createAccount, 
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary,
                                letterSpacing = (-1).sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = strings.joinOrCare, 
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondary,
                                letterSpacing = 0.2.sp
                            )
                        )

                        
                        Spacer(modifier = Modifier.height(40.dp))
                        
                        // Premium Signup Card
                        Surface(
                            modifier = Modifier.fillMaxWidth().animateContentSize(),
                            shape = RoundedCornerShape(32.dp),
                            color = Color.White.copy(alpha = 0.9f),
                            shadowElevation = 8.dp,
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
                        ) {
                            Column(modifier = Modifier.padding(28.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                ORCareTextField(
                                    value = fullName, 
                                    onValueChange = { fullName = it }, 
                                    label = strings.fullName, 
                                    imageVector = Icons.Default.Person
                                )
                                
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    ORCareTextField(
                                        value = age, 
                                        onValueChange = { age = it }, 
                                        label = strings.ageLabel, 
                                        imageVector = Icons.Default.CalendarToday, 
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        modifier = Modifier.weight(1f)
                                    )
                                    
                                    var genderExpanded by remember { mutableStateOf(false) }
                                    Box(modifier = Modifier.weight(1.3f)) {
                                        OutlinedTextField(
                                            value = if (gender.isEmpty()) strings.selectGender else gender,
                                            onValueChange = {},
                                            readOnly = true,
                                            label = if (gender.isNotEmpty()) ({ Text(strings.selectGender) }) else null,
                                            trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = PrimaryOrange) },
                                            modifier = Modifier.fillMaxWidth().clickable { genderExpanded = true },
                                            enabled = false,
                                            shape = RoundedCornerShape(16.dp),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                disabledTextColor = if (gender.isEmpty()) TextMuted else TextPrimary,
                                                disabledBorderColor = GrayBorder,
                                                disabledLabelColor = TextMuted,
                                                disabledLeadingIconColor = PrimaryOrange,
                                                disabledPlaceholderColor = TextMuted
                                            )
                                        )
                                        DropdownMenu(
                                            expanded = genderExpanded,
                                            onDismissRequest = { genderExpanded = false },
                                            modifier = Modifier.background(Color.White).fillMaxWidth(0.4f)
                                        ) {
                                            listOf("Male", "Female", "Other").forEach { option ->
                                                DropdownMenuItem(
                                                    text = { Text(option, fontWeight = FontWeight.Medium) },
                                                    onClick = { gender = option; genderExpanded = false }
                                                )
                                            }
                                        }
                                        Box(modifier = Modifier.matchParentSize().clickable { genderExpanded = true })
                                    }
                                }
                                
                                ORCareTextField(
                                    value = email, 
                                    onValueChange = { email = it }, 
                                    label = strings.email, 
                                    imageVector = Icons.Default.Email, 
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                                )
                                
                                ORCareTextField(
                                    value = password, 
                                    onValueChange = { password = it }, 
                                    label = strings.password, 
                                    imageVector = Icons.Default.Lock, 
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                
                                ORCareTextField(
                                    value = confirmPassword, 
                                    onValueChange = { confirmPassword = it }, 
                                    label = strings.confirmPassword, 
                                    imageVector = Icons.Default.Lock, 
                                    visualTransformation = PasswordVisualTransformation()
                                )
                                
                                if (isError) {
                                    Text(
                                        text = errorMessage, 
                                        color = MaterialTheme.colorScheme.error, 
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                }
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                ORCareButton(
                                    text = strings.signUp, 
                                    isLoading = registerState is AuthState.Loading, 
                                    onClick = {
                                        if (password != confirmPassword) { 
                                            isError = true; errorMessage = "Passwords do not match" 
                                        } else if (fullName.isBlank() || email.isBlank() || age.isBlank() || gender.isBlank()) {
                                            isError = true; errorMessage = "All fields are required"
                                        } else {
                                            scope.launch { 
                                                viewModel.register(fullName, email, password, age.toIntOrNull() ?: 0, gender, currentLanguage) 
                                            }
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
                                text = strings.alreadyHaveAccount, 
                                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = strings.login, 
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = PrimaryOrange, 
                                    fontWeight = FontWeight.ExtraBold
                                ), 
                                modifier = Modifier.clickable { navController.popBackStack() }
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

