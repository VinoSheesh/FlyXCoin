// File: app/src/main/java/com/example/flyxcoin/ui/theme/FuturisticColors.kt
package com.example.flyxcoin.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object FuturisticColors {
    // Dark Theme Colors
    private val DarkBackground = Color(0xFF0A0E27)
    private val DarkSurface = Color(0xFF141B3D)
    private val DarkCardBackground = Color(0xFF141B3D)
    private val DarkTextPrimary = Color.White
    private val DarkTextSecondary = Color(0xFF9CA3AF) // Increased brightness for better visibility

    // Light Theme Colors - Improved for better visibility
    private val LightBackground = Color(0xFFF8F9FA)
    private val LightSurface = Color(0xFFFFFFFF)
    private val LightCardBackground = Color(0xFFFFFFFF)
    private val LightTextPrimary = Color(0xFF1A1F36)
    private val LightTextSecondary = Color(0xFF6B7280)

    // Common Colors (same for both themes)
    private val CommonPrimary = Color(0xFF00F5FF)
    private val CommonSecondary = Color(0xFF7C3AED)
    private val CommonAccent = Color(0xFFFF00FF)
    private val CommonSuccess = Color(0xFF00FF88)
    private val CommonError = Color(0xFFFF3366)

    val Primary: Color
        @Composable get() = CommonPrimary

    val Secondary: Color
        @Composable get() = CommonSecondary

    val Accent: Color
        @Composable get() = CommonAccent

    val Success: Color
        @Composable get() = CommonSuccess

    val Error: Color
        @Composable get() = CommonError

    val Background: Color
        @Composable get() = if (isSystemInDarkTheme()) DarkBackground else LightBackground

    val Surface: Color
        @Composable get() = if (isSystemInDarkTheme()) DarkSurface else LightSurface

    val CardBackground: Color
        @Composable get() = if (isSystemInDarkTheme()) DarkCardBackground else LightCardBackground

    val TextPrimary: Color
        @Composable get() = if (isSystemInDarkTheme()) DarkTextPrimary else LightTextPrimary

    val TextSecondary: Color
        @Composable get() = if (isSystemInDarkTheme()) DarkTextSecondary else LightTextSecondary
}