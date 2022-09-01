package com.giby78king.merch.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.ChannelDetailPoolViewHolder
import com.giby78king.merch.Holder.MemberPoolViewHolder
import com.giby78king.merch.R


class PoolGroupAdapter(private var inputData: MutableList<String>) :
    RecyclerView.Adapter<MemberPoolViewHolder>() {

    private var context: Context? = null

    interface IOnItemClickListener<T> {
        fun onItemClick(t: T)
    }

    private lateinit var itemClickListener: IOnItemClickListener<String>

    fun setOnItemClickListener(itemClickListener: IOnItemClickListener<String>) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberPoolViewHolder {
        this.context = parent.context
        return MemberPoolViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tag_delete_item, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: MemberPoolViewHolder, position: Int) {
        holder.bind(inputData[position])
    }
}

class PoolChannelDetailAdapter(private var inputData: MutableList<String>) :
    RecyclerView.Adapter<ChannelDetailPoolViewHolder>() {

    private var context: Context? = null

    interface IOnItemClickListener<T> {
        fun onItemClick(t: T)
    }

    private lateinit var itemClickListener: IOnItemClickListener<String>

    fun setOnItemClickListener(itemClickListener: IOnItemClickListener<String>) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelDetailPoolViewHolder {
        this.context = parent.context
        return ChannelDetailPoolViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_activity_selected_channeldetail, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: ChannelDetailPoolViewHolder, position: Int) {
        holder.bind(inputData[position])
    }
}