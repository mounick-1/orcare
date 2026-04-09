package com.simats.orcare.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.data.LearningRepository
import com.simats.orcare.data.QuizQuestion
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleQuizScreen(navController: NavController, moduleId: String?) {
    val module = remember(moduleId) {
        LearningRepository.getModule(moduleId ?: "")
    }

    if (module == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Module not found", color = TextPrimary)
        }
        return
    }

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var showResults by remember { mutableStateOf(false) }
    val userAnswers = remember { mutableStateMapOf<Int, Int>() } // questionIndex -> selectedOptionIndex

    val context = androidx.compose.ui.platform.LocalContext.current
    val viewModel: com.simats.orcare.ui.viewmodel.AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = com.simats.orcare.ui.viewmodel.AuthViewModel.Factory(
            context.applicationContext as android.app.Application,
            com.simats.orcare.data.UserPreferences(context)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quiz: ${module.title}",
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
    ) { paddingValues: PaddingValues ->
        GradientBackground {
            Box(Modifier.padding(paddingValues)) {

            if (showResults) {
                QuizResults(
                    score = score,
                    total = module.quiz.size,
                    onRetake = {
                        score = 0
                        showResults = false
                        userAnswers.clear()
                        currentQuestionIndex = 0
                    },
                    onBack = { navController.popBackStack() }
                )
            } else {
                val currentQuestion = module.quiz[currentQuestionIndex]
                
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Progress Indicator
                    LinearProgressIndicator(
                        progress = { (currentQuestionIndex + 1).toFloat() / module.quiz.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = PrimaryOrange,
                        trackColor = PrimaryOrange.copy(alpha = 0.1f)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Question ${currentQuestionIndex + 1} of ${module.quiz.size}",
                        style = MaterialTheme.typography.labelLarge.copy(color = TextSecondary)
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    QuizQuestionCard(
                        question = currentQuestion,
                        selectedOption = userAnswers[currentQuestionIndex],
                        onOptionSelected = { optionIndex ->
                            userAnswers[currentQuestionIndex] = optionIndex
                        }
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (currentQuestionIndex > 0) {
                            OutlinedButton(
                                onClick = { currentQuestionIndex-- },
                                modifier = Modifier.weight(1f).height(56.dp),
                                shape = Shapes.medium,
                                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryOrange)
                            ) {
                                Text("Previous", color = PrimaryOrange)
                            }
                        }
                        
                        Button(
                            onClick = {
                                if (currentQuestionIndex < module.quiz.size - 1) {
                                    currentQuestionIndex++
                                } else {
                                    // Calculate score
                                    score = 0
                                    module.quiz.forEachIndexed { index, question ->
                                        if (userAnswers[index] == question.correctAnswerIndex) {
                                            score++
                                        }
                                    }
                                    showResults = true
                                }
                            },
                            enabled = userAnswers[currentQuestionIndex] != null,
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = Shapes.medium,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                        ) {
                            Text(if (currentQuestionIndex < module.quiz.size - 1) "Next" else "Finish")
                        }
                    }
                }
            }
            }
        }
    }
}

@Composable
fun QuizQuestionCard(
    question: QuizQuestion,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        question.options.forEachIndexed { index, option ->
            val isSelected = selectedOption == index
            Surface(
                onClick = { onOptionSelected(index) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = Shapes.medium,
                color = if (isSelected) PrimaryOrange else SurfaceWhite,
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = if (isSelected) PrimaryOrange else TextMuted
                ),
                shadowElevation = if (isSelected) 8.dp else 0.dp
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                color = if (isSelected) SurfaceWhite else Color.Transparent,
                                shape = CircleShape
                            )
                            .border(
                                width = 2.dp,
                                color = if (isSelected) SurfaceWhite else TextMuted,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(PrimaryOrange, CircleShape)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) SurfaceWhite else TextPrimary
                        )
                    )
                }
        }
    }
}
}

@Composable
fun QuizResults(score: Int, total: Int, onRetake: () -> Unit, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            color = SuccessGreen.copy(alpha = 0.1f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Quiz Completed!",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Congratulations on finishing the module quiz.",
            style = MaterialTheme.typography.bodyLarge.copy(color = TextSecondary),
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = Shapes.large,
            colors = CardDefaults.cardColors(containerColor = PrimaryOrange.copy(alpha = 0.05f))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "YOUR SCORE",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = PrimaryOrange,
                        letterSpacing = 2.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$score",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary
                        )
                    )
                    Text(
                        text = "/$total",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = TextSecondary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(56.dp))
        
        ORCareButton(
            text = "Retake Quiz",
            onClick = onRetake,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Back to Module", color = TextSecondary)
        }
    }
}
