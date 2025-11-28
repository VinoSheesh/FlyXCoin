package com.example.flyxcoin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flyxcoin.ui.theme.FuturisticColors
import com.example.flyxcoin.ui.theme.PoppinsFontFamily

@Composable
fun SettingsButton(settingsViewModel: SettingsViewModel = viewModel()) {
    val currentLanguage by settingsViewModel.getLanguage().collectAsState()
    var showLanguageDialog by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()

    IconButton(
        onClick = { showLanguageDialog = true },
        modifier = Modifier
            .padding(16.dp)
            .size(48.dp)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        FuturisticColors.Primary.copy(alpha = if (isDark) 0.3f else 0.2f),
                        Color.Transparent
                    )
                ),
                CircleShape
            )
            .border(
                2.dp,
                FuturisticColors.Primary.copy(alpha = if (isDark) 0.5f else 0.4f),
                CircleShape
            )
    ) {
        Icon(
            Icons.Filled.Language,
            contentDescription = "Change Language",
            tint = FuturisticColors.Primary,
            modifier = Modifier.size(24.dp)
        )
    }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            containerColor = FuturisticColors.CardBackground,
            shape = RoundedCornerShape(20.dp),
            title = {
                Text(
                    text = if (currentLanguage == "en") "Select Language" else "Pilih Bahasa",
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = FuturisticColors.TextPrimary
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LanguageOption(
                        language = "English",
                        isSelected = currentLanguage == "en",
                        onClick = {
                            settingsViewModel.setLanguage("en")
                            showLanguageDialog = false
                        }
                    )
                    LanguageOption(
                        language = "Bahasa Indonesia",
                        isSelected = currentLanguage == "id",
                        onClick = {
                            settingsViewModel.setLanguage("id")
                            showLanguageDialog = false
                        }
                    )
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
}

@Composable
fun LanguageOption(language: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                1.dp,
                if (isSelected) FuturisticColors.Primary else FuturisticColors.Primary.copy(alpha = 0.3f),
                RoundedCornerShape(12.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected)
                FuturisticColors.Primary.copy(alpha = 0.15f)
            else
                Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = language,
            fontFamily = PoppinsFontFamily,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) FuturisticColors.Primary else FuturisticColors.TextPrimary
        )
    }
}