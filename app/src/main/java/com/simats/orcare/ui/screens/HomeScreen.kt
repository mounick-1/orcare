package com.simats.orcare.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
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
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import com.simats.orcare.ui.components.AutoSizeText
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val strings = LocalORCareStrings.current
    val context = androidx.compose.ui.platform.LocalContext.current
    val userPreferences = remember { com.simats.orcare.data.UserPreferences(context) }
    
    val userName by userPreferences.userName.collectAsState(initial = "User")
    val userImageUri by userPreferences.profileImageUri.collectAsState(initial = null)
    val selectedImageIndex by userPreferences.profileImageIndex.collectAsState(initial = 0)

    val profileImages = listOf(
        Icons.Rounded.Person,
        Icons.Rounded.Face,
        Icons.Rounded.AccountCircle,
        Icons.Rounded.EmojiPeople,
        Icons.Rounded.SentimentSatisfied
    )

    // Staggered entry
    var showContent by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) { 
        delay(100)
        showContent = true 
    }

    androidx.activity.compose.BackHandler {
        // Prevent back navigation
    }

    GradientBackground {
        Scaffold(
            containerColor = Color.Transparent
        ) { paddingValues: PaddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .padding(top = paddingValues.calculateTopPadding() + 24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Header Section
                    AnimatedVisibility(
                        visible = showContent,
                        enter = slideInVertically(initialOffsetY = { 40 }) + fadeIn()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${strings.hello}, $userName",
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = TextPrimary,
                                        letterSpacing = (-0.5).sp
                                    ),
                                    maxLines = 1,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = strings.homeQuestion,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = TextSecondary,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }

                            ORCareGlassCard(
                                modifier = Modifier.size(60.dp).clickable { navController.navigate("profile") },
                                shape = CircleShape,
                                elevation = 4.dp
                            ) {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                    if (userImageUri != null) {
                                        AsyncImage(
                                            model = userImageUri,
                                            contentDescription = "Profile",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                        )
                                    } else {
                                        Icon(
                                            imageVector = profileImages.getOrElse(selectedImageIndex) { Icons.Rounded.Person },
                                            contentDescription = "Profile",
                                            tint = PrimaryOrange,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // AI Health Assistant Banner
                    AnimatedVisibility(
                        visible = showContent,
                        enter = slideInVertically(initialOffsetY = { 50 }) + fadeIn(tween(delayMillis = 100))
                    ) {
                        ORCareGlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clickable { navController.navigate("chatbot/Start") },
                            shape = RoundedCornerShape(32.dp),
                            elevation = 8.dp
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Brush.linearGradient(listOf(PrimaryOrange.copy(alpha = 0.1f), AccentMint.copy(alpha = 0.05f))))
                            ) {
                                // Decorative elements
                                Box(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .offset(x = 220.dp, y = (-20).dp)
                                        .background(AccentMint.copy(alpha = 0.1f), CircleShape)
                                )
                                
                                Row(
                                    modifier = Modifier.fillMaxSize().padding(24.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            ORCareGlassCard(
                                                modifier = Modifier.size(32.dp),
                                                shape = RoundedCornerShape(8.dp),
                                                elevation = 0.dp
                                            ) {
                                                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                                    Icon(Icons.Default.AutoAwesome, null, tint = PrimaryOrange, modifier = Modifier.size(18.dp))
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Text(
                                                text = strings.orcareAI,
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = TextPrimary
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = strings.aiAssistantSubtitle,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = TextSecondary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            text = "Consult AI Assistant →",
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                color = PrimaryOrange,
                                                fontWeight = FontWeight.ExtraBold
                                            )
                                        )
                                    }
                                    
                                    ORCareGlassCard(
                                        modifier = Modifier.size(80.dp),
                                        shape = CircleShape,
                                        elevation = 0.dp
                                    ) {
                                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                            Icon(Icons.Default.ChatBubble, null, tint = PrimaryOrange, modifier = Modifier.size(36.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Quick Actions
                    AnimatedVisibility(
                        visible = showContent,
                        enter = slideInVertically(initialOffsetY = { 50 }) + fadeIn(tween(delayMillis = 200))
                    ) {
                        Column {
                            Text(
                                text = strings.quickActions,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextPrimary,
                                    letterSpacing = (-0.5).sp
                                ),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            ORCareGlassCard(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(32.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    QuickActionItem(
                                        icon = Icons.Default.Search,
                                        label = strings.complaintChecker,
                                        color = AccentMint,
                                        modifier = Modifier.weight(1f),
                                        onClick = { navController.navigate("symptom_checker") }
                                    )
                                    QuickActionItem(
                                        icon = Icons.Default.School,
                                        label = strings.learningCenter,
                                        color = PrimaryOrange,
                                        modifier = Modifier.weight(1f),
                                        onClick = { navController.navigate("learning_center") }
                                    )
                                    QuickActionItem(
                                        icon = Icons.Default.Lightbulb,
                                        label = strings.dailyTips,
                                        color = Color(0xFFDAA520),
                                        modifier = Modifier.weight(1f),
                                        onClick = { navController.navigate("daily_tips") }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Common Symptoms
                    AnimatedVisibility(
                        visible = showContent,
                        enter = slideInVertically(initialOffsetY = { 50 }) + fadeIn(tween(delayMillis = 300))
                    ) {
                        Column {
                            Text(
                                text = strings.commonSymptoms,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextPrimary,
                                    letterSpacing = (-0.5).sp
                                ),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    CommonSymptomCard(
                                        title = "Tooth Pain",
                                        icon = Icons.Default.MoodBad,
                                        color = Color.Transparent,
                                        iconColor = PrimaryOrange,
                                        modifier = Modifier.weight(1f),
                                        onClick = { navController.navigate("chatbot/Tooth Pain") }
                                    )
                                    CommonSymptomCard(
                                        title = "Bleeding",
                                        icon = Icons.Default.WaterDrop,
                                        color = Color.Transparent,
                                        iconColor = PrimaryOrange,
                                        modifier = Modifier.weight(1f),
                                        onClick = { navController.navigate("chatbot/Bleeding Gums") }
                                    )
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    CommonSymptomCard(
                                        title = "Bad Breath",
                                        icon = Icons.Default.Air,
                                        color = Color.Transparent,
                                        iconColor = PrimaryOrange,
                                        modifier = Modifier.weight(1f),
                                        onClick = { navController.navigate("chatbot/Bad Breath") }
                                    )
                                    CommonSymptomCard(
                                        title = "Sensitivity",
                                        icon = Icons.Default.Bolt,
                                        color = Color.Transparent,
                                        iconColor = PrimaryOrange,
                                        modifier = Modifier.weight(1f),
                                        onClick = { navController.navigate("chatbot/Sensitivity") }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Tip of the Day
                    AnimatedVisibility(
                        visible = showContent,
                        enter = slideInVertically(initialOffsetY = { 50 }) + fadeIn(tween(delayMillis = 400))
                    ) {
                        Column {
                            Text(
                                text = strings.dailyBrushingTip,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = TextPrimary,
                                    letterSpacing = (-0.5).sp
                                ),
                                modifier = Modifier.padding(bottom = 16.dp)
                            )

                            val dailyTips = com.simats.orcare.data.TipRepository.dailyBrushingTips.take(3)
                            val pagerState = rememberPagerState(pageCount = { dailyTips.size })

                            HorizontalPager(
                                state = pagerState,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp),
                                pageSpacing = 16.dp
                            ) { page ->
                                TipOfTheDayCard(dailyTips[page].description)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(150.dp))
                }
            }
        }
    }
}

@Composable
fun QuickActionItem(
    icon: ImageVector,
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        AutoSizeText(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 16.sp
            ),
            modifier = Modifier.padding(horizontal = 4.dp),
            minTextSize = 8.sp,
            maxLines = 2
        )
    }
}
