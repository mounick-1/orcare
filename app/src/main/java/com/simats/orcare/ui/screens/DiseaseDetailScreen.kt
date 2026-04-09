package com.simats.orcare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.data.DiseaseRepository
import com.simats.orcare.data.Disease
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseDetailScreen(
    navController: NavController,
    diseaseId: String?,
    viewModel: com.simats.orcare.ui.viewmodel.ContentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val diseasesState by viewModel.diseasesState.collectAsState()
    
    // Attempt to find the disease in the state
    val disease = when (val state = diseasesState) {
        is com.simats.orcare.ui.viewmodel.ContentState.Success<List<Disease>> -> state.data.find { it.id == diseaseId }
        else -> null
    }

    LaunchedEffect(Unit) {
        if (diseasesState is com.simats.orcare.ui.viewmodel.ContentState.Idle) {
            viewModel.fetchDiseases()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = disease?.name ?: "Disease Detail",
                        style = MaterialTheme.typography.titleMedium.copy(
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        // Replace GradientBackground with Box for stability
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues).background(Color.White)) {
            when (val state = diseasesState) {
                is com.simats.orcare.ui.viewmodel.ContentState.Idle -> {
                    // Do nothing or show placeholder
                }
                is com.simats.orcare.ui.viewmodel.ContentState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PrimaryOrange)
                    }
                }
                is com.simats.orcare.ui.viewmodel.ContentState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(state.message, color = ErrorRed)
                    }
                }
                is com.simats.orcare.ui.viewmodel.ContentState.Success<List<Disease>> -> {
                    if (disease == null) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Disease not found", color = TextSecondary)
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            // Header with Icon
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .background(disease.color, RoundedCornerShape(16.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = disease.icon,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                Column {
                                    Text(
                                        text = disease.name,
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                    )
                                    Text(
                                        text = "Condition Overview",
                                        style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                                    )
                                }
                            }

                            HorizontalDivider(color = SlateLight.copy(alpha = 0.5f))

                            DiseaseInfoSection(label = "What is happening?", content = disease.whatIsHappening)
                            DiseaseInfoSection(label = "What people notice", content = disease.whatPeopleNotice)
                            DiseaseInfoSection(label = "Why it happens", content = disease.whyItHappens)
                            DiseaseInfoSection(label = "Why not ignore it?", content = disease.whyNotIgnore)

                            // Warning card
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF4ED)),
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column {
                                        Text(
                                            text = "When to see a dentist",
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = PrimaryOrange
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = disease.whenToSeeDentist,
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = TextPrimary
                                            )
                                        )
                                    }
                                }
                            }
                            
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
fun DiseaseInfoSection(label: String, content: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = PrimaryOrange
            )
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextPrimary
            )
        )
    }
}
