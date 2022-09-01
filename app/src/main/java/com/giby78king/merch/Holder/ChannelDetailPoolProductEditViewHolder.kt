package com.giby78king.merch.Holder

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolProductEditChannelDetailAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.productChannelDetailList
import com.giby78king.merch.R


class ChannelDetailPoolProductEditViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val res: Resources = v.context.resources

    fun bind(item: String) {

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

            rvAddPoolChannelDetail.adapter = PoolProductEditChannelDetailAdapter(sortList)
        }
    }
}