package com.simats.orcare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.data.LearningRepository
import com.simats.orcare.data.LearningModule
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningCategoryScreen(
    navController: NavController,
    categoryId: String?,
    viewModel: com.simats.orcare.ui.viewmodel.ContentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val learningState by viewModel.learningState.collectAsState()

    val category = when (val state = learningState) {
        is com.simats.orcare.ui.viewmodel.ContentState.Success<List<com.simats.orcare.data.LearningCategory>> -> {
            state.data.find { it.id == categoryId }
        }
        else -> null
    }

    LaunchedEffect(Unit) {
        if (learningState is com.simats.orcare.ui.viewmodel.ContentState.Idle) {
            viewModel.fetchLearningContent()
        }
    }

    GradientBackground {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = category?.title ?: "Category",
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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {

            when (val state = learningState) {
                is com.simats.orcare.ui.viewmodel.ContentState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PrimaryCoral)
                    }
                }
                is com.simats.orcare.ui.viewmodel.ContentState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(state.message, color = ErrorRed)
                    }
                }
                is com.simats.orcare.ui.viewmodel.ContentState.Success<List<com.simats.orcare.data.LearningCategory>> -> {
                    if (category == null) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Category not found", color = TextPrimary)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(category.modules) { module ->
                                LearningModuleCard(
                                    module = module,
                                    categoryColor = category.color,
                                    onClick = {
                                        navController.navigate("module_detail/${module.id}")
                                    }
                                )
                            }
                        }
                    }
                }
                else -> {}
            }
            }
        }
    }
}

@Composable
fun LearningModuleCard(module: LearningModule, categoryColor: Color, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Box
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(categoryColor.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = module.icon,
                    contentDescription = null,
                    tint = categoryColor,
                    modifier = Modifier.size(28.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = module.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.PlayCircle,
                        contentDescription = null,
                        tint = TextMuted,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${module.lessonCount} Lessons • ${module.duration}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextMuted
                        )
                    )
                }
            }
        }
    }
}
