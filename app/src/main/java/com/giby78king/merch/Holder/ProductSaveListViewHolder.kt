package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.CustomDropDownAdapter
import com.giby78king.merch.Adapter.MemberSelectedAdapter
import com.giby78king.merch.Adapter.PriceEditAdapter
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.ChannelDetail.Companion.productChannelDetailList
import com.giby78king.merch.Model.DdlNormalModel
import com.giby78king.merch.Model.Group.Companion.dbGroupList
import com.giby78king.merch.Model.Group.Companion.ddlGroupList
import com.giby78king.merch.Model.Member
import com.giby78king.merch.Model.Member.Companion.dbMemberList
import com.giby78king.merch.Model.Member.Companion.ddlMemberList
import com.giby78king.merch.Model.Specification
import com.giby78king.merch.Model.Specification.Companion.tempSpecificationList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmProductSaveViewModel
import com.giby78king.merch.ui.ProductEditPage
import java.text.DecimalFormat

class ProductSaveListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val imgSpecification: ImageView = v.findViewById(R.id.imgSpecification)
    private val linearDelete: LinearLayout = v.findViewById(R.id.linearDelete)
    private val txtCount: TextView = v.findViewById(R.id.txtCount)
    private val editTitle: EditText = v.findViewById(R.id.editTitle)
    private val spinnerGroup: Spinner = v.findViewById(R.id.spinnerGroup)
    private val spinnerMember: Spinner = v.findViewById(R.id.spinnerMember)
    private val btnAddMember: Button = v.findViewById(R.id.btnAddMember)
    private val switchGroup: Switch = v.findViewById(R.id.switchGroup)
    private val rvAddPoolChannelDetailPrice: RecyclerView =
        v.findViewById(R.id.rvAddPoolChannelDetailPrice)
    private val rvAddPoolMember: RecyclerView = v.findViewById(R.id.rvAddPoolMember)

    val res: Resources = v.context.resources
    private lateinit var page: ProductEditPage


    @SuppressLint("SetTextI18n")
    fun bind(data: Specification, productEditPage: ProductEditPage, position: Int) {
        page = productEditPage
        val index = tempSpecificationList.indexOf(data)

        var iconId = ""

        if (data.imgUrl.isNotEmpty()) {
            imgSpecification.setBackgroundColor(Color.parseColor("#00ff0000"))
            ImgSetting().setImage("specification", res, imgSpecification, data.imgUrl)
        }

        linearDelete.setOnClickListener {
            val vmProductSaveViewModel =
                ViewModelProvider(productEditPage)[VmProductSaveViewModel::class.java]
            vmProductSaveViewModel.setSelectSpecificationInfo(index)
        }

        txtCount.text =
            DecimalFormat("00").format(index + 1) + "/" + DecimalFormat("00").format(
                tempSpecificationList.size
            )

        editTitle.setText(data.title)
        editTitle.addTextChangedListener(object : TextWatcher {
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

                tempSpecificationList[index].title = editTitle.text.toString()

            }
        })

        var ddlGroupPosition = 0
        var ddlMemberPosition = 0

        ddlGroupList.clear()
        ddlGroupList.add(
            DdlNormalModel(
                "請選擇",
                "",
                ""
            )
        )
        dbGroupList.forEach {
            ddlGroupList.add(
                DdlNormalModel(
                    it.name,
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
                        "請選擇",
                        "",
                        ""
                    )
                )

                dbMemberList.filter { it.group.contains(ddlGroupList[ddlGroupPosition].id) }
                    .forEach {
                        ddlMemberList.add(
                            DdlNormalModel(
                                it.id,
                                it.imgUrl,
                                it.id
                            )
                        )
                    }
                val customDropDownAdapter =
                    CustomDropDownAdapter(
                        "member",
                        parent.context,
                        "small",
                        ddlMemberList
                    )
                spinnerMember.adapter = customDropDownAdapter
                spinnerMember.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val customDropDownAdapter =
            CustomDropDownAdapter("group", parentView.context,"small", ddlGroupList)
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

                if(tempSpecificationList[index].member.isEmpty()){
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

                    if (selectedMemberList.none { it.id == ddlMemberList[ddlMemberPosition].id }) {
                        dbMemberList.firstOrNull { it.id == ddlMemberList[ddlMemberPosition].id }
                            ?.let { it1 ->
                                selectedMemberList.add(
                                    it1
                                )
                            }
                    }
                    if (switchGroup.isChecked) {
                        dbMemberList.filter { it.group.contains(ddlGroupList[ddlGroupPosition].id) }
                            .forEach { member ->
                                if (selectedMemberList.none { it.id == member.id }) {
                                    selectedMemberList.add(
                                        member
                                    )
                                }
                            }
                    }

                    selectedMemberList =
                        selectedMemberList.sortedBy { it.number }.sortedBy { it.group }.toMutableList()

                    if (selectedMemberList.isNotEmpty()) {
                        var count = 0

                        var list = arrayListOf<String>()

                        selectedMemberList.forEach {
                            list.add(it.id)
                            count++
                        }

                        tempSpecificationList[index].member = list.toTypedArray()

                        setMemberSelectedRecyclerView(selectedMemberList, index, productEditPage)

                        if (productChannelDetailList.size > 0) {
                            setPriceEditRecyclerView(productChannelDetailList, index, productEditPage)
                        }
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        if (data.member.isNotEmpty()) {

            var selectedMemberList = mutableListOf<Member>()

            dbMemberList.filter {
                tempSpecificationList[index].member.contains(it.id)
            }.sortedBy { it.group }.sortedBy { it.number }.toMutableList().forEach { member ->
                if (selectedMemberList.none { it.id == member.id }) {
                    selectedMemberList.add(
                        member
                    )
                }
            }

            selectedMemberList =
                selectedMemberList.sortedBy { it.number }.sortedBy { it.group }.toMutableList()
            setMemberSelectedRecyclerView(selectedMemberList, index, productEditPage)

        }

        if (productChannelDetailList.size > 0) {
            setPriceEditRecyclerView(productChannelDetailList, index, productEditPage)
        }

        btnAddMember.setOnClickListener {
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

            if (selectedMemberList.none { it.id == ddlMemberList[ddlMemberPosition].id }) {
                dbMemberList.firstOrNull { it.id == ddlMemberList[ddlMemberPosition].id }
                    ?.let { it1 ->
                        selectedMemberList.add(
                            it1
                        )
                    }
            }
            if (switchGroup.isChecked) {
                dbMemberList.filter { it.group.contains(ddlGroupList[ddlGroupPosition].id) }
                    .forEach { member ->
                        if (selectedMemberList.none { it.id == member.id }) {
                            selectedMemberList.add(
                                member
                            )
                        }
                    }
            }

            selectedMemberList =
                selectedMemberList.sortedBy { it.number }.sortedBy { it.group }.toMutableList()

            if (selectedMemberList.isNotEmpty()) {
                var count = 0

                var list = arrayListOf<String>()

                selectedMemberList.forEach {
                    list.add(it.id)
                    count++
                }

                tempSpecificationList[index].member = list.toTypedArray()

                setMemberSelectedRecyclerView(selectedMemberList, index, productEditPage)

                if (productChannelDetailList.size > 0) {
                    setPriceEditRecyclerView(productChannelDetailList, index, productEditPage)
                }
            }
        }
    }

    private fun setMemberSelectedRecyclerView(
        list: MutableList<Member>,
        index: Int,
        productEditPage: ProductEditPage
    ) {
        val layoutManager = LinearLayoutManager(parentView.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val numberOfColumns = 3
        rvAddPoolMember.layoutManager = GridLayoutManager(parentView.context, numberOfColumns)
        rvAddPoolMember.adapter = MemberSelectedAdapter(list, index, productEditPage)
    }

    private fun setPriceEditRecyclerView(
        list: MutableList<String>,
        index: Int,
        productEditPage: ProductEditPage
    ) {
        val layoutManager = LinearLayoutManager(parentView.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvAddPoolChannelDetailPrice.layoutManager = layoutManager
        rvAddPoolChannelDetailPrice.adapter = PriceEditAdapter(list, index, productEditPage)
    }
}