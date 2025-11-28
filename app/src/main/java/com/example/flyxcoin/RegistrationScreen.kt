package com.example.flyxcoin

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flyxcoin.ui.theme.FuturisticColors
import com.example.flyxcoin.ui.theme.PoppinsFontFamily

@Composable
fun RegistrationScreen(navController: NavController, authViewModel: AuthViewModel = viewModel()) {
    val settingsViewModel: SettingsViewModel = viewModel()
    val language by settingsViewModel.getLanguage().collectAsState()
    val isDark = isSystemInDarkTheme()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        authViewModel.authEvents.collect {
            when(it) {
                is AuthEvent.RegistrationSuccess -> {
                    Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
                    navController.navigate("crypto_list") {
                        popUpTo("login") { inclusive = true }
                    }
                }
                is AuthEvent.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isDark) {
                    Brush.verticalGradient(
                        colors = listOf(
                            FuturisticColors.Background,
                            Color(0xFF1A1F4D),
                            FuturisticColors.Background
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8F9FA),
                            Color(0xFFE8EAED),
                            Color(0xFFF8F9FA)
                        )
                    )
                }
            )
    ) {
        if (isDark) {
            AnimatedRegisterOrbs()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RegisterLogoSection()
            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        if (isDark) {
                            Brush.linearGradient(
                                colors = listOf(
                                    FuturisticColors.Primary.copy(alpha = 0.5f),
                                    FuturisticColors.Secondary.copy(alpha = 0.5f)
                                )
                            )
                        } else {
                            Brush.linearGradient(
                                colors = listOf(
                                    FuturisticColors.Primary.copy(alpha = 0.3f),
                                    FuturisticColors.Secondary.copy(alpha = 0.3f)
                                )
                            )
                        },
                        RoundedCornerShape(24.dp)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) {
                        FuturisticColors.CardBackground.copy(alpha = 0.8f)
                    } else {
                        Color.White
                    }
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = if (isDark) 0.dp else 8.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        Strings.get("create_account", language),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = FuturisticColors.TextPrimary
                    )
                    Text(
                        Strings.get("create_subtitle", language),
                        fontSize = 14.sp,
                        fontFamily = PoppinsFontFamily,
                        color = FuturisticColors.TextSecondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(28.dp))

                    RegisterTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = Strings.get("username", language),
                        leadingIcon = Icons.Filled.Person
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    RegisterTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = Strings.get("email_address", language),
                        leadingIcon = Icons.Filled.Email,
                        keyboardType = KeyboardType.Email
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    RegisterTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = Strings.get("password", language),
                        leadingIcon = Icons.Filled.Lock,
                        isPassword = true,
                        passwordVisible = passwordVisible,
                        onPasswordVisibilityToggle = { passwordVisible = !passwordVisible }
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    RegisterTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = Strings.get("confirm_password", language),
                        leadingIcon = Icons.Filled.Lock,
                        isPassword = true,
                        passwordVisible = confirmPasswordVisible,
                        onPasswordVisibilityToggle = { confirmPasswordVisible = !confirmPasswordVisible }
                    )
                    Spacer(modifier = Modifier.height(28.dp))

                    if (uiState is AuthUiState.Loading) {
                        Box(modifier = Modifier.fillMaxWidth().height(56.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = FuturisticColors.Primary, strokeWidth = 3.dp)
                        }
                    } else {
                        RegisterButton(
                            text = Strings.get("sign_up", language),
                            onClick = {
                                if (password == confirmPassword) {
                                    authViewModel.signUp(email, password, username)
                                } else {
                                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "${Strings.get("already_have_account", language)} ",
                            fontFamily = PoppinsFontFamily,
                            fontSize = 14.sp,
                            color = FuturisticColors.TextSecondary
                        )
                        TextButton(onClick = { navController.popBackStack() }, contentPadding = PaddingValues(0.dp)) {
                            Text(
                                Strings.get("login", language),
                                fontFamily = PoppinsFontFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = FuturisticColors.Primary
                            )
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.TopEnd)) {
            SettingsButton(settingsViewModel)
        }
    }
}

@Composable
fun AnimatedRegisterOrbs() {
    val infiniteTransition = rememberInfiniteTransition(label = "register_orbs")
    val orb1Offset by infiniteTransition.animateFloat(initialValue = 0f, targetValue = 120f, animationSpec = infiniteRepeatable(animation = tween(4500, easing = LinearEasing), repeatMode = RepeatMode.Reverse), label = "orb1_register")
    val orb2Offset by infiniteTransition.animateFloat(initialValue = 0f, targetValue = -90f, animationSpec = infiniteRepeatable(animation = tween(5500, easing = LinearEasing), repeatMode = RepeatMode.Reverse), label = "orb2_register")

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.align(Alignment.TopStart).offset(x = -30.dp, y = orb1Offset.dp).size(220.dp).background(FuturisticColors.Secondary.copy(alpha = 0.1f), CircleShape))
        Box(modifier = Modifier.align(Alignment.BottomEnd).offset(x = 40.dp, y = orb2Offset.dp).size(240.dp).background(FuturisticColors.Primary.copy(alpha = 0.1f), CircleShape))
    }
}

@Composable
fun RegisterLogoSection() {
    val scale = remember { Animatable(0.8f) }
    LaunchedEffect(Unit) { scale.animateTo(targetValue = 1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)) }
    Box(modifier = Modifier.size(80.dp).scale(scale.value).background(Brush.radialGradient(colors = listOf(FuturisticColors.Secondary.copy(alpha = 0.3f), Color.Transparent)), CircleShape).padding(3.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(3.dp, Brush.linearGradient(colors = listOf(FuturisticColors.Secondary, FuturisticColors.Primary)), CircleShape)
                .background(FuturisticColors.Surface, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Filled.PersonAdd, contentDescription = null, modifier = Modifier.size(36.dp), tint = FuturisticColors.Secondary)
        }
    }
}

@Composable
fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityToggle: (() -> Unit)? = null
) {
    val isDark = isSystemInDarkTheme()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                fontFamily = PoppinsFontFamily,
                fontSize = 14.sp,
                color = if (isDark) FuturisticColors.TextSecondary else Color(0xFF6B7280)
            )
        },
        leadingIcon = {
            Icon(
                leadingIcon,
                contentDescription = null,
                tint = FuturisticColors.Primary
            )
        },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { onPasswordVisibilityToggle?.invoke() }) {
                    Icon(
                        if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = if (isDark) FuturisticColors.TextSecondary else Color(0xFF6B7280)
                    )
                }
            }
        } else null,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                FuturisticColors.Primary.copy(alpha = if (isDark) 0.3f else 0.2f),
                RoundedCornerShape(16.dp)
            )
            .background(
                if (isDark) Color.Black.copy(alpha = 0.2f) else Color(0xFFF8F9FA),
                RoundedCornerShape(16.dp)
            ),
        colors = TextFieldDefaults.colors(
            focusedTextColor = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36),
            unfocusedTextColor = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36),
            cursorColor = FuturisticColors.Primary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedLabelColor = FuturisticColors.Primary,
            unfocusedLabelColor = if (isDark) FuturisticColors.TextSecondary else Color(0xFF6B7280)
        )
    )
}

@Composable
fun RegisterButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(Brush.horizontalGradient(colors = listOf(FuturisticColors.Secondary, FuturisticColors.Accent))),
            contentAlignment = Alignment.Center
        ) {
            Text(text, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = PoppinsFontFamily, color = Color.White)
        }
    }
}