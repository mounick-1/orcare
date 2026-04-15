package com.simats.orcare.ui.screens

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import com.simats.orcare.data.SymptomRepository
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomCheckerScreen(navController: NavController) {
    var isVisible by remember { mutableStateOf(false) }
    val strings = LocalORCareStrings.current
    
    val symptoms = remember {
        SymptomRepository.symptomsList.filter { it.title.lowercase() != "reminders" }
    }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    GradientBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = strings.complaintChecker,
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
                                contentDescription = strings.skip,
                                tint = TextPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = strings.checkDentalComplaints,
                    style = MaterialTheme.typography.bodyLarge.copy(color = TextSecondary)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = strings.selectSymptomDesc,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextSecondary,
                        lineHeight = 22.sp
                    )
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    itemsIndexed(symptoms) { index, symptom ->
                        var itemVisible by remember { mutableStateOf(false) }
                        LaunchedEffect(isVisible) {
                            if (isVisible) {
                                delay(index * 50L)
                                itemVisible = true
                            }
                        }
                        
                        AnimatedVisibility(
                            visible = itemVisible,
                            enter = fadeIn() + slideInVertically(initialOffsetY = { 20 })
                        ) {
                            CompactSymptomCard(symptom) {
                                val route = "symptom_detail/${Uri.encode(symptom.title)}"
                                navController.navigate(route)
                            }
                        }
                    }
                }
            }
        }
    }
}
}

