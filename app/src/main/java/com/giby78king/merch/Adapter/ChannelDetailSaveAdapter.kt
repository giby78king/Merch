package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.ChannelDetailSaveListViewHolder
import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmChannelDetailSaveViewModel

class ChannelDetailSaveAdapter(private var inputData: MutableList<ChannelDetail>, private var vmChannelDetailSaveViewModel: VmChannelDetailSaveViewModel) :
    RecyclerView.Adapter<ChannelDetailSaveListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelDetailSaveListViewHolder {
        return ChannelDetailSaveListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_channeldetail_save, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: ChannelDetailSaveListViewHolder, position: Int) {
        holder.bind(inputData[position],vmChannelDetailSaveViewModel)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}