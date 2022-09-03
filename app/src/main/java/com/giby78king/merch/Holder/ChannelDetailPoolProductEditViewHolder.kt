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
import com.giby78king.merch.Adapter.PoolProductEditChannelDetailAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.productChannelDetailList
import com.giby78king.merch.Model.Specification
import com.giby78king.merch.Model.Specification.Companion.tempSpecificationList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmChannelDetailViewModel
import com.giby78king.merch.ui.ProductEditPage
import kotlin.math.abs


class ChannelDetailPoolProductEditViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val res: Resources = v.context.resources

    fun bind(item: String, page: ProductEditPage) {

        val data = dbChannelDetailList.filter { it.id == item }[0]

        ImgSetting().setImage("channeldetail", res, imgChannelDetail, data.id)

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

            if (productChannelDetailList.contains(data.id))            //排除重複點選
            {
                productChannelDetailList.remove(data.id)
            }
            val rvAddPoolChannelDetail =
                itemView.rootView.findViewById<RecyclerView>(R.id.rvAddPoolChannelDetail)

            var sortList = mutableListOf<String>()
            dbChannelDetailList.sortedBy { it.channel }.toMutableList().forEach { chd ->
                productChannelDetailList.forEach { act ->
                    if (act == chd.id) {
                        sortList.add(act)
                    }
                }
            }

            val padding =
                productChannelDetailList.size - tempSpecificationList[0].limit.size

            if (padding > 0) {
                for (i in 1..padding) {
                    tempSpecificationList.forEach { sp ->
                        sp.price.add(0)
                        sp.limit.add(0)
                    }
                }
            }
            if (padding < 0) {
                for (i in 1..abs(padding)) {
                    tempSpecificationList.forEach { sp ->
                        sp.price.removeLast()
                        sp.limit.removeLast()
                    }
                }
            }

            val vmChannelDetailViewModel =
                ViewModelProvider(page)[VmChannelDetailViewModel::class.java]

            rvAddPoolChannelDetail.adapter = PoolProductEditChannelDetailAdapter(sortList,page)
            vmChannelDetailViewModel.setSelectedChannelDetail()
        }
    }
}