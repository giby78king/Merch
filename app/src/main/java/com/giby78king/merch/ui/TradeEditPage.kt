package com.giby78king.merch.ui

import android.app.DatePickerDialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.giby78king.merch.Adapter.*
import com.giby78king.merch.Domain.StockDepositoryEn
import com.giby78king.merch.Domain.TradeDetailEn
import com.giby78king.merch.Domain.TradeEn
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.*
import com.giby78king.merch.Model.Activity.Companion.dbActivityList
import com.giby78king.merch.Model.Activity.Companion.ddlActivityList
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.ddlChannelDetailList
import com.giby78king.merch.Model.DdlPositionCollection.Companion.ddlPositionActivity
import com.giby78king.merch.Model.DdlPositionCollection.Companion.ddlPositionChannelDetail
import com.giby78king.merch.Model.DdlPositionCollection.Companion.ddlPositionModify
import com.giby78king.merch.Model.DdlPositionCollection.Companion.ddlPositionOther
import com.giby78king.merch.Model.DdlPositionCollection.Companion.ddlPositionProduct
import com.giby78king.merch.Model.DdlPositionCollection.Companion.ddlPositionSpecification
import com.giby78king.merch.Model.DdlPositionCollection.Companion.ddlPositionTransType
import com.giby78king.merch.Model.Product.Companion.dbProductList
import com.giby78king.merch.Model.Product.Companion.ddlProductList
import com.giby78king.merch.Model.Specification.Companion.dbSpecificationList
import com.giby78king.merch.Model.Specification.Companion.ddlSpecificationList
import com.giby78king.merch.Model.StockDepository.Companion.dbStockDepositoryList
import com.giby78king.merch.Model.Trade.Companion.dbTradeList
import com.giby78king.merch.Model.Trade.Companion.ddlModifyList
import com.giby78king.merch.Model.Trade.Companion.ddlOtherList
import com.giby78king.merch.Model.Trade.Companion.ddlTransType
import com.giby78king.merch.Model.Trade.Companion.tradeModifyList
import com.giby78king.merch.Model.Trade.Companion.tradeOtherList
import com.giby78king.merch.Model.TradeDetail.Companion.dbTradeDetailList
import com.giby78king.merch.Model.TradeDetail.Companion.nowEditId
import com.giby78king.merch.Model.TradeDetail.Companion.specModifyList
import com.giby78king.merch.Model.TradeDetail.Companion.specOtherList
import com.giby78king.merch.Model.TradeDetail.Companion.tempSpecList
import com.giby78king.merch.Model.TradeRule.Companion.dbTradeRuleList
import com.giby78king.merch.R
import com.giby78king.merch.TimeFormat
import com.giby78king.merch.ViewModel.*
import kotlinx.android.synthetic.main.activity_product_edit_page.*
import kotlinx.android.synthetic.main.activity_trade_edit_page.*
import kotlinx.android.synthetic.main.activity_trade_edit_page.btnSubmit
import kotlinx.android.synthetic.main.activity_trade_edit_page.spinnerActivity
import kotlinx.android.synthetic.main.activity_trade_edit_page.spinnerChannelDetail
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.getInstance


class TradeEditPage : AppCompatActivity() {

    private lateinit var nowEditText: EditText
    var init = true
    lateinit var page: TradeEditPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trade_edit_page)

        val bundle = intent.extras
        var selectedTrade = bundle?.getString("selectedTrade").toString()
        page = this

        editProcessDate.setText(TimeFormat().TodayDate().yyyyMMdd())

        tempSpecList.clear()
        ddlTransType.clear()
        ddlTransType.add(DdlNormalModel("購買", "", "Purchase"))
        ddlTransType.add(DdlNormalModel("銷售", "", "Sell"))
        dbActivityList.forEach {
            ddlActivityList.add(
                DdlNormalModel(
                    it.name,
                    it.imgUrl,
                    it.id
                )
            )
        }

        spinnerTransType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ddlPositionTransType = position

                    when (ddlTransType[ddlPositionTransType].id) {
                        "Purchase" -> {
                            txtPrice.text = "總定價"
                            txtTitleTotalSpecPrice.text = "當前總定價"
                            txtModify.text = "總折扣"
                            txtTitleTotalSpecModify.text = "當前總折扣"
                            txtTradeRule.text = "折扣選擇"
                        }
                        "Sell" -> {
                            txtPrice.text = "總售價"
                            txtTitleTotalSpecPrice.text = "當前總售價"
                            txtModify.text = "總手續費"
                            txtTitleTotalSpecModify.text = "當前總手續費"
                            txtTradeRule.text = "手續費選擇"
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        var customDropDownAdapter =
            CustomDropDownAdapter(
                "channeldetail",
                this,
                ddlTransType
            )
        spinnerTransType.adapter = customDropDownAdapter
        spinnerTransType.setSelection(0)


        val vmActivitylViewModel =
            ViewModelProvider(this)[VmActivitylViewModel::class.java]
        val vmChannelDetailViewModel =
            ViewModelProvider(this)[VmChannelDetailViewModel::class.java]
        val vmTradeViewModel =
            ViewModelProvider(this)[VmTradeViewModel::class.java]
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


        ddlChannelDetailList.clear()
        dbChannelDetailList.forEach {
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
                    ddlPositionChannelDetail = position

                    ddlActivityList.clear()
                    dbActivityList.filter {
                        it.channelDetail.contains(
                            ddlChannelDetailList[ddlPositionChannelDetail].id
                        )
                    }.forEach {
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

                                ddlProductList.clear()
                                dbProductList.filter {
                                    it.activity == ddlActivityList[ddlPositionActivity].id && it.channelDetail.contains(
                                        ddlChannelDetailList[ddlPositionChannelDetail].id
                                    )
                                }.forEach {
                                    ddlProductList.add(
                                        DdlNormalModel(
                                            it.name,
                                            it.imgUrl,
                                            it.id
                                        )
                                    )
                                }
                                spinnerProduct.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            ddlPositionProduct = position

                                            ddlSpecificationList.clear()
                                            dbSpecificationList.filter {
                                                it.product == ddlProductList[ddlPositionProduct].id
                                            }.forEach {
                                                ddlSpecificationList.add(
                                                    DdlNormalModel(
                                                        it.title,
                                                        it.imgUrl,
                                                        it.id
                                                    )
                                                )
                                            }
                                            setSpinner(
                                                spinnerSpecification,
                                                ddlSpecificationList,
                                                "specification",
                                                0
                                            )

                                            spinnerSpecification.onItemSelectedListener =
                                                object :
                                                    AdapterView.OnItemSelectedListener {
                                                    override fun onItemSelected(
                                                        parent: AdapterView<*>,
                                                        view: View?,
                                                        position: Int,
                                                        id: Long
                                                    ) {
                                                        ddlPositionSpecification =
                                                            position

                                                        val spInfo =
                                                            dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0]
                                                        val priceIndex =
                                                            dbProductList.filter { it.id == spInfo.product }[0].channelDetail.indexOf(
                                                                ddlChannelDetailList[ddlPositionChannelDetail].id
                                                            )
                                                        txtSpecTagPrice.text =
                                                            dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0].price[priceIndex].toString()

                                                        txtSpecPrice.text =
                                                            dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0].price[priceIndex].toString()

                                                        if (nowEditId.isNotEmpty()) {
                                                            val lastId =
                                                                tempSpecList.indexOfFirst { it.id == nowEditId }
                                                            if (lastId != -1) {
                                                                tempSpecList[lastId].specification =
                                                                    ddlSpecificationList[ddlPositionSpecification].id

                                                                tempSpecList[lastId].price =
                                                                    dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0].price[priceIndex].toString()
                                                                        .toInt()

                                                                val layoutManager =
                                                                    LinearLayoutManager(
                                                                        page
                                                                    )
                                                                layoutManager.orientation =
                                                                    LinearLayoutManager.HORIZONTAL
                                                                rvSpecification.layoutManager =
                                                                    layoutManager
                                                                rvSpecification.adapter =
                                                                    TradeDetailEditSpecificationAdapter(
                                                                        tempSpecList,
                                                                        page
                                                                    )
                                                            }
                                                        }
//                                                                        setSpec()
                                                    }

                                                    override fun onNothingSelected(
                                                        parent: AdapterView<*>
                                                    ) {
                                                    }
                                                }
                                        }

                                        override fun onNothingSelected(parent: AdapterView<*>) {}
                                    }
                                setSpinner(
                                    spinnerProduct,
                                    ddlProductList,
                                    "product",
                                    0
                                )

                                if (ddlProductList.size == 0) {
                                    ddlSpecificationList.clear()
                                    setSpinner(
                                        spinnerSpecification,
                                        ddlSpecificationList,
                                        "specification",
                                        0
                                    )
                                    txtSpecTagPrice.text = "0"
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }

                    setSpinner(spinnerActivity, ddlActivityList, "activity", 0)

                    ddlModifyList.clear()
                    var sortModifyList = mutableListOf<String>()

                    dbTradeRuleList.sortedBy { it.id }.filter {
                        (it.type == "Modify" || it.type == "Fluctuate") && it.channelDetail.contains(
                            ddlChannelDetailList[ddlPositionChannelDetail].id
                        ) && (it.transType == ddlTransType[ddlPositionTransType].id || it.transType == "Fluctuate")
                    }.forEach {
                        ddlModifyList.add(
                            DdlNormalModel(
                                it.name,
                                it.imgUrl,
                                it.id
                            )
                        )
                        if (it.default) {
                            if (!sortModifyList.contains(it.id))            //排除重複點選
                            {
                                sortModifyList.add(it.id)
                            }
                        }
                    }

                    tradeModifyList = sortModifyList

                    vmTradeViewModel.setSelectedModify()

                    setSpinner(
                        spinnerModify,
                        ddlModifyList,
                        "traderule",
                        0
                    )

                    ddlOtherList.clear()
                    var sortOtherList = mutableListOf<String>()
                    sortOtherList.clear()

                    dbTradeRuleList.sortedBy { it.id }.filter {
                        (it.type == "Other" || it.type == "Fluctuate") && it.channelDetail.contains(
                            ddlChannelDetailList[ddlPositionChannelDetail].id
                        ) && (it.transType == ddlTransType[ddlPositionTransType].id || it.transType == "Fluctuate")
                    }.forEach {
                        ddlOtherList.add(
                            DdlNormalModel(
                                it.name,
                                it.imgUrl,
                                it.id
                            )
                        )
                        if (it.default) {
                            if (!sortOtherList.contains(it.id))            //排除重複點選
                            {
                                sortOtherList.add(it.id)
                            }
                        }
                    }

                    tradeOtherList = sortOtherList
                    vmTradeViewModel.setSelectedOther()

                    spinnerOther.onItemSelectedListener =
                        object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                ddlPositionOther = position
                            }

                            override fun onNothingSelected(parent: AdapterView<*>) {}
                        }
                    setSpinner(
                        spinnerOther,
                        ddlOtherList,
                        "traderule",
                        0
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        customDropDownAdapter =
            CustomDropDownAdapter(
                "channeldetail",
                this,
                ddlChannelDetailList
            )
        spinnerChannelDetail.adapter = customDropDownAdapter


        ddlModifyList.clear()
        dbTradeRuleList.sortedBy { it.id }.filter {
            (it.type == "Modify" || it.type == "Fluctuate") && it.channelDetail.contains(
                ddlChannelDetailList[ddlPositionChannelDetail].id
            ) && (it.transType == ddlTransType[ddlPositionTransType].id || it.transType == "Fluctuate")
        }.forEach {
            ddlModifyList.add(
                DdlNormalModel(
                    it.name,
                    it.imgUrl,
                    it.id
                )
            )
        }
        spinnerModify.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ddlPositionModify = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        setSpinner(
            spinnerModify,
            ddlModifyList,
            "traderule",
            0
        )

        ddlOtherList.clear()
        dbTradeRuleList.sortedBy { it.id }.filter {
            (it.type == "Other" || it.type == "Fluctuate") && it.channelDetail.contains(
                ddlChannelDetailList[ddlPositionChannelDetail].id
            ) && (it.transType == ddlTransType[ddlPositionTransType].id || it.transType == "Fluctuate")
        }.forEach {
            ddlOtherList.add(
                DdlNormalModel(
                    it.name,
                    it.imgUrl,
                    it.id
                )
            )
        }

        spinnerOther.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ddlPositionOther = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        setSpinner(
            spinnerOther,
            ddlOtherList,
            "traderule",
            0
        )
        setSpec()




        btnAddModify.setOnClickListener {
            if (ddlModifyList.size > 0) {
                if (!tradeModifyList.contains(ddlModifyList[ddlPositionModify].id))            //排除重複點選
                {
                    tradeModifyList.add(ddlModifyList[ddlPositionModify].id)
                }

                var sortList = mutableListOf<String>()
                dbTradeRuleList.filter { it.type == "Modify" || it.type == "Fluctuate" }
                    .sortedBy { it.id }.toMutableList().forEach { chd ->
                        tradeModifyList.forEach { act ->
                            if (act == chd.id) {
                                sortList.add(act)
                            }
                        }
                    }
                tradeModifyList = sortList

                vmTradeViewModel.setSelectedModify()
            }
        }

        vmTradeViewModel.SelectedModifyDatas.observe(this) {
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            val numberOfColumns = 3
            rvAddPoolModify.layoutManager =
                GridLayoutManager(this, numberOfColumns)
            rvAddPoolModify.adapter = PoolTradeEditModifyAdapter(tradeModifyList, page)

            specModifyList.clear()

            tradeModifyList.forEach {
                specModifyList.add(
                    tempPriceDetail(
                        price = 0,
                        rule = it
                    )
                )
            }
            if (specModifyList.none { it.rule == "sum" }) {
                specModifyList.add(
                    tempPriceDetail(
                        price = 0,
                        rule = "sum"
                    )
                )
            }
            val layoutManagerSpec = LinearLayoutManager(this)
            layoutManagerSpec.orientation = LinearLayoutManager.VERTICAL
            rvAddPoolSpecModify.layoutManager = layoutManagerSpec
            rvAddPoolSpecModify.adapter = PoolTradeEditSpecModifyAdapter(specModifyList, page)
        }

        btnAddOther.setOnClickListener {
            if (ddlOtherList.size > 0) {
                if (!tradeOtherList.contains(ddlOtherList[ddlPositionOther].id))            //排除重複點選
                {
                    tradeOtherList.add(ddlOtherList[ddlPositionOther].id)
                }

                var sortList = mutableListOf<String>()
                dbTradeRuleList.filter { it.type == "Other" || it.type == "Fluctuate" }
                    .sortedBy { it.id }.toMutableList().forEach { chd ->
                        tradeOtherList.forEach { act ->
                            if (act == chd.id) {
                                sortList.add(act)
                            }
                        }
                    }
                tradeOtherList = sortList
                vmTradeViewModel.setSelectedOther()
            }
        }

        vmTradeViewModel.SelectedOtherDatas.observe(this) {
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            val numberOfColumns = 3
            rvAddPoolOther.layoutManager =
                GridLayoutManager(this, numberOfColumns)
            rvAddPoolOther.adapter = PoolTradeEditOtherAdapter(tradeOtherList, page)

            specOtherList.clear()

            tradeOtherList.forEach {
                specOtherList.add(
                    tempPriceDetail(
                        price = 0,
                        rule = it
                    )
                )
            }
            if (specOtherList.none { it.rule == "sum" }) {
                specOtherList.add(
                    tempPriceDetail(
                        price = 0,
                        rule = "sum"
                    )
                )
            }
            val layoutManagerSpec = LinearLayoutManager(this)
            layoutManagerSpec.orientation = LinearLayoutManager.VERTICAL
            rvAddPoolSpecOther.layoutManager = layoutManagerSpec
            rvAddPoolSpecOther.adapter = PoolTradeEditSpecOtherAdapter(specOtherList, page)
        }

        editProcessDate.setOnClickListener(listener)
        editAccountDate.setOnClickListener(listener)
        editStockDate.setOnClickListener(listener)

        vmTradeViewModel.ProcessTradeDetailDatas.observe(this) { data ->

            if (nowEditId.isNotEmpty()) {
                val lastId =
                    data.indexOfFirst { it.id == nowEditId }
                if (lastId != -1) {
                    data[lastId].specification =
                        ddlSpecificationList[ddlPositionSpecification].id
                    val layoutManager =
                        LinearLayoutManager(page)
                    layoutManager.orientation =
                        LinearLayoutManager.HORIZONTAL
                    rvSpecification.layoutManager =
                        layoutManager
                    rvSpecification.adapter =
                        TradeDetailEditSpecificationAdapter(
                            data,
                            page
                        )

                    val modify = arrayListOf<Int>()
                    val other = arrayListOf<Int>()
                    tradeModifyList.clear()
                    specModifyList.forEach {
                        if (it.rule != "sum") {
                            Log.d("tradeModifyList.add(it.rule)", "::" + it.rule)
                            modify.add(it.price)
                            tradeModifyList.add(it.rule)
                        }
                    }
                    if (specModifyList.none { it.rule == "sum" }) {
                        specModifyList.add(
                            tempPriceDetail(
                                price = 0,
                                rule = "sum"
                            )
                        )
                    }
                    tradeOtherList.clear()
                    specOtherList.forEach {
                        if (it.rule != "sum") {
                            other.add(it.price)
                            tradeOtherList.add(it.rule)
                        }
                    }
                    if (specOtherList.none { it.rule == "sum" }) {
                        specOtherList.add(
                            tempPriceDetail(
                                price = 0,
                                rule = "sum"
                            )
                        )
                    }
                    tempSpecList[lastId].price = txtSpecPrice.text.toString().toInt()
                    tempSpecList[lastId].modify =
                        modify
                    tempSpecList[lastId].other =
                        other

                }
            }
            setSpec()
        }

        vmTradeViewModel.SelectedSpecDatas.observe(this) { data ->

            spinnerChannelDetail.onItemSelectedListener = null
            spinnerActivity.onItemSelectedListener = null
            spinnerProduct.onItemSelectedListener = null
            spinnerSpecification.onItemSelectedListener = null

            val spInfo = dbSpecificationList.first { it.id == data.specification }
            val productInfo = dbProductList.first { it.id == spInfo.product }

            ddlChannelDetailList.clear()
            dbChannelDetailList.forEach {
                ddlChannelDetailList.add(
                    DdlNormalModel(
                        it.name,
                        it.imgUrl,
                        it.id
                    )
                )
            }
            setSpinner(spinnerChannelDetail, ddlChannelDetailList, "channeldetail", 0)
            spinnerChannelDetail.setSelection(ddlPositionChannelDetail)

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
                        dbActivityList.filter {
                            it.channelDetail.contains(
                                ddlChannelDetailList[ddlPositionChannelDetail].id
                            )
                        }.forEach {
                            ddlActivityList.add(
                                DdlNormalModel(
                                    it.name,
                                    it.imgUrl,
                                    it.id
                                )
                            )
                        }

                        setSpinner(spinnerActivity, ddlActivityList, "activity", 0)

                        spinnerActivity.setSelection(ddlActivityList.indexOfFirst { it.id == productInfo.activity })

                        spinnerActivity.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    ddlPositionActivity = position

                                    ddlProductList.clear()
                                    dbProductList.filter {
                                        it.activity == ddlActivityList[ddlPositionActivity].id && it.channelDetail.contains(
                                            ddlChannelDetailList[ddlPositionChannelDetail].id
                                        )
                                    }.forEach {
                                        ddlProductList.add(
                                            DdlNormalModel(
                                                it.name,
                                                it.imgUrl,
                                                it.id
                                            )
                                        )
                                    }

                                    setSpinner(
                                        spinnerProduct,
                                        ddlProductList,
                                        "product",
                                        0
                                    )

                                    if (ddlProductList.size == 0) {
                                        ddlSpecificationList.clear()
                                        setSpinner(
                                            spinnerSpecification,
                                            ddlSpecificationList,
                                            "specification",
                                            0
                                        )
                                        txtSpecTagPrice.text = "0"
                                    }

                                    spinnerProduct.setSelection(ddlProductList.indexOfFirst { it.id == productInfo.id })

                                    spinnerProduct.onItemSelectedListener =
                                        object : AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                parent: AdapterView<*>,
                                                view: View?,
                                                position: Int,
                                                id: Long
                                            ) {
                                                ddlPositionProduct = position

                                                ddlSpecificationList.clear()
                                                dbSpecificationList.filter {
                                                    it.product == ddlProductList[ddlPositionProduct].id
                                                }.forEach {
                                                    ddlSpecificationList.add(
                                                        DdlNormalModel(
                                                            it.title,
                                                            it.imgUrl,
                                                            it.id
                                                        )
                                                    )
                                                }
                                                setSpinner(
                                                    spinnerSpecification,
                                                    ddlSpecificationList,
                                                    "specification",
                                                    0
                                                )

                                                spinnerSpecification.setSelection(
                                                    ddlSpecificationList.indexOfFirst { it.id == data.specification })

                                                spinnerSpecification.onItemSelectedListener =
                                                    object :
                                                        AdapterView.OnItemSelectedListener {
                                                        override fun onItemSelected(
                                                            parent: AdapterView<*>,
                                                            view: View?,
                                                            position: Int,
                                                            id: Long
                                                        ) {
                                                            ddlPositionSpecification =
                                                                position

                                                            val spInfo =
                                                                dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0]
                                                            val priceIndex =
                                                                dbProductList.filter { it.id == spInfo.product }[0].channelDetail.indexOf(
                                                                    ddlChannelDetailList[ddlPositionChannelDetail].id
                                                                )
                                                            txtSpecTagPrice.text =
                                                                dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0].price[priceIndex].toString()

                                                            var sum = 0
                                                            var sumModify = 0
                                                            var sumOther = 0

                                                            if (data.other.size > 0 || data.modify.size > 0) {
                                                                sum =
                                                                    dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0].price[priceIndex].toString()
                                                                        .toInt()

                                                                for (i in 0 until data.modify.size) {
                                                                    specModifyList[i].price =
                                                                        data.modify[i]
                                                                    sum -= data.modify[i]
                                                                    sumModify += data.modify[i]
                                                                }

                                                                specModifyList[specModifyList.size - 1].price =
                                                                    sumModify

                                                                for (i in 0 until data.other.size) {
                                                                    specOtherList[i].price =
                                                                        data.other[i]
                                                                    sum += data.other[i]
                                                                    sumOther += data.modify[i]
                                                                }
                                                                specOtherList[specOtherList.size - 1].price =
                                                                    sumOther


                                                                tradeModifyList.clear()
                                                                specModifyList.forEach {
                                                                    if (it.rule != "sum") {
                                                                        tradeModifyList.add(it.rule)
                                                                    }
                                                                }
                                                                tradeOtherList.clear()
                                                                specOtherList.forEach {
                                                                    if (it.rule != "sum") {
                                                                        tradeOtherList.add(it.rule)
                                                                    }
                                                                }

                                                                txtSpecPrice.text = sum.toString()
                                                            } else {
                                                                txtSpecPrice.text =
                                                                    dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0].price[priceIndex].toString()
                                                            }



                                                            if (nowEditId.isNotEmpty()) {
                                                                val lastId =
                                                                    tempSpecList.indexOfFirst { it.id == nowEditId }
                                                                if (lastId != -1) {
                                                                    tempSpecList[lastId].specification =
                                                                        ddlSpecificationList[ddlPositionSpecification].id
                                                                    tempSpecList[lastId].price =
                                                                        txtSpecPrice.text.toString()
                                                                            .toInt()

                                                                    val layoutManager =
                                                                        LinearLayoutManager(page)
                                                                    layoutManager.orientation =
                                                                        LinearLayoutManager.HORIZONTAL
                                                                    rvSpecification.layoutManager =
                                                                        layoutManager
                                                                    rvSpecification.adapter =
                                                                        TradeDetailEditSpecificationAdapter(
                                                                            tempSpecList,
                                                                            page
                                                                        )
                                                                }
                                                                setSpec()
                                                            }

                                                        }

                                                        override fun onNothingSelected(
                                                            parent: AdapterView<*>
                                                        ) {
                                                        }
                                                    }
                                            }

                                            override fun onNothingSelected(parent: AdapterView<*>) {}
                                        }

                                }

                                override fun onNothingSelected(parent: AdapterView<*>) {}
                            }


                        var sum = txtSpecTagPrice.text.toString().toInt()

                        val specModifyList = mutableListOf<tempPriceDetail>()

                        if (tradeModifyList.size == data.modify.size) {
                            for (i in 0 until tradeModifyList.size) {
                                specModifyList.add(
                                    tempPriceDetail(
                                        price = data.modify[i],
                                        rule = tradeModifyList[i],
                                    )
                                )
                            }
                        }
                        var sumModify = 0

                        data.modify.forEach {
                            sumModify += it
                        }

                        if (specModifyList.none { it.rule == "sum" }) {
                            specModifyList.add(
                                tempPriceDetail(
                                    price = sumModify,
                                    rule = "sum"
                                )
                            )
                        }

                        val layoutManagerSpec = LinearLayoutManager(page)
                        layoutManagerSpec.orientation = LinearLayoutManager.VERTICAL
                        rvAddPoolSpecModify.layoutManager = layoutManagerSpec

                        rvAddPoolSpecModify.adapter =
                            PoolTradeEditSpecModifyAdapter(specModifyList, page)


                        val specOtherList = mutableListOf<tempPriceDetail>()
                        if (tradeOtherList.size == data.other.size) {
                            for (i in 0 until tradeOtherList.size) {
                                specOtherList.add(
                                    tempPriceDetail(
                                        price = data.other[i],
                                        rule = tradeOtherList[i],
                                    )
                                )
                            }
                        }
                        var sumOther = 0

                        data.other.forEach {
                            sumOther += it
                        }
                        if (specOtherList.none { it.rule == "sum" }) {
                            specOtherList.add(
                                tempPriceDetail(
                                    price = sumOther,
                                    rule = "sum"
                                )
                            )
                        }

                        if (specModifyList.size > 0) {
                            sum -= specModifyList[specModifyList.size - 1].price
                        }

                        if (specOtherList.size > 0) {
                            sum += specOtherList[specOtherList.size - 1].price
                        }
                        txtSpecPrice.text = sum.toString()

                        val layoutManagerSpecOther = LinearLayoutManager(page)
                        layoutManagerSpecOther.orientation = LinearLayoutManager.VERTICAL
                        rvAddPoolSpecOther.layoutManager = layoutManagerSpecOther
                        rvAddPoolSpecOther.adapter =
                            PoolTradeEditSpecOtherAdapter(specOtherList, page)

                        setSpinner(
                            spinnerModify,
                            ddlModifyList,
                            "traderule",
                            0
                        )

                        ddlOtherList.clear()
                        var sortOtherList = mutableListOf<String>()
                        sortOtherList.clear()

                        dbTradeRuleList.sortedBy { it.id }.filter {
                            (it.type == "Other" || it.type == "Fluctuate") && it.channelDetail.contains(
                                ddlChannelDetailList[ddlPositionChannelDetail].id
                            ) && (it.transType == ddlTransType[ddlPositionTransType].id || it.transType == "Fluctuate")
                        }.forEach {
                            ddlOtherList.add(
                                DdlNormalModel(
                                    it.name,
                                    it.imgUrl,
                                    it.id
                                )
                            )
                            if (it.default) {
                                if (!sortOtherList.contains(it.id))            //排除重複點選
                                {
                                    sortOtherList.add(it.id)
                                }
                            }
                        }

                        tradeOtherList = sortOtherList

                        spinnerOther.onItemSelectedListener =
                            object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    parent: AdapterView<*>,
                                    view: View?,
                                    position: Int,
                                    id: Long
                                ) {
                                    ddlPositionOther = position
                                }

                                override fun onNothingSelected(parent: AdapterView<*>) {}
                            }
                        setSpinner(
                            spinnerOther,
                            ddlOtherList,
                            "traderule",
                            0
                        )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

        }

        vmTradeViewModel.SelectedSpecModify.observe(this) {
            val layoutManagerSpec = LinearLayoutManager(this)
            layoutManagerSpec.orientation = LinearLayoutManager.VERTICAL
            rvAddPoolSpecModify.layoutManager = layoutManagerSpec
            rvAddPoolSpecModify.adapter = PoolTradeEditSpecModifyAdapter(specModifyList, page)

            var sum = txtSpecTagPrice.text.toString().toInt()
            if (specModifyList.size > 0) {
                sum -= specModifyList[specModifyList.size - 1].price
            }
            if (specOtherList.size > 0) {
                sum += specOtherList[specOtherList.size - 1].price
            }
            txtSpecPrice.text = sum.toString()

            val lastId = tempSpecList.indexOfFirst { it.id == nowEditId }
            if (lastId != -1) {

                val modify = arrayListOf<Int>()
                val other = arrayListOf<Int>()

                specModifyList.filter { it.rule != "sum" }.forEach {
                    modify.add(it.price)
                }

                specOtherList.filter { it.rule != "sum" }.forEach {
                    other.add(it.price)
                }

                tempSpecList[lastId].price = sum
                tempSpecList[lastId].modify = modify
                tempSpecList[lastId].other = other
            }


            setSpec()
        }

        vmTradeViewModel.SelectedSpecOther.observe(this) {
            val layoutManagerSpec = LinearLayoutManager(this)
            layoutManagerSpec.orientation = LinearLayoutManager.VERTICAL
            rvAddPoolSpecOther.layoutManager = layoutManagerSpec
            rvAddPoolSpecOther.adapter = PoolTradeEditSpecOtherAdapter(specOtherList, page)

            var sum = txtSpecTagPrice.text.toString().toInt()
            if (specModifyList.size > 0) {
                sum -= specModifyList[specModifyList.size - 1].price
            }
            if (specOtherList.size > 0) {
                sum += specOtherList[specOtherList.size - 1].price
            }
            txtSpecPrice.text = sum.toString()


            val lastId = tempSpecList.indexOfFirst { it.id == nowEditId }
            if (lastId != -1) {

                val modify = arrayListOf<Int>()
                val other = arrayListOf<Int>()

                specModifyList.filter { it.rule != "sum" }.forEach {
                    modify.add(it.price)
                }

                specOtherList.filter { it.rule != "sum" }.forEach {
                    other.add(it.price)
                }

                tempSpecList[lastId].price = sum
                tempSpecList[lastId].modify = modify
                tempSpecList[lastId].other = other
            }

            setSpec()
        }

        if (selectedTrade.isNotEmpty() && selectedTrade != "null") {
            setEditPageData(selectedTrade)
        }
        btnSubmit.setOnClickListener {
            try {
                btnSubmit.startLoading()

                if (editProcessDate.text.toString() == "") {
                    Toast.makeText(applicationContext, "交易日不得為空！！", Toast.LENGTH_SHORT)
                        .show()
                    btnSubmit.loadingFailed()
                    return@setOnClickListener
                }

                var tradeId = TimeFormat().TodayDate().yyyyMMddHHmmss()

                if (selectedTrade.isNotEmpty() && selectedTrade != "null") {
                    tradeId = selectedTrade
                }

                var speList = mutableListOf<String>()

                tempSpecList.filter { it.id != "addOne" }.forEach { temp ->
                    speList.add(temp.id)

                    val tradeDetailData = TradeDetailEn(
                        accountDate = temp.accountDate,
                        id = temp.id,
                        modify = temp.modify,
                        other = temp.other,
                        price = temp.price,
                        processDate = editProcessDate.text.toString(),
                        specification = temp.specification,
                        stockDate = temp.stockDate,
                        tradeId = tradeId,
                    )
                    vmTradeDetailViewModel.upsertOne(tradeDetailData)

                    if (temp.stockDate >= TimeFormat().TodayDate().yyyyMMdd()) {
                        vmStockDepositoryViewModel.getDatas("")
                        vmStockDepositoryViewModel.stockDepositoryDatas.observe(this) {


                            val spId = mutableListOf<String>()
                            val cost = arrayListOf<Int>()
                            tempSpecList.filter { it.id != "addOne" && it.specification == temp.specification }
                                .forEach {
                                    spId.add(it.id)
                                    cost.add(it.price)
                                }


                            val dbSt =
                                dbStockDepositoryList.filter { db -> db.id == temp.specification }
                                    .toMutableList()

                            if (dbSt.size > 0) {

                                val list = dbSt[0].tradeDetailId.toMutableList()
                                val listCost = arrayListOf<Int>()
                                val listDbCost = dbSt[0].holdingCost.toMutableList()

                                listDbCost.forEach {
                                    listCost.add(it)
                                }


                                for (i in 0 until spId.size) {
                                    for (j in 0 until list.size) {
                                        if (spId[i] == list[j]) {
                                            listCost[j] = cost[i]
                                        }
                                    }
                                }
                                for (i in 0 until spId.size) {
                                    if (!list.contains(spId[i])) {
                                        list.add(spId[i])
                                        listCost.add(cost[i])
                                    }
                                }

                                val stockDepositoryData = StockDepositoryEn(
                                    favorite = dbSt[0].favorite,
                                    group = dbSt[0].group,
                                    holdingCost = listCost,
                                    id = dbSt[0].id,
                                    imgUrl = dbSt[0].specification,
                                    member = dbSt[0].member,
                                    message = dbSt[0].message,
                                    print = dbSt[0].print,
                                    specification = dbSt[0].specification,
                                    tradeDetailId = list.toTypedArray(),
                                    profit = dbSt[0].profit,
                                    sign = dbSt[0].sign,
                                    valuation = dbSt[0].valuation,
                                )

                                vmStockDepositoryViewModel.upsertOne(stockDepositoryData)

                            } else {
                                val dbSp =
                                    dbSpecificationList.first { db -> db.id == temp.specification }
                                val dbProd = dbProductList.first { db -> db.id == dbSp.product }

                                val stockDepositoryData = StockDepositoryEn(
                                    favorite = false,
                                    group = dbProd.group,
                                    holdingCost = cost,
                                    id = temp.specification,
                                    imgUrl = temp.specification,
                                    member = dbSp.member,
                                    message = arrayOf(),
                                    print = false,
                                    specification = temp.specification,
                                    tradeDetailId = spId.toTypedArray(),
                                    profit = 0,
                                    sign = arrayOf(),
                                    valuation = 0,
                                )
                                vmStockDepositoryViewModel.upsertOne(stockDepositoryData)

                            }
                        }
                    }
                }


                val tradeData = TradeEn(
                    channelDetail = ddlChannelDetailList[ddlPositionChannelDetail].id,
                    date = editProcessDate.text.toString(),
                    id = tradeId,
                    modifyRule = tradeModifyList.toTypedArray(),
                    otherRule = tradeOtherList.toTypedArray(),
                    tradeDetail = speList.toTypedArray(),
                    transType = ddlTransType[ddlPositionTransType].id,
                    ym = editProcessDate.text.toString().substring(0, 6),
                )
                vmTradeViewModel.upsertOne(tradeData)

                btnSubmit.loadingSuccessful()

                btnSubmit.animationEndAction = {
                    btnSubmit.reset()
                }

            } catch (ex: Exception) {
                Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
                btnSubmit.loadingFailed()
            }
        }
    }


    private val calender: Calendar = getInstance()

    private val listener = View.OnClickListener {
        when (it) {
            editProcessDate -> {
                nowEditText = editProcessDate
                datePicker()
            }
            editStockDate -> {
                nowEditText = editStockDate
                datePicker()
            }
            editAccountDate -> {
                nowEditText = editAccountDate
                datePicker()
            }
        }
    }

    private fun datePicker() {
        DatePickerDialog(
            this,
            dateListener,
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private var selectedDate = ""
    private val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        calender.set(year, month, day)
        val time = SimpleDateFormat("yyyyMMdd", Locale.TAIWAN)
        selectedDate = time.format(calender.time)
        nowEditText.setText(selectedDate)
    }

    private fun setSpinner(
        spinner: Spinner,
        list: ArrayList<DdlNormalModel>,
        module: String,
        init: Int
    ) {

        val customDropDownAdapter =
            CustomDropDownAdapter(
                module,
                page,
                list
            )
        spinner.adapter = customDropDownAdapter
        // spinner.setSelection(init)
    }

    private fun setSpec() {

        if (tempSpecList.findLast { it.id == "addOne" } == null) {
            tempSpecList.add(
                TradeDetail(
                    accountDate = "",
                    id = "addOne",
                    modify = arrayListOf(),
                    other = arrayListOf(),
                    price = 0,
                    processDate = "",
                    specification = "",
                    stockDate = "",
                    tradeId = "",

                    )
            )
        }

        var sumPrice = 0
        var sumModify = 0
        var sumOther = 0

        tempSpecList.filter { it.id != "addOne" }.forEach {
            sumPrice += it.price
            it.modify.forEach {
                sumModify += it
            }
            it.other.forEach {
                sumOther += it
            }
        }

        txtTotalSpecPrice.text = sumPrice.toString()
        txtTotalSpecModify.text = sumModify.toString()
        txtTotalSpecOther.text = sumOther.toString()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvSpecification.layoutManager = layoutManager
        rvSpecification.adapter = TradeDetailEditSpecificationAdapter(tempSpecList, page)
    }

    private fun setEditPageData(selected: String) {

        val tradeInfo = dbTradeList.filter { it.id == selected }.toMutableList()[0]
        val tradeDetailInfoList =
            dbTradeDetailList.filter { it.tradeId == selected }.toMutableList()
        editProcessDate.setText(tradeInfo.date)
        editAccountDate.setText(tradeDetailInfoList[0].accountDate)
        editStockDate.setText(tradeDetailInfoList[0].stockDate)

        spinnerTransType.setSelection(ddlTransType.indexOfFirst { it.id == tradeInfo.transType })
        tempSpecList.clear()
        tempSpecList = tradeDetailInfoList

        val vmTradeViewModel =
            ViewModelProvider(this)[VmTradeViewModel::class.java]

        nowEditId = tempSpecList[0].id

        if (tradeInfo.modifyRule.size > 1 || tradeInfo.modifyRule[0] != "") {
            tradeModifyList = tradeInfo.modifyRule.toMutableList()
        }
        if (tradeInfo.otherRule.size > 1 || tradeInfo.otherRule[0] != "") {
            tradeOtherList = tradeInfo.otherRule.toMutableList()
        }


        vmTradeViewModel.setSelectedSpecDatas(tempSpecList[0])

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val numberOfColumns = 3
        rvAddPoolModify.layoutManager =
            GridLayoutManager(this, numberOfColumns)
        rvAddPoolModify.adapter = PoolTradeEditModifyAdapter(tradeModifyList, page)

        specModifyList.clear()

        for (i in 0 until tradeModifyList.size) {
            specModifyList.add(
                tempPriceDetail(
                    price = tradeDetailInfoList[0].modify[i],
                    rule = tradeModifyList[i]
                )
            )
        }
        if (specModifyList.none { it.rule == "sum" }) {
            specModifyList.add(
                tempPriceDetail(
                    price = 0,
                    rule = "sum"
                )
            )
        }

        val layoutManagerSpec = LinearLayoutManager(this)
        layoutManagerSpec.orientation = LinearLayoutManager.VERTICAL
        rvAddPoolSpecModify.layoutManager = layoutManagerSpec
        rvAddPoolSpecModify.adapter = PoolTradeEditSpecModifyAdapter(specModifyList, page)

        specOtherList.clear()

        for (i in 0 until tradeOtherList.size) {
            specOtherList.add(
                tempPriceDetail(
                    price = tradeDetailInfoList[0].other[i],
                    rule = tradeOtherList[i]
                )
            )
        }
        if (specOtherList.none { it.rule == "sum" }) {
            specOtherList.add(
                tempPriceDetail(
                    price = 0,
                    rule = "sum"
                )
            )
        }

        val layoutOtherManagerSpec = LinearLayoutManager(this)
        layoutOtherManagerSpec.orientation = LinearLayoutManager.VERTICAL
        rvAddPoolSpecOther.layoutManager = layoutOtherManagerSpec
        rvAddPoolSpecOther.adapter = PoolTradeEditSpecOtherAdapter(specOtherList, page)

        setSpec()
    }

}