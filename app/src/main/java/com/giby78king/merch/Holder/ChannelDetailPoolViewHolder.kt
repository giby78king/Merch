package com.giby78king.merch.Holder

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolChannelDetailAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Activity.Companion.activityChannelDetailList
import com.giby78king.merch.Model.Activity.Companion.dbActivityList
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.ddlChannelDetailList
import com.giby78king.merch.Model.Group.Companion.ddlGroupList
import com.giby78king.merch.R


class ChannelDetailPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val res: Resources = v.context.resources

    fun bind(item: String) {

        val data = ddlChannelDetailList.filter { it.id == item }[0]

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

            if (activityChannelDetailList.contains(data.id))            //排除重複點選
            {
                activityChannelDetailList.remove(data.id)
            }
            val rvAddPoolRemark =
                itemView.rootView.findViewById<RecyclerView>(R.id.rvAddPoolChannelDetail)

            var sortList = mutableListOf<String>()
            dbChannelDetailList.sortedBy { it.channel }.toMutableList().forEach { chd ->
                activityChannelDetailList.forEach { act ->
                    if (act == chd.id) {
                        sortList.add(act)
                    }
                }
            }

            rvAddPoolRemark.adapter = PoolChannelDetailAdapter(sortList)
        }
    }
}