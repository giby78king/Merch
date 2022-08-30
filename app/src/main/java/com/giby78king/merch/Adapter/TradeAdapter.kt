package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.ProductDepositoryListViewHolder
import com.giby78king.merch.Holder.TradeListViewHolder
import com.giby78king.merch.Model.ProductDepository
import com.giby78king.merch.Model.Trade
import com.giby78king.merch.R

class TradeAdapter(private var inputData: MutableList<Trade>) :
    RecyclerView.Adapter<TradeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeListViewHolder {
        return TradeListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_trade, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: TradeListViewHolder, position: Int) {
        holder.bind(inputData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}