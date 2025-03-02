package com.alois.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alois.myapplication.CryptoCurrencyEntity

// DAO interface to access the cached cryptocurrency data
@Dao
interface CryptoDao {

    // Retrieve all cached cryptocurrency
    @Query("SELECT * FROM crypto_currancy_tbl")
    suspend fun getAllCrpt(): List<CryptoCurrencyEntity>

    // Insert list of cryptocurrency; replace if conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCryptos(cryptoList: List<CryptoCurrencyEntity>)

    // Delete all cached
    @Query("DELETE FROM crypto_currancy_tbl")
    suspend fun deleteAllCrpt()
}
