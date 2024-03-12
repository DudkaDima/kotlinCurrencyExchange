package com.example.currencyexchangeapi.screens.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchangeapi.R

class ListPersonalCurrency(
    private val context: Context,
    private val list: ArrayList<String>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        internal var tvLabel: TextView;
        init {
            tvLabel = itemView.findViewById(R.id.tvContact);
        }

        internal fun bind(position: Int) {
            tvLabel.text = list[position].toString();
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.balance_layout, parent, false
            )
        );
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position);
    }

}