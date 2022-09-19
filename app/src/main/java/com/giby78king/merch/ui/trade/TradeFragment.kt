package com.giby78king.merch.ui.trade

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Adapter.TradeAdapter
import com.giby78king.merch.Model.Trade
import com.giby78king.merch.Model.Trade.Companion.dbTradeList
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.*
import com.giby78king.merch.ui.TradeEditPage
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TradeFragment : Fragment() {

    lateinit var root: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_trade, container, false)
        val vmTradeViewModel =
            ViewModelProvider(this)[VmTradeViewModel::class.java]

        vmTradeViewModel.getDatas("")
        vmTradeViewModel.tradeDatas.observe(viewLifecycleOwner) {
                    setTradeRecyclerView(dbTradeList)
            }


        val vmActivitylViewModel =
            ViewModelProvider(this)[VmActivitylViewModel::class.java]
        val vmChannelDetailViewModel =
            ViewModelProvider(this)[VmChannelDetailViewModel::class.java]
        val vmTradeDetailViewModel =
            ViewModelProvider(this)[VmTradeDetailViewModel::class.java]
        val vmTradeRuleViewModel =
            ViewModelProvider(this)[VmTradeRuleViewModel::class.java]
        val vmProductViewModel =
            ViewModelProvider(this)[VmProductViewModel::class.java]
        val vmSpecificationViewModel =
            ViewModelProvider(this)[VmSpecificationViewModel::class.java]
        val vmStockDepositoryViewModel =
            ViewModelProvider(this)[VmStockDepositoryViewModel::class.java]
        vmActivitylViewModel.getDatas("")
        vmChannelDetailViewModel.getDatas("")
        vmTradeRuleViewModel.getDatas("")
        vmTradeViewModel.getDatas("")
        vmTradeDetailViewModel.getDatas("")
        vmProductViewModel.getDatas("")
        vmSpecificationViewModel.getDatas("")
        vmStockDepositoryViewModel.getDatas("")



        val fab = root.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener(fabClick)

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
    private val fabClick =
        View.OnClickListener {
            startActivity(Intent().setClass(this.requireActivity(), TradeEditPage::class.java))
        }
}