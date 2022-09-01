package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.Product
import com.giby78king.merch.R
import com.giby78king.merch.ui.ProductEditPage


class ProductListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val txtMember: TextView = v.findViewById(R.id.txtMember)
    private val txtPublish: TextView = v.findViewById(R.id.txtPublish)
    private val txtChannelDetail: TextView = v.findViewById(R.id.txtChannelDetail)
    private val imgProduct: ImageView = v.findViewById(R.id.imgProduct)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: Product) {

        txtPublish.text = data.publish
        txtChannelDetail.text =
            dbChannelDetailList.filter { it.id == data.channelDetail[0] }.toMutableList()[0].name
        txtMember.text = data.name

        itemView.setOnClickListener {

            var intent = Intent(itemView.context, ProductEditPage::class.java)

            val bundle = Bundle()
            bundle.putString("selectedProduct", data.id)
            intent.putExtras(bundle)

            itemView.context.startActivity(intent)
        }

        ImgSetting().setImage("product", res, imgProduct, data.id)

    }
}