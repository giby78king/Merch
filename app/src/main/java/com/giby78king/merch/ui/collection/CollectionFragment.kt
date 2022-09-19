package com.giby78king.merch.ui.collection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.Model.TradeDetail.Companion.dbTradeDetailList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.*

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

//    private fun setProductDepositoryRecyclerView(list: MutableList<StockDepository>) {
//        val rvSetting =
//            root.findViewById<RecyclerView>(R.id.rv_list_item_member)
//
//        val layoutManager = LinearLayoutManager(context)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        val numberOfColumns = 1
//        rvSetting.layoutManager = GridLayoutManager(context, numberOfColumns)
//        rvSetting.adapter = ProductDepositoryAdapter(list)
//    }

}