package com.giby78king.merch.Holder

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolTradeEditOtherAdapter
import com.giby78king.merch.Adapter.PoolTradeEditSpecModifyAdapter
import com.giby78king.merch.Adapter.PoolTradeEditSpecOtherAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Trade
import com.giby78king.merch.Model.Trade.Companion.ddlOtherList
import com.giby78king.merch.Model.Trade.Companion.tradeOtherList
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.Model.TradeDetail.Companion.specOtherList
import com.giby78king.merch.Model.TradeDetail.Companion.tempSpecList
import com.giby78king.merch.Model.tempPriceDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTradeViewModel
import com.giby78king.merch.ui.TradeEditPage


class OtherPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val res: Resources = v.context.resources

    fun bind(item: String, page: TradeEditPage) {

        val vmTradeViewModel = ViewModelProvider(page)[VmTradeViewModel::class.java]

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

                val index = tradeOtherList.indexOf(data.id)

                if (index != -1)            //排除重複點選
                {
                    tradeOtherList.remove(data.id)
                }
                val rvAddPoolOther =
                    page.findViewById<RecyclerView>(R.id.rvAddPoolOther)

                var sortList = mutableListOf<String>()
                ddlOtherList.sortedBy { it.id }.toMutableList().forEach { chd ->
                    tradeOtherList.forEach { act ->
                        if (act == chd.id) {
                            sortList.add(act)
                        }
                    }
                }

                rvAddPoolOther.adapter = PoolTradeEditOtherAdapter(sortList, page)

                val rvAddPoolSpecOther =
                    page.findViewById<RecyclerView>(R.id.rvAddPoolSpecOther)

                if (rvAddPoolSpecOther != null) {

                    tempSpecList.forEach {
                        if(it.id!="addOne" && it.other.size>0) {
                            val priceFix = it.other[index]
                            it.other.removeAt(index)
                            it.price += priceFix
                        }
                    }

                    val priceFix = specOtherList[index].price
                    specOtherList[specOtherList.size - 1].price += priceFix
                    specOtherList.removeAt(index)

                    rvAddPoolSpecOther.adapter = PoolTradeEditSpecOtherAdapter(specOtherList, page)

                    vmTradeViewModel.setSelectedSpecOther()
                }
            }
        }
    }
}