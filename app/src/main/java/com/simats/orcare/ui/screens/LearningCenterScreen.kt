package com.simats.orcare.ui.screens

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
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.PlayCircle
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.data.LearningCategory
import com.simats.orcare.data.LearningModule
import com.simats.orcare.data.LearningRepository
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningCenterScreen(
    navController: NavController,
    viewModel: com.simats.orcare.ui.viewmodel.ContentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val strings = LocalORCareStrings.current
    val learningState by viewModel.learningState.collectAsState()

    LaunchedEffect(Unit) {
        if (learningState is com.simats.orcare.ui.viewmodel.ContentState.Idle) {
            viewModel.fetchLearningContent()
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
                                        imageVector = Icons.AutoMirrored.Rounded.MenuBook,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(
                                        text = strings.learningCenter, 
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = TextPrimary,
                                            letterSpacing = (-0.5).sp
                                        )
                                    ) 
                                    Text(
                                        text = "Professional Oral Care Courses",
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
            
            when (val state = learningState) {
                is com.simats.orcare.ui.viewmodel.ContentState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = PrimaryOrange)
                    }
                }
                is com.simats.orcare.ui.viewmodel.ContentState.Error -> {
                    Box(modifier = Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
                        ORCareGlassCard(shape = RoundedCornerShape(24.dp)) {
                            Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("⚠️", fontSize = 48.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(text = state.message, color = ErrorRed, textAlign = TextAlign.Center)
                                Spacer(modifier = Modifier.height(24.dp))
                                ORCareButton(text = "Retry", onClick = { viewModel.fetchLearningContent() })
                            }
                        }
                    }
                }
                is com.simats.orcare.ui.viewmodel.ContentState.Success<List<LearningCategory>> -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 24.dp, bottom = 100.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Header & Featured Section
                        item {
                            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                                Text(
                                    text = "Ready to learn?",
                                    style = MaterialTheme.typography.displaySmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = TextPrimary,
                                        letterSpacing = (-1.5).sp
                                    )
                                )
                                Text(
                                    text = "Enhance your oral hygiene with science-backed lessons.",
                                    style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                                )
                                Spacer(modifier = Modifier.height(28.dp))
                                
                                state.data.firstOrNull()?.modules?.firstOrNull()?.let { featuredModule ->
                                    FeaturedModuleCard(featuredModule, navController)
                                }
                                
                                Spacer(modifier = Modifier.height(36.dp))
                                
                                Text(
                                    text = "Learning Categories",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = TextPrimary,
                                        letterSpacing = (-0.5).sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                        }

                        items(state.data) { category ->
                            AnimatedCategoryItem(category = category, navController = navController)
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
fun AnimatedCategoryItem(category: LearningCategory, navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { 30 }) + fadeIn(tween(400))
    ) {
        LearningCategoryListItem(category = category, navController = navController)
    }
}

@Composable
fun FeaturedModuleCard(featuredModule: LearningModule, navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .shadow(12.dp, RoundedCornerShape(32.dp))
            .clickable { navController.navigate("module_detail/${featuredModule.id}") },
        shape = RoundedCornerShape(32.dp),
        color = Color.White
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(PrimaryOrange.copy(alpha = 0.9f), SecondaryOrange),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset(1000f, 1000f)
                        )
                    )
            )
            
            // Abstract decorations
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = 180.dp, y = (-80).dp)
                    .background(Color.White.copy(alpha = 0.15f), CircleShape)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(28.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(50)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Rounded.Star, null, tint = Color.White, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "FEATURED LESSON", 
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.sp
                                ), 
                                color = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = featuredModule.title,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            lineHeight = 32.sp
                        )
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.PlayCircle, null, tint = Color.White, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Start Lesson", 
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold), 
                            color = Color.White
                        )
                    }
                    
                    Text(
                        text = "15 min",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color.White.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun LearningCategoryListItem(category: LearningCategory, navController: NavController) {
    val strings = LocalORCareStrings.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 6.dp)
            .shadow(4.dp, RoundedCornerShape(24.dp))
            .clickable { navController.navigate("category_detail/${category.id}") },
        shape = RoundedCornerShape(24.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        PrimaryOrange.copy(alpha = 0.08f),
                        RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = null,
                    tint = PrimaryOrange,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(20.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimary,
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${category.modules.size} Professional Modules",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextSecondary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(PrimaryOrange.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = null,
                    tint = PrimaryOrange,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}


