package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.TradeDetailEditSpecificationViewHolder
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.R
import com.giby78king.merch.ui.TradeEditPage

class TradeDetailEditSpecificationAdapter(
    private var inputData: MutableList<TradeDetail>,
    private var page: TradeEditPage
) :
    RecyclerView.Adapter<TradeDetailEditSpecificationViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TradeDetailEditSpecificationViewHolder {
        return TradeDetailEditSpecificationViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_trade_specification, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: TradeDetailEditSpecificationViewHolder, position: Int) {
        holder.bind(inputData[position], page)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}