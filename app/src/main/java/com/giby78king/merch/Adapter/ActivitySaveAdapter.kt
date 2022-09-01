package com.giby78king.merch.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.ActivitySaveListViewHolder
import com.giby78king.merch.Model.Activity
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmChannelDetailSaveViewModel

class ActivitySaveAdapter(private var inputData: MutableList<Activity>, private var vmChannelDetailSaveViewModel: VmChannelDetailSaveViewModel) :
    RecyclerView.Adapter<ActivitySaveListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitySaveListViewHolder {
        return ActivitySaveListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_channeldetail_save, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: ActivitySaveListViewHolder, position: Int) {
        holder.bind(inputData[position],vmChannelDetailSaveViewModel)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}