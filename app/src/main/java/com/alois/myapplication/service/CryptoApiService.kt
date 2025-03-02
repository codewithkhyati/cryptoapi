package com.alois.myapplication.service

import com.alois.myapplication.model.CryptoCurrency
import retrofit2.http.GET
import retrofit2.http.Query
////CoinGecko API to fetch cryptocurrency data
interface CryptoApiService {
    @GET("coins/markets")
        suspend fun getTopCryptocurrencies(
        @Query("vs_currency") vsCurrency: String = "usd",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 5,
        @Query("page") page: Int = 1,
        @Query("sparkline") sparkline: Boolean = false
    ): List<CryptoCurrency>
}
