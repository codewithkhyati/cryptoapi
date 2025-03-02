package com.alois.myapplication
import com.alois.myapplication.model.CryptoCurrency
import com.alois.myapplication.service.CryptoApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ApiServiceTest {

    @Test
    fun testgetMarketDataAPIcall() = runBlocking {
        // Arrange
        val mockApiService = mock(CryptoApiService::class.java)
        val mockCoins = listOf(
            CryptoCurrency(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                image = "https://path_to_image",
                current_price = 45000.0,
                market_cap = 850000000000L,
                market_cap_rank = 1,
                total_volume = 35000000000.0,
                high_24h = 46000.0,
                low_24h = 44000.0,
                price_change_24h = 500.0,
                price_change_percentage_24h = 1.12
            )
        )
        `when`(mockApiService.getTopCryptocurrencies("usd", "market_cap_desc", 5, 1, false))
            .thenReturn(mockCoins)

        // Act
        val result = mockApiService.getTopCryptocurrencies("usd", "market_cap_desc", 5, 1, false)

        // Assert
        assertEquals(mockCoins, result)
    }
}
