package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.ProductDepositoryListViewHolder
import com.giby78king.merch.Model.ProductDepository
import com.giby78king.merch.R

class ProductDepositoryAdapter(private var inputData: MutableList<ProductDepository>) :
    RecyclerView.Adapter<ProductDepositoryListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDepositoryListViewHolder {
        return ProductDepositoryListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_productdepository, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: ProductDepositoryListViewHolder, position: Int) {
        holder.bind(inputData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}