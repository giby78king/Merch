package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Member
import com.giby78king.merch.Model.Member.Companion.dbMemberList
import com.giby78king.merch.Model.Specification.Companion.tempSpecificationList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmSpecificationViewModel
import com.giby78king.merch.ui.ProductEditPage


class MemberSelectedListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val imgEmployed: ImageView = v.findViewById(R.id.imgEmployed)
    private val imgMember: ImageView = v.findViewById(R.id.imgMember)
    private val txtGroup: TextView = v.findViewById(R.id.txtGroup)
    private val txtNumber: TextView = v.findViewById(R.id.txtMember)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: Member, index: Int, productEditPage: ProductEditPage) {

        txtGroup.text = data.group
        txtNumber.text = data.number

        if (data.employed) {
            ImageViewCompat.setImageTintList(
                imgEmployed, ColorStateList.valueOf(
                    Color.parseColor(
                        "#ffd1dc"
                    )
                )
            )
        }

        itemView.setOnClickListener {

            var selectedMemberList = mutableListOf<Member>()

            dbMemberList.filter {
                tempSpecificationList[index].member.contains(it.id)
            }.sortedBy { it.group }.sortedBy { it.number }.toMutableList().forEach { member ->
                if (selectedMemberList.none { it.id == member.id }
                ) {
                    selectedMemberList.add(
                        member
                    )
                }
            }

            selectedMemberList.remove(selectedMemberList.find {
                it.id == data.id
            })

            var list = arrayListOf<String>()
            selectedMemberList.forEach {
                list.add(it.id)
            }
            tempSpecificationList[index].member = list.toTypedArray()

            val vmSpecificationViewModel =
                ViewModelProvider(productEditPage)[VmSpecificationViewModel::class.java]

            vmSpecificationViewModel.setSelectedMember(tempSpecificationList)
        }

        ImgSetting().setImage("member", res, imgMember, data.imgUrl)
    }
}