package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.MemberListViewHolder
import com.giby78king.merch.Model.Member
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel

class MemberAdapter(private var inputData: MutableList<Member>,private var vmTopProductDepositoryViewModel: VmTopProductDepositoryViewModel) :
    RecyclerView.Adapter<MemberListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        return MemberListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_member, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {
        holder.bind(inputData[position],vmTopProductDepositoryViewModel)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}