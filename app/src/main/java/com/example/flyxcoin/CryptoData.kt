package com.example.flyxcoin

import com.google.gson.annotations.SerializedName

data class CryptoData(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerializedName("current_price")
    val current_price: Double,
    @SerializedName("price_change_percentage_24h")
    val price_change_percentage_24h: Double,
    @SerializedName("market_cap")
    val market_cap: Double?,
    @SerializedName("market_cap_rank")
    val market_cap_rank: Int?,
    @SerializedName("high_24h")
    val high_24h: Double?,
    @SerializedName("low_24h")
    val low_24h: Double?,
    @SerializedName("total_volume")
    val total_volume: Double?,
    @SerializedName("circulating_supply")
    val circulating_supply: Double?
)
