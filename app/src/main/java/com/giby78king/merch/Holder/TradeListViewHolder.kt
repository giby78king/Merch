package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.TradeDetailTradeFgAdapter
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList

import com.giby78king.merch.Model.Trade
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.Model.TradeDetail.Companion.dbTradeDetailList

import com.giby78king.merch.R
import com.giby78king.merch.ui.TradeEditPage


class TradeListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val txtDate: TextView = v.findViewById(R.id.txtDate)
    private val txtAmount: TextView = v.findViewById(R.id.txtAmount)
    private val txtChannelDetail: TextView = v.findViewById(R.id.txtChannelDetail)

    private val rv_list_item_specification: RecyclerView =
        v.findViewById(R.id.rv_list_item_specification)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: Trade) {

        txtDate.text = data.date
        txtChannelDetail.text = dbChannelDetailList.first { it.id == data.channelDetail }.name

        var totalAmount = 0
        var list = mutableListOf<TradeDetail>()

        if (data.tradeDetail.isNotEmpty()) {
            data.tradeDetail.forEach { tradeDetailId ->
                val tradeDetailInfo = dbTradeDetailList.firstOrNull { it.id == tradeDetailId }
                if (tradeDetailInfo != null) {
                    totalAmount += tradeDetailInfo.price
                    list.add(tradeDetailInfo)
                }
            }
        }

        txtAmount.text = totalAmount.toString()
        list = list.sortedByDescending {it.price }.toMutableList()

        val layoutManager = LinearLayoutManager(parentView.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val numberOfColumns = 5
        rv_list_item_specification.layoutManager =
            GridLayoutManager(parentView.context, numberOfColumns)
        rv_list_item_specification.adapter = TradeDetailTradeFgAdapter(list)

        itemView.setOnClickListener {

            var intent = Intent(itemView.context, TradeEditPage::class.java)

            val bundle = Bundle()
            bundle.putString("selectedTrade", data.id)
            intent.putExtras(bundle)

            itemView.context.startActivity(intent)
        }

    }
}