package com.giby78king.merch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.ProductDepositoryAdapter
import com.giby78king.merch.Model.ProductDepository
import com.giby78king.merch.Model.ProductDepository.Companion.dbProductDepositoryList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.*
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel.Companion.selectChannel
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel.Companion.selectMember

class TabProductDepositoryFragment : Fragment() {
    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_tab_productdepository, container, false)

        val vmProductDepositoryViewModel =
            ViewModelProvider(this)[VmProductDepositoryViewModel::class.java]

        vmProductDepositoryViewModel.getDatas("")
        vmProductDepositoryViewModel.productDepositoryDatas.observe(viewLifecycleOwner) {

            val vmProductViewModel =
                ViewModelProvider(this)[VmProductViewModel::class.java]
            vmProductViewModel.getDatas("")
            vmProductViewModel.productDatas.observe(viewLifecycleOwner) {
                val vmMemberViewModel =
                    ViewModelProvider(this)[VmMemberViewModel::class.java]
                vmMemberViewModel.getDatas("")
                vmMemberViewModel.memberDatas.observe(viewLifecycleOwner) {
                    setProductDepositoryRecyclerView(dbProductDepositoryList)
                }
            }

            val vmCostStockDepositoryViewModel =
                ViewModelProvider(requireActivity())[VmTopProductDepositoryViewModel::class.java]

            vmCostStockDepositoryViewModel.depoToDetail.observe(viewLifecycleOwner) { vmData ->
                var filterList = mutableListOf<ProductDepository>()

                if (selectMember.id.isNotEmpty()) {
                    filterList = dbProductDepositoryList.filter {
                        it.group.contains(selectMember.belong) && it.member.contains(
                            selectMember.id
                        )
                    }.toMutableList()

                    if (selectChannel.id.isNotEmpty()) {
                        filterList = filterList.filter {
                            it.channelDetail == selectChannel.id
                        }.toMutableList()
                    }
                }

                if (selectChannel.id.isNotEmpty()) {
                    filterList = dbProductDepositoryList.filter {
                        it.channelDetail == selectChannel.id
                    }.toMutableList()
                    if (selectMember.id.isNotEmpty()) {
                        filterList = filterList.filter {
                            it.group.contains(selectMember.belong) && it.member.contains(
                                selectMember.id
                            )
                        }.toMutableList()
                    }
                }

                if (selectMember.id.isEmpty() && selectChannel.id.isEmpty()) {
                    filterList = dbProductDepositoryList
                }

                setProductDepositoryRecyclerView(filterList)
            }
        }

        return root
    }

    private fun setProductDepositoryRecyclerView(list: MutableList<ProductDepository>) {
        val rvSetting =
            root.findViewById<RecyclerView>(R.id.rv_list_item_member)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val numberOfColumns = 1
        rvSetting.layoutManager = GridLayoutManager(context, numberOfColumns)
        rvSetting.adapter = ProductDepositoryAdapter(list)
    }
}