package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.data.Disease
import com.simats.orcare.data.DiseaseRepository
import com.simats.orcare.ui.components.*
import com.simats.orcare.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OralDiseaseScreen(
    navController: NavController,
    viewModel: com.simats.orcare.ui.viewmodel.ContentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val strings = LocalORCareStrings.current
    val diseasesState by viewModel.diseasesState.collectAsState()

    LaunchedEffect(Unit) {
        if (diseasesState is com.simats.orcare.ui.viewmodel.ContentState.Idle) {
            viewModel.fetchDiseases()
        }
    }

    GradientBackground {
        Scaffold(
            topBar = {
                Surface(
                    color = Color.White.copy(alpha = 0.85f),
                    shadowElevation = 2.dp,
                    tonalElevation = 0.dp
                ) {
                    TopAppBar(
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .background(
                                            Brush.linearGradient(listOf(PrimaryOrange, SecondaryOrange)),
                                            RoundedCornerShape(14.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column {
                                    Text(
                                        text = strings.oralDiseases,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = TextPrimary,
                                            letterSpacing = (-0.5).sp
                                        )
                                    )
                                    Text(
                                        text = "Learn & understand conditions",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = TextSecondary,
                                            fontWeight = FontWeight.Medium
                                        )
                                    )
                                }
                            }
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
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                when (val state = diseasesState) {
                    is com.simats.orcare.ui.viewmodel.ContentState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator(
                                    color = PrimaryOrange,
                                    modifier = Modifier.size(48.dp),
                                    strokeWidth = 3.dp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "Loading diseases…",
                                    style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                                )
                            }
                        }
                    }

                    is com.simats.orcare.ui.viewmodel.ContentState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ORCareGlassCard(shape = RoundedCornerShape(24.dp)) {
                                Column(
                                    modifier = Modifier.padding(32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "⚠️",
                                        fontSize = 48.sp
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = state.message,
                                        color = ErrorRed,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                    ORCareButton(text = "Retry", onClick = { viewModel.fetchDiseases() })
                                }
                            }
                        }
                    }

                    is com.simats.orcare.ui.viewmodel.ContentState.Success<List<Disease>> -> {
                        val diseases = state.data
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = 20.dp, start = 20.dp, end = 20.dp, bottom = 120.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Header section
                            item {
                                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                                    Text(
                                        text = strings.commonOralConditions,
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = TextPrimary,
                                            letterSpacing = (-0.5).sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = strings.oralDiseaseSubtitle,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = TextSecondary,
                                            lineHeight = 22.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    // Stats banner removed
                                }
                            }

                            items(diseases) { disease ->
                                AnimatedDiseaseCard(
                                    disease = disease,
                                    onClick = { navController.navigate("disease_detail/${disease.id}") }
                                )
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}

// Stats banner sections removed

// ─────────────────────────────────────────────────────────
//  Animated Disease Card — with BIGGER icons
// ─────────────────────────────────────────────────────────
@Composable
fun AnimatedDiseaseCard(disease: Disease, onClick: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { 30 }) + fadeIn(tween(350))
    ) {
        DiseaseCard(disease = disease, onClick = onClick)
    }
}

@Composable
fun DiseaseCard(disease: Disease, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(24.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ── BIGGER ICON container ──
            Box(
                modifier = Modifier
                    .size(72.dp)                        // was 52dp
                    .background(
                        Brush.radialGradient(
                            listOf(PrimaryOrange.copy(alpha = 0.15f), PrimaryOrange.copy(alpha = 0.05f))
                        ),
                        CircleShape
                    )
                    .shadow(0.dp, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = disease.icon,
                    contentDescription = null,
                    tint = PrimaryOrange,
                    modifier = Modifier.size(38.dp)     // was 26dp
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = disease.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary,
                        letterSpacing = (-0.3).sp
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Tap to learn about this condition",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Severity indicator pill
                Surface(
                    shape = RoundedCornerShape(50),
                    color = PrimaryOrange.copy(alpha = 0.1f),
                    tonalElevation = 0.dp
                ) {
                    Text(
                        text = "Oral Disease",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = PrimaryOrange,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Arrow
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(PrimaryOrange.copy(alpha = 0.08f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = "Details",
                    tint = PrimaryOrange,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
