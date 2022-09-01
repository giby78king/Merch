package com.giby78king.merch.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.ProductDepositoryAdapter
import com.giby78king.merch.Model.Member
import com.giby78king.merch.Model.ProductDepository
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmMemberViewModel
import com.giby78king.merch.ViewModel.VmProductDepositoryViewModel
import com.giby78king.merch.ViewModel.VmProductViewModel
import com.giby78king.merch.ViewModel.VmTopProductDepositoryViewModel

class CollectionFragment : Fragment() {

    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_collection, container, false)
//        val vmProductDepositoryViewModel =
//            ViewModelProvider(this)[VmProductDepositoryViewModel::class.java]
//
//        vmProductDepositoryViewModel.getDatas("")
//        vmProductDepositoryViewModel.productDepositoryDatas.observe(viewLifecycleOwner) {
//
//            val vmProductViewModel =
//                ViewModelProvider(this)[VmProductViewModel::class.java]
//            vmProductViewModel.getDatas("")
//            vmProductViewModel.productDatas.observe(viewLifecycleOwner) {
//                val vmMemberViewModel =
//                    ViewModelProvider(this)[VmMemberViewModel::class.java]
//                vmMemberViewModel.getDatas("")
//                vmMemberViewModel.memberDatas.observe(viewLifecycleOwner) {
//                    setProductDepositoryRecyclerView(ProductDepository.ProductDepositoryList)
//                }
//            }
//        }
        //TODO 排序按鈕 喜愛 最近▲ 價值▲
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