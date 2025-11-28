package com.example.flyxcoin

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppTheme(
    val background: Color,
    val surface: Color,
    val cardBackground: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val primary: Color,
    val secondary: Color
)

val DarkTheme = AppTheme(
    background = Color(0xFF0A0E27),
    surface = Color(0xFF141B3D),
    cardBackground = Color(0xFF1A2351),
    textPrimary = Color.White,
    textSecondary = Color(0xFFB4B4C8),
    primary = Color(0xFF00F5FF),
    secondary = Color(0xFF7C3AED)
)

val LightTheme = AppTheme(
    background = Color(0xFFF5F5F7),
    surface = Color(0xFFFFFFFF),
    cardBackground = Color(0xFFFAFAFC),
    textPrimary = Color(0xFF1A1A1A),
    textSecondary = Color(0xFF666666),
    primary = Color(0xFF0084FF),
    secondary = Color(0xFF6C5CE7)
)

val LocalTheme = compositionLocalOf { DarkTheme }
