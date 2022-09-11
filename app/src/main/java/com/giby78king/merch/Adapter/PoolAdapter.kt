package com.giby78king.merch.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Holder.*
import com.giby78king.merch.Model.tempPriceDetail
import com.giby78king.merch.R
import com.giby78king.merch.ui.ProductEditPage
import com.giby78king.merch.ui.TradeEditPage


//class PoolGroupAdapter(private var inputData: MutableList<String>) :
//    RecyclerView.Adapter<MemberPoolViewHolder>() {
//
//    private var context: Context? = null
//
//    interface IOnItemClickListener<T> {
//        fun onItemClick(t: T)
//    }
//
//    private lateinit var itemClickListener: IOnItemClickListener<String>
//
//    fun setOnItemClickListener(itemClickListener: IOnItemClickListener<String>) {
//        this.itemClickListener = itemClickListener
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberPoolViewHolder {
//        this.context = parent.context
//        return MemberPoolViewHolder(
//            LayoutInflater.from(parent.context).inflate(
//                R.layout.tag_delete_item, parent, false
//            )
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return inputData.size
//    }
//
//    override fun onBindViewHolder(holder: MemberPoolViewHolder, position: Int) {
//        holder.bind(inputData[position])
//    }
//}

class PoolChannelDetailInfoAdapter(private var inputData: MutableList<String>) :
    RecyclerView.Adapter<ChannelDetailInfoPoolViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelDetailInfoPoolViewHolder {
        this.context = parent.context
        return ChannelDetailInfoPoolViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_product_channeldetail, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: ChannelDetailInfoPoolViewHolder, position: Int) {
        holder.bind(inputData[position])
    }
}

class PoolChannelDetailAdapter(private var inputData: MutableList<String>) :
    RecyclerView.Adapter<ChannelDetailPoolViewHolder>() {

    private var context: Context? = null

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

class PoolProductEditGroupAdapter(private var inputData: MutableList<String>) :
    RecyclerView.Adapter<GroupPoolViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupPoolViewHolder {
        this.context = parent.context
        return GroupPoolViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_activity_selected_channeldetail, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: GroupPoolViewHolder, position: Int) {
        holder.bind(inputData[position])
    }
}

class PoolProductEditChannelDetailAdapter(private var inputData: MutableList<String>,private var page: ProductEditPage) :
    RecyclerView.Adapter<ChannelDetailPoolProductEditViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelDetailPoolProductEditViewHolder {
        this.context = parent.context
        return ChannelDetailPoolProductEditViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_activity_selected_channeldetail, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: ChannelDetailPoolProductEditViewHolder, position: Int) {
        holder.bind(inputData[position],page)
    }
}

class PoolTradeEditModifyAdapter(private var inputData: MutableList<String>,private var page: TradeEditPage) :
    RecyclerView.Adapter<ModifyPoolViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModifyPoolViewHolder {
        this.context = parent.context
        return ModifyPoolViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_activity_selected_channeldetail, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: ModifyPoolViewHolder, position: Int) {
        holder.bind(inputData[position],page)
    }
}

class PoolTradeEditOtherAdapter(private var inputData: MutableList<String>,private var page: TradeEditPage) :
    RecyclerView.Adapter<OtherPoolViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherPoolViewHolder {
        this.context = parent.context
        return OtherPoolViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_activity_selected_channeldetail, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: OtherPoolViewHolder, position: Int) {
        holder.bind(inputData[position],page)
    }
}

class PoolTradeEditSpecModifyAdapter(private var inputData: MutableList<tempPriceDetail>, private var page: TradeEditPage) :
    RecyclerView.Adapter<SpecModifyPoolViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecModifyPoolViewHolder {
        this.context = parent.context
        return SpecModifyPoolViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_trade_specification_selected_traderule_price, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: SpecModifyPoolViewHolder, position: Int) {
        holder.bind(inputData[position],page)
    }
}

class PoolTradeEditSpecOtherAdapter(private var inputData: MutableList<tempPriceDetail>,private var page:TradeEditPage) :
    RecyclerView.Adapter<SpecOtherPoolViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecOtherPoolViewHolder {
        this.context = parent.context
        return SpecOtherPoolViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_list_item_trade_specification_selected_traderule_price, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return inputData.size
    }

    override fun onBindViewHolder(holder: SpecOtherPoolViewHolder, position: Int) {
        holder.bind(inputData[position],page)
    }
}
