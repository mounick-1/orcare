package com.simats.orcare.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.orcare.ui.components.ORCareButton
import com.simats.orcare.ui.theme.*
import com.simats.orcare.ui.components.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first
import com.simats.orcare.data.UserPreferences
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val strings = LocalORCareStrings.current
    val pages = listOf(
        OnboardingPage(
            title = strings.onboarding1Title,
            description = strings.onboarding1Desc,
            icon = Icons.Rounded.Forum,
            iconBackgroundColor = PrimaryOrange
        ),
        OnboardingPage(
            title = strings.onboarding2Title,
            description = strings.onboarding2Desc,
            icon = Icons.Rounded.HealthAndSafety,
            iconBackgroundColor = PrimaryOrange
        ),
        OnboardingPage(
            title = strings.onboarding3Title,
            description = strings.onboarding3Desc,
            icon = Icons.Rounded.School,
            iconBackgroundColor = PrimaryOrange
        ),
        OnboardingPage(
            title = strings.onboarding4Title,
            description = strings.onboarding4Desc,
            icon = Icons.Rounded.Public,
            iconBackgroundColor = PrimaryOrange
        ),
        OnboardingPage(
            title = strings.onboarding5Title,
            description = strings.onboarding5Desc,
            icon = Icons.Rounded.NotificationsActive,
            iconBackgroundColor = PrimaryOrange
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    
    suspend fun getNextDestination(): String {
        return if (userPreferences.authToken.first() != null) "home" else "sign_in"
    }

    GradientBackground {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.statusBarsPadding().height(16.dp))
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Skip Button
                Box(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = pagerState.currentPage < pages.size - 1,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        TextButton(onClick = { 
                            scope.launch {
                                userPreferences.saveOnboardingCompleted(true)
                                val destination = getNextDestination()
                                navController.navigate(destination) { popUpTo(0) { inclusive = true } } 
                            }
                        }) {
                            Text(
                                text = strings.skip, 
                                color = PrimaryOrange, 
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(0.2f))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { pageIndex ->
                    OnboardingSlide(pages[pageIndex])
                }

                // Pager Indicator and Continue Button
                Column(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        repeat(pages.size) { index ->
                            val isSelected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .height(8.dp)
                                    .width(if (isSelected) 32.dp else 8.dp)
                                    .background(
                                        color = if (isSelected) PrimaryOrange else PrimaryOrange.copy(alpha = 0.2f),
                                        shape = CircleShape
                                    )
                            )
                        }
                    }

                    ORCareButton(
                        text = if (pagerState.currentPage == pages.size - 1) strings.getStarted else strings.continueLabel,
                        onClick = {
                            if (pagerState.currentPage < pages.size - 1) {
                                scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                            } else {
                                scope.launch {
                                    userPreferences.saveOnboardingCompleted(true)
                                    val destination = getNextDestination()
                                    navController.navigate(destination) { popUpTo(0) }
                                }
                            }
                        }
                    )
                }
            }
            
            SIMATSFooter(modifier = Modifier.navigationBarsPadding())
        }
    }

}

data class OnboardingPage(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val iconBackgroundColor: Color
)

@Composable
fun OnboardingSlide(page: OnboardingPage) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ORCareGlassCard(
            modifier = Modifier.size(140.dp),
            shape = CircleShape,
            elevation = 0.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = page.icon,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = PrimaryOrange
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary,
                letterSpacing = (-0.5).sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = TextSecondary,
                lineHeight = 26.sp,
                fontWeight = FontWeight.Medium
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

