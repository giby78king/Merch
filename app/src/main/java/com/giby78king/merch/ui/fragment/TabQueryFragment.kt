package com.giby78king.merch.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.ChannelDetailAdapter
import com.giby78king.merch.Adapter.CustomDropDownAdapter
import com.giby78king.merch.Adapter.MemberAdapter
import com.giby78king.merch.Model.*
import com.giby78king.merch.Model.Channel.Companion.ddlChannelList
import com.giby78king.merch.Model.ChannelDetail.Companion.ChannelDetailList
import com.giby78king.merch.Model.Member.Companion.MemberList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.*


class TabQueryFragment : Fragment() {
    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_tab_query, container, false)

        var ddlGroupPosition = 0
        var ddlChannelPosition = 0

        val vmGroupViewModel =
            ViewModelProvider(this)[VmGroupViewModel::class.java]

        vmGroupViewModel.groupDatas.observe(viewLifecycleOwner) {
            Group.ddlGroupList.clear()
            Group.ddlGroupList.add(
                DdlNormalModel(
                    "請選擇",
                    "",
                    ""
                )
            )
            Group.GroupList.forEach {
                Group.ddlGroupList.add(
                    DdlNormalModel(
                        it.chName,
                        it.id,
                        it.id
                    )
                )
            }
            val spinnerGroup = root.findViewById<Spinner>(R.id.spinnerGroup)
            spinnerGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ddlGroupPosition = position

                    val rvMember =
                        root.findViewById<RecyclerView>(R.id.rv_list_item_member)
                    val rvChannelDetail =
                        root.findViewById<RecyclerView>(R.id.rv_list_item_channelDetail)
                    var filterList =
                        MemberList.filter { it.group.contains(Group.ddlGroupList[position].id) }
                            .toMutableList()

                    if (position == 0) {
                        filterList = MemberList
                    }

                    setMemberRecyclerView(filterList)
                    rvMember.isVisible = true
                    rvChannelDetail.isVisible = false
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>
                ) {
                }
            }

            spinnerGroup.setOnTouchListener(OnTouchListener { v, event ->
                val rvMember =
                    root.findViewById<RecyclerView>(R.id.rv_list_item_member)
                rvMember.isVisible = true
                val rvChannelDetail =
                    root.findViewById<RecyclerView>(R.id.rv_list_item_channelDetail)
                rvChannelDetail.isVisible = false
                false
            })

            val customDropDownAdapter =
                CustomDropDownAdapter("group", this.context, Group.ddlGroupList)
            spinnerGroup.adapter = customDropDownAdapter
            spinnerGroup.setSelection(ddlGroupPosition)
        }

        val vmChannelViewModel =
            ViewModelProvider(this)[VmChannelViewModel::class.java]

        vmChannelViewModel.channelDatas.observe(viewLifecycleOwner) {
            Channel.ddlChannelList.clear()
            Channel.ddlChannelList.add(
                DdlNormalModel(
                    "請選擇",
                    "",
                    ""
                )
            )
            Channel.ChannelList.sortedBy { it.order }.forEach {
                Channel.ddlChannelList.add(
                    DdlNormalModel(
                        it.name,
                        it.icon,
                        it.id
                    )
                )
            }

            val spinnerChannel = root.findViewById<Spinner>(R.id.spinnerChannel)

            spinnerChannel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    val rvMember =
                        root.findViewById<RecyclerView>(R.id.rv_list_item_member)

                    val rvChannelDetail =
                        root.findViewById<RecyclerView>(R.id.rv_list_item_channelDetail)

                    var filterList =
                        ChannelDetailList.filter { it.belong == ddlChannelList[position].id }
                            .toMutableList()

                    if (position == 0) {
                        filterList = ChannelDetailList
                    }
                    setChannelDetailRecyclerView(filterList)

                    rvMember.isVisible = false
                    rvChannelDetail.isVisible = true

                    ddlChannelPosition = position
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>
                ) {
                }
            }
            spinnerChannel.setOnTouchListener(OnTouchListener { v, event ->
                val rvMember =
                    root.findViewById<RecyclerView>(R.id.rv_list_item_member)
                rvMember.isVisible = false
                val rvChannelDetail =
                    root.findViewById<RecyclerView>(R.id.rv_list_item_channelDetail)
                rvChannelDetail.isVisible = true
                false
            })

            val customDropDownAdapter =
                CustomDropDownAdapter("channel", this.context, Channel.ddlChannelList)
            spinnerChannel.adapter = customDropDownAdapter
            spinnerChannel.setSelection(ddlChannelPosition)
        }


        val vmMemberViewModel =
            ViewModelProvider(this)[VmMemberViewModel::class.java]

//        val memberList = arrayOf(
//            "沁沁",
////            "AMBER",
////            "JESSY",
////            "KESHA",
////            "TIFFANY",
////            "卡卡",
////            "奶昔",
////            "安娜",
////            "慈妹",
////            "朱朱",
////            "東東",
////            "檸檬",
////            "潔米",
////            "游游",
////            "盈瑩",
////            "穎兒",
////            "維心",
////            "蓁蓁",
////            "豫花花"
//        )
//        for (mem in memberList) {
//            for (i in 0..0) {
//                val id = mem.toString()
//                val mstData = hashMapOf(
//                    "firstBoard" to "2022",
//                    "birthday" to "0",
//                    "employed" to true,
//                    "group" to arrayOf("Fubon Angels","Fubon Guardians","Fubon Braves").toCollection(java.util.ArrayList()),
//                    "height" to 100,
//                    "icon" to "",
//                    "weight" to 0,
//                    "id" to id,
//                    "ig" to "ig",
//                    "name" to id.toString(),
//                    "number" to "097",
//                    "sex" to "girl",
//                )
//                vmMemberViewModel.insertOne(id, mstData)
//            }
//        }


        vmMemberViewModel.getDatas("")
        vmMemberViewModel.memberDatas.observe(viewLifecycleOwner) {
            val vmProductViewModel =
                ViewModelProvider(this)[VmProductViewModel::class.java]
            vmProductViewModel.getDatas("")
            vmProductViewModel.productDatas.observe(viewLifecycleOwner) {
                //todo 依照價值排序
                setMemberRecyclerView(MemberList)
            }
        }

        val vmChannelDetailViewModel =
            ViewModelProvider(this)[VmChannelDetailViewModel::class.java]

        vmChannelDetailViewModel.getDatas("")
        vmChannelDetailViewModel.channelDetailDatas.observe(viewLifecycleOwner) {
            val vmProductViewModel =
                ViewModelProvider(this)[VmProductViewModel::class.java]
            vmProductViewModel.getDatas("")
            vmProductViewModel.productDatas.observe(viewLifecycleOwner) {
                setChannelDetailRecyclerView(ChannelDetailList)
            }

        }
//        val id = "Fubon Braves Store"
//        val mstData = hashMapOf(
//                    "belong" to "Store",
//                    "id" to id,
//                    "imgUrl" to "Fubon Braves Store",
//                    "name" to "臺北富邦勇士實體商店",
//
//                )
//        vmChannelDetailViewModel.insertOne(id, mstData)

        return root
    }

    fun setMemberRecyclerView(list: MutableList<Member>) {
        val vmTopStockDepositoryViewModel: VmTopProductDepositoryViewModel =
            ViewModelProvider(requireActivity())[VmTopProductDepositoryViewModel::class.java]

        val rvSetting =
            root.findViewById<RecyclerView>(R.id.rv_list_item_member)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val numberOfColumns = 2
        rvSetting.layoutManager = GridLayoutManager(context, numberOfColumns)
        rvSetting.adapter = MemberAdapter(list, vmTopStockDepositoryViewModel)
        rvSetting.isVisible = true
    }

    fun setChannelDetailRecyclerView(list: MutableList<ChannelDetail>) {
        val vmTopStockDepositoryViewModel: VmTopProductDepositoryViewModel =
            ViewModelProvider(requireActivity())[VmTopProductDepositoryViewModel::class.java]

        val rvSetting =
            root.findViewById<RecyclerView>(R.id.rv_list_item_channelDetail)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val numberOfColumns = 1
        rvSetting.layoutManager = GridLayoutManager(context, numberOfColumns)
        rvSetting.adapter = ChannelDetailAdapter(list, vmTopStockDepositoryViewModel)
        rvSetting.isVisible = false
    }
}