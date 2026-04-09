package com.simats.orcare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.simats.orcare.ui.components.*
import com.simats.orcare.ui.theme.*
import kotlinx.coroutines.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpFeedbackScreen(
    navController: NavController,
    viewModel: com.simats.orcare.ui.viewmodel.SystemViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    
    val feedbackState by viewModel.feedbackState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(feedbackState) {
        when (feedbackState) {
            is com.simats.orcare.ui.viewmodel.UIState.Success -> {
                snackbarHostState.showSnackbar((feedbackState as com.simats.orcare.ui.viewmodel.UIState.Success).message)
                viewModel.resetFeedbackState()
                navController.popBackStack()
            }
            is com.simats.orcare.ui.viewmodel.UIState.Error -> {
                snackbarHostState.showSnackbar((feedbackState as com.simats.orcare.ui.viewmodel.UIState.Error).message)
                viewModel.resetFeedbackState()
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Help & Feedback",
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
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(24.dp)
            ) {
                ORCareButton(
                    text = "Submit Feedback",
                    onClick = {
                        if (name.isBlank() || email.isBlank() || message.isBlank()) {
                            scope.launch { 
                                snackbarHostState.showSnackbar("Please fill all fields") 
                            }
                            return@ORCareButton
                        }
                        viewModel.submitFeedback(name, email, message)
                    },
                    isLoading = feedbackState is com.simats.orcare.ui.viewmodel.UIState.Loading
                )
            }
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
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Contact Info
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryCoral.copy(alpha = 0.05f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryCoral.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Contact Support", 
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), 
                            color = PrimaryCoral
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.Email, contentDescription = null, tint = PrimaryCoral, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = "support@orcare.com", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.Phone, contentDescription = null, tint = PrimaryCoral, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(text = "+91 98765 43210", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold))
                        }
                    }
                }

                Text(
                    text = "Send us your feedback",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                )

                ORCareTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Your Name",
                )

                ORCareTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Your Email Address",
                )

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("How can we help you?") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryCoral,
                        unfocusedBorderColor = SunsetPeach,
                        focusedLabelColor = PrimaryCoral,
                        unfocusedLabelColor = TextMuted
                    ),
                    maxLines = 5
                )
            }
        }
    }
}
