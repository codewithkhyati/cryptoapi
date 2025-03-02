package com.alois.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey

// Room entity for caching cryptocurrency data
@Entity(tableName = "crypto_currancy_tbl")
data class CryptoCurrencyEntity(
    @PrimaryKey(autoGenerate = false)
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
    val price_change_percentage_24h: Double,
    val cacheTimestamp: Long
)
