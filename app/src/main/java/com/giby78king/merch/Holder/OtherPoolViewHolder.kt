package com.giby78king.merch.Holder

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolTradeEditOtherAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Trade.Companion.ddlOtherList
import com.giby78king.merch.Model.Trade.Companion.tradeOtherList
import com.giby78king.merch.R


class OtherPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val res: Resources = v.context.resources

    fun bind(item: String) {
        if (ddlOtherList.filter { it.id == item }.isNotEmpty()) {
            val data = ddlOtherList.filter { it.id == item }[0]

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

                if (tradeOtherList.contains(data.id))            //排除重複點選
                {
                    tradeOtherList.remove(data.id)
                }
                val rvAddPoolOther =
                    itemView.rootView.findViewById<RecyclerView>(R.id.rvAddPoolOther)

                var sortList = mutableListOf<String>()
                ddlOtherList.sortedBy { it.id }.toMutableList().forEach { chd ->
                    tradeOtherList.forEach { act ->
                        if (act == chd.id) {
                            sortList.add(act)
                        }
                    }
                }

                rvAddPoolOther.adapter = PoolTradeEditOtherAdapter(sortList)
            }
        }
    }
}