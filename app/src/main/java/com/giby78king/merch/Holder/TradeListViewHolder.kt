package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.giby78king.merch.Model.TextAmountSetting
import com.giby78king.merch.Model.Trade

import com.giby78king.merch.R


class TradeListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val txtDate: TextView = v.findViewById(R.id.txtDate)
    private val txtAmount: TextView = v.findViewById(R.id.txtAmount)
    private val txtChannelDetail: TextView = v.findViewById(R.id.txtChannelDetail)

    private val rv_list_item_member: RecyclerView = v.findViewById(R.id.rv_list_item_member)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: Trade) {

        txtDate.text = data.date
        var totalAmount = 0
//        data.amount.forEach {
//            if (it != null) {
//                totalAmount += it
//            }
//        }
//        txtAmount.text = TextAmountSetting().formatAmountNoDollar(totalAmount.toString())
//        txtChannelDetail.text = data.channelDetail
//
//        itemView.setOnClickListener {
//            Toast.makeText(it.context, "Click", Toast.LENGTH_SHORT).show()
//        }
//
//        var list = mutableListOf<TradeInner>()
//
//        var index = 0
//        data.holdingMember.forEach {
//            list.add(
//                TradeInner(
//                    amount = data.amount[index]!!,
//                    holdingAmount = data.holdingAmount[index]!!,
//                    holdingMember = data.holdingMember[index]!!,
//                    imgUrl = data.imgUrl[index]!!,
//                    product = data.product[index]!!,
//                )
//            )
//            index++
//        }

//        val layoutManager = LinearLayoutManager(parentView.context)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        val numberOfColumns = 1
//        rv_list_item_member.layoutManager = GridLayoutManager(parentView.context, numberOfColumns)
//        rv_list_item_member.adapter = TradeInnerAdapter(list)

    }
}