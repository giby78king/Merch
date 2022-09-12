package com.giby78king.merch.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.giby78king.merch.Adapter.*
import com.giby78king.merch.Model.Activity.Companion.dbActivityList
import com.giby78king.merch.Model.Activity.Companion.ddlActivityList
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.ddlChannelDetailList
import com.giby78king.merch.Model.DdlNormalModel
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
import com.giby78king.merch.Model.Trade.Companion.ddlModifyList
import com.giby78king.merch.Model.Trade.Companion.ddlOtherList
import com.giby78king.merch.Model.Trade.Companion.ddlTransType
import com.giby78king.merch.Model.Trade.Companion.tradeModifyList
import com.giby78king.merch.Model.Trade.Companion.tradeOtherList
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.Model.TradeDetail.Companion.specModifyList
import com.giby78king.merch.Model.TradeDetail.Companion.specOtherList
import com.giby78king.merch.Model.TradeDetail.Companion.tempSpecList
import com.giby78king.merch.Model.TradeRule.Companion.dbTradeRuleList
import com.giby78king.merch.Model.tempPriceDetail
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.*
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.*
import kotlinx.android.synthetic.main.activity_trade_edit_page.*
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
        var selectedProduct = bundle?.getString("selectedProduct").toString()
        page = this

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

        val customDropDownAdapter =
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
        val vmTradeRuleViewModel =
            ViewModelProvider(this)[VmTradeRuleViewModel::class.java]
        val vmProductViewModel =
            ViewModelProvider(this)[VmProductViewModel::class.java]
        val vmSpecificationViewModel =
            ViewModelProvider(this)[VmSpecificationViewModel::class.java]

        vmActivitylViewModel.getDatas("")
        vmActivitylViewModel.activityDatas.observe(this) {
            vmChannelDetailViewModel.getDatas("")
            vmChannelDetailViewModel.channelDetailDatas.observe(this) {
                vmProductViewModel.getDatas("")
                vmProductViewModel.productDatas.observe(this) {
                    vmSpecificationViewModel.getDatas("")
                    vmSpecificationViewModel.specificationDatas.observe(this) {
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

//                                                                        if (specModifyList.size > 0) {
//                                                                            var sum = dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0].price[priceIndex].toString().toInt()
//                                                                            if (specModifyList.size > 0) {
//                                                                                sum -= specModifyList[specModifyList.size - 1].price
//                                                                            }
//                                                                            if (specOtherList.size > 0) {
//                                                                                sum += specOtherList[specOtherList.size - 1].price
//                                                                            }
//                                                                            txtSpecPrice.text = sum.toString()
//                                                                        } else {
//                                                                            txtSpecPrice.text =
//                                                                                dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0].price[priceIndex].toString()
//                                                                        }
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

                        val customDropDownAdapter =
                            CustomDropDownAdapter(
                                "channeldetail",
                                this,
                                ddlChannelDetailList
                            )
                        spinnerChannelDetail.adapter = customDropDownAdapter

                        vmTradeRuleViewModel.getDatas("")
                        vmTradeRuleViewModel.tradeRuleDatas.observe(this) {

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
                        }
                    }
                }
            }
        }

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
            specModifyList.add(
                tempPriceDetail(
                    price = 0,
                    rule = "sum"
                )
            )
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
            specOtherList.add(
                tempPriceDetail(
                    price = 0,
                    rule = "sum"
                )
            )
            val layoutManagerSpec = LinearLayoutManager(this)
            layoutManagerSpec.orientation = LinearLayoutManager.VERTICAL
            rvAddPoolSpecOther.layoutManager = layoutManagerSpec
            rvAddPoolSpecOther.adapter = PoolTradeEditSpecOtherAdapter(specOtherList, page)
        }

        editProcessDate.setOnClickListener(listener)
        editAccountDate.setOnClickListener(listener)
        editStockDate.setOnClickListener(listener)


        vmTradeViewModel.ProcessTradeDetailDatas.observe(this) {
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
            spinnerChannelDetail.setSelection(ddlChannelDetailList.indexOfFirst { it.id == data.channelDetail })

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

                                                            if (data.other.size > 0 || data.modify.size > 0) {
                                                                var sum = dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0].price[priceIndex].toString().toInt()

                                                                val specModifyList = mutableListOf<tempPriceDetail>()
                                                                for (i in data.modify.indices) {
                                                                    specModifyList.add(
                                                                        tempPriceDetail(
                                                                            price = data.modify[i],
                                                                            rule = data.modifyRule[i],
                                                                        )
                                                                    )
                                                                }

                                                                val specOtherList = mutableListOf<tempPriceDetail>()
                                                                for (i in data.other.indices) {
                                                                    specOtherList.add(
                                                                        tempPriceDetail(
                                                                            price = data.other[i],
                                                                            rule = data.otherRule[i],
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
                                                            } else {
                                                                txtSpecPrice.text =
                                                                    dbSpecificationList.filter { it.id == ddlSpecificationList[ddlPositionSpecification].id }[0].price[priceIndex].toString()
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

                        var sum = txtSpecTagPrice.text.toString().toInt()

                        val specModifyList = mutableListOf<tempPriceDetail>()
                        for (i in data.modify.indices) {
                            specModifyList.add(
                                tempPriceDetail(
                                    price = data.modify[i],
                                    rule = data.modifyRule[i],
                                )
                            )
                        }

                        val layoutManagerSpec = LinearLayoutManager(page)
                        layoutManagerSpec.orientation = LinearLayoutManager.VERTICAL
                        rvAddPoolSpecModify.layoutManager = layoutManagerSpec

                        rvAddPoolSpecModify.adapter =
                            PoolTradeEditSpecModifyAdapter(specModifyList, page)

                        val specOtherList = mutableListOf<tempPriceDetail>()
                        for (i in data.other.indices) {
                            specOtherList.add(
                                tempPriceDetail(
                                    price = data.other[i],
                                    rule = data.otherRule[i],
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
        }

//
//        btnSubmit.setOnClickListener {
//            try {
//                btnSubmit.startLoading()
//
//                if (productChannelDetailList.size == 0) {
//                    Toast.makeText(applicationContext, "發行日不得為空！！", Toast.LENGTH_SHORT)
//                        .show()
//                    return@setOnClickListener
//                }
//
//                var formatId = editPublish.text.toString() + "_" + editId.text.toString()
//
//                if (txtId.text.isNotEmpty()) {
//                    formatId = txtId.text.toString()
//                }
//                var speList = mutableListOf<String>()
//
//                tempSpecificationList.forEach {
//                    var specificationType = "Group"
//                    var formatTitle = it.title
//
//                    if (it.member.isNotEmpty() && it.member[0].isNotEmpty()) {
//                        specificationType = "Member"
//                        if(formatTitle.isEmpty()){
//                            formatTitle = ""
//                            it.member.forEach { mem -> formatTitle += ",$mem" }
//                            formatTitle = formatTitle.substring(1)
//                        }
//                    }
//
//                    it.specificationType = specificationType
//                    it.title = formatTitle
//
//                }
//
//                var groupList =
//                    tempSpecificationList.filter { it.specificationType == "Group" }.toMutableList()
//
//                var sortedGroupList = mutableListOf<Specification>()
//                tempSpecificationList.sortedByDescending { it.price[0] }.forEach { temp ->
//                    groupList.forEach {
//                        if (it.price.isNotEmpty()) {
//                            if(it.id == temp.id) {
//                                sortedGroupList.add(it)
//                            }
//                        }
//                    }
//                }
//
//                val memberList =
//                    tempSpecificationList.filter { it.specificationType == "Member" && it.member.size == 1 }
//                        .toMutableList()
//                val memberMultiList =
//                    tempSpecificationList.filter { it.specificationType == "Member" && it.member.size > 1 }
//                        .toMutableList()
//
//                var sortedMemberList = mutableListOf<Specification>()
//                var sortedMultiMemberList = mutableListOf<Specification>()
//
//                dbMemberList.sortedBy { it.group }.sortedBy { it.number }.forEach { db ->
//                    memberList.forEach {
//                        if (it.member.isNotEmpty()) {
//                            if (it.member[0] == db.id) {
//                                sortedMemberList.add(it)
//                            }
//                        }
//                    }
//
//                    memberMultiList.forEach {
//                        if (it.member.isNotEmpty()) {
//                            if (it.member[0] == db.id) {
//                                sortedMultiMemberList.add(it)
//                            }
//                        }
//                    }
//                }
//
//
//
//                tempSpecificationList =
//                    (sortedGroupList + sortedMemberList + sortedMultiMemberList).toMutableList()
//
//                tempSpecificationList.forEach {
//                    speList.add(it.id)
//
//                    val specificationData = SpecificationEn(
//                        id = it.id,
//                        imgUrl = it.id.toLowerCase().replace(" ", ""),
//                        limit = it.limit.toTypedArray(),
//                        member = it.member,
//                        order = 0,
//                        price = it.price.toTypedArray(),
//                        product = formatId,
//                        specificationType = it.specificationType,
//                        title = it.title,
//                    )
//                    vmSpecificationViewModel.upsertOne(specificationData)
//                }
//
//                if (tempDeleteSpecificationList.size > 0) {
//                    tempDeleteSpecificationList.forEach {
//                        vmSpecificationViewModel.deleteOne(it)
//                    }
//                }
//
//                val productData = ProductEn(
//                    activity = ddlActivityList[ddlPositionActivity].id,
//                    channelDetail = productChannelDetailList.toTypedArray(),
//                    group = productGroupList.toTypedArray(),
//                    id = formatId,
//                    imgUrl = formatId.toLowerCase().replace(" ", ""),
//                    name = editName.text.toString(),
//                    onSell = switchOnSell.isChecked,
//                    preOrder = switchPreOrder.isChecked,
//                    productType = ddlProductTypeList[ddlPostitionProductType].id,
//                    publish = editPublish.text.toString(),
//                    specification = speList.toTypedArray(),
//                )
//
//                vmProductViewModel.upsertOne(productData)
//
//                btnSubmit.loadingSuccessful()
//
//                btnSubmit.animationEndAction = {
//                    btnSubmit.reset()
//                    vmProductViewModel.getDatas("")
//                }
//
//            } catch (ex: Exception) {
//                Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
//                btnSubmit.loadingFailed()
//            }
//        }
//        vmProductViewModel.productDatas.observe(this) {
//            vmSpecificationViewModel.getDatas("")
//            vmSpecificationViewModel.specificationDatas.observe(this) {
//                setEditPageData(selectedProduct)
//            }
//        }
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
//        tempSpecList.clear()

        if (tempSpecList.findLast { it.id == "addOne" } == null) {
            tempSpecList.add(
                TradeDetail(
                    accountDate = "",
                    channelDetail = "",
                    id = "addOne",
                    modify = arrayListOf(),
                    modifyRule = arrayOf(),
                    other = arrayListOf(),
                    otherRule = arrayOf(),
                    price = 0,
                    processDate = "",
                    specification = "",
                    stockDate = "",
                    tradeId = "",
                    transType = "",
                )
            )
        }
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvSpecification.layoutManager = layoutManager
        rvSpecification.adapter = TradeDetailEditSpecificationAdapter(tempSpecList, page)
    }

}