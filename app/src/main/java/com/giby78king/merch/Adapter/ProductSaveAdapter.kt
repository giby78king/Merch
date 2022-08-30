package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.ProductSaveListViewHolder
import com.giby78king.merch.Model.ProductDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmProductSaveViewModel

class ProductSaveAdapter(
    private var inputData: MutableList<ProductDetail>,
    private var vmProductSaveViewModel: VmProductSaveViewModel
) :
    RecyclerView.Adapter<ProductSaveListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSaveListViewHolder {
        return ProductSaveListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_product_save, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: ProductSaveListViewHolder, position: Int) {
        holder.bind(inputData[position],vmProductSaveViewModel)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}