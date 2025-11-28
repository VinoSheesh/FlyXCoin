package com.example.flyxcoin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape // <-- FIX: Import CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush // <-- FIX: Import Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight // <-- FIX: Import FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flyxcoin.ui.theme.PoppinsFontFamily

@Composable
fun ThemeLanguageButtons(
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit,
    currentLanguage: String,
    onLanguageChange: (String) -> Unit,
    theme: AppTheme
) {
    var showLanguageMenu by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Theme Button
        IconButton(
            onClick = { onThemeChange(!isDarkMode) },
            modifier = Modifier
                .background(theme.cardBackground, RoundedCornerShape(12.dp))
                .border(1.dp, theme.primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                .size(40.dp)
        ) {
            Icon(
                if (isDarkMode) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                contentDescription = "Toggle Theme",
                tint = theme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Language Button
        Box {
            IconButton(
                onClick = { showLanguageMenu = !showLanguageMenu },
                modifier = Modifier
                    .background(theme.cardBackground, RoundedCornerShape(12.dp))
                    .border(1.dp, theme.primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .size(40.dp)
            ) {
                Icon(
                    Icons.Filled.Language,
                    contentDescription = "Change Language",
                    tint = theme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            DropdownMenu(
                expanded = showLanguageMenu,
                onDismissRequest = { showLanguageMenu = false },
                modifier = Modifier.background(theme.surface)
            ) {
                DropdownMenuItem(
                    text = { Text("English", fontFamily = PoppinsFontFamily, color = theme.textPrimary) },
                    onClick = {
                        onLanguageChange("en")
                        showLanguageMenu = false
                    },
                    modifier = if (currentLanguage == "en")
                        Modifier.background(theme.primary.copy(alpha = 0.1f))
                    else Modifier
                )
                DropdownMenuItem(
                    text = { Text("Bahasa Indonesia", fontFamily = PoppinsFontFamily, color = theme.textPrimary) },
                    onClick = {
                        onLanguageChange("id")
                        showLanguageMenu = false
                    },
                    modifier = if (currentLanguage == "id")
                        Modifier.background(theme.primary.copy(alpha = 0.1f))
                    else Modifier
                )
            }
        }
    }
}

@Composable
fun Header(username: String, theme: AppTheme) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Brush.radialGradient(colors = listOf(theme.primary.copy(alpha = 0.3f), Color.Transparent)), CircleShape)
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(2.dp, theme.primary, CircleShape)
                        .background(theme.surface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(username.firstOrNull()?.uppercase() ?: "U", fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = PoppinsFontFamily, color = theme.primary)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Welcome Back", fontSize = 12.sp, fontFamily = PoppinsFontFamily, color = theme.textSecondary)
                Text(username, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = PoppinsFontFamily, color = theme.textPrimary)
            }
        }
    }
}