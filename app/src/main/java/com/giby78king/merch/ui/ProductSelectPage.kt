package com.giby78king.merch.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giby78king.merch.Adapter.CustomDropDownAdapter
import com.giby78king.merch.Adapter.ProductAdapter
import com.giby78king.merch.Model.Channel.Companion.ChannelList
import com.giby78king.merch.Model.Channel.Companion.ddlChannelList
import com.giby78king.merch.Model.ChannelDetail.Companion.ChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.ddlChannelDetailList
import com.giby78king.merch.Model.DdlNormalModel
import com.giby78king.merch.Model.Group
import com.giby78king.merch.Model.Group.Companion.ddlGroupList
import com.giby78king.merch.Model.Member.Companion.MemberList
import com.giby78king.merch.Model.Member.Companion.ddlMemberList
import com.giby78king.merch.Model.Product
import com.giby78king.merch.Model.Product.Companion.ProductList
import com.giby78king.merch.Model.Product.Companion.copyProductDetailList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.*
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.spinnerChannel
import kotlinx.android.synthetic.main.activity_member_edit_page.spinnerGroup
import kotlinx.android.synthetic.main.activity_product_edit_page.*
import kotlinx.android.synthetic.main.activity_product_edit_page.spinnerChannelDetail
import kotlinx.android.synthetic.main.activity_product_select_page.*


class ProductSelectPage : AppCompatActivity() {

    var ddlChannelPosition = 0
    var ddlChannelDetailPosition = 0

    var ddlGroupPosition = 0
    var ddlMemberPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_select_page)

        copyProductDetailList.clear()


        val vmChannelViewModel =
            ViewModelProvider(this)[VmChannelViewModel::class.java]
        val vmChannelDetailViewModel =
            ViewModelProvider(this)[VmChannelDetailViewModel::class.java]
        val vmProductViewModel =
            ViewModelProvider(this)[VmProductViewModel::class.java]

        vmChannelViewModel.channelDatas.observe(this) {
            ddlChannelList.clear()
            ddlChannelList.add(
                DdlNormalModel(
                    "請選擇",
                    "",
                    ""
                )
            )
            ChannelList.sortedBy { it.order }.forEach {
                ddlChannelList.add(
                    DdlNormalModel(
                        it.name,
                        it.icon,
                        it.id
                    )
                )
            }

            vmChannelDetailViewModel.getDatas("")
            vmChannelDetailViewModel.channelDetailDatas.observe(this) {
                vmProductViewModel.getDatas("")
                vmProductViewModel.productDatas.observe(this) {
                    setProductRecyclerView(it)


                    spinnerChannel.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                ddlChannelPosition = position

                                ddlChannelDetailList.clear()
                                ddlChannelDetailList.add(
                                    DdlNormalModel(
                                        "請選擇",
                                        "",
                                        ""
                                    )
                                )

                                ChannelDetailList.filter { it.belong == ddlChannelList[ddlChannelPosition].id }
                                    .forEach {
                                        ddlChannelDetailList.add(
                                            DdlNormalModel(
                                                it.name,
                                                it.imgUrl,
                                                it.id
                                            )
                                        )
                                    }

                                spinnerChannelDetail.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            ddlChannelDetailPosition = position
                                            processFilter()
                                        }

                                        override fun onNothingSelected(parent: AdapterView<*>) {}
                                    }

                                val customDropDownAdapter =
                                    CustomDropDownAdapter(
                                        "channelDetail",
                                        parent.context,
                                        ddlChannelDetailList
                                    )
                                spinnerChannelDetail.adapter = customDropDownAdapter
                                spinnerChannelDetail.setSelection(0)


                                processFilter()

                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }

                    val customDropDownAdapter =
                        CustomDropDownAdapter("channel", this, ddlChannelList)
                    spinnerChannel.adapter = customDropDownAdapter
                    spinnerChannel.setSelection(ddlChannelPosition)
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
                    Group.GroupList.forEach {
                        ddlGroupList.add(
                            DdlNormalModel(
                                it.chName,
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
                                ddlGroupPosition = position

                                ddlMemberList.clear()
                                ddlMemberList.add(
                                    DdlNormalModel(
                                        "請選擇",
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

                                processFilter()

                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }

                    val customDropDownAdapter =
                        CustomDropDownAdapter("group", this, Group.ddlGroupList)
                    spinnerGroup.adapter = customDropDownAdapter
                    spinnerGroup.setSelection(ddlGroupPosition)


                    spinnerMember.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                ddlMemberPosition = position

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
        var list = ProductList

        if (ddlChannelPosition != 0) {
            list =
                list.filter { product ->
                    ChannelDetailList.filter { it.id == product.channelDetail }
                        .toMutableList()[0].belong == ddlChannelList[ddlChannelPosition].id
                }.toMutableList()
        }
        if (ddlChannelPosition != 0 && ddlChannelDetailPosition != 0) {
            list =
                list.filter { it.channelDetail == ddlChannelDetailList[ddlChannelDetailPosition].id }
                    .toMutableList()

        }

        if (ddlGroupPosition != 0) {
            var memberGroupList =
                MemberList.filter { it.group == ddlGroupList[ddlGroupPosition].id }
                    .toMutableList()

            var groupList = mutableListOf<Product>()
            memberGroupList.forEach { member->
                list.forEach {pro->
                    if(pro.member.contains(member.id))
                    {
                        if(groupList.filter { it.id == pro.id }.isEmpty())
                        {
                            groupList.add(pro)
                        }
                    }
                }
            }
            list = groupList
        }

        if (ddlGroupPosition != 0 && ddlMemberPosition != 0) {
            var id =
                MemberList.filter { it.id == ddlMemberList[ddlMemberPosition].id && it.group == ddlGroupList[ddlGroupPosition].id }
                    .toMutableList()[0].id

            list =
                list.filter { product -> product.member.contains(id) }
                    .toMutableList()
        }

        setProductRecyclerView(list)
    }
}



