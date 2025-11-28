package com.example.flyxcoin.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// --- Color definitions moved here for guaranteed resolution ---
// Palette for Dark Theme
private val DarkPrimary = Color(0xFF4C6FFF)
private val DarkOnPrimary = Color.White
private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val DarkOnSurface = Color(0xFFE0E0E0)
private val DarkOnSurfaceVariant = Color(0xFFBDBDBD)
private val DarkError = Color(0xFFFF6B6B)

// Palette for Light Theme
private val LightPrimary = Color(0xFF3B5BFF)
private val LightOnPrimary = Color.White
private val LightBackground = Color(0xFFF7F9FC)
private val LightSurface = Color.White
private val LightOnSurface = Color(0xFF333333)
private val LightOnSurfaceVariant = Color(0xFF666666)
private val LightError = Color(0xFFD93030)
// --- End of color definitions ---

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    background = DarkBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurfaceVariant,
    error = DarkError,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    background = LightBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    onSurfaceVariant = LightOnSurfaceVariant,
    error = LightError,
    onError = Color.White
)

@Composable
fun FlyXCoinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
