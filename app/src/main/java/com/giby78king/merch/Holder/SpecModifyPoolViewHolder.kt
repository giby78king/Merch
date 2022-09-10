package com.giby78king.merch.Holder

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolTradeEditModifyAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.DdlNormalModel
import com.giby78king.merch.Model.Trade.Companion.ddlModifyList
import com.giby78king.merch.Model.Trade.Companion.tradeModifyList
import com.giby78king.merch.R


class SpecModifyPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val txtName:TextView = v.findViewById(R.id.txtName)
    val res: Resources = v.context.resources

    fun bind(item: String) {

        if (ddlModifyList.filter { it.id == item }.isNotEmpty()) {

            val data = ddlModifyList.filter { it.id == item }[0]

            txtName.text = data.name

            ImgSetting().setImage("traderule", res, imgChannelDetail, data.id)

            itemView.setOnLongClickListener {

                //todo ask to delete
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

                rvAddPoolModify.adapter = PoolTradeEditModifyAdapter(sortList)

                true
            }
        }

        if (item == "sum") {
            ImgSetting().setImage("traderule", res, imgChannelDetail, "sum")
            txtName.text = "小計"
        }
    }
}