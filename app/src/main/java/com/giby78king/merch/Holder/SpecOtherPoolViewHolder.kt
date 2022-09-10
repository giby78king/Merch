package com.giby78king.merch.Holder

import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolTradeEditSpecOtherAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Trade.Companion.ddlOtherList
import com.giby78king.merch.Model.Trade.Companion.tradeOtherList
import com.giby78king.merch.R


class SpecOtherPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val txtName: TextView = v.findViewById(R.id.txtName)
    val res: Resources = v.context.resources

    fun bind(item: String) {

        if (ddlOtherList.filter { it.id == item }.isNotEmpty()) {

            val data = ddlOtherList.filter { it.id == item }[0]

            txtName.text = data.name

            ImgSetting().setImage("traderule", res, imgChannelDetail, data.id)

            itemView.setOnLongClickListener {

                //todo ask to delete
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

                rvAddPoolOther.adapter = PoolTradeEditSpecOtherAdapter(sortList)

                true
            }
        }

        if (item == "sum") {
            ImgSetting().setImage("traderule", res, imgChannelDetail, "sum")
            txtName.text = "小計"
        }
    }
}