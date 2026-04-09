package com.simats.orcare.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.clickable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.Dp
import com.simats.orcare.R
import com.simats.orcare.ui.theme.*
import com.simats.orcare.data.Symptom
import kotlinx.coroutines.delay
import kotlin.random.Random
import androidx.compose.ui.text.style.TextAlign

object MotionTokens {
    const val DurationShort = 200
    const val DurationMedium = 400
    const val DurationLong = 600
    val EaseOut = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
    val EaseInOut = CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f)
}

/**
 * A beautiful animated background for authentication and secondary screens.
 */
@Composable
fun AnimatedAuthBackground(
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Background")
    
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Phase"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val xOffset1 = (kotlin.math.sin(phase) * 100).dp
        val yOffset1 = (kotlin.math.sin(phase * 0.5f) * 150).dp
        
        val xOffset2 = (kotlin.math.sin(phase * 0.7f) * 120).dp
        val yOffset2 = (kotlin.math.sin(phase * 0.3f) * 180).dp

        Box(
            modifier = Modifier
                .size(400.dp)
                .offset(x = 250.dp + xOffset1, y = (-100).dp + yOffset1)
                .blur(80.dp)
                .alpha(0.12f)
                .background(PrimaryOrange, CircleShape)
        )

        Box(
            modifier = Modifier
                .size(350.dp)
                .offset(x = (-150).dp + xOffset2, y = 400.dp + yOffset2)
                .blur(100.dp)
                .alpha(0.1f)
                .background(AccentMint, CircleShape)
        )

        content()
    }
}

@Composable
fun ORCareLogo(
    modifier: Modifier = Modifier,
    scale: Float = 1f,
    showText: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp * scale)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color.White.copy(alpha = 0.3f), Color.White.copy(alpha = 0.1f))
                    ),
                    RoundedCornerShape(32.dp * scale)
                )
                .border(
                    width = 1.dp * scale,
                    brush = Brush.linearGradient(
                        colors = listOf(Color.White.copy(alpha = 0.5f), Color.White.copy(alpha = 0.2f))
                    ),
                    shape = RoundedCornerShape(32.dp * scale)
                )
                .shadow(
                    elevation = 20.dp * scale,
                    shape = RoundedCornerShape(32.dp * scale),
                    ambientColor = PrimaryOrange.copy(alpha = 0.2f),
                    spotColor = AccentMint.copy(alpha = 0.2f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "ORCare Logo",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp * scale),
                contentScale = androidx.compose.ui.layout.ContentScale.Fit
            )
        }
        
        if (showText) {
            Spacer(modifier = Modifier.height(16.dp * scale))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "OR",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = PrimaryOrange,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "Care",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
            Text(
                text = "Your Oral Care Companion",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp
                )
            )
        }
    }
}

@Composable
fun ORCareGlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    elevation: Dp = 15.dp,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(24.dp),
    border: BorderStroke? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val combinedModifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier
    
    Surface(
        modifier = combinedModifier
            .shadow(
                elevation = elevation,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.05f),
                spotColor = Color.Black.copy(alpha = 0.05f)
            ),
        shape = shape,
        color = Color.White.copy(alpha = 0.7f),
        border = border ?: BorderStroke(1.dp, Color.White.copy(alpha = 0.5f))
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.4f),
                            Color.White.copy(alpha = 0.1f)
                        )
                    )
                )
                .padding(2.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
fun ORCareButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    isSecondary: Boolean = false,
    containerColor: Color? = null,
    contentColor: Color? = null
) {
    val buttonBg = when {
        containerColor != null -> Brush.linearGradient(listOf(containerColor, containerColor))
        isSecondary -> Brush.linearGradient(listOf(AccentMint, Color(0xFF10B981)))
        else -> Brush.linearGradient(listOf(PrimaryOrange, Color(0xFFFFB877)))
    }

    val finalContentColor = contentColor ?: TextWhite

    Surface(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .shadow(
                elevation = if (enabled && !isLoading) 8.dp else 0.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = PrimaryOrange.copy(alpha = 0.3f),
                spotColor = PrimaryOrange.copy(alpha = 0.3f)
            )
            .alpha(if (enabled && !isLoading) 1f else 0.6f),
        color = Color.Transparent,
        shape = RoundedCornerShape(18.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .background(buttonBg)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = finalContentColor,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.5.dp
                )
            } else {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = finalContentColor,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
    }
}

@Composable
fun AnimatedGridBackground() {
    SimplifiedBubbleAnimation()
}

@Composable
fun ORCareScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    containerColor: Color = Color.Transparent,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        containerColor = containerColor,
        content = content
    )
}

@Composable
fun ORCareTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    isError: Boolean = false,
    enabled: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation = androidx.compose.ui.text.input.VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = if (imageVector != null) {
            { Icon(imageVector = imageVector, contentDescription = null, tint = PrimaryOrange) }
        } else null,
        trailingIcon = trailingIcon,
        isError = isError,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryOrange,
            unfocusedBorderColor = GrayBorder,
            focusedLabelColor = PrimaryOrange,
            unfocusedLabelColor = TextMuted,
            errorBorderColor = ErrorRed,
            cursorColor = PrimaryOrange,
            focusedContainerColor = SurfaceWhite.copy(alpha = 0.5f),
            unfocusedContainerColor = SurfaceWhite.copy(alpha = 0.3f)
        ),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        singleLine = true
    )
}

@Composable
fun ORCareCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    shape: androidx.compose.ui.graphics.Shape = Shapes.large,
    containerColor: Color = SurfaceWhite,
    elevation: Dp = 2.dp,
    border: BorderStroke? = null,
    accentColor: Color? = null,
    content: @Composable () -> Unit
) {
    val combinedModifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier

    Surface(
        modifier = combinedModifier,
        shape = shape,
        color = containerColor,
        shadowElevation = elevation,
        tonalElevation = 0.dp,
        border = border
    ) {
        Box {
            if (accentColor != null) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(4.dp)
                        .background(accentColor)
                        .align(Alignment.CenterStart)
                )
            }
            content()
        }
    }
}

data class BubbleData(
    val startX: Float,
    val startY: Float,
    val size: androidx.compose.ui.unit.Dp,
    val color: Color,
    val duration: Int,
    val driftX: androidx.compose.ui.unit.Dp
)

@Composable
fun FloatingBubbleComponent(data: BubbleData) {
    val infiniteTransition = rememberInfiniteTransition(label = "bubbleTransition")
    
    val floatY by infiniteTransition.animateFloat(
        initialValue = 1.2f,
        targetValue = -0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(data.duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "floatY"
    )

    val drift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(data.duration / 2, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "drift"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.08f,
        targetValue = 0.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(data.duration / 3, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(
                    x = (data.startX * 400).dp + (drift * data.driftX.value).dp,
                    y = (floatY * 1000).dp
                )
                .size(data.size)
                .background(
                    Brush.radialGradient(
                        colors = listOf(data.color.copy(alpha = alpha), Color.Transparent)
                    ), CircleShape
                )
        )
    }
}

@Composable
fun GradientBackground(content: @Composable () -> Unit) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(LightMint.copy(alpha = 0.4f), Color.White),
                            center = androidx.compose.ui.geometry.Offset(0f, 0f),
                            radius = 1000f
                        )
                    )
            )
            
            SimplifiedBubbleAnimation()
            
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                content()
            }
        }
    }
}

@Composable
fun SimplifiedBubbleAnimation() {
    val bubbles = remember {
        listOf(
            BubbleData(0.1f, 0.8f, 150.dp, AccentMint, 12000, 20.dp),
            BubbleData(0.8f, 0.5f, 100.dp, PrimaryOrange, 10000, (-15).dp),
            BubbleData(0.4f, 1.2f, 120.dp, LightMint, 15000, 10.dp),
            BubbleData(0.7f, 0.2f, 80.dp, SecondaryOrange, 8000, (-10).dp),
            BubbleData(0.2f, 0.4f, 60.dp, PrimaryOrange, 14000, 5.dp)
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        bubbles.forEach { bubble ->
            FloatingBubbleComponent(bubble)
        }
    }
}

@Composable
fun ORCareBottomNavigation(
    navController: NavController,
    initialSelectedTab: Int = 0,
    modifier: Modifier = Modifier
) {
    val strings = LocalORCareStrings.current
    var selectedTab by remember { mutableIntStateOf(initialSelectedTab) }
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            "home" -> selectedTab = 0
            "chatbot" -> selectedTab = 1
            "learning_center" -> selectedTab = 2 
            "oral_disease" -> selectedTab = 3
            "profile" -> selectedTab = 4
        }
        if (currentRoute?.startsWith("chatbot/") == true) selectedTab = 1
    }

    ORCareGlassCard(
        modifier = modifier
            .fillMaxWidth()
            .height(84.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = CircleShape,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(Icons.Outlined.Home, Icons.Filled.Home, strings.navHome, selectedTab == 0) { 
                navController.navigate("home") { popUpTo("home") { inclusive = true } }
            }
            BottomNavItem(Icons.AutoMirrored.Outlined.Chat, Icons.AutoMirrored.Filled.Chat, strings.navChatbot, selectedTab == 1) { 
                navController.navigate("chatbot/Start") { popUpTo("home") }
            }
            BottomNavItem(Icons.Outlined.School, Icons.Filled.School, strings.navEducation, selectedTab == 2) { 
                navController.navigate("learning_center") { popUpTo("home") }
            }
            BottomNavItem(Icons.Outlined.Coronavirus, Icons.Filled.Coronavirus, "Disease", selectedTab == 3) { 
                navController.navigate("oral_disease") { popUpTo("home") }
            }
            BottomNavItem(Icons.Outlined.Person, Icons.Filled.Person, strings.navProfile, selectedTab == 4) { 
                navController.navigate("profile") { popUpTo("home") }
            }
        }
    }
}


@Composable
fun BottomNavItem(
    outlineIcon: ImageVector,
    filledIcon: ImageVector,
    label: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(
            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
            indication = null
        ) { onSelect() }
    ) {
        Icon(
            imageVector = if (isSelected) filledIcon else outlineIcon,
            contentDescription = label,
            tint = if (isSelected) PrimaryOrange else TextSecondary,
            modifier = Modifier.size(26.dp)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        AnimatedVisibility(
            visible = isSelected,
            enter = expandHorizontally() + fadeIn(),
            exit = shrinkHorizontally() + fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(PrimaryOrange, CircleShape)
            )
        }
    }
}

@Composable
fun CompactSymptomCard(symptom: Symptom, onClick: () -> Unit) {
    ORCareGlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = PrimaryOrange.copy(alpha = 0.08f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = symptom.icon,
                    fontSize = 30.sp
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = symptom.title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary,
                    fontSize = 13.sp,
                    letterSpacing = (-0.5).sp
                ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun TipOfTheDayCard(tip: String) {
    ORCareCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        accentColor = PrimaryOrange,
        containerColor = SurfaceWhite
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Tip of the Day",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryOrange
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = tip,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextPrimary
                )
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ORCareDropdown(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>,
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                leadingIcon = if (imageVector != null) {
                    { Icon(imageVector = imageVector, contentDescription = null, tint = PrimaryOrange) }
                } else null,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryOrange,
                    unfocusedBorderColor = GrayBorder,
                    focusedLabelColor = PrimaryOrange,
                    unfocusedLabelColor = TextMuted
                ),
                shape = Shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(SurfaceWhite)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option, color = TextPrimary) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun SIMATSFooter(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Powered by SIMATS Engineering",
            style = MaterialTheme.typography.labelMedium.copy(
                color = TextSecondary,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
        )
    }
}
