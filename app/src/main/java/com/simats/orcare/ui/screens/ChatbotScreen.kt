package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.simats.orcare.data.ChatMessage
import com.simats.orcare.ui.components.*
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.viewmodel.ChatbotViewModel

// ─────────────────────────────────────────────────────────
//  Data model for quick suggestion chips
// ─────────────────────────────────────────────────────────
private data class SuggestionChip(val emoji: String, val text: String)

private val chatSuggestions = listOf(
    SuggestionChip("🦷", "Why are my gums bleeding?"),
    SuggestionChip("😮‍💨", "What causes bad breath?"),
    SuggestionChip("⚡", "Sensitive teeth remedies?"),
    SuggestionChip("🤕", "How to soothe a toothache?"),
    SuggestionChip("🫧", "Best brushing technique?"),
    SuggestionChip("🍬", "Foods bad for teeth?")
)

// ─────────────────────────────────────────────────────────
//  Main Chatbot Screen
// ─────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(navController: NavController, symptomName: String? = null) {
    val viewModel: ChatbotViewModel = viewModel()
    val messages by viewModel.messages.collectAsState()
    val generatingMessage by viewModel.generatingMessage.collectAsState()

    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    // Auto-scroll to latest message
    LaunchedEffect(messages.size, generatingMessage) {
        if (messages.isNotEmpty()) {
            val target = messages.size + (if (generatingMessage != null) 1 else 0) - 1
            listState.animateScrollToItem(target)
        }
    }

    var initialMessageSent by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(symptomName) {
        // "Start" is the default route value when opened from bottom nav — not a real symptom
        val validSymptom = symptomName?.takeIf { it.isNotBlank() && it != "Start" && it != "null" }
        if (validSymptom != null && !initialMessageSent) {
            viewModel.sendSymptomQuery(validSymptom)
            initialMessageSent = true
        }
    }

    GradientBackground {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            modifier = Modifier.fillMaxSize(),
            topBar = { ChatTopBar(navController, viewModel) },
            bottomBar = {
                ChatInputBar(
                    text = messageText,
                    onTextChange = { messageText = it },
                    isGenerating = generatingMessage != null,
                    onSend = {
                        if (messageText.isNotBlank()) {
                            viewModel.sendMessage(messageText.trim())
                            messageText = ""
                            keyboardController?.hide()
                        }
                    }
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AnimatedContent(
                    targetState = messages.isEmpty() && generatingMessage == null,
                    transitionSpec = {
                        fadeIn(tween(300)) togetherWith fadeOut(tween(200))
                    },
                    label = "chatContent"
                ) { isEmpty ->
                    if (isEmpty) {
                        ChatEmptyState { prompt ->
                            viewModel.sendMessage(prompt)
                        }
                    } else {
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            contentPadding = PaddingValues(top = 12.dp, bottom = 20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(messages, key = { it.timestamp }) { message ->
                                AnimatedChatBubble(message)
                            }
                            if (generatingMessage != null) {
                                item { TypingIndicatorBubble() }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────
//  Top App Bar
// ─────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatTopBar(navController: NavController, viewModel: ChatbotViewModel) {
    // Pulsing online indicator
    val pulse = rememberInfiniteTransition(label = "pulse")
    val pulseScale by pulse.animateFloat(
        initialValue = 1f, targetValue = 1.4f,
        animationSpec = infiniteRepeatable(tween(900), RepeatMode.Reverse),
        label = "pulseScale"
    )

    Surface(
        color = Color.White.copy(alpha = 0.85f),
        shadowElevation = 2.dp,
        tonalElevation = 0.dp
    ) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // AI Avatar
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
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            "ORCare AI",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary,
                                letterSpacing = (-0.5).sp
                            )
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size((6 * pulseScale).dp)
                                    .background(AccentMint, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                "Online · Ready to help",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = AccentMint,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, "Back", tint = TextPrimary)
                }
            },
            actions = {
                IconButton(onClick = { viewModel.startNewSession() }) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "New Chat",
                        tint = PrimaryOrange
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
    }
}

// ─────────────────────────────────────────────────────────
//  Input Bar
// ─────────────────────────────────────────────────────────
@Composable
private fun ChatInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    isGenerating: Boolean,
    onSend: () -> Unit
) {
    Surface(
        color = Color.White.copy(alpha = 0.95f),
        shadowElevation = 8.dp,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(28.dp),
                color = Color(0xFFF5F5F5),
                tonalElevation = 0.dp
            ) {
                TextField(
                    value = text,
                    onValueChange = onTextChange,
                    placeholder = {
                        Text(
                            "Ask about oral health…",
                            color = TextMuted,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = PrimaryOrange
                    ),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextPrimary),
                    maxLines = 5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Send / Loading button
            val sendEnabled = text.isNotBlank() && !isGenerating
            val buttonAlpha by animateFloatAsState(
                if (sendEnabled) 1f else 0.4f,
                label = "sendAlpha"
            )

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(if (sendEnabled) 6.dp else 0.dp, CircleShape)
                    .background(
                        Brush.linearGradient(
                            if (sendEnabled) listOf(PrimaryOrange, SecondaryOrange)
                            else listOf(TextMuted, TextMuted)
                        ),
                        CircleShape
                    )
                    .clickable(
                        enabled = sendEnabled,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onSend() },
                contentAlignment = Alignment.Center
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────
//  Empty / Welcome State
// ─────────────────────────────────────────────────────────
@Composable
fun ChatEmptyState(onPromptClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Hero icon with glow ring
        Box(contentAlignment = Alignment.Center) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        Brush.radialGradient(listOf(PrimaryOrange.copy(alpha = 0.15f), Color.Transparent)),
                        CircleShape
                    )
            )
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .shadow(12.dp, CircleShape)
                    .background(
                        Brush.linearGradient(listOf(PrimaryOrange, SecondaryOrange)),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "How can ORCare AI\nhelp you today?",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary,
                textAlign = TextAlign.Center,
                letterSpacing = (-1).sp,
                lineHeight = 36.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ask me anything about oral health,\ndiseases, symptoms, or home remedies.",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Suggestion chips in a 2-column grid
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            chatSuggestions.chunked(2).forEach { rowChips ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    rowChips.forEach { chip ->
                        SuggestionChipItem(
                            chip = chip,
                            modifier = Modifier.weight(1f),
                            onClick = { onPromptClick(chip.text) }
                        )
                    }
                    // If odd number, fill remaining space
                    if (rowChips.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun SuggestionChipItem(
    chip: SuggestionChip,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 3.dp,
        tonalElevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, GrayBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(chip.emoji, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = chip.text,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 18.sp
                ),
                maxLines = 2
            )
        }
    }
}

// ─────────────────────────────────────────────────────────
//  Animated Chat Bubble (with slide-in)
// ─────────────────────────────────────────────────────────
@Composable
fun AnimatedChatBubble(message: ChatMessage) {
    val isUser = message.isFromUser
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(initialOffsetY = { 40 }) + fadeIn(tween(300))
    ) {
        ChatBubble(message = message, isUser = isUser)
    }
}

// ─────────────────────────────────────────────────────────
//  Chat Bubble
// ─────────────────────────────────────────────────────────
@Composable
fun ChatBubble(message: ChatMessage, isUser: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        // AI avatar
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .shadow(4.dp, CircleShape)
                    .background(
                        Brush.linearGradient(listOf(PrimaryOrange, SecondaryOrange)),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = "AI",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 300.dp)
        ) {
            // Bubble
            Surface(
                shape = if (isUser)
                    RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 5.dp)
                else
                    RoundedCornerShape(topStart = 5.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp),
                color = Color.Transparent,
                shadowElevation = if (isUser) 6.dp else 2.dp,
                tonalElevation = 0.dp
            ) {
                Box(
                    modifier = Modifier.background(
                        if (isUser)
                            Brush.linearGradient(listOf(PrimaryOrange, Color(0xFFFF6B35)))
                        else
                            Brush.linearGradient(listOf(Color.White, Color(0xFFFAFAFA)))
                    )
                ) {
                    // Parse **bold** markdown
                    val annotated = buildAnnotatedString {
                        val parts = message.text.split("**")
                        parts.forEachIndexed { i, part ->
                            if (i % 2 == 1) {
                                withStyle(
                                    androidx.compose.ui.text.SpanStyle(fontWeight = FontWeight.ExtraBold)
                                ) { append(part) }
                            } else {
                                append(part)
                            }
                        }
                    }
                    Text(
                        text = annotated,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = if (isUser) Color.White else TextPrimary,
                            lineHeight = 22.sp,
                            fontWeight = if (isUser) FontWeight.Medium else FontWeight.Normal
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

            // Label
            if (!isUser) {
                Text(
                    "ORCare Assistant",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextMuted,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    ),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        // User spacer
        if (isUser) {
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────
//  Typing Indicator (3 animated dots)
// ─────────────────────────────────────────────────────────
@Composable
fun TypingIndicatorBubble() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .shadow(4.dp, CircleShape)
                .background(
                    Brush.linearGradient(listOf(PrimaryOrange, SecondaryOrange)),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.SmartToy,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        Surface(
            shape = RoundedCornerShape(topStart = 5.dp, topEnd = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp),
            color = Color.White,
            shadowElevation = 2.dp,
            tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    BouncingDot(delayMs = index * 150)
                }
            }
        }
    }
}

@Composable
private fun BouncingDot(delayMs: Int) {
    val infiniteTransition = rememberInfiniteTransition(label = "dot_$delayMs")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(450, delayMillis = delayMs, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotY_$delayMs"
    )
    Box(
        modifier = Modifier
            .offset(y = offsetY.dp)
            .size(8.dp)
            .background(PrimaryOrange.copy(alpha = 0.7f), CircleShape)
    )
}
