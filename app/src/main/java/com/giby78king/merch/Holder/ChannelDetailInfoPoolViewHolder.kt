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


class ChannelDetailInfoPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val res: Resources = v.context.resources

    fun bind(item: String) {

        val data = dbChannelDetailList.filter { it.id == item }[0]

        ImgSetting().setImage("channeldetail", res, imgChannelDetail, data.id)

        itemView.setOnClickListener {

            Toast.makeText(
                it.context,
                "通路:" + data.name,
                Toast.LENGTH_SHORT
            )
        }
    }
}