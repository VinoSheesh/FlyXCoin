package com.example.flyxcoin

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.flyxcoin.ui.theme.FuturisticColors
import com.example.flyxcoin.ui.theme.PoppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoDetailScreen(navController: NavController, viewModel: CryptoDetailViewModel = viewModel()) {
    val settingsViewModel: SettingsViewModel = viewModel()
    val language by settingsViewModel.getLanguage().collectAsState()

    val coinDetails by viewModel.coinDetails.collectAsState()
    var selectedTimeFrame by remember { mutableStateOf("1D") }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        coinDetails?.name ?: Strings.get("details", language),
                        fontFamily = PoppinsFontFamily
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Back",
                            tint = FuturisticColors.TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FuturisticColors.Background.copy(alpha = 0.8f)
                )
            )
        },
        containerColor = FuturisticColors.Background
    ) { paddingValues ->
        val crypto = coinDetails
        if (crypto != null) {
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()){
                AnimatedDetailOrbs()
                Column(modifier = Modifier.fillMaxSize().verticalScroll(scrollState)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CryptoHeader(crypto)
                    Spacer(modifier = Modifier.height(24.dp))
                    PriceCard(crypto, language)
                    Spacer(modifier = Modifier.height(24.dp))
                    ChartSection(crypto, selectedTimeFrame, language) { newTimeFrame ->
                        selectedTimeFrame = newTimeFrame
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    StatsSection(crypto, language)
                    Spacer(modifier = Modifier.height(24.dp))
                    ActionButtons(language)
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = FuturisticColors.Primary)
            }
        }
    }
}

@Composable
fun AnimatedDetailOrbs() {
    val infiniteTransition = rememberInfiniteTransition(label = "detail_orbs")
    val orb1Offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 120f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orb1"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .offset(x = (-60).dp, y = orb1Offset.dp)
                .size(250.dp)
                .background(FuturisticColors.Primary.copy(alpha = 0.15f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 60.dp, y = (-orb1Offset).dp)
                .size(280.dp)
                .background(Color(0xFF7C3AED).copy(alpha = 0.15f), CircleShape)
        )
    }
}

@Composable
fun CryptoHeader(crypto: CryptoData) {
    val isDark = isSystemInDarkTheme()

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(FuturisticColors.Primary.copy(alpha = 0.3f), Color.Transparent)
                    ),
                    CircleShape
                )
                .padding(4.dp)
        ) {
            AsyncImage(
                model = crypto.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(2.dp, FuturisticColors.Primary.copy(alpha = 0.4f), CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                crypto.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily,
                color = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36)
            )
            Box(
                modifier = Modifier
                    .background(FuturisticColors.Primary.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    crypto.symbol.uppercase(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = PoppinsFontFamily,
                    color = FuturisticColors.Primary
                )
            }
        }
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFFDE047).copy(alpha = 0.2f),
                            Color(0xFFFFA500).copy(alpha = 0.2f)
                        )
                    ),
                    RoundedCornerShape(12.dp)
                )
                .border(1.dp, Color(0xFFFDE047).copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                "#${crypto.market_cap_rank ?: "N/A"}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily,
                color = Color(0xFFFDE047)
            )
        }
    }
}

@Composable
fun PriceCard(crypto: CryptoData, language: String) {
    val isDark = isSystemInDarkTheme()
    val changeColor = if (crypto.price_change_percentage_24h >= 0)
        FuturisticColors.Success else FuturisticColors.Error

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(
                1.dp,
                Brush.linearGradient(
                    colors = listOf(
                        FuturisticColors.Primary.copy(alpha = if (isDark) 0.25f else 0.2f),
                        Color(0xFF7C3AED).copy(alpha = if (isDark) 0.25f else 0.2f)
                    )
                ),
                RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) {
                FuturisticColors.CardBackground.copy(alpha = 0.75f)
            } else {
                Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDark) 0.dp else 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                Strings.get("current_price", language),
                fontSize = 13.sp,
                fontFamily = PoppinsFontFamily,
                color = if (isDark) FuturisticColors.TextSecondary else Color(0xFF6B7280)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "$${String.format("%,.2f", crypto.current_price)}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFontFamily,
                    color = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36)
                )
                Box(
                    modifier = Modifier
                        .background(changeColor.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            if (crypto.price_change_percentage_24h >= 0)
                                Icons.Filled.TrendingUp
                            else
                                Icons.Filled.TrendingDown,
                            contentDescription = null,
                            tint = changeColor,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "${if (crypto.price_change_percentage_24h >= 0) "+" else ""}${String.format("%.2f", crypto.price_change_percentage_24h)}%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = PoppinsFontFamily,
                            color = changeColor
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                Strings.get("change_24h", language),
                fontSize = 11.sp,
                fontFamily = PoppinsFontFamily,
                color = if (isDark) {
                    FuturisticColors.TextSecondary.copy(alpha = 0.7f)
                } else {
                    Color(0xFF6B7280)
                }
            )
        }
    }
}

// LANJUTAN DARI PART 1 - Tambahkan ke file CryptoDetailScreen.kt

@Composable
fun ChartSection(crypto: CryptoData, selectedTimeFrame: String, language: String, onTimeFrameChange: (String) -> Unit) {
    val isDark = isSystemInDarkTheme()
    val timeFrames = listOf("1H", "1D", "1W", "1M", "1Y")
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                Strings.get("price_chart", language),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily,
                color = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                timeFrames.forEach { timeFrame ->
                    TimeFrameChip(
                        text = timeFrame,
                        isSelected = selectedTimeFrame == timeFrame,
                        onClick = { onTimeFrameChange(timeFrame) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isLandscape) 280.dp else 380.dp)
                .border(
                    1.dp,
                    FuturisticColors.Primary.copy(alpha = if (isDark) 0.2f else 0.15f),
                    RoundedCornerShape(18.dp)
                ),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDark) {
                    FuturisticColors.CardBackground.copy(alpha = 0.85f)
                } else {
                    Color.White
                }
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = if (isDark) 0.dp else 4.dp
            )
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                TradingViewChart(crypto.symbol, selectedTimeFrame)
            }
        }
    }
}

@Composable
fun TimeFrameChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
            .height(32.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) FuturisticColors.Primary else FuturisticColors.Surface.copy(alpha = 0.6f)
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
        border = if (!isSelected) BorderStroke(1.dp, FuturisticColors.Primary.copy(alpha = 0.3f)) else null
    ) {
        Text(
            text,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontFamily = PoppinsFontFamily,
            color = if (isSelected) Color.Black else FuturisticColors.TextSecondary
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun TradingViewChart(symbol: String, timeFrame: String) {
    val isDark = isSystemInDarkTheme()
    val cleanSymbol = symbol.uppercase().replace("USDT", "").replace("USD", "")

    val coinId = when(cleanSymbol) {
        "BTC" -> "bitcoin"
        "ETH" -> "ethereum"
        "BNB" -> "binancecoin"
        "XRP" -> "ripple"
        "ADA" -> "cardano"
        "DOGE" -> "dogecoin"
        "SOL" -> "solana"
        "TRX" -> "tron"
        "DOT" -> "polkadot"
        "MATIC" -> "matic-network"
        "LTC" -> "litecoin"
        else -> cleanSymbol.lowercase()
    }

    val days = when(timeFrame) {
        "1H" -> "1"
        "1D" -> "1"
        "1W" -> "7"
        "1M" -> "30"
        "1Y" -> "365"
        else -> "7"
    }

    val bgColor = if (isDark) "#141B3D" else "#FFFFFF"
    val textColor = if (isDark) "#B4B4C8" else "#6B7280"
    val gridColor = if (isDark) "rgba(44, 44, 74, 0.5)" else "rgba(200, 200, 220, 0.5)"
    val borderColor = if (isDark) "#2C2C4A" else "#E5E7EB"

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    databaseEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    builtInZoomControls = false
                    displayZoomControls = false
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }

                webChromeClient = object : WebChromeClient() {}
                webViewClient = WebViewClient()
                setBackgroundColor(android.graphics.Color.parseColor(bgColor))
            }
        },
        update = { webView ->
            val htmlContent = """
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <style>
        body, html { margin: 0; padding: 0; background-color: $bgColor; height: 100vh; width: 100vw; overflow: hidden; }
        #chart-container { position: absolute; top: 0; left: 0; bottom: 0; right: 0; width: 100%; height: 100%; }
        #status { position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); color: #00F5FF; font-family: sans-serif; font-size: 12px; z-index: 100; }
    </style>
    <script src="https://unpkg.com/lightweight-charts@4.1.1/dist/lightweight-charts.standalone.production.js"></script>
</head>
<body>
    <div id="status">Loading...</div>
    <div id="chart-container"></div>
    <script>
        const container = document.getElementById('chart-container');
        const statusEl = document.getElementById('status');

        async function renderChart() {
            try {
                await new Promise(r => setTimeout(r, 100));
                let width = container.clientWidth;
                let height = container.clientHeight;
                if (height === 0 || height < 50) { height = 300; container.style.height = '300px'; }
                
                container.innerHTML = '';
                
                const chart = LightweightCharts.createChart(container, {
                    width: width, height: height,
                    layout: { background: { type: 'solid', color: '$bgColor' }, textColor: '$textColor' },
                    grid: { vertLines: { color: '$gridColor' }, horzLines: { color: '$gridColor' } },
                    timeScale: { timeVisible: true, borderColor: '$borderColor' },
                    rightPriceScale: { borderColor: '$borderColor' }
                });

                const candleSeries = chart.addCandlestickSeries({
                    upColor: '#00FF88', downColor: '#FF3366', borderVisible: false, wickUpColor: '#00FF88', wickDownColor: '#FF3366'
                });

                const response = await fetch(`https://api.coingecko.com/api/v3/coins/$coinId/ohlc?vs_currency=usd&days=$days`);
                const data = await response.json();
                
                if (Array.isArray(data)) {
                    statusEl.style.display = 'none';
                    const candleData = data.map(item => ({
                        time: item[0] / 1000, open: item[1], high: item[2], low: item[3], close: item[4]
                    }));
                    candleSeries.setData(candleData);
                    chart.timeScale().fitContent();
                }

                new ResizeObserver(entries => {
                    if (entries.length === 0 || entries[0].target !== container) return;
                    const newRect = entries[0].contentRect;
                    if (newRect.height > 0) chart.applyOptions({ height: newRect.height, width: newRect.width });
                }).observe(container);

            } catch (err) { statusEl.innerText = "Chart Error (Check Internet)"; }
        }
        renderChart();
    </script>
</body>
</html>
            """.trimIndent()

            webView.loadDataWithBaseURL("https://api.coingecko.com/", htmlContent, "text/html", "UTF-8", null)
        }
    )
}

@Composable
fun StatsSection(crypto: CryptoData, language: String) {
    val isDark = isSystemInDarkTheme()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .border(1.dp, Color(0xFF7C3AED).copy(alpha = if (isDark) 0.25f else 0.2f), RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDark) {
                FuturisticColors.CardBackground.copy(alpha = 0.75f)
            } else {
                Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDark) 0.dp else 4.dp
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            Text(
                Strings.get("market_statistics", language),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = PoppinsFontFamily,
                color = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36)
            )
            Spacer(modifier = Modifier.height(20.dp))
            crypto.market_cap?.let {
                StatRow(
                    Strings.get("market_cap", language),
                    "${formatLargeNumber(it)}"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            crypto.total_volume?.let {
                StatRow(
                    Strings.get("volume_24h", language),
                    "${formatLargeNumber(it)}"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            crypto.high_24h?.let {
                StatRow(
                    Strings.get("high_24h", language),
                    "${String.format("%,.2f", it)}"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            crypto.low_24h?.let {
                StatRow(
                    Strings.get("low_24h", language),
                    "${String.format("%,.2f", it)}"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            crypto.circulating_supply?.let {
                StatRow(
                    Strings.get("circulating_supply", language),
                    formatLargeNumber(it)
                )
            }
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    val isDark = isSystemInDarkTheme()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            fontSize = 14.sp,
            fontFamily = PoppinsFontFamily,
            color = if (isDark) FuturisticColors.TextSecondary else Color(0xFF6B7280)
        )
        Text(
            value,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = PoppinsFontFamily,
            color = if (isDark) FuturisticColors.TextPrimary else Color(0xFF1A1F36)
        )
    }
}

@Composable
fun ActionButtons(language: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = { /* Handle buy */ },
            modifier = Modifier.weight(1f).height(56.dp).border(1.dp, Brush.horizontalGradient(colors = listOf(Color(0xFF00FF88), FuturisticColors.Primary)), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().background(Brush.horizontalGradient(colors = listOf(Color(0xFF00FF88), FuturisticColors.Primary))),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Icon(Icons.Filled.AddShoppingCart, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        Strings.get("buy", language),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.Black
                    )
                }
            }
        }

        Button(
            onClick = { /* Handle sell */ },
            modifier = Modifier.weight(1f).height(56.dp).border(1.dp, Brush.horizontalGradient(colors = listOf(Color(0xFFFF3366), Color(0xFFFF00FF))), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().background(Brush.horizontalGradient(colors = listOf(Color(0xFFFF3366), Color(0xFFFF00FF)))),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Icon(Icons.Filled.RemoveShoppingCart, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        Strings.get("sell", language),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White
                    )
                }
            }
        }
    }
}

fun formatLargeNumber(number: Double?): String {
    if (number == null) return "N/A"
    return when {
        number >= 1_000_000_000_000 -> String.format("%.2fT", number / 1_000_000_000_000)
        number >= 1_000_000_000 -> String.format("%.2fB", number / 1_000_000_000)
        number >= 1_000_000 -> String.format("%.2fM", number / 1_000_000)
        else -> String.format("%,.0f", number)
    }
}