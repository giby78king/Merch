package com.giby78king.merch.Holder

import android.content.ClipboardManager
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.PoolProductEditGroupAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Group.Companion.ddlGroupList
import com.giby78king.merch.Model.Group.Companion.productGroupList
import com.giby78king.merch.R


class GroupPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    val res: Resources = v.context.resources

    fun bind(item: String) {

        val data = ddlGroupList.filter { it.id == item }[0]

        ImgSetting().setImage("group", res, imgChannelDetail, data.id)

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

            if (productGroupList.contains(data.id))            //排除重複點選
            {
                productGroupList.remove(data.id)
            }
            val rvAddPoolGroup =
                itemView.rootView.findViewById<RecyclerView>(R.id.rvAddPoolGroup)

            var sortList = mutableListOf<String>()
            ddlGroupList.sortedBy { it.id }.toMutableList().forEach { chd ->
                productGroupList.forEach { act ->
                    if (act == chd.id) {
                        sortList.add(act)
                    }
                }
            }

            rvAddPoolGroup.adapter = PoolProductEditGroupAdapter(sortList)
        }
    }
}