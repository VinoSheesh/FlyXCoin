package com.example.flyxcoin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.coingecko.com/api/v3/"

    val api: CryptoApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }
}

class CryptoRepository {
    suspend fun getPrices(): List<CryptoData> {
        return RetrofitInstance.api.getCryptoPrices()
    }

    suspend fun getMarketChart(coinId: String): MarketChartResponse {
        return RetrofitInstance.api.getMarketChart(coinId)
    }
}
