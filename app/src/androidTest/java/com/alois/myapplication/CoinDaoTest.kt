package com.alois.myapplication// Imports
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alois.myapplication.database.CryptoDao
import com.alois.myapplication.database.CryptoDatabase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class CoinDaoTest {

    private lateinit var database: CryptoDatabase
    private lateinit var coinDao: CryptoDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CryptoDatabase::class.java
        ).build()
        coinDao = database.cryptoDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun testInsertAndGetAllCoins() = runBlocking {
        // Arrange
        val mockCoins = listOf(
            CryptoCurrencyEntity(
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
                price_change_percentage_24h = 1.12,
                cacheTimestamp = 4
            )
        )
        coinDao.insertCryptos(mockCoins)

        // Act
        val result = coinDao.getAllCrpt()

        // Assert
        assertEquals(mockCoins, result)
    }
}