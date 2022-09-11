package com.giby78king.merch.Holder

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolTradeEditModifyAdapter
import com.giby78king.merch.Adapter.PoolTradeEditSpecModifyAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.*
import com.giby78king.merch.Model.Trade.Companion.ddlModifyList
import com.giby78king.merch.Model.Trade.Companion.tradeModifyList
import com.giby78king.merch.Model.TradeDetail.Companion.specModifyList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTradeViewModel
import com.giby78king.merch.ui.TradeEditPage
import kotlinx.android.synthetic.main.activity_trade_edit_page.*


class SpecModifyPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val txtName: TextView = v.findViewById(R.id.txtName)
    val editPrice: EditText = v.findViewById(R.id.editPrice)

    val res: Resources = v.context.resources

    fun bind(item: tempPriceDetail, page: TradeEditPage) {

        Log.d("SpecModifyPoolViewHolder","::"+ item.price+ item.rule)

        val rvAddPoolModify =
            itemView.rootView.findViewById<RecyclerView>(R.id.rvAddPoolModify)
        val rvAddPoolSpecModify =
            page.findViewById<RecyclerView>(R.id.rvAddPoolSpecModify)

        val vmTradeViewModel = ViewModelProvider(page)[VmTradeViewModel::class.java]

        if (ddlModifyList.filter { it.id == item.rule }.isNotEmpty()) {

            val data = ddlModifyList.filter { it.id == item.rule }[0]

            txtName.text = data.name

            ImgSetting().setImage("traderule", res, imgChannelDetail, data.id)

            itemView.setOnLongClickListener {

                //todo ask to delete
                if (tradeModifyList.contains(data.id))            //排除重複點選
                {
                    tradeModifyList.remove(data.id)
                }

                var sortList = mutableListOf<String>()
                ddlModifyList.sortedBy { it.id }.toMutableList().forEach { chd ->
                    tradeModifyList.forEach { act ->
                        if (act == chd.id) {
                            sortList.add(act)
                        }
                    }
                }

                rvAddPoolModify.adapter = PoolTradeEditModifyAdapter(sortList, page)

                specModifyList.clear()

                tradeModifyList.forEach {
                    specModifyList.add(
                        tempPriceDetail(
                            price = 0,
                            rule = it
                        )
                    )
                }
                rvAddPoolSpecModify.adapter =
                    PoolTradeEditSpecModifyAdapter(specModifyList, page)

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
            val index = specModifyList.indexOfFirst { it.rule == item.rule }
            val indexSum = specModifyList.indexOfFirst { it.rule == "sum" }

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
                        specModifyList[index].price = 0
                    } else {
                        specModifyList[index].price =
                            editPrice.text.toString().replace(",", "").toInt()
                        var sum = 0
                        specModifyList.filter { it.rule != "sum" }.forEach {
                            sum += it.price
                        }
                        specModifyList[indexSum].price = sum
                        vmTradeViewModel.setSelectedSpecModify()
                    }
                }
            })
        }
    }
}