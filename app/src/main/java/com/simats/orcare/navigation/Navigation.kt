package com.simats.orcare.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.simats.orcare.ui.screens.*
import com.simats.orcare.ui.theme.MotionTokens
import com.simats.orcare.ui.viewmodel.AuthViewModel
import com.simats.orcare.data.UserPreferences

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(500)) },
        exitTransition = { fadeOut(animationSpec = tween(500)) },
        popEnterTransition = { fadeIn(animationSpec = tween(500)) },
        popExitTransition = { fadeOut(animationSpec = tween(500)) }
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("language_selection") { LanguageSelectionScreen(navController) }
        composable("sign_in") { SignInScreen(navController) }
        composable("sign_up") { SignUpScreen(navController) }
        composable(
            route = "otp_verification?email={email}",
            arguments = listOf(
                androidx.navigation.navArgument("email") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val context = androidx.compose.ui.platform.LocalContext.current
            val application = context.applicationContext as android.app.Application
            val viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
                factory = AuthViewModel.Factory(application, UserPreferences(context))
            )
            OtpVerificationScreenV2(navController, email, viewModel)
        }


        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("reset_password_sent") { ResetPasswordSentScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("chatbot/{symptomName}") { backStackEntry ->
            val symptomName = backStackEntry.arguments?.getString("symptomName")
            ChatbotScreen(navController, symptomName)
        }
        composable("chatbot") { ChatbotScreen(navController, null) }
        composable("symptom_checker") { SymptomCheckerScreen(navController) }
        composable("tooth_pain") { ToothPainScreen(navController) }
        composable("bleeding_gums") { BleedingGumsScreen(navController) }
        composable("sensitivity") { SensitivityScreen(navController) }
        composable("ulcer") { UlcerScreen(navController) }
        composable("bad_breath") { BadBreathScreen(navController) }
        composable("swelling") { SwellingScreen(navController) }
        composable("tooth_decay") { ToothDecayScreen(navController) }
        composable("chipped_tooth") { ChippedToothScreen(navController) }
        composable("gum_recession") { GumRecessionScreen(navController) }
        composable("jaw_pain") { JawPainScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("reminders") { RemindersScreen(navController) }
        composable("daily_tips") { DailyTipsScreen(navController) }
        composable("oral_disease") { OralDiseaseScreen(navController) }
        composable("learning_center") { LearningCenterScreen(navController) }
        composable("edit_profile") { EditProfileScreen(navController) }
        composable("privacy_security") { PrivacySecurityScreen(navController) }
        composable("help_feedback") { HelpFeedbackScreen(navController) }
        composable("profile_language_selection") { ProfileLanguageScreen(navController) }
        composable("category_detail/{categoryId}") { backStackEntry ->
            LearningCategoryScreen(navController, backStackEntry.arguments?.getString("categoryId"))
        }
        composable("module_detail/{moduleId}") { backStackEntry ->
            ModuleDetailScreen(navController, backStackEntry.arguments?.getString("moduleId"))
        }
        composable("module_quiz/{moduleId}") { backStackEntry ->
            ModuleQuizScreen(navController, backStackEntry.arguments?.getString("moduleId"))
        }
        composable("symptom_detail/{symptomName}") { backStackEntry ->
            GenericSymptomDetailScreen(navController, backStackEntry.arguments?.getString("symptomName"))
        }
        composable("disease_detail/{diseaseId}") { backStackEntry ->
            DiseaseDetailScreen(navController, backStackEntry.arguments?.getString("diseaseId"))
        }
        composable("privacy_policy") { PrivacyPolicyScreen(navController) }
        composable("delete_account") { DeleteAccountScreen(navController) }
    }
}

