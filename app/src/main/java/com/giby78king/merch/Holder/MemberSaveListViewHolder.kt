package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Model.Member
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmMemberSaveViewModel

class MemberSaveListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val imgMember: ImageView = v.findViewById(R.id.imgMember)
    private val txtName: TextView = v.findViewById(R.id.txtName)
    private val txtNumber: TextView = v.findViewById(R.id.txtMember)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: Member, vmMemberSaveViewModel: VmMemberSaveViewModel) {

        txtName.text = data.id
        txtNumber.text = data.number

        itemView.setOnClickListener {
            vmMemberSaveViewModel.setSelectMemberDatas(data.group, data.number, data.imgUrl)
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