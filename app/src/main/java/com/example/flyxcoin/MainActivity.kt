package com.example.flyxcoin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.flyxcoin.ui.theme.FlyXCoinTheme

// Define Poppins Font Family
val PoppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_medium, FontWeight.Medium),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)

// Modern Color Palette
object FuturisticColors {
    val Background = Color(0xFF0A0E27)
    val Surface = Color(0xFF141B3D)
    val CardBackground = Color(0xFF1A2351)
    val Primary = Color(0xFF00F5FF)
    val Secondary = Color(0xFF7C3AED)
    val Accent = Color(0xFFFF00FF)
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFB4B4C8)
    val Success = Color(0xFF00FF88)
    val Error = Color(0xFFFF3366)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            FlyXCoinTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = FuturisticColors.Background
                ) {
                    NavGraph()
                }
            }
        }
    }
}
