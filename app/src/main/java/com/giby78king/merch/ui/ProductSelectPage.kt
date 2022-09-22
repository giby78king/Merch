package com.giby78king.merch.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giby78king.merch.Adapter.CustomDropDownAdapter
import com.giby78king.merch.Adapter.ProductAdapter
import com.giby78king.merch.Model.Activity.Companion.dbActivityList
import com.giby78king.merch.Model.Activity.Companion.ddlActivityList
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.ddlChannelDetailList
import com.giby78king.merch.Model.DdlNormalModel
import com.giby78king.merch.Model.Group
import com.giby78king.merch.Model.Group.Companion.ddlGroupList
import com.giby78king.merch.Model.Member.Companion.dbMemberList
import com.giby78king.merch.Model.Member.Companion.ddlMemberList
import com.giby78king.merch.Model.Product
import com.giby78king.merch.Model.Product.Companion.dbProductList
import com.giby78king.merch.Model.Specification.Companion.dbSpecificationList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.*
import kotlinx.android.synthetic.main.activity_member_edit_page.spinnerGroup
import kotlinx.android.synthetic.main.activity_product_select_page.*


class ProductSelectPage : AppCompatActivity() {

    var ddlPositionChannelDetail = 0
    var ddlPositionActivity = 0

    var ddlPositionGroup = 0
    var ddlPositionMember = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_select_page)

        val vmChannelDetailViewModel =
            ViewModelProvider(this)[VmChannelDetailViewModel::class.java]
        val vmActivitylViewModel =
            ViewModelProvider(this)[VmActivitylViewModel::class.java]
        val vmProductViewModel =
            ViewModelProvider(this)[VmProductViewModel::class.java]
        vmChannelDetailViewModel.getDatas("")
        val vmSpecificationViewModel =
            ViewModelProvider(this)[VmSpecificationViewModel::class.java]


        vmChannelDetailViewModel.channelDetailDatas.observe(this) {
            ddlChannelDetailList.clear()
            ddlChannelDetailList.add(
                DdlNormalModel(
                    "請選擇",
                    "",
                    ""
                )
            )
            dbChannelDetailList.sortedBy { it.channel }.forEach {
                ddlChannelDetailList.add(
                    DdlNormalModel(
                        it.name,
                        it.imgUrl,
                        it.id
                    )
                )
            }

            vmActivitylViewModel.getDatas("")
            vmActivitylViewModel.activityDatas.observe(this) {
                vmProductViewModel.getDatas("")
                vmProductViewModel.productDatas.observe(this) { pro ->
                    vmSpecificationViewModel.getDatas("")
                    vmSpecificationViewModel.specificationDatas.observe(this) {

                        setProductRecyclerView(pro)

                        spinnerChannelDetail.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    ddlPositionChannelDetail = position

                                    ddlActivityList.clear()
                                    ddlActivityList.add(
                                        DdlNormalModel(
                                            "請選擇",
                                            "",
                                            ""
                                        )
                                    )

                                    dbActivityList.filter {
                                        it.channelDetail.contains(
                                            ddlChannelDetailList[ddlPositionChannelDetail].id
                                        )
                                    }
                                        .forEach {
                                            ddlActivityList.add(
                                                DdlNormalModel(
                                                    it.name,
                                                    it.imgUrl,
                                                    it.id
                                                )
                                            )
                                        }

                                    spinnerActivity.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                parent: AdapterView<*>,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                ddlPositionActivity = position
                                                processFilter()
                                            }

                                            override fun onNothingSelected(parent: AdapterView<*>) {}
                                        }

                                    val customDropDownAdapter =
                                        CustomDropDownAdapter(
                                            "activity",
                                            parent.context,
                                            "small",
                                            ddlActivityList
                                        )
                                    spinnerActivity.adapter = customDropDownAdapter
                                    spinnerActivity.setSelection(0)


                                    processFilter()

                                }

                                override fun onNothingSelected(parent: AdapterView<*>) {}
                            }

                        val customDropDownAdapter =
                            CustomDropDownAdapter("channeldetail", this,"small", ddlChannelDetailList)
                        spinnerChannelDetail.adapter = customDropDownAdapter
                        spinnerChannelDetail.setSelection(ddlPositionChannelDetail)
                    }
                }
            }
        }


        val vmGroupViewModel =
            ViewModelProvider(this)[VmGroupViewModel::class.java]
        val vmMemberViewModel =
            ViewModelProvider(this)[VmMemberViewModel::class.java]
        vmMemberViewModel.getDatas("")

        vmGroupViewModel.groupDatas.observe(this) {
            vmMemberViewModel.memberDatas.observe(this) {
                vmProductViewModel.productDatas.observe(this) {

                    ddlGroupList.clear()
                    ddlGroupList.add(
                        DdlNormalModel(
                            "請選擇",
                            "",
                            ""
                        )
                    )
                    Group.dbGroupList.forEach {
                        ddlGroupList.add(
                            DdlNormalModel(
                                it.name,
                                it.id,
                                it.id
                            )
                        )
                    }

                    spinnerGroup.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                ddlPositionGroup = position

                                ddlMemberList.clear()
                                ddlMemberList.add(
                                    DdlNormalModel(
                                        "請選擇",
                                        "",
                                        ""
                                    )
                                )

                                dbMemberList.filter { it.group.contains(ddlGroupList[ddlPositionGroup].id) }
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

                                processFilter()

                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }

                    val customDropDownAdapter =
                        CustomDropDownAdapter("group", this,"small", Group.ddlGroupList)
                    spinnerGroup.adapter = customDropDownAdapter
                    spinnerGroup.setSelection(ddlPositionGroup)


                    spinnerMember.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                ddlPositionMember = position

                                processFilter()

                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                }
            }
        }


    }

    private fun setProductRecyclerView(list: MutableList<Product>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list_item_product.layoutManager = layoutManager
        rv_list_item_product.adapter = ProductAdapter(list)
    }

    private fun processFilter() {
        var list = dbProductList

        if (ddlPositionChannelDetail != 0) {
            list =
                list.filter { it.channelDetail.contains(ddlChannelDetailList[ddlPositionChannelDetail].id) }
                    .toMutableList()
        }
        if (ddlPositionChannelDetail != 0 && ddlPositionActivity != 0) {
            list =
                list.filter { it.activity == ddlActivityList[ddlPositionActivity].id }
                    .toMutableList()
        }
        if (ddlPositionGroup != 0) {
            list =
                list.filter { it.group.contains(ddlGroupList[ddlPositionGroup].id) }
                    .toMutableList()
        }
        if (ddlPositionGroup != 0 && ddlPositionMember != 0) {

            var specificationList = dbSpecificationList.filter {
                it.specificationType == "Member" && it.member.contains(ddlMemberList[ddlPositionMember].id)
            }.toMutableList()

            var memberProductList =  mutableListOf<Product>()

            specificationList.forEach { sp->
                list.forEach {
                    if(it.specification.contains(sp.id))
                    {
                        if(!memberProductList.contains(it)){
                            memberProductList.add(it)
                        }
                    }
                }
            }

            list = memberProductList
        }

        setProductRecyclerView(list)
    }
}



