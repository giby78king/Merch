package com.giby78king.merch.Holder

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolTradeEditModifyAdapter
import com.giby78king.merch.Adapter.PoolTradeEditSpecModifyAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Trade.Companion.ddlModifyList
import com.giby78king.merch.Model.Trade.Companion.tradeModifyList
import com.giby78king.merch.Model.TradeDetail.Companion.specModifyList
import com.giby78king.merch.Model.TradeDetail.Companion.tempSpecList
import com.giby78king.merch.Model.tempPriceDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTradeViewModel
import com.giby78king.merch.ui.ProductEditPage
import com.giby78king.merch.ui.TradeEditPage


class ModifyPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val res: Resources = v.context.resources

    fun bind(item: String, page: TradeEditPage) {

        val vmTradeViewModel = ViewModelProvider(page)[VmTradeViewModel::class.java]

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

                val index = tradeModifyList.indexOf(data.id)

                if (index != -1)            //排除重複點選
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

                    tempSpecList.forEach {
                        if(it.id!="addOne" && it.modify.size>0) {
                            val priceFix = it.modify[index]
                            it.modify.removeAt(index)
                            it.price -= priceFix
                        }
                    }

                    val priceFix = specModifyList[index].price
                    specModifyList[specModifyList.size - 1].price -= priceFix
                    specModifyList.removeAt(index)

                    rvAddPoolSpecModify.adapter =
                        PoolTradeEditSpecModifyAdapter(specModifyList, page)

                    vmTradeViewModel.setSelectedSpecModify()
                }
            }
        }
    }
}