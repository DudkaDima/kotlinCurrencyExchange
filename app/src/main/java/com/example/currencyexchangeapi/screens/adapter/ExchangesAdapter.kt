package com.example.currencyexchangeapi.screens.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangeapi.R
import com.example.currencyexchangeapi.history.ExchangesHistory

class ExchangesAdapter(
    private val context: Context,
    private val exchanges: List<ExchangesHistory>,
) : RecyclerView.Adapter<ExchangesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.exchange_history_layout, parent, false);
        return ViewHolder(layoutView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exchange = exchanges[position]

        holder.view.findViewById<TextView>(R.id.exchangesList).text = exchange.toString()
        holder.view.findViewById<ImageView>(R.id.lastActivityImage)
            .setImageDrawable(holder.view.context.getDrawable(exchange.getOperationImage()))
    }

    override fun getItemCount() = exchanges.size;

    class ViewHolder(
        val view: View,
    ) : RecyclerView.ViewHolder(view)
}