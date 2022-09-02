package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.PriceEditListViewHolder
import com.giby78king.merch.R
import com.giby78king.merch.ui.ProductEditPage

class PriceEditAdapter(private var inputData: MutableList<String>, private var index:Int, private var productEditPage: ProductEditPage) :
    RecyclerView.Adapter<PriceEditListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceEditListViewHolder {
        return PriceEditListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_specification_price_edit, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: PriceEditListViewHolder, position: Int) {
        holder.bind(inputData[position],index,productEditPage)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}