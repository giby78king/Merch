package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Model.ChannelDetail.Companion.ChannelDetailList
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
            ChannelDetailList.filter { it.id == data.channelDetail }.toMutableList()[0].name
        txtMember.text = data.name

        itemView.setOnClickListener {

            var intent = Intent(itemView.context, ProductEditPage::class.java)

            val bundle = Bundle()
            bundle.putString("selectedProduct", data.id)
            intent.putExtras(bundle)

            itemView.context.startActivity(intent)
        }


        //Img相關
        var img = "img_product_" + data.id.toLowerCase()
        val resourceId: Int = res.getIdentifier(
            img, "drawable",
            "com.giby78king.merch"
        )
        imgProduct.setImageResource(resourceId)

    }
}