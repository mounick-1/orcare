package com.simats.orcare.ui.theme

import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.orcare.ui.components.*

// ── Medical Orange-Mint Theme Colors ──────────────────────────────
val PrimaryOrange = Color(0xFFFF8C42)     // #FF8C42 (Warm sunset orange)
val SecondaryOrange = Color(0xFFFFB877)   // #FFB877 (Soft peach)
val AccentMint = Color(0xFF34D399)        // #34D399 (Vibrant Emerald Mint from logo)
val LightMint = Color(0xFFECFDF5)         // #ECFDF5 (Ultra light mint)
val NeutralBackground = Color(0xFFF9FAFB) // #F9FAFB
val AccentYellow = Color(0xFFFACC15)     // #FACC15

// ── Gradients ───────────────────────────────────────────────────────
val OrangeGradientStart = PrimaryOrange
val OrangeGradientEnd = SecondaryOrange
val MintGradientStart = Color(0xFF10B981)
val MintGradientEnd = Color(0xFF6EE7B7)

// ── Surface & UI (Glassmorphism Support) ──────────────────────────────
val SurfaceWhite = Color(0xFFFFFFFF)
val GlassWhite = Color(0xB3FFFFFF)        // 70% opacity for glass effect
val GlassBorder = Color(0x33FFFFFF)       // 20% white border
val GrayBorder = Color(0xFFE5E7EB)

// ── Text Colors ─────────────────────────────────────────────────────
val TextPrimary = Color(0xFF1F2937)       // Deep charcoal
val TextSecondary = Color(0xFF4B5563)     // Resilient grey
val TextMuted = Color(0xFF9CA3AF)         // Light grey
val TextWhite = Color(0xFFFFFFFF)

// ── Status / Feedback ───────────────────────────────────────────────
val ErrorRed = Color(0xFFEF4444)
val SuccessGreen = Color(0xFF10B981)

// ── Legacy Aliases (to prevent breaking existing screens) ─────────────
val PrimaryCoral = PrimaryOrange
val SunrisePink = PrimaryOrange
val SunrisePeach = SecondaryOrange
val SunsetPeach = SecondaryOrange
val HealthGreen = AccentMint
val MintAccent = AccentMint
val MintLight = LightMint
val IndigoPrimary = PrimaryOrange
val IndigoLight = LightMint
val SlateMuted = TextMuted
val SlateLight = GrayBorder
val SlateDark = TextPrimary
val GradientStart = OrangeGradientStart
val GradientEnd = OrangeGradientEnd
val BubbleWhite = Color(0x1AFFFFFF)