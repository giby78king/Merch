package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.ProductListViewHolder
import com.giby78king.merch.Model.Product
import com.giby78king.merch.R

class ProductAdapter(private var inputData: MutableList<Product>) :
    RecyclerView.Adapter<ProductListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_product, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(inputData[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}