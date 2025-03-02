package com.alois.myapplication.database

import android.content.Context
import com.alois.myapplication.CryptoCurrencyEntity
import com.alois.myapplication.model.CryptoCurrency
import com.alois.myapplication.service.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.System.currentTimeMillis

/**
 * This class manage cryptocurrency data.
 * It checks the local Room cache and fetches from the network if the cache is expired.
 */
class CryptoRepository(private val context: Context) {

    // Define cache expiration duration (1 day = 86,400,000 milliseconds)
    private val CACHE_EXPIRATION = 86_400_000L

    private val cryptoDao = CryptoDatabase.getDatabase(context).cryptoDao()

    /**
     * Checks if the cache is expired based on the timestamp.
     */
    private fun isCacheExpired(timestamp: Long): Boolean {
        return (currentTimeMillis() - timestamp) > CACHE_EXPIRATION
    }

    ////Returns a list of data from cache CryptoCurrency.

    suspend fun getCryptoData(): List<CryptoCurrency> {
        return withContext(Dispatchers.IO) {
            // Retrieve cached data from Room
            val cachedData = cryptoDao.getAllCrpt()

            // If cache exists and is not expired, return cached data
            if (cachedData.isNotEmpty() && !isCacheExpired(cachedData.first().cacheTimestamp)) {
                cachedData.map { entity ->
                    // Convert Room entity to domain model
                    CryptoCurrency(
                        id = entity.id,
                        symbol = entity.symbol,
                        name = entity.name,
                        image = entity.image,
                        current_price = entity.current_price,
                        market_cap = entity.market_cap,
                        market_cap_rank = entity.market_cap_rank,
                        total_volume = entity.total_volume,
                        high_24h = entity.high_24h,
                        low_24h = entity.low_24h,
                        price_change_24h = entity.price_change_24h,
                        price_change_percentage_24h = entity.price_change_percentage_24h
                    )
                }
            } else {
                try {
                    // Fetch fresh data
                    val response = RetrofitInstance.api.getTopCryptocurrencies()

                    // Update the cache: clear old data and insert new data with current timestamp
                    cryptoDao.deleteAllCrpt()
                    val currentTime = currentTimeMillis()
                    val entityList = response.map { crypto ->
                        CryptoCurrencyEntity(
                            id = crypto.id,
                            symbol = crypto.symbol,
                            name = crypto.name,
                            image = crypto.image,
                            current_price = crypto.current_price,
                            market_cap = crypto.market_cap,
                            market_cap_rank = crypto.market_cap_rank,
                            total_volume = crypto.total_volume,
                            high_24h = crypto.high_24h,
                            low_24h = crypto.low_24h,
                            price_change_24h = crypto.price_change_24h,
                            price_change_percentage_24h = crypto.price_change_percentage_24h,
                            cacheTimestamp = currentTime
                        )
                    }
                    cryptoDao.insertCryptos(entityList)
                    response
                } catch (e: Exception) {
                    e.printStackTrace()
                    // If API call fails, fallback to cached data if available
                    if (cachedData.isNotEmpty()) {
                        cachedData.map { entity ->
                            CryptoCurrency(
                                id = entity.id,
                                symbol = entity.symbol,
                                name = entity.name,
                                image = entity.image,
                                current_price = entity.current_price,
                                market_cap = entity.market_cap,
                                market_cap_rank = entity.market_cap_rank,
                                total_volume = entity.total_volume,
                                high_24h = entity.high_24h,
                                low_24h = entity.low_24h,
                                price_change_24h = entity.price_change_24h,
                                price_change_percentage_24h = entity.price_change_percentage_24h
                            )
                        }
                    } else {
                        // no cached data is available
                        throw e
                    }
                }
            }
        }
    }
}
