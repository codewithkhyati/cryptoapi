package com.alois.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alois.myapplication.R
import com.alois.myapplication.model.CryptoCurrency
import com.bumptech.glide.Glide

class CryptoAdapter(private val cryptoList: List<CryptoCurrency>) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    inner class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImage: ImageView = itemView.findViewById(R.id.cryptoIcon)
        val nameText: TextView = itemView.findViewById(R.id.cryptoName)
        val priceText: TextView = itemView.findViewById(R.id.cryptoPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_crypto, parent, false)
        return CryptoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = cryptoList[position]
        holder.nameText.text = crypto.name
        holder.priceText.text = String.format("$%.2f", crypto.current_price)

        // Use Glide to load the  icon from image URL
        Glide.with(holder.itemView)
            .load(crypto.image)
            .into(holder.iconImage)
    }
        //get listofsize
    override fun getItemCount(): Int = cryptoList.size
}
