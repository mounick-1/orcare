package com.simats.orcare.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.data.Tip
import com.simats.orcare.data.TipRepository
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyTipsScreen(navController: NavController) {
    val strings = LocalORCareStrings.current
    var selectedCategory by remember { mutableStateOf("All") }
    
    val filteredTips = remember(selectedCategory) {
        if (selectedCategory == "All") {
            com.simats.orcare.data.TipRepository.tipsList
        } else {
            com.simats.orcare.data.TipRepository.tipsList.filter { it.category == selectedCategory }
        }
    }

    GradientBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = strings.dailyBrushingTip,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
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
            containerColor = Color.Transparent
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Tip of the Day
                item {
                    TipOfTheDayCard(com.simats.orcare.data.TipRepository.getDailyTip().description)
                }
    
                // Category Filter
                item {
                    Text(
                        text = "Browse by Category",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(com.simats.orcare.data.TipRepository.categories) { category ->
                            FilterChip(
                                selected = selectedCategory == category,
                                onClick = { selectedCategory = category },
                                label = { Text(category) },
                                shape = RoundedCornerShape(20.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PrimaryOrange,
                                    selectedLabelColor = Color.White,
                                    containerColor = SurfaceWhite.copy(alpha = 0.5f),
                                    labelColor = TextSecondary
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = selectedCategory == category,
                                    borderColor = Color.White.copy(alpha = 0.3f),
                                    selectedBorderColor = PrimaryOrange
                                )
                            )
                        }
                    }
                }
    
                // Tips List
                items(filteredTips) { tip ->
                    TipCard(tip)
                }
    
                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}

@Composable
fun TipCard(tip: Tip) {
    ORCareGlassCard(
        modifier = Modifier.fillMaxWidth().clickable { },
        shape = RoundedCornerShape(28.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.Top
        ) {
            ORCareGlassCard(
                modifier = Modifier.size(52.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = 0.dp
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = tip.icon, fontSize = 24.sp)
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = tip.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary,
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tip.description,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextSecondary,
                        lineHeight = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}
