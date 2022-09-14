package com.giby78king.merch.Holder

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolTradeEditModifyAdapter
import com.giby78king.merch.Adapter.PoolTradeEditSpecModifyAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Trade.Companion.ddlModifyList
import com.giby78king.merch.Model.Trade.Companion.tradeModifyList
import com.giby78king.merch.Model.TradeDetail.Companion.specModifyList
import com.giby78king.merch.Model.tempPriceDetail
import com.giby78king.merch.R
import com.giby78king.merch.ui.ProductEditPage
import com.giby78king.merch.ui.TradeEditPage


class ModifyPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val res: Resources = v.context.resources

    fun bind(item: String, page: TradeEditPage) {

        if (ddlModifyList.filter { it.id == item }.isNotEmpty()) {
            val data = ddlModifyList.filter { it.id == item }[0]

            ImgSetting().setImage("traderule", res, imgChannelDetail, data.id)

            itemView.setOnLongClickListener {

                val copy: ClipboardManager =
                    it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                copy.setText(data.name)

                Toast.makeText(
                    it.context,
                    "已複製:" + data.name,
                    Toast.LENGTH_SHORT
                )
                    .show()
                true
            }


            itemView.setOnClickListener {

                if (tradeModifyList.contains(data.id))            //排除重複點選
                {
                    tradeModifyList.remove(data.id)
                }
                val rvAddPoolModify =
                    itemView.rootView.findViewById<RecyclerView>(R.id.rvAddPoolModify)

                var sortList = mutableListOf<String>()
                ddlModifyList.sortedBy { it.id }.toMutableList().forEach { chd ->
                    tradeModifyList.forEach { act ->
                        if (act == chd.id) {
                            sortList.add(act)
                        }
                    }
                }
                rvAddPoolModify.adapter = PoolTradeEditModifyAdapter(sortList, page)

                val rvAddPoolSpecModify =
                    page.findViewById<RecyclerView>(R.id.rvAddPoolSpecModify)

                if (rvAddPoolSpecModify != null) {
                    specModifyList.clear()
                    tradeModifyList.forEach {
                        specModifyList.add(
                            tempPriceDetail(
                                price= 0,
                                rule = it
                            )
                        )
                    }
                    if (specModifyList.none { it.rule == "sum" }) {
                        specModifyList.add(
                            tempPriceDetail(
                                price = 0,
                                rule = "sum"
                            )
                        )
                    }
                    rvAddPoolSpecModify.adapter =
                        PoolTradeEditSpecModifyAdapter(specModifyList, page)
                }
            }
        }
    }
}