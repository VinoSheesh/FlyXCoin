package com.example.flyxcoin

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("registration") {
            RegistrationScreen(navController = navController)
        }
        composable("crypto_list") {
            CryptoListScreen(navController = navController)
        }
        composable(
            "crypto_detail/{cryptoId}",
            arguments = listOf(navArgument("cryptoId") { type = NavType.StringType })
        ) {
            CryptoDetailScreen(navController = navController)
        }
    }
}
