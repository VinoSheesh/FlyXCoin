package com.example.flyxcoin

object Strings {
    private val en = mapOf(
        // Login Screen
        "welcome_back" to "Welcome Back",
        "login_to_account" to "Login to your account",
        "email_address" to "Email Address",
        "password" to "Password",
        "login" to "Login",
        "dont_have_account" to "Don't have an account?",
        "sign_up" to "Sign Up",

        // Registration Screen
        "create_account" to "Create Account",
        "create_subtitle" to "Sign up to get started",
        "username" to "Username",
        "confirm_password" to "Confirm Password",
        "already_have_account" to "Already have an account?",

        // Crypto List Screen
        "market_overview" to "Market Overview",
        "live" to "Live",
        "welcome" to "Welcome",
        "search_crypto" to "Search cryptocurrency...",

        // Crypto Detail Screen
        "details" to "Details",
        "current_price" to "Current Price",
        "change_24h" to "24h Change",
        "price_chart" to "Price Chart",
        "market_statistics" to "Market Statistics",
        "market_cap" to "Market Cap",
        "volume_24h" to "24h Volume",
        "high_24h" to "24h High",
        "low_24h" to "24h Low",
        "circulating_supply" to "Circulating Supply",
        "buy" to "Buy",
        "sell" to "Sell"
    )

    private val id = mapOf(
        // Login Screen
        "welcome_back" to "Selamat Datang Kembali",
        "login_to_account" to "Masuk ke akun Anda",
        "email_address" to "Alamat Email",
        "password" to "Kata Sandi",
        "login" to "Masuk",
        "dont_have_account" to "Tidak punya akun?",
        "sign_up" to "Daftar",

        // Registration Screen
        "create_account" to "Buat Akun",
        "create_subtitle" to "Daftar untuk memulai",
        "username" to "Nama Pengguna",
        "confirm_password" to "Konfirmasi Kata Sandi",
        "already_have_account" to "Sudah punya akun?",

        // Crypto List Screen
        "market_overview" to "Tinjauan Pasar",
        "live" to "Langsung",
        "welcome" to "Selamat Datang",
        "search_crypto" to "Cari cryptocurrency...",

        // Crypto Detail Screen
        "details" to "Detail",
        "current_price" to "Harga Saat Ini",
        "change_24h" to "Perubahan 24j",
        "price_chart" to "Grafik Harga",
        "market_statistics" to "Statistik Pasar",
        "market_cap" to "Kapitalisasi Pasar",
        "volume_24h" to "Volume 24j",
        "high_24h" to "Tertinggi 24j",
        "low_24h" to "Terendah 24j",
        "circulating_supply" to "Pasokan Beredar",
        "buy" to "Beli",
        "sell" to "Jual"
    )

    fun get(key: String, language: String): String {
        return (if (language == "id") id[key] else en[key]) ?: ""
    }
}
