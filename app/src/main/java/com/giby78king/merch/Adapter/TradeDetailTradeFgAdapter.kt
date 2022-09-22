package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.TradeDetailTradeFgListViewHolder
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.R

class TradeDetailTradeFgAdapter(private var inputData: MutableList<TradeDetail>) :
    RecyclerView.Adapter<TradeDetailTradeFgListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeDetailTradeFgListViewHolder {
        return TradeDetailTradeFgListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_tradedetail_specification, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: TradeDetailTradeFgListViewHolder, position: Int) {
        holder.bind(inputData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}