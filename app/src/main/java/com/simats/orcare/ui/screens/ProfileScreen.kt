package com.simats.orcare.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import com.simats.orcare.ui.components.AutoSizeText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val strings = LocalORCareStrings.current
    val context = androidx.compose.ui.platform.LocalContext.current
    val userPreferences = remember { com.simats.orcare.data.UserPreferences(context) }
    
    val viewModel: com.simats.orcare.ui.viewmodel.AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = com.simats.orcare.ui.viewmodel.AuthViewModel.Factory(
            context.applicationContext as android.app.Application,
            com.simats.orcare.data.UserPreferences(context)
        )
    )
    
    val selectedImageIndex by userPreferences.profileImageIndex.collectAsState(initial = 0)
    val district by userPreferences.district.collectAsState(initial = "")
    val state by userPreferences.state.collectAsState(initial = "")
    val language by userPreferences.language.collectAsState(initial = "English")
    val userName by userPreferences.userName.collectAsState(initial = "User")
    val userImageUri by userPreferences.profileImageUri.collectAsState(initial = null)

    val profileImages = listOf(
        Icons.Rounded.Person,
        Icons.Rounded.Face,
        Icons.Rounded.AccountCircle,
        Icons.Rounded.EmojiPeople,
        Icons.Rounded.SentimentSatisfied
    )

    // Staggered animation
    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { delay(100); showContent = true }

    GradientBackground {
        ORCareScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = strings.profile,
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
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // User Profile Card — stagger 0
                AnimatedVisibility(
                    visible = showContent,
                    enter = slideInVertically(
                        initialOffsetY = { 50 },
                        animationSpec = tween(400, 0, CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f))
                    ) + fadeIn(tween(400, 0))
                ) {
                    ORCareGlassCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(24.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .background(PrimaryOrange.copy(alpha = 0.1f), CircleShape)
                                        .padding(4.dp)
                                ) {
                                    Surface(
                                        modifier = Modifier.fillMaxSize(),
                                        shape = CircleShape,
                                        color = Color.White.copy(alpha = 0.5f),
                                        border = androidx.compose.foundation.BorderStroke(
                                            2.dp, Brush.linearGradient(listOf(PrimaryOrange, SecondaryOrange))
                                        )
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            if (userImageUri != null) {
                                                AsyncImage(
                                                    model = android.net.Uri.parse(userImageUri),
                                                    contentDescription = "Profile Image",
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = profileImages.getOrElse(selectedImageIndex) { Icons.Rounded.Person },
                                                    contentDescription = null,
                                                    tint = PrimaryCoral,
                                                    modifier = Modifier.size(44.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                Column {
                                    AutoSizeText(
                                        text = "${strings.hello}, $userName!",
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = TextPrimary,
                                            letterSpacing = (-0.5).sp
                                        ),
                                        maxLines = 1
                                    )
                                    if (district.isNotBlank() || state.isNotBlank()) {
                                        Text(
                                            text = listOf(district, state).filter { it.isNotBlank() }.joinToString(", "),
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                color = TextSecondary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                    Text(
                                        text = strings.members,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = PrimaryOrange.copy(alpha = 0.7f),
                                            fontWeight = FontWeight.ExtraBold
                                        )
                                    )
                                }
                            }
                            
                            Surface(
                                onClick = { navController.navigate("edit_profile") },
                                modifier = Modifier.size(44.dp),
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.5f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Rounded.Edit,
                                        contentDescription = "Edit Profile",
                                        tint = TextPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }


                // Language Settings — stagger 1
                AnimatedVisibility(
                    visible = showContent,
                    enter = slideInVertically(
                        initialOffsetY = { 50 },
                        animationSpec = tween(400, 80, CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f))
                    ) + fadeIn(tween(400, 80))
                ) {
                    SettingsSectionItem(
                        icon = Icons.Rounded.Language,
                        iconColor = PrimaryCoral,
                        title = strings.language,
                        subtitle = language,
                        onClick = { navController.navigate("profile_language_selection") }
                    )
                }

                // Settings header — stagger 2
                AnimatedVisibility(
                    visible = showContent,
                    enter = slideInVertically(
                        initialOffsetY = { 50 },
                        animationSpec = tween(400, 160, CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f))
                    ) + fadeIn(tween(400, 160))
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = strings.settings,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            ),
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        ORCareGlassCard(
                            shape = RoundedCornerShape(32.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                SettingsRowItem(
                                    icon = Icons.Rounded.Notifications,
                                    iconColor = PrimaryOrange,
                                    title = strings.reminders,
                                    onClick = { navController.navigate("reminders") }
                                )
                                SettingsRowItem(
                                    icon = Icons.Rounded.Description,
                                    iconColor = PrimaryOrange,
                                    title = strings.privacyPolicy,
                                    onClick = { navController.navigate("privacy_policy") }
                                )
                                SettingsRowItem(
                                    icon = Icons.Rounded.DeleteForever,
                                    iconColor = Color(0xFFE53935),
                                    title = strings.deleteAccount,
                                    onClick = { navController.navigate("delete_account") }
                                )
                            }
                        }

                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Logout Button — stagger 3
                AnimatedVisibility(
                    visible = showContent,
                    enter = slideInVertically(
                        initialOffsetY = { 50 },
                        animationSpec = tween(400, 240, CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f))
                    ) + fadeIn(tween(400, 240))
                ) {
                    ORCareButton(
                        text = strings.logout,
                        onClick = {
                            viewModel.logout()
                            navController.navigate("sign_in") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = TextMuted.copy(alpha = 0.1f),
                        contentColor = TextPrimary
                    )
                }
                
                Spacer(modifier = Modifier.height(120.dp))
            }
        }
    }
}
}

@Composable
fun SettingsSectionItem(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    ORCareGlassCard(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(iconColor.copy(alpha = 0.1f), RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            letterSpacing = 0.5.sp
                        )
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PrimaryOrange,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                    }
                }
            }
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = TextPrimary.copy(alpha = 0.3f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun SettingsRowItem(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = iconColor.copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextPrimary
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
