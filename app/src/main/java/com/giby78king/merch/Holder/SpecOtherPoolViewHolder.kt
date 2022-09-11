package com.giby78king.merch.Holder

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolTradeEditOtherAdapter
import com.giby78king.merch.Adapter.PoolTradeEditSpecOtherAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.EditAmountSetting
import com.giby78king.merch.Model.Trade.Companion.ddlOtherList
import com.giby78king.merch.Model.Trade.Companion.tradeOtherList
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.Model.TradeDetail.Companion.specOtherList
import com.giby78king.merch.Model.tempPriceDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTradeViewModel
import com.giby78king.merch.ui.TradeEditPage


class SpecOtherPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val txtName: TextView = v.findViewById(R.id.txtName)
    val editPrice: EditText = v.findViewById(R.id.editPrice)
    
    val res: Resources = v.context.resources

    fun bind(item: tempPriceDetail, page: TradeEditPage) {

        val rvAddPoolOther =
            itemView.rootView.findViewById<RecyclerView>(R.id.rvAddPoolOther)
        val rvAddPoolSpecOther =
            page.findViewById<RecyclerView>(R.id.rvAddPoolSpecOther)
        
        val vmTradeViewModel = ViewModelProvider(page)[VmTradeViewModel::class.java]
        
        if (ddlOtherList.filter { it.id == item.rule }.isNotEmpty()) {

            val data = ddlOtherList.filter { it.id == item.rule }[0]

            txtName.text = data.name

            ImgSetting().setImage("traderule", res, imgChannelDetail, data.id)

            itemView.setOnLongClickListener {

                //todo ask to delete
                if (tradeOtherList.contains(data.id))            //排除重複點選
                {
                    tradeOtherList.remove(data.id)
                }

                var sortList = mutableListOf<String>()
                ddlOtherList.sortedBy { it.id }.toMutableList().forEach { chd ->
                    tradeOtherList.forEach { act ->
                        if (act == chd.id) {
                            sortList.add(act)
                        }
                    }
                }

                rvAddPoolOther.adapter = PoolTradeEditOtherAdapter(sortList,page)
                
                specOtherList.clear()

                tradeOtherList.forEach {
                    specOtherList.add(
                        tempPriceDetail(
                            price = 0,
                            rule = it
                        )
                    )
                }
                rvAddPoolSpecOther.adapter =
                    PoolTradeEditSpecOtherAdapter(specOtherList, page)
                true
            }
        }
        
        editPrice.setText(item.price.toString())

        if (item.rule == "sum") {
            ImgSetting().setImage("traderule", res, imgChannelDetail, "sum")
            txtName.text = "小計"
            editPrice.isFocusableInTouchMode = false;
            editPrice.isFocusable = false;
        } else {
            val index = specOtherList.indexOfFirst { it.rule == item.rule }
            val indexSum = specOtherList.indexOfFirst { it.rule == "sum" }

            editPrice.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun afterTextChanged(editable: Editable) {
                    EditAmountSetting().editNoDollarRule(editPrice, this)
                    if (editPrice.text.toString().isEmpty()) {
                        specOtherList[index].price = 0
                    } else {
                        specOtherList[index].price =
                            editPrice.text.toString().replace(",", "").toInt()
                        var sum = 0
                        specOtherList.filter { it.rule != "sum" }.forEach {
                            sum += it.price
                        }
                        specOtherList[indexSum].price = sum
                        vmTradeViewModel.setSelectedSpecOther()
                    }
                }
            })
        }
    }
}