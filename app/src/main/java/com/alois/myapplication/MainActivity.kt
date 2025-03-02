package com.alois.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alois.myapplication.adapter.CryptoAdapter
import com.alois.myapplication.database.CryptoRepository
import com.alois.myapplication.model.CryptoCurrency
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var cryptoAdapt: CryptoAdapter
    private lateinit var recyclerView: RecyclerView
    private val cryptoList = mutableListOf<CryptoCurrency>()

    private lateinit var repository: CryptoRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize repository for data management
        repository = CryptoRepository(applicationContext)

        // Find views from the layout
        recyclerView = findViewById(R.id.recyclerView)
        cryptoAdapt = CryptoAdapter(cryptoList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cryptoAdapt

        // Fetch data and update UI
        lifecycleScope.launch {
            try {
                val data = repository.getCryptoData()
                cryptoList.clear()
                cryptoList.addAll(data)
                cryptoAdapt.notifyDataSetChanged()


            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MainActivity, "No dat found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}