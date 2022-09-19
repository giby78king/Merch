package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.StockDepositoryListViewHolder
import com.giby78king.merch.Model.StockDepository
import com.giby78king.merch.R

class StockDepositoryAdapter(private var inputData: MutableList<StockDepository>) :
    RecyclerView.Adapter<StockDepositoryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockDepositoryListViewHolder {
        return StockDepositoryListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_productdepository, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: StockDepositoryListViewHolder, position: Int) {
        holder.bind(inputData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}