package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.MemberListViewHolder
import com.giby78king.merch.Holder.MemberSelectedListViewHolder
import com.giby78king.merch.Model.Member
import com.giby78king.merch.Model.ProductDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel

class MemberSelectedAdapter(private var inputData: MutableList<Member>,private var member:ProductDetail) :
    RecyclerView.Adapter<MemberSelectedListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberSelectedListViewHolder {
        return MemberSelectedListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_product_selected_member, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: MemberSelectedListViewHolder, position: Int) {
        holder.bind(inputData[position],member)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}