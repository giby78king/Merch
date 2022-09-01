package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel


class ChannelDetailListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v
    private val linearDuration: LinearLayout = v.findViewById(R.id.linearDuration)
    private val txtDuration: TextView = v.findViewById(R.id.txtDuration)

    private val imgChannelDetail: ImageView = v.findViewById(R.id.imgChannelDetail)
    private val txtName: TextView = v.findViewById(R.id.txtName)


    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(
        data: ChannelDetail,
        vmTopProductDepositoryViewModel: VmTopProductDepositoryViewModel
    ) {

//        if (data.channel == "Activity") {
//            linearDuration.isVisible = true
//
//            var dateText = ""
//            if (data.startDate.isNotEmpty()) {
//                dateText = data.startDate + "－"
//            }
//            dateText += data.endDate
//            txtDuration.text = dateText
//        }
        txtName.text = data.name

        itemView.setOnClickListener {
            vmTopProductDepositoryViewModel.setTopChannelDetailDatas(data.id, data.imgUrl)
        }

        //Img相關
        var img = "img_channeldetail_" + data.imgUrl.toLowerCase().replace(" ", "")
        val resourceId: Int = res.getIdentifier(
            img, "drawable",
            "com.giby78king.merch"
        )

        imgChannelDetail.setImageResource(resourceId)
    }
}