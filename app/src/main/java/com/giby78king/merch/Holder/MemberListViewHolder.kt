package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Model.Member
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel


class MemberListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val imgEmployed: ImageView = v.findViewById(R.id.imgEmployed)
    private val imgMember: ImageView = v.findViewById(R.id.imgMember)
    private val txtName: TextView = v.findViewById(R.id.txtName)
    private val txtNumber: TextView = v.findViewById(R.id.txtMember)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: Member, vmTopProductDepositoryViewModel: VmTopProductDepositoryViewModel) {

        txtName.text = data.id
        txtNumber.text = data.number

        itemView.setOnClickListener {
            vmTopProductDepositoryViewModel.setTopMemberDatas(data.group,data.number,data.imgUrl)
        }

        if (data.employed) {
            ImageViewCompat.setImageTintList(
                imgEmployed, ColorStateList.valueOf(
                    Color.parseColor(
                        "#ffd1dc"
                    )
                )
            )
        }

        //Img相關
        var img = "img_member_" + data.imgUrl.toLowerCase()
        val resourceId: Int = res.getIdentifier(
            img, "drawable",
            "com.giby78king.merch"
        )
        imgMember.setImageResource(resourceId)
    }
}