package com.alois.myapplication.model
//models the structure of the cryptocurrency data
// * retrieved from the CoinGecko API  && easy parsing and manipulation of the data within the application.
data class CryptoCurrency(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: Double,
    val market_cap: Long,
    val market_cap_rank: Int,
    val total_volume: Double,
    val high_24h: Double,
    val low_24h: Double,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double
)
