package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolChannelDetailInfoAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Activity.Companion.dbActivityList
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.Product
import com.giby78king.merch.R
import com.giby78king.merch.ui.ProductEditPage
import kotlinx.android.synthetic.main.activity_product_select_page.*


class ProductListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val txtName: TextView = v.findViewById(R.id.txtName)
    private val txtPublish: TextView = v.findViewById(R.id.txtPublish)
    private val txtActivity:TextView = v.findViewById(R.id.txtActivity)
    private val rvPoolChannelDetail: RecyclerView = v.findViewById(R.id.rvPoolChannelDetail)
    private val imgProduct: ImageView = v.findViewById(R.id.imgProduct)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: Product) {

        txtPublish.text = data.publish
        txtActivity.text = dbActivityList.filter { it.id==data.activity }.toMutableList()[0].name
        txtName.text = data.name

        itemView.setOnClickListener {

            var intent = Intent(itemView.context, ProductEditPage::class.java)

            val bundle = Bundle()
            bundle.putString("selectedProduct", data.id)
            intent.putExtras(bundle)

            itemView.context.startActivity(intent)
        }

        ImgSetting().setImage("product", res, imgProduct, data.id)

        var sortList = mutableListOf<String>()
        dbChannelDetailList.sortedBy { it.channel }.toMutableList().forEach { chd ->
            data.channelDetail.forEach { act ->
                if (act == chd.id) {
                    sortList.add(act)
                }
            }
        }
        val layoutManager = LinearLayoutManager(parentView.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val numberOfColumns = 3
        rvPoolChannelDetail.layoutManager = GridLayoutManager(parentView.context, numberOfColumns)
        rvPoolChannelDetail.adapter = PoolChannelDetailInfoAdapter(sortList)
    }
}