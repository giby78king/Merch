package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.MemberSelectedAdapter
import com.giby78king.merch.Adapter.PoolGroupAdapter
import com.giby78king.merch.Model.Member
import com.giby78king.merch.Model.Member.Companion.MemberList
import com.giby78king.merch.Model.Member.Companion.selectedMemberList
import com.giby78king.merch.Model.Product
import com.giby78king.merch.Model.Product.Companion.copyProductDetailList
import com.giby78king.merch.Model.ProductDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel


class MemberSelectedListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val imgEmployed: ImageView = v.findViewById(R.id.imgEmployed)
    private val imgMember: ImageView = v.findViewById(R.id.imgMember)
    private val txtGroup: TextView = v.findViewById(R.id.txtGroup)
    private val txtNumber: TextView = v.findViewById(R.id.txtMember)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: Member, productDetail: ProductDetail) {

        var indexCopy = productDetail.count - 1
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
            selectedMemberList.clear()
            MemberList.filter {
                productDetail.member.contains(it.number) && productDetail.group.contains(
                    it.group[0]
                )
            }.toMutableList().forEach { member ->
                if (selectedMemberList.none { it.id == member.id }) {
                    selectedMemberList.add(
                        member
                    )
                }
            }


            if (productDetail.member.contains(data.number))            //排除重複點選
            {
                selectedMemberList.remove(selectedMemberList.find {
                    it.id == data.id && copyProductDetailList[indexCopy].group.contains(
                        it.group[0]
                    )
                })

                copyProductDetailList[indexCopy].group = ""
                copyProductDetailList[indexCopy].member = ""
                if (selectedMemberList.size > 0) {
                    selectedMemberList.forEach {
                        copyProductDetailList[indexCopy].group += "," + it.group[0]
                        copyProductDetailList[indexCopy].member += "," + it.number
                    }
                    copyProductDetailList[indexCopy].group =
                        copyProductDetailList[indexCopy].group.substring(1)
                    copyProductDetailList[indexCopy].member =
                        copyProductDetailList[indexCopy].member.substring(1)
                }
                val rvAddPoolMember =
                    itemView.rootView.findViewById<RecyclerView>(R.id.rvAddPoolMember)
                val layoutManager = LinearLayoutManager(parentView.context)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                val numberOfColumns = 3
                rvAddPoolMember.layoutManager =
                    GridLayoutManager(parentView.context, numberOfColumns)
                rvAddPoolMember.adapter = MemberSelectedAdapter(
                    selectedMemberList,
                    copyProductDetailList[indexCopy]
                )
            }
        }

        //Img相關
        var img = "img_member_" + data.icon.toLowerCase()
        val resourceId: Int = res.getIdentifier(
            img, "drawable",
            "com.giby78king.merch"
        )
        imgMember.setImageResource(resourceId)
    }
}