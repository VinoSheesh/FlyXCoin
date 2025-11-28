package com.example.flyxcoin

object LanguageManager {
    private val strings = mapOf(
        "en" to mapOf(
            "light_mode" to "Light",
            "dark_mode" to "Dark",
            "language" to "Language",
            "welcome_back" to "Welcome Back",
            "welcome" to "Welcome", // ADDED: used by Header
            "notifications" to "Notifications",
            "market_overview" to "Market Overview",
            "search_crypto" to "Search cryptocurrency...",
            "live" to "Live",
            "price_chart" to "Price Chart",
            "market_statistics" to "Market Statistics",
            "market_cap" to "Market Cap",
            "volume_24h" to "24h Volume",
            "high_24h" to "24h High",
            "low_24h" to "24h Low",
            "circulating_supply" to "Circulating Supply",
            "current_price" to "Current Price",
            "change_24h" to "24h change",
            "buy" to "Buy",
            "sell" to "Sell",
            "create_account" to "Create Account",
            "create_subtitle" to "Join FlyXCoin today",
            "username" to "Username",
            "email_address" to "Email Address",
            "password" to "Password",
            "confirm_password" to "Confirm Password",
            "sign_up" to "Sign Up",
            "already_have_account" to "Already have an account?",
            "login" to "Login",
            "login_to_account" to "Login to your account",
            "email" to "Email",
            "remember_me" to "Remember me",
            "forgot_password" to "Forgot password?",
            "sign_in" to "Sign In",
            "dont_have_account" to "Don't have an account?"
        ),
        "id" to mapOf(
            "light_mode" to "Terang",
            "dark_mode" to "Gelap",
            "language" to "Bahasa",
            "welcome_back" to "Selamat Datang Kembali",
            "welcome" to "Selamat Datang", // ADDED: used by Header
            "notifications" to "Notifikasi",
            "market_overview" to "Ikhtisar Pasar",
            "search_crypto" to "Cari cryptocurrency...",
            "live" to "Langsung",
            "price_chart" to "Grafik Harga",
            "market_statistics" to "Statistik Pasar",
            "market_cap" to "Kapitalisasi Pasar",
            "volume_24h" to "Volume 24j",
            "high_24h" to "Tertinggi 24j",
            "low_24h" to "Terendah 24j",
            "circulating_supply" to "Pasokan Beredar",
            "current_price" to "Harga Saat Ini",
            "change_24h" to "Perubahan 24j",
            "buy" to "Beli",
            "sell" to "Jual",
            "create_account" to "Buat Akun",
            "create_subtitle" to "Bergabunglah dengan FlyXCoin hari ini",
            "username" to "Nama Pengguna",
            "email_address" to "Alamat Email",
            "password" to "Kata Sandi",
            "confirm_password" to "Konfirmasi Kata Sandi",
            "sign_up" to "Daftar",
            "already_have_account" to "Sudah punya akun?",
            "login" to "Masuk",
            "email" to "Email",
            "remember_me" to "Ingat saya",
            "forgot_password" to "Lupa kata sandi?",
            "sign_in" to "Masuk",
            "dont_have_account" to "Belum punya akun?"
        )
    )

    fun get(key: String, language: String): String {
        return strings[language]?.get(key) ?: strings["en"]?.get(key) ?: key
    }
}