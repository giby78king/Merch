package com.giby78king.merch.ui.trade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.TradeAdapter
import com.giby78king.merch.Model.Trade
import com.giby78king.merch.R

class TradeFragment : Fragment() {

    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_trade, container, false)
//        val vmTradeViewModel =
//            ViewModelProvider(this)[VmTradeViewModel::class.java]
//
//        vmTradeViewModel.getDatas("")
//        vmTradeViewModel.tradeDatas.observe(viewLifecycleOwner) {
//
//            val vmProductViewModel =
//                ViewModelProvider(this)[VmProductViewModel::class.java]
//            vmProductViewModel.getDatas("")
//            vmProductViewModel.productDatas.observe(viewLifecycleOwner) {
//                val vmMemberViewModel =
//                    ViewModelProvider(this)[VmMemberViewModel::class.java]
//                vmMemberViewModel.getDatas("")
//                vmMemberViewModel.memberDatas.observe(viewLifecycleOwner) {
//                    setTradeRecyclerView(TradeList)
//                }
//            }
//        }
        return root
    }
    private fun setTradeRecyclerView(list: MutableList<Trade>) {
        val rvSetting =
            root.findViewById<RecyclerView>(R.id.rv_list_item_trade)

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val numberOfColumns = 1
        rvSetting.layoutManager = GridLayoutManager(context, numberOfColumns)
        rvSetting.adapter = TradeAdapter(list)
    }

}