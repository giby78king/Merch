package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.ChannelDetail.Companion.productChannelDetailList
import com.giby78king.merch.Model.EditAmountSetting
import com.giby78king.merch.Model.Specification.Companion.tempSpecificationList
import com.giby78king.merch.Model.TextAmountSetting
import com.giby78king.merch.R
import com.giby78king.merch.ui.ProductEditPage


class PriceEditListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v
    private val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    private val editPrice: EditText = v.findViewById(R.id.editPrice)
    private val editLimit: EditText = v.findViewById(R.id.editLimit)
    val res: Resources = v.context.resources

    fun bind(data: String, index: Int, productEditPage: ProductEditPage) {

        Log.d("price",":"+tempSpecificationList[index].price[productChannelDetailList.indexOf(data)].toString())
        Log.d("limit",":"+tempSpecificationList[index].limit[productChannelDetailList.indexOf(data)].toString())


        editPrice.setText(TextAmountSetting().formatAmountNoDollar(tempSpecificationList[index].price[productChannelDetailList.indexOf(data)].toString()))
        editLimit.setText(TextAmountSetting().formatAmountNoDollar(tempSpecificationList[index].limit[productChannelDetailList.indexOf(data)].toString()))

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
                Log.d("price11111111",":"+tempSpecificationList[index].price[productChannelDetailList.indexOf(data)].toString())
                Log.d("limit11111111",":"+tempSpecificationList[index].limit[productChannelDetailList.indexOf(data)].toString())

                EditAmountSetting().editNoDollarRule(editPrice, this)
                if (editPrice.text.toString().isEmpty()) {
                    tempSpecificationList[index].price[productChannelDetailList.indexOf(data)] = 0
                } else {
                    tempSpecificationList[index].price[productChannelDetailList.indexOf(data)] =
                        editPrice.text.toString().replace(",", "").toInt()
                }

                Log.d("price",":"+tempSpecificationList[index].price[productChannelDetailList.indexOf(data)].toString())
                Log.d("limit",":"+tempSpecificationList[index].limit[productChannelDetailList.indexOf(data)].toString())
            }
        })

        editLimit.addTextChangedListener(object : TextWatcher {
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
                Log.d("price11111111",":"+tempSpecificationList[index].price[productChannelDetailList.indexOf(data)].toString())
                Log.d("limit11111111",":"+tempSpecificationList[index].limit[productChannelDetailList.indexOf(data)].toString())

                EditAmountSetting().editNoDollarRule(editLimit, this)
                if (editLimit.text.toString().isEmpty()) {
                    tempSpecificationList[index].limit[productChannelDetailList.indexOf(data)] = 0
                } else {
                    tempSpecificationList[index].limit[productChannelDetailList.indexOf(data)] =
                        editLimit.text.toString().replace(",", "").toInt()
                }

                tempSpecificationList[index].price[productChannelDetailList.indexOf(data)] =100

                Log.d("price",":"+tempSpecificationList[index].price[productChannelDetailList.indexOf(data)].toString())
                Log.d("limit",":"+tempSpecificationList[index].limit[productChannelDetailList.indexOf(data)].toString())
            }
        })

        ImgSetting().setImage("channeldetail", res, imgChannelDetail, data)
    }
}