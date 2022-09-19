package com.giby78king.merch.ui.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.giby78king.merch.Adapter.CustomDropDownAdapter
import com.giby78king.merch.Adapter.ProductAdapter
import com.giby78king.merch.Model.*
import com.giby78king.merch.R
import com.giby78king.merch.TabViewClass.TabViewPagerAdapter
import com.giby78king.merch.ViewModel.*
import com.giby78king.merch.ui.fragment.TabQueryFragment
import com.giby78king.merch.ui.fragment.TabProductDepositoryFragment
import com.giby78king.merch.ui.fragment.TopProductDepositoryFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_member_edit_page.*
import kotlinx.android.synthetic.main.activity_product_select_page.*
import kotlinx.android.synthetic.main.activity_product_select_page.spinnerGroup
import java.util.ArrayList

class QueryFragment : Fragment() {

    lateinit var root: View
    var ddlPositionChannelDetail = 0
    var ddlPositionActivity = 0

    var ddlPositionGroup = 0
    var ddlPositionMember = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_query, container, false)

        val vmChannelDetailViewModel =
            ViewModelProvider(this)[VmChannelDetailViewModel::class.java]
        val vmActivitylViewModel =
            ViewModelProvider(this)[VmActivitylViewModel::class.java]
        val vmProductViewModel =
            ViewModelProvider(this)[VmProductViewModel::class.java]
        vmChannelDetailViewModel.getDatas("")
        val vmSpecificationViewModel =
            ViewModelProvider(this)[VmSpecificationViewModel::class.java]


        vmChannelDetailViewModel.channelDetailDatas.observe(viewLifecycleOwner) {
            ChannelDetail.ddlChannelDetailList.clear()
            ChannelDetail.ddlChannelDetailList.add(
                DdlNormalModel(
                    "請選擇",
                    "",
                    ""
                )
            )
            ChannelDetail.dbChannelDetailList.sortedBy { it.channel }.forEach {
                ChannelDetail.ddlChannelDetailList.add(
                    DdlNormalModel(
                        it.name,
                        it.imgUrl,
                        it.id
                    )
                )
            }

            vmActivitylViewModel.getDatas("")
            vmActivitylViewModel.activityDatas.observe(viewLifecycleOwner) {
                vmProductViewModel.getDatas("")
                vmProductViewModel.productDatas.observe(viewLifecycleOwner) { pro ->
                    vmSpecificationViewModel.getDatas("")
                    vmSpecificationViewModel.specificationDatas.observe(viewLifecycleOwner) {

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

                                    Activity.ddlActivityList.clear()
                                    Activity.ddlActivityList.add(
                                        DdlNormalModel(
                                            "請選擇",
                                            "",
                                            ""
                                        )
                                    )

                                    Activity.dbActivityList.filter {
                                        it.channelDetail.contains(
                                            ChannelDetail.ddlChannelDetailList[ddlPositionChannelDetail].id
                                        )
                                    }
                                        .forEach {
                                            Activity.ddlActivityList.add(
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
                                            Activity.ddlActivityList
                                        )
                                    spinnerActivity.adapter = customDropDownAdapter
                                    spinnerActivity.setSelection(0)


                                    processFilter()

                                }

                                override fun onNothingSelected(parent: AdapterView<*>) {}
                            }

                        val customDropDownAdapter =
                            CustomDropDownAdapter("channeldetail", context, ChannelDetail.ddlChannelDetailList)
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

        vmGroupViewModel.groupDatas.observe(viewLifecycleOwner) {
            vmMemberViewModel.memberDatas.observe(viewLifecycleOwner) {
                vmProductViewModel.productDatas.observe(viewLifecycleOwner) {

                    Group.ddlGroupList.clear()
                    Group.ddlGroupList.add(
                        DdlNormalModel(
                            "請選擇",
                            "",
                            ""
                        )
                    )
                    Group.dbGroupList.forEach {
                        Group.ddlGroupList.add(
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

                                Member.ddlMemberList.clear()
                                Member.ddlMemberList.add(
                                    DdlNormalModel(
                                        "請選擇",
                                        "",
                                        ""
                                    )
                                )

                                Member.dbMemberList.filter { it.group.contains(Group.ddlGroupList[ddlPositionGroup].id) }
                                    .forEach {
                                        Member.ddlMemberList.add(
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
                                        Member.ddlMemberList
                                    )
                                spinnerMember.adapter = customDropDownAdapter
                                spinnerMember.setSelection(0)

                                processFilter()

                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }

                    val customDropDownAdapter =
                        CustomDropDownAdapter("group", context, Group.ddlGroupList)
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

        return root
    }

    private fun setProductRecyclerView(list: MutableList<Product>) {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_list_item_product.layoutManager = layoutManager
        rv_list_item_product.adapter = ProductAdapter(list)
    }

    private fun processFilter() {
        var list = Product.dbProductList

        if (ddlPositionChannelDetail != 0) {
            list =
                list.filter { it.channelDetail.contains(ChannelDetail.ddlChannelDetailList[ddlPositionChannelDetail].id) }
                    .toMutableList()
        }
        if (ddlPositionChannelDetail != 0 && ddlPositionActivity != 0) {
            list =
                list.filter { it.activity == Activity.ddlActivityList[ddlPositionActivity].id }
                    .toMutableList()
        }
        if (ddlPositionGroup != 0) {
            list =
                list.filter { it.group.contains(Group.ddlGroupList[ddlPositionGroup].id) }
                    .toMutableList()
        }
        if (ddlPositionGroup != 0 && ddlPositionMember != 0) {

            var specificationList = Specification.dbSpecificationList.filter {
                it.specificationType == "Member" && it.member.contains(Member.ddlMemberList[ddlPositionMember].id)
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