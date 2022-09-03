package com.giby78king.merch.ui

import android.app.DatePickerDialog
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.giby78king.merch.Adapter.CustomDropDownAdapter
import com.giby78king.merch.Adapter.PoolProductEditChannelDetailAdapter
import com.giby78king.merch.Adapter.PoolProductEditGroupAdapter
import com.giby78king.merch.Adapter.ProductSaveAdapter
import com.giby78king.merch.Domain.ProductEn
import com.giby78king.merch.Domain.SpecificationEn
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Activity.Companion.dbActivityList
import com.giby78king.merch.Model.Activity.Companion.ddlActivityList
import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.ddlChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.productChannelDetailList
import com.giby78king.merch.Model.DdlNormalModel
import com.giby78king.merch.Model.EditAmountSetting
import com.giby78king.merch.Model.Group.Companion.dbGroupList
import com.giby78king.merch.Model.Group.Companion.ddlGroupList
import com.giby78king.merch.Model.Group.Companion.productGroupList
import com.giby78king.merch.Model.Product.Companion.dbProductList
import com.giby78king.merch.Model.ProductType.Companion.ddlProductTypeList
import com.giby78king.merch.Model.Specification
import com.giby78king.merch.Model.Specification.Companion.dbSpecificationList
import com.giby78king.merch.Model.Specification.Companion.tempSpecificationList
import com.giby78king.merch.R
import com.giby78king.merch.TimeFormat
import com.giby78king.merch.ViewModel.*
import kotlinx.android.synthetic.main.activity_product_edit_page.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.getInstance
import kotlin.math.abs


class ProductEditPage : AppCompatActivity() {

    private lateinit var nowEdit: EditText
    var ddlPositionChannelDetail = 0
    var init = true
    lateinit var page: ProductEditPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_edit_page)

        val bundle = intent.extras
        val selectedProduct = bundle?.getString("selectedProduct").toString()
        page = this

        val vmActivitylViewModel =
            ViewModelProvider(this)[VmActivitylViewModel::class.java]
        val vmGroupViewModel =
            ViewModelProvider(this)[VmGroupViewModel::class.java]
        val vmChannelDetailViewModel =
            ViewModelProvider(this)[VmChannelDetailViewModel::class.java]
        val vmProductViewModel =
            ViewModelProvider(this)[VmProductViewModel::class.java]

        var ddlPositionActivity = 0
        var ddlPositionGroup = 0
        var ddlPostitionProductType = 0

        ddlProductTypeList.clear()
        ddlProductTypeList.add(DdlNormalModel("應援毛巾", "", "Cheer Towels"))
        ddlProductTypeList.add(DdlNormalModel("紀念球", "", "Ball"))
        ddlProductTypeList.add(DdlNormalModel("服飾", "", "Clothes"))
        ddlProductTypeList.add(DdlNormalModel("用品", "", "Supplies"))
        val customDropDownAdapter =
            CustomDropDownAdapter("producttype", this, ddlProductTypeList)
        spinnerProductType.adapter = customDropDownAdapter
        spinnerProductType.setSelection(ddlPostitionProductType)
        spinnerProductType.setSelection(0)

        vmActivitylViewModel.getDatas("")
        vmActivitylViewModel.activityDatas.observe(this) {
            ddlActivityList.clear()
            ddlActivityList.add(DdlNormalModel("請選擇", "", ""))

            dbActivityList.forEach {
                ddlActivityList.add(
                    DdlNormalModel(
                        it.name,
                        it.imgUrl,
                        it.id
                    )
                )
            }
            vmChannelDetailViewModel.getDatas("")
            vmChannelDetailViewModel.channelDetailDatas.observe(this) {
                spinnerActivity.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            ddlPositionActivity = position

                            if (!init) {
                                productChannelDetailList.clear()

                                rvAddPoolChannelDetail.adapter =
                                    PoolProductEditChannelDetailAdapter(
                                        productChannelDetailList,
                                        page
                                    )
                            }
                            init = false

                            ddlChannelDetailList.clear()
                            ddlChannelDetailList.add(
                                DdlNormalModel(
                                    "請選擇",
                                    "",
                                    ""
                                )
                            )

                            if (ddlPositionActivity != 0) {
                                var nowActivityList =
                                    dbActivityList.filter { it.id == ddlActivityList[ddlPositionActivity].id }
                                        .toMutableList()[0]


                                var dbList = mutableListOf<ChannelDetail>()

                                nowActivityList.channelDetail.forEach { now ->
                                    dbChannelDetailList.forEach { db ->
                                        if (db.id == now) {
                                            dbList.add(db)
                                        }
                                    }
                                }

                                dbList.forEach {
                                    ddlChannelDetailList.add(
                                        DdlNormalModel(
                                            it.name,
                                            it.imgUrl,
                                            it.id
                                        )
                                    )
                                }
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
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>) {}
                                }

                            val customDropDownAdapter =
                                CustomDropDownAdapter(
                                    "channeldetail",
                                    parent.context,
                                    ddlChannelDetailList
                                )
                            spinnerChannelDetail.adapter = customDropDownAdapter
                            spinnerChannelDetail.setSelection(0)

                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {}
                    }

                val customDropDownAdapter =
                    CustomDropDownAdapter("activity", this, ddlActivityList)
                spinnerActivity.adapter = customDropDownAdapter
                spinnerActivity.setSelection(ddlPositionActivity)

                if (selectedProduct.isNotEmpty() && selectedProduct != "null") {
                    setEditPageData(selectedProduct)
                }

                btnAddChannelDetail.setOnClickListener {
                    if (ddlPositionChannelDetail != 0) {
                        if (!productChannelDetailList.contains(ddlChannelDetailList[ddlPositionChannelDetail].id))            //排除重複點選
                        {
                            productChannelDetailList.add(ddlChannelDetailList[ddlPositionChannelDetail].id)
                        }

                        var sortList = mutableListOf<String>()
                        dbChannelDetailList.sortedBy { it.channel }.sortedBy { it.id }
                            .toMutableList().forEach { chd ->
                                productChannelDetailList.forEach { act ->
                                    if (act == chd.id) {
                                        sortList.add(act)
                                    }
                                }
                            }
                        productChannelDetailList = sortList

                        val padding =
                            productChannelDetailList.size - tempSpecificationList[0].limit.size

                        if (padding > 0) {
                            for (i in 1..padding) {
                                tempSpecificationList.forEach { sp ->
                                    sp.price.add(0)
                                    sp.limit.add(0)
                                }
                            }
                        }
                        if (padding < 0) {
                            for (i in 1..abs(padding)) {
                                tempSpecificationList.forEach { sp ->
                                    sp.price.removeLast()
                                    sp.limit.removeLast()
                                }
                            }
                        }

                        if (tempSpecificationList[0].limit.size != 0) {
                            vmChannelDetailViewModel.setSelectedChannelDetail()
                        }

                    }
                }

                vmChannelDetailViewModel.SelectedChannelDetailDatas.observe(this) {
                    val layoutManager = LinearLayoutManager(this)
                    layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                    val numberOfColumns = 3
                    rvAddPoolChannelDetail.layoutManager =
                        GridLayoutManager(this, numberOfColumns)
                    rvAddPoolChannelDetail.adapter =
                        PoolProductEditChannelDetailAdapter(productChannelDetailList, page)
                    setProductDetailRecyclerView(tempSpecificationList)
                }
            }
        }

        vmGroupViewModel.getDatas("")
        vmGroupViewModel.groupDatas.observe(this) {
            ddlGroupList.clear()
            ddlGroupList.add(DdlNormalModel("請選擇", "", ""))
            dbGroupList.forEach {
                ddlGroupList.add(
                    DdlNormalModel(
                        it.name,
                        it.imgUrl,
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
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

            val customDropDownAdapter =
                CustomDropDownAdapter(
                    "group",
                    this,
                    ddlGroupList
                )
            spinnerGroup.adapter = customDropDownAdapter
            spinnerGroup.setSelection(0)
        }

        btnAddGroup.setOnClickListener {
            if (ddlPositionGroup != 0) {
                if (!productGroupList.contains(ddlGroupList[ddlPositionGroup].id))            //排除重複點選
                {
                    productGroupList.add(ddlGroupList[ddlPositionGroup].id)
                }

                var sortList = mutableListOf<String>()
                dbGroupList.sortedBy { it.id }.toMutableList().forEach { chd ->
                    productGroupList.forEach { act ->
                        if (act == chd.id) {
                            sortList.add(act)
                        }
                    }
                }
                productGroupList = sortList

                vmGroupViewModel.setSelectedGroup()
            }
        }
        vmGroupViewModel.SelectedGroupDatas.observe(this) {
            val layoutManager = LinearLayoutManager(this)
            layoutManager.orientation = LinearLayoutManager.HORIZONTAL
            val numberOfColumns = 3
            rvAddPoolGroup.layoutManager =
                GridLayoutManager(this, numberOfColumns)
            rvAddPoolGroup.adapter = PoolProductEditGroupAdapter(productGroupList)
        }

        editPublish.setOnClickListener(listener)

        btnAddProduct.setOnClickListener {
            if (btnAddProduct.text == "NEW PRODUCT") {
                btnAddProduct.text = "INSERT ID"
                linearDetail.isVisible = false
                linearEditID.isVisible = true
                linearTxtId.isVisible = false
                txtId.text = ""
                editId.setText("")
                txtPublish.text = ""
                editPublish.setText("")

                val res: Resources = this.resources
                //Img相關
                var img = "ic_member_save_new"
                val resourceId: Int = res.getIdentifier(
                    img, "drawable",
                    "com.giby78king.merch"
                )
                imgDetailIcon.setImageResource(resourceId)
            } else {
                if (editPublish.text.isEmpty()) {
                    Toast.makeText(applicationContext, "發行日不得為空！！", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                if (editId.text.isEmpty()) {
                    Toast.makeText(applicationContext, "暱稱/識別不得為空！！", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }


                var count = 0
                if (tempSpecificationList.isNotEmpty()) {
                    tempSpecificationList.forEach {
                        val uuidTimestamp = TimeFormat().TodayDate().yyyyMMddHHmmssSSS(count)
                        it.id =
                            editPublish.text.toString() + "_" + editId.text.toString() + uuidTimestamp
                        count++
                    }
                }

                btnAddProduct.text = "NEW PRODUCT"
                linearEditID.isVisible = false
                linearTxtId.isVisible = true
                linearDetail.isVisible = true
            }
        }

        btnAddSpecification.setOnClickListener {

            val uuidTimestamp = TimeFormat().TodayDate().yyyyMMddHHmmssSSS()
            val formatId = editId.text.toString() + uuidTimestamp

            var arrLimit = arrayListOf<Int>()
            productChannelDetailList.indices.forEach { arrLimit.add(0) }

            var arrPrice = arrayListOf<Int>()
            productChannelDetailList.indices.forEach { arrPrice.add(0) }

            tempSpecificationList.add(
                0,
                Specification(
                    id = formatId,
                    imgUrl = "",
                    limit = arrLimit,
                    member = arrayOf(),
                    order = 1,
                    price = arrPrice,
                    product = "",
                    specificationType = "",
                    title = "",
                )
            )

            setProductDetailRecyclerView(tempSpecificationList)
        }

        val vmProductSaveViewModel =
            ViewModelProvider(this)[VmProductSaveViewModel::class.java]

        vmProductSaveViewModel.specificationSelectInfo.observe(this) {
            tempSpecificationList.removeAt(it)
            setProductDetailRecyclerView(tempSpecificationList)
        }

        val vmSpecificationViewModel =
            ViewModelProvider(this)[VmSpecificationViewModel::class.java]

        vmSpecificationViewModel.SelectedMemberDatas.observe(page) {
            setProductDetailRecyclerView(tempSpecificationList)
        }

        txtFixAll.setOnClickListener {
            if (productChannelDetailList.size < 1) {
                return@setOnClickListener
            }

            val inflater =
                LayoutInflater.from(this).inflate(R.layout.fragment_dialog_product_edit_all, null)

            val dialog = AlertDialog.Builder(this)
                .setView(inflater)
                .show()

            val lp = WindowManager.LayoutParams()
            val window: Window = dialog.window!!
            lp.copyFrom(window.attributes)
            lp.width = 800
            lp.height = 710
            window.attributes = lp

            val btnEditAll = inflater.findViewById<Button>(R.id.btnEditAll)
            val editPrice = inflater.findViewById<EditText>(R.id.editPrice)
            val editLimit = inflater.findViewById<EditText>(R.id.editLimit)

            editPrice.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun afterTextChanged(editable: Editable) {
                    EditAmountSetting().editNoDollarRule(editPrice, this)
                }
            })

            editLimit.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun afterTextChanged(editable: Editable) {
                    EditAmountSetting().editNoDollarRule(editLimit, this)
                }
            })

            if (tempSpecificationList.size > 0 && tempSpecificationList[0].price.size > 0) {
                editPrice.setText(tempSpecificationList[tempSpecificationList.size - 1].price[productChannelDetailList.size - 1].toString())
            }
            if (tempSpecificationList.size > 0 && tempSpecificationList[0].limit.size > 0) {
                editLimit.setText(tempSpecificationList[tempSpecificationList.size - 1].limit[productChannelDetailList.size - 1].toString())
            }

            btnEditAll.setOnClickListener {
                tempSpecificationList.forEach {
                    for (i in it.price.indices) {
                        it.price[i] = editPrice.text.toString().replace(",", "").toInt()
                        it.limit[i] = editLimit.text.toString().replace(",", "").toInt()
                    }
                }
                setProductDetailRecyclerView(tempSpecificationList)
            }
        }

        btnSubmit.setOnClickListener {
            try {
                btnSubmit.startLoading()

                var formatId = editPublish.text.toString() + "_" + editId.text.toString()

                if (txtId.text.isNotEmpty()) {
                    formatId = txtId.text.toString()
                }
                var speList = mutableListOf<String>()
                tempSpecificationList.forEach {
                    speList.add(it.id)

                    var specificationType = "Group"
                    var formatTitle = it.title

                    if (it.member.isNotEmpty()) {
                        specificationType = "Member"
                        formatTitle = ""
                        it.member.forEach { mem -> formatTitle += ",$mem" }
                        formatTitle.substring(1, formatTitle.length - 1)
                    }

                    val specificationData = SpecificationEn(
                        id = it.id,
                        imgUrl = it.id.toLowerCase().replace(" ", ""),
                        limit = it.limit.toTypedArray(),
                        member = it.member,
                        order = 0,
                        price = it.price.toTypedArray(),
                        product = formatId,
                        specificationType = specificationType,
                        title = formatTitle,
                    )

                    vmSpecificationViewModel.upsertOne(specificationData)
                }


                val productData = ProductEn(
                    activity = ddlActivityList[ddlPositionActivity].id,
                    channelDetail = productChannelDetailList.toTypedArray(),
                    group = productGroupList.toTypedArray(),
                    id = formatId,
                    imgUrl = formatId.toLowerCase().replace(" ", ""),
                    name = editName.text.toString(),
                    onSell = switchOnSell.isChecked,
                    preOrder = switchPreOrder.isChecked,
                    productType = ddlProductTypeList[ddlPostitionProductType].id,
                    publish = editPublish.text.toString(),
                    specification = speList.toTypedArray(),
                )

                vmProductViewModel.upsertOne(productData)

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

    private fun setProductDetailRecyclerView(list: MutableList<Specification>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvAddPoolProduct.layoutManager = layoutManager
        rvAddPoolProduct.adapter = ProductSaveAdapter(list, this)
    }

    private val calender: Calendar = getInstance()

    private val listener = View.OnClickListener {
        when (it) {
            editPublish -> {
                nowEdit = editPublish
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
        nowEdit.setText(selectedDate)
    }

    private fun setEditPageData(selectedProduct: String) {

        btnAddProduct.text = "NEW PRODUCT"
        linearDetail.isVisible = true

        val productInfo = dbProductList.filter { it.id == selectedProduct }.toMutableList()[0]

        val res: Resources = this.resources
        ImgSetting().setImage("product", res, imgDetailIcon, productInfo.id)


        linearTxtId.isVisible = true
        linearEditID.isVisible = false

        editPublish.setText(productInfo.publish)
        txtPublish.text = productInfo.publish
        txtId.text = productInfo.id
        editId.setText(productInfo.id)

        editName.setText(productInfo.name)

        switchOnSell.isChecked = productInfo.onSell
        switchPreOrder.isChecked = productInfo.preOrder

        spinnerActivity.setSelection(ddlActivityList.indexOfFirst { it.id == productInfo.activity })

        productChannelDetailList.clear()
        productInfo.channelDetail.forEach { pcd ->
            dbChannelDetailList.forEach {
                if (pcd == it.id) {
                    productChannelDetailList.add(it.id)
                }
            }
        }
        rvAddPoolChannelDetail.adapter =
            PoolProductEditChannelDetailAdapter(productChannelDetailList, page)

        productGroupList.clear()
        dbGroupList.forEach {
            if (productInfo.group.contains(it.id)) {
                productGroupList.add(it.id)
            }
        }
        rvAddPoolGroup.adapter = PoolProductEditGroupAdapter(productGroupList)


        tempSpecificationList.clear()

        dbSpecificationList.forEach { dbsp ->
            productInfo.specification.forEach { sp ->
                if (sp == dbsp.id) {
                    tempSpecificationList.add(dbsp)
                }
            }

        }
        setProductDetailRecyclerView(tempSpecificationList)

    }
}