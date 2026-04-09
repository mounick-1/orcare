package com.simats.orcare.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.simats.orcare.data.LearningModule
import com.simats.orcare.data.LearningRepository
import com.simats.orcare.data.Lesson
import com.simats.orcare.ui.components.*
import com.simats.orcare.ui.theme.*
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleDetailScreen(
    navController: NavController,
    moduleId: String?,
    viewModel: com.simats.orcare.ui.viewmodel.ContentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val learningState by viewModel.learningState.collectAsState()

    val module = when (val state = learningState) {
        is com.simats.orcare.ui.viewmodel.ContentState.Success<List<com.simats.orcare.data.LearningCategory>> -> {
            state.data.flatMap { it.modules }.find { it.id == moduleId }
        }
        else -> null
    }

    LaunchedEffect(Unit) {
        if (learningState is com.simats.orcare.ui.viewmodel.ContentState.Idle) {
            viewModel.fetchLearningContent()
        }
    }

    when (val state = learningState) {
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
        else -> {
            if (module == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Module not found", color = TextPrimary)
                }
            } else {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = module.title,
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
                    bottomBar = {
                        ORCareButton(
                            text = "Take Quiz",
                            onClick = { navController.navigate("module_quiz/${module.id}") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    },
                    containerColor = Color.Transparent
                ) { paddingValues: PaddingValues ->
                    GradientBackground {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                Column {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = PrimaryOrange.copy(alpha = 0.1f),
                                        modifier = Modifier.padding(bottom = 12.dp)
                                    ) {
                                        Text(
                                            text = "${module.points} Points",
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = PrimaryOrange
                                            ),
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                    Text(
                                        text = module.objective,
                                        style = MaterialTheme.typography.bodyLarge.copy(color = TextSecondary),
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                }
                            }

                            items(module.lessons) { lesson ->
                                LessonCard(lesson)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LessonCard(lesson: Lesson) {
    Card(
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = lesson.icon,
                    contentDescription = null,
                    tint = PrimaryCoral,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = lesson.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = TextPrimary)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = lesson.content,
                style = MaterialTheme.typography.bodyLarge.copy(color = TextSecondary)
            )
        }
    }
}
