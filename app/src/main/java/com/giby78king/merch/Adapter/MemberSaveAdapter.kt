package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.MemberSaveListViewHolder
import com.giby78king.merch.Model.Member
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmMemberSaveViewModel

class MemberSaveAdapter(private var inputData: MutableList<Member>, private var vmMemberSaveViewModel: VmMemberSaveViewModel) :
    RecyclerView.Adapter<MemberSaveListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberSaveListViewHolder {
        return MemberSaveListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_member_save, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: MemberSaveListViewHolder, position: Int) {
        holder.bind(inputData[position],vmMemberSaveViewModel)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}