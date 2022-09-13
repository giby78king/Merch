package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Activity.Companion.ddlActivityList
import com.giby78king.merch.Model.ChannelDetail.Companion.ddlChannelDetailList
import com.giby78king.merch.Model.DdlPositionCollection.Companion.ddlPositionChannelDetail
import com.giby78king.merch.Model.DdlPositionCollection.Companion.ddlPositionSpecification
import com.giby78king.merch.Model.DdlPositionCollection.Companion.ddlPositionTransType
import com.giby78king.merch.Model.Product.Companion.dbProductList
import com.giby78king.merch.Model.Product.Companion.ddlProductList
import com.giby78king.merch.Model.Specification.Companion.dbSpecificationList
import com.giby78king.merch.Model.Specification.Companion.ddlSpecificationList

import com.giby78king.merch.Model.TextAmountSetting
import com.giby78king.merch.Model.Trade
import com.giby78king.merch.Model.Trade.Companion.ddlTransType
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.Model.TradeDetail.Companion.nowEditId
import com.giby78king.merch.Model.TradeDetail.Companion.specModifyList
import com.giby78king.merch.Model.TradeDetail.Companion.specOtherList
import com.giby78king.merch.Model.TradeDetail.Companion.tempSpecList
import com.giby78king.merch.Model.tempPriceDetail

import com.giby78king.merch.R
import com.giby78king.merch.TimeFormat
import com.giby78king.merch.ViewModel.VmSpecificationViewModel
import com.giby78king.merch.ViewModel.VmTradeViewModel
import com.giby78king.merch.ui.TradeEditPage
import kotlinx.android.synthetic.main.activity_trade_edit_page.*
import java.text.SimpleDateFormat


class TradeDetailEditSpecificationViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val txtName: TextView = v.findViewById(R.id.txtName)

    private val imgSpec: ImageView = v.findViewById(R.id.imgSpec)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: TradeDetail, page: TradeEditPage) {

        val spinnerChannelDetail: Spinner = page.findViewById(R.id.spinnerChannelDetail)

        val txtTotalSpecPrice: TextView = page.findViewById(R.id.txtTotalSpecPrice)
        val txtTotalSpecModify: TextView = page.findViewById(R.id.txtTotalSpecModify)
        val txtTotalSpecOther: TextView = page.findViewById(R.id.txtTotalSpecOther)

        val txtSpecPrice: TextView = page.findViewById(R.id.txtSpecPrice)
        val editProcessDate: EditText = page.findViewById(R.id.editProcessDate)
        val editStockDate: EditText = page.findViewById(R.id.editStockDate)
        val editAccountDate: EditText = page.findViewById(R.id.editAccountDate)

        if (data.id == "addOne") {
            val resources: Resources = itemView.context!!.resources
            val resourceId: Int = resources.getIdentifier(
                "ic_add", "drawable",
                itemView.context!!.packageName
            )
            imgSpec.setImageResource(resourceId)
        } else {
            ImgSetting().setImage("specification", res, imgSpec, data.specification)
        }

        if (dbSpecificationList.filter { it.id == data.specification }.isNotEmpty()) {
            txtName.text = dbSpecificationList.filter { it.id == data.specification }[0].title
        }

        itemView.setOnClickListener {
            if (data.id == "addOne") {
                if (ddlSpecificationList.size > 0) {
                    val specId = TimeFormat().TodayDate().yyyyMMddHHmmssSSS()

                    val modify = arrayListOf<Int>()
                    val modifyRule = arrayListOf<String>()
                    val other = arrayListOf<Int>()
                    val otherRule = arrayListOf<String>()
                    specModifyList.forEach {
                        modify.add(it.price)
                        modifyRule.add(it.rule)
                    }

                    specOtherList.forEach {
                        other.add(it.price)
                        otherRule.add(it.rule)
                    }
                    tempSpecList.add(
                        0, TradeDetail(
                            accountDate = editAccountDate.text.toString(),
                            channelDetail = ddlChannelDetailList[ddlPositionChannelDetail].id,
                            id = specId,
                            modify = modify,
                            modifyRule = modifyRule.toTypedArray(),
                            other = other,
                            otherRule = otherRule.toTypedArray(),
                            price = txtSpecPrice.text.toString().toInt(),
                            processDate = editProcessDate.text.toString(),
                            specification = ddlSpecificationList[ddlPositionSpecification].id,
                            stockDate = editStockDate.text.toString(),
                            tradeId = "",
                            transType = ddlTransType[ddlPositionTransType].id,
                        )
                    )

                    nowEditId = specId

                    val vmTradeViewModel =
                        ViewModelProvider(page)[VmTradeViewModel::class.java]
                    vmTradeViewModel.setProcessTradeDetail()


                }
            } else {

                val lastId = tempSpecList.indexOfFirst { it.id == nowEditId }

                val modify = arrayListOf<Int>()
                val modifyRule = arrayListOf<String>()
                val other = arrayListOf<Int>()
                val otherRule = arrayListOf<String>()
                specModifyList.forEach {
                    modify.add(it.price)
                    modifyRule.add(it.rule)
                }

                specOtherList.forEach {
                    other.add(it.price)
                    otherRule.add(it.rule)
                }

                tempSpecList[lastId].accountDate = editAccountDate.text.toString()
                tempSpecList[lastId].modify = modify
                tempSpecList[lastId].modifyRule = modifyRule.toTypedArray()
                tempSpecList[lastId].other = other
                tempSpecList[lastId].otherRule = otherRule.toTypedArray()
                tempSpecList[lastId].price = txtSpecPrice.text.toString().toInt()
                tempSpecList[lastId].processDate = editProcessDate.text.toString()
                tempSpecList[lastId].specification =
                    ddlSpecificationList[ddlPositionSpecification].id
                tempSpecList[lastId].stockDate = editStockDate.text.toString()

//todo 點選rv顯示正常
                editProcessDate.setText(data.processDate)
                editStockDate.setText(data.stockDate)
                editAccountDate.setText(data.accountDate)

                for (i in data.modify.indices){
                    specModifyList[i] = tempPriceDetail(
                        modify[i],
                        modifyRule[i]
                    )
                }
                for (i in data.other.indices){
                    specOtherList[i] = tempPriceDetail(
                        other[i],
                        otherRule[i]
                    )
                }

                nowEditId = data.id

                if (data.specification.isNotEmpty()) {
                    val vmTradeViewModel =
                        ViewModelProvider(page)[VmTradeViewModel::class.java]
                    vmTradeViewModel.setSelectedSpecDatas(data)
                }
            }
        }

        itemView.setOnLongClickListener {
            if (data.id != "addOne") {
                tempSpecList.remove(tempSpecList.first { it.id == data.id })
                val vmTradeViewModel =
                    ViewModelProvider(page)[VmTradeViewModel::class.java]
                vmTradeViewModel.setProcessTradeDetail()
            }
            true
        }
    }
}