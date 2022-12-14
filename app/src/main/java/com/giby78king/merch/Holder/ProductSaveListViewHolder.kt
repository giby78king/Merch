package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.*
import com.giby78king.merch.Model.*
import com.giby78king.merch.Model.Group.Companion.GroupList
import com.giby78king.merch.Model.Group.Companion.ddlGroupList
import com.giby78king.merch.Model.Member.Companion.MemberList
import com.giby78king.merch.Model.Member.Companion.ddlMemberList
import com.giby78king.merch.Model.Member.Companion.selectedMemberList
import com.giby78king.merch.Model.Product.Companion.copyProductDetailList
import com.giby78king.merch.Model.Product.Companion.nowEditProductId
import com.giby78king.merch.Model.Product.Companion.productDetailList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmProductSaveViewModel
import java.text.DecimalFormat

class ProductSaveListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val imgProductDetail: ImageView = v.findViewById(R.id.imgProductDetail)
    private val linearDelete: LinearLayout = v.findViewById(R.id.linearDelete)
    private val txtCount: TextView = v.findViewById(R.id.txtCount)
    private val editPrice: EditText = v.findViewById(R.id.editPrice)
    private val editLimit: EditText = v.findViewById(R.id.editLimit)
    private val spinnerGroup: Spinner = v.findViewById(R.id.spinnerGroup)
    private val spinnerMember: Spinner = v.findViewById(R.id.spinnerMember)
    private val btnAddMember: Button = v.findViewById(R.id.btnAddMember)
    private val switchGroup: Switch = v.findViewById(R.id.switchGroup)
    private val rvAddPoolMember: RecyclerView = v.findViewById(R.id.rvAddPoolMember)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: ProductDetail, vmProductSaveViewModel: VmProductSaveViewModel) {
        val index = data.count - 1

        var iconId = ""
        if (data.group.isNotEmpty()) {
            val groupFirst = data.group.split(" ")

            groupFirst.forEach {
                iconId += it.substring(0, 1)
            }

            val productImg =
                nowEditProductId.toLowerCase() + "_" + iconId.toLowerCase() + data.member

            //Img??????
            var img = "img_product_$productImg"
            val resourceId: Int = res.getIdentifier(
                img, "drawable",
                "com.giby78king.merch"
            )
            imgProductDetail.setImageResource(resourceId)
        }



        linearDelete.setOnClickListener {
            vmProductSaveViewModel.setSelectSpecificationInfo(index)
        }

        txtCount.text =
            DecimalFormat("00").format(data.count) + "/" + DecimalFormat("00").format(
                copyProductDetailList.size
            )
        editPrice.setText(TextAmountSetting().formatAmountNoDollar(data.price.toString()))
        editLimit.setText(TextAmountSetting().formatAmountNoDollar(data.limit.toString()))

        editPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                EditAmountSetting().editNoDollarRule(editPrice, this)
                if (editPrice.text.toString().isEmpty()) {
                    copyProductDetailList[index].price = 0
                } else {
                    copyProductDetailList[index].price =
                        editPrice.text.toString().replace(",", "").toInt()
                }
            }
        })

        editLimit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                EditAmountSetting().editNoDollarRule(editLimit, this)
                if (editLimit.text.toString().isEmpty()) {
                    copyProductDetailList[index].limit = 0
                } else {
                    copyProductDetailList[index].limit =
                        editLimit.text.toString().replace(",", "").toInt()
                }
            }
        })

        var ddlGroupPosition = 0
        var ddlMemberPosition = 0

        ddlGroupList.clear()
        ddlGroupList.add(
            DdlNormalModel(
                "?????????",
                "",
                ""
            )
        )
        GroupList.forEach {
            ddlGroupList.add(
                DdlNormalModel(
                    it.chName,
                    it.id,
                    it.id
                )
            )
        }

        spinnerGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                ddlGroupPosition = position

                ddlMemberList.clear()
                ddlMemberList.add(
                    DdlNormalModel(
                        "?????????",
                        "",
                        ""
                    )
                )

                MemberList.filter { it.group.contains(ddlGroupList[ddlGroupPosition].id) }
                    .forEach {
                        ddlMemberList.add(
                            DdlNormalModel(
                                it.id,
                                it.icon,
                                it.id
                            )
                        )
                    }
                val customDropDownAdapter =
                    CustomDropDownAdapter(
                        "member",
                        parent.context,
                        ddlMemberList
                    )
                spinnerMember.adapter = customDropDownAdapter
                spinnerMember.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        val customDropDownAdapter =
            CustomDropDownAdapter("group", parentView.context, ddlGroupList)
        spinnerGroup.adapter = customDropDownAdapter
        spinnerGroup.setSelection(ddlGroupPosition)

        spinnerMember.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                ddlMemberPosition = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        selectedMemberList.clear()

        if (data.member.isNotEmpty()) {

            MemberList.filter {
                copyProductDetailList[index].member.contains(it.number) && copyProductDetailList[index].group.contains(
                    it.group[0]
                )
            }.toMutableList().forEach { member ->
                if (selectedMemberList.none { it.id == member.id }) {
                    selectedMemberList.add(
                        member
                    )
                }
            }

            copyProductDetailList[index].group = ""
            copyProductDetailList[index].member = ""
            selectedMemberList.forEach {
                copyProductDetailList[index].group += "," + it.group[0]
                copyProductDetailList[index].member += "," + it.number
            }
            copyProductDetailList[index].group =
                copyProductDetailList[index].group.substring(1)
            copyProductDetailList[index].member =
                copyProductDetailList[index].member.substring(1)
            setMemberSelectedRecyclerView(selectedMemberList, copyProductDetailList[index])

        }

        btnAddMember.setOnClickListener {
            if (selectedMemberList.none { it.id == ddlMemberList[ddlMemberPosition].id && it.group == ddlGroupList[ddlGroupPosition].id }) {
                MemberList.firstOrNull { it.id == ddlMemberList[ddlMemberPosition].id && it.group == ddlGroupList[ddlGroupPosition].id }
                    ?.let { it1 ->
                        selectedMemberList.add(
                            it1
                        )
                    }
            }
            if (switchGroup.isChecked) {
                MemberList.filter { it.group.contains(ddlGroupList[ddlGroupPosition].id) }
                    .forEach { member ->
                        if (selectedMemberList.none { it.id == member.id && it.group == ddlGroupList[ddlGroupPosition].id }) {
                            selectedMemberList.add(
                                member
                            )
                        }
                    }
            }

            selectedMemberList = selectedMemberList.sortedBy { it.number }.toMutableList()

            if (selectedMemberList.isNotEmpty()) {
                copyProductDetailList[index].group = ""
                copyProductDetailList[index].member = ""
                selectedMemberList.forEach {
                    copyProductDetailList[index].group += "," + it.group[0]
                    copyProductDetailList[index].member += "," + it.number
                }
                copyProductDetailList[index].group =
                    copyProductDetailList[index].group.substring(1)
                copyProductDetailList[index].member =
                    copyProductDetailList[index].member.substring(1)
                setMemberSelectedRecyclerView(selectedMemberList, copyProductDetailList[index])
            }

        }


    }

    private fun setMemberSelectedRecyclerView(list: MutableList<Member>, member: ProductDetail) {
        val layoutManager = LinearLayoutManager(parentView.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val numberOfColumns = 3
        rvAddPoolMember.layoutManager = GridLayoutManager(parentView.context, numberOfColumns)
        rvAddPoolMember.adapter = MemberSelectedAdapter(list, member)
    }
}