package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Activity
import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmChannelDetailSaveViewModel


class ActivitySaveListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v
    private val linearDuration: LinearLayout = v.findViewById(R.id.linearDuration)
    private val txtDuration: TextView = v.findViewById(R.id.txtDuration)

    private val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    private val txtName: TextView = v.findViewById(R.id.txtName)


    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(
        data: Activity,
        vmChannelDetailSaveViewModel: VmChannelDetailSaveViewModel
    ) {

        linearDuration.isVisible = true

        var dateText = ""
        if (data.startDate.isNotEmpty()) {
            dateText = data.startDate + "－"
        }
        dateText += data.endDate
        txtDuration.text = dateText

        txtName.text = data.name

        itemView.setOnClickListener {
            vmChannelDetailSaveViewModel.setSelectChannelDetailDatas(data.id, data.imgUrl)
        }

        ImgSetting().setImage("activity", res, imgChannelDetail, data.imgUrl)

    }
}