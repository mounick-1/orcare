package com.simats.orcare.ui.theme

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.simats.orcare.ui.components.*

object MotionTokens {
    // Easing
    val EaseOut = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f) // Standard efficient ease-out
    val SoftOvershoot = CubicBezierEasing(0.34f, 1.56f, 0.64f, 1.0f) // Soft bounce for logo

    // Durations
    const val DurationShort = 300
    const val DurationMedium = 500
    const val DurationLong = 800
    const val DurationExtraLong = 1200

    // Delays
    const val DelayShort = 100
    const val DelayMedium = 300
    const val StaggerDelay = 60

    // Transitions
    val ScreenEnterTransition = tween<Float>(durationMillis = DurationMedium, easing = EaseOut)
    val ScreenExitTransition = tween<Float>(durationMillis = DurationMedium, easing = EaseOut)

    // Breathing Animation
    const val BreathingDuration = 4000
    const val BreathingScaleTarget = 1.03f

    // Values
    val SlideUpDistance = 14.dp
    val SlideUpDistanceSmall = 10.dp
    val SlideDownDistanceSmall = 12.dp
}
