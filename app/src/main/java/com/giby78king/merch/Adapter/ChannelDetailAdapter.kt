package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.ChannelDetailListViewHolder
import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel

class ChannelDetailAdapter(private var inputData: MutableList<ChannelDetail>, private var vmTopProductDepositoryViewModel: VmTopProductDepositoryViewModel) :
    RecyclerView.Adapter<ChannelDetailListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelDetailListViewHolder {
        return ChannelDetailListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_channeldetail, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: ChannelDetailListViewHolder, position: Int) {
        holder.bind(inputData[position],vmTopProductDepositoryViewModel)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}