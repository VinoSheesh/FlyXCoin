package com.example.flyxcoin

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.flyxcoin.ui.theme.FuturisticColors
import com.example.flyxcoin.ui.theme.PoppinsFontFamily

@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel: CryptoViewModel = viewModel()
) {
    val settingsViewModel: SettingsViewModel = viewModel()
    val language by settingsViewModel.getLanguage().collectAsState()

    // 1. Deteksi Orientasi Layar
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var searchQuery by remember { mutableStateOf("") }

    val cryptos by viewModel.cryptoList
    val isLoading by viewModel.isLoading
    val error by viewModel.error
    val username by viewModel.username

    val filteredCryptos = cryptos.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.symbol.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize().background(FuturisticColors.Background)) {
        AnimatedBackgroundGradient()

        if (isLandscape) {
            // 2. TAMPILAN LANDSCAPE (Kiri: Header/Search, Kanan: List)
            Row(modifier = Modifier.fillMaxSize()) {
                // Sisi Kiri (40%)
                Column(
                    modifier = Modifier
                        .weight(0.4f)
                        .fillMaxHeight()
                        .padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 70.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Header(username = username, language = language)
                    SearchBar(searchQuery, language) { newQuery -> searchQuery = newQuery }
                }

                // Sisi Kanan (60%)
                Box(modifier = Modifier.weight(0.6f).padding(top = 16.dp, end = 16.dp, bottom = 16.dp)) {
                    BalanceSection(
                        filteredCryptos,
                        isLoading,
                        error,
                        viewModel::fetchPrices,
                        navController,
                        language,
                        isLandscape = true
                    )
                }
            }
        } else {
            // 3. TAMPILAN PORTRAIT (Original)
            Column(modifier = Modifier.fillMaxSize()) {
                Header(username = username, language = language)
                SearchBar(searchQuery, language) { newQuery -> searchQuery = newQuery }
                Spacer(modifier = Modifier.height(16.dp))
                BalanceSection(
                    filteredCryptos,
                    isLoading,
                    error,
                    viewModel::fetchPrices,
                    navController,
                    language,
                    isLandscape = false
                )
            }
        }

        // Tombol Pengaturan
        Box(
            modifier = Modifier.align(
                if (isLandscape) Alignment.BottomStart else Alignment.TopEnd
            ).padding(
                if (isLandscape) PaddingValues(start = 16.dp, bottom = 0.dp)
                else PaddingValues(end = 0.dp, top = 0.dp)
            )
        ) {
            SettingsButton(settingsViewModel)
        }
    }
}

@Composable
fun AnimatedBackgroundGradient() {
    val infiniteTransition = rememberInfiniteTransition(label = "background_transition")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FuturisticColors.Background)
    )
}

@Composable
fun Header(username: String, language: String) {
    val isDark = isSystemInDarkTheme()

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
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                FuturisticColors.Primary.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        ), CircleShape
                    )
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(2.dp, FuturisticColors.Primary, CircleShape)
                        .background(FuturisticColors.Surface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = username.firstOrNull()?.uppercase() ?: "U",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = FuturisticColors.Primary
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = Strings.get("welcome", language),
                    fontSize = 12.sp,
                    fontFamily = PoppinsFontFamily,
                    color = if (isDark) FuturisticColors.TextSecondary else Color(0xFF6B7280)
                )
                Text(
                    text = username,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFontFamily,
                    color = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36)
                )
            }
        }
    }
}

@Composable
fun SearchBar(query: String, language: String, onQueryChanged: (String) -> Unit) {
    val isDark = isSystemInDarkTheme()

    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)) {
        TextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    FuturisticColors.Primary.copy(alpha = if (isDark) 0.3f else 0.2f),
                    RoundedCornerShape(16.dp)
                )
                .background(
                    if (isDark) {
                        FuturisticColors.CardBackground.copy(alpha = 0.5f)
                    } else {
                        Color.White
                    },
                    RoundedCornerShape(16.dp)
                ),
            placeholder = {
                Text(
                    Strings.get("search_crypto", language),
                    color = if (isDark) FuturisticColors.TextSecondary else Color(0xFF6B7280),
                    fontFamily = PoppinsFontFamily
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = FuturisticColors.Primary
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36),
                unfocusedTextColor = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36),
                cursorColor = FuturisticColors.Primary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontFamily = PoppinsFontFamily,
                fontSize = 14.sp,
                color = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36)
            )
        )
    }
}

@Composable
fun BalanceSection(
    filteredCryptos: List<CryptoData>,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    navController: NavController,
    language: String,
    isLandscape: Boolean = false
) {
    val isDark = isSystemInDarkTheme()

    // 4. Logika Bentuk Sudut (Adaptive Shape)
    val containerShape = if (isLandscape) {
        // Landscape: Lengkung di kiri
        RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp)
    } else {
        // Portrait: Lengkung di atas
        RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    }

    // Border mengikuti bentuk shape
    val borderModifier = Modifier.border(
        1.dp,
        FuturisticColors.Primary.copy(alpha = if (isDark) 0.2f else 0.15f),
        containerShape
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(containerShape)
            .background(
                if (isDark) {
                    Brush.verticalGradient(
                        colors = listOf(
                            FuturisticColors.Surface.copy(alpha = 0.95f),
                            FuturisticColors.CardBackground.copy(alpha = 0.95f)
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color(0xFFF8F9FA)
                        )
                    )
                }
            )
            .then(borderModifier)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                Strings.get("market_overview", language),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily,
                color = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36)
            )
            Box(
                modifier = Modifier
                    .background(FuturisticColors.Primary.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    Strings.get("live", language),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PoppinsFontFamily,
                    color = FuturisticColors.Primary
                )
            }
        }

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = FuturisticColors.Primary)
                }
            }
            error != null -> {
                ErrorView(error, onRetry)
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredCryptos) { crypto ->
                        BalanceItem(crypto, navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceItem(crypto: CryptoData, navController: NavController) {
    val isDark = isSystemInDarkTheme()
    val changeColor = if (crypto.price_change_percentage_24h >= 0) FuturisticColors.Success else FuturisticColors.Error
    val scale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        scale.animateTo(targetValue = 1f, animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))
    }

    Card(
        modifier = Modifier.fillMaxWidth().scale(scale.value),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) {
                FuturisticColors.CardBackground.copy(alpha = 0.6f)
            } else {
                Color.White
            }
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDark) 0.dp else 4.dp
        ),
        onClick = { navController.navigate("crypto_detail/${crypto.id}") }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    Brush.horizontalGradient(
                        colors = listOf(
                            FuturisticColors.Primary.copy(alpha = if (isDark) 0.3f else 0.2f),
                            FuturisticColors.Secondary.copy(alpha = if (isDark) 0.3f else 0.2f)
                        )
                    ),
                    RoundedCornerShape(20.dp)
                )
                .background(
                    if (isDark) {
                        Brush.horizontalGradient(
                            colors = listOf(
                                FuturisticColors.CardBackground.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        )
                    } else {
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.White,
                                Color.White
                            )
                        )
                    }
                )
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(Brush.radialGradient(colors = listOf(FuturisticColors.Primary.copy(alpha = 0.2f), Color.Transparent)), CircleShape)
                        .padding(4.dp)
                ) {
                    AsyncImage(model = crypto.image, contentDescription = null, modifier = Modifier.fillMaxSize().clip(CircleShape).border(2.dp, FuturisticColors.Primary.copy(alpha = 0.3f), CircleShape))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        crypto.name,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        fontSize = 16.sp,
                        color = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(if (crypto.price_change_percentage_24h >= 0) Icons.Filled.TrendingUp else Icons.Filled.TrendingDown, contentDescription = null, tint = changeColor, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${if (crypto.price_change_percentage_24h >= 0) "+" else ""}${String.format("%.2f", crypto.price_change_percentage_24h)}%", color = changeColor, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, fontFamily = PoppinsFontFamily)
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "$${String.format("%,.2f", crypto.current_price)}",
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        fontSize = 16.sp,
                        color = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36)
                    )
                    Text(
                        crypto.symbol.uppercase(),
                        color = if (isDark) FuturisticColors.TextSecondary else Color(0xFF6B7280),
                        fontSize = 12.sp,
                        fontFamily = PoppinsFontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorView(error: String?, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Error: $error", color = FuturisticColors.Error, fontFamily = PoppinsFontFamily, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = FuturisticColors.Primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Retry", fontFamily = PoppinsFontFamily, color = Color.Black, fontWeight = FontWeight.SemiBold)
        }
    }
}