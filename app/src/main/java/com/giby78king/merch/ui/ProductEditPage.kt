package com.giby78king.merch.ui

import android.app.DatePickerDialog
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giby78king.merch.Adapter.CustomDropDownAdapter
import com.giby78king.merch.Adapter.ProductSaveAdapter
import com.giby78king.merch.Domain.ProductEn
import com.giby78king.merch.Model.*
import com.giby78king.merch.Model.Channel.Companion.ChannelList
import com.giby78king.merch.Model.Channel.Companion.ddlChannelList
import com.giby78king.merch.Model.ChannelDetail.Companion.ChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.ddlChannelDetailList
import com.giby78king.merch.Model.Product.Companion.ProductList
import com.giby78king.merch.Model.Product.Companion.copyProductDetailList
import com.giby78king.merch.Model.Product.Companion.nowEditProductId
import com.giby78king.merch.Model.Product.Companion.productDetailList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.*
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.*
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.spinnerChannel
import kotlinx.android.synthetic.main.activity_member_edit_page.btnSubmit
import kotlinx.android.synthetic.main.activity_member_edit_page.editId
import kotlinx.android.synthetic.main.activity_member_edit_page.editName
import kotlinx.android.synthetic.main.activity_member_edit_page.imgDetailIcon
import kotlinx.android.synthetic.main.activity_member_edit_page.linearEditID
import kotlinx.android.synthetic.main.activity_member_edit_page.linearTxtId
import kotlinx.android.synthetic.main.activity_member_edit_page.txtId
import kotlinx.android.synthetic.main.activity_product_edit_page.*
import java.text.SimpleDateFormat
import java.util.*


class ProductEditPage : AppCompatActivity() {

    private lateinit var nowEdit: EditText
    var ddlChannelDetailPosition = 0
    var init = true
    lateinit var page: ProductEditPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_edit_page)

        val bundle = intent.extras
        val selectedProduct = bundle?.getString("selectedProduct").toString()
        page = this

        copyProductDetailList.clear()

        var ddlChannelPosition = 0
        ddlChannelDetailPosition = 0

        val vmChannelViewModel =
            ViewModelProvider(this)[VmChannelViewModel::class.java]
        val vmChannelDetailViewModel =
            ViewModelProvider(this)[VmChannelDetailViewModel::class.java]
        val vmProductViewModel =
            ViewModelProvider(this)[VmProductViewModel::class.java]


        vmChannelViewModel.channelDatas.observe(this) {
            ddlChannelList.clear()
            ddlChannelList.add(
                DdlNormalModel(
                    "請選擇",
                    "",
                    ""
                )
            )
            ChannelList.sortedBy { it.order }.forEach {
                ddlChannelList.add(
                    DdlNormalModel(
                        it.name,
                        it.icon,
                        it.id
                    )
                )
            }

            vmChannelDetailViewModel.getDatas("")
            vmChannelDetailViewModel.channelDetailDatas.observe(this) {
                spinnerChannel.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            ddlChannelPosition = position

                            ddlChannelDetailList.clear()
                            ddlChannelDetailList.add(
                                DdlNormalModel(
                                    "請選擇",
                                    "",
                                    ""
                                )
                            )

                            ChannelDetailList.filter { it.belong == ddlChannelList[ddlChannelPosition].id }
                                .forEach {
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
                                        ddlChannelDetailPosition = position
                                    }

                                    override fun onNothingSelected(parent: AdapterView<*>) {}
                                }

                            val customDropDownAdapter =
                                CustomDropDownAdapter(
                                    "channelDetail",
                                    parent.context,
                                    ddlChannelDetailList
                                )
                            spinnerChannelDetail.adapter = customDropDownAdapter
                            if (init && ddlChannelDetailPosition != 0) {
                                spinnerChannelDetail.setSelection(ddlChannelDetailPosition)
                                init = false
                            } else {
                                spinnerChannelDetail.setSelection(0)
                            }
                            switchOnSell.isChecked = false
                            switchPreOrder.isChecked = false

                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {}
                    }

                val customDropDownAdapter =
                    CustomDropDownAdapter("channel", this, ddlChannelList)
                spinnerChannel.adapter = customDropDownAdapter
                spinnerChannel.setSelection(ddlChannelPosition)
            }
        }


        val vmChannelDetailSaveViewModel =
            ViewModelProvider(this)[VmChannelDetailSaveViewModel::class.java]

        vmChannelDetailSaveViewModel.channelDetailSelectInfo.observe(this) {
            val selected = it
            val channelDetailInfo =
                ChannelDetailList.filter { it.id == selected.id }
                    .toMutableList()[0]
            editId.setText(channelDetailInfo.id)
            editName.setText(channelDetailInfo.name)

            if (channelDetailInfo.belong == "Activity" || channelDetailInfo.belong == "Seller") {
                if (channelDetailInfo.startDate.isNotEmpty()) {
                    linearStartDate.isVisible = true
                    switchPeriod.isChecked = true
                    editStartDate.setText(channelDetailInfo.startDate)
                }
                linearEndDate.isVisible = true
                editEndDate.setText(channelDetailInfo.endDate)
            } else {
                linearStartDate.isVisible = false
                linearEndDate.isVisible = false
                editStartDate.setText("")
                editEndDate.setText("")
            }

            val ddlIndex = ddlChannelList.indexOfFirst { it.id == channelDetailInfo.belong }
            spinnerChannel.setSelection(ddlIndex)

            linearEditID.isVisible = false
            linearTxtId.isVisible = true
            txtId.text = channelDetailInfo.id


            val res: Resources = this.resources
            //Img相關
            var img = "img_channeldetail_" + channelDetailInfo.imgUrl.toLowerCase().replace(" ", "")
            val resourceId: Int = res.getIdentifier(
                img, "drawable",
                "com.giby78king.merch"
            )
            imgDetailIcon.setImageResource(resourceId)
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

                val res: Resources = this.resources
                //Img相關
                var img = "ic_member_save_new"
                val resourceId: Int = res.getIdentifier(
                    img, "drawable",
                    "com.giby78king.merch"
                )
                imgDetailIcon.setImageResource(resourceId)
            } else {
                if (editId.text.isEmpty()) {
                    Toast.makeText(applicationContext, "暱稱/識別不得為空！！", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                nowEditProductId = editId.text.toString()
                btnAddProduct.text = "NEW PRODUCT"
                linearEditID.isVisible = false
                linearTxtId.isVisible = true
                linearDetail.isVisible = true
            }

        }

        btnAddSpecification.setOnClickListener {


            productDetailList.clear()

            productDetailList.add(
                ProductDetail(
                    count = 1,
                    group = "",
                    limit = 0,
                    member = "",
                    price = 0
                )
            )

            copyProductDetailList.forEach {
                productDetailList.add(
                    ProductDetail(
                        count = it.count + 1,
                        group = it.group,
                        limit = it.limit,
                        member = it.member,
                        price = it.price
                    )
                )
            }


            copyProductDetailList.clear()
            productDetailList.forEach {
                copyProductDetailList.add(
                    ProductDetail(
                        count = it.count,
                        group = it.group,
                        limit = it.limit,
                        member = it.member,
                        price = it.price
                    )
                )
            }

            setProductDetailRecyclerView(productDetailList)
        }

        val vmGroupViewModel =
            ViewModelProvider(this)[VmGroupViewModel::class.java]
        val vmMemberViewModel =
            ViewModelProvider(this)[VmMemberViewModel::class.java]

        vmChannelViewModel.channelDatas.observe(this) {
            vmChannelDetailViewModel.getDatas("")
            vmChannelDetailViewModel.channelDetailDatas.observe(this) {
                vmGroupViewModel.groupDatas.observe(this) {
                    vmMemberViewModel.getDatas("")
                    vmMemberViewModel.memberDatas.observe(this) {
                        if (selectedProduct.isNotEmpty() && selectedProduct != "null") {
                            vmProductViewModel.getDatas("editProductId")
                            vmProductViewModel.productDatas.observe(this) {
                                setEditPageData(selectedProduct)
                            }
                        }
                    }
                }
            }
        }

        val vmProductSaveViewModel =
            ViewModelProvider(this)[VmProductSaveViewModel::class.java]

        vmProductSaveViewModel.specificationSelectInfo.observe(this) {

            copyProductDetailList.removeAt(it)

            var index = 0
            copyProductDetailList.forEach { _ ->
                copyProductDetailList[index].count = index + 1
                index++
            }

            setProductDetailRecyclerView(copyProductDetailList)
        }

        txtFixAll.setOnClickListener {
            if (copyProductDetailList.size < 1) {
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

            btnEditAll.setOnClickListener {

                copyProductDetailList.forEach {
                    it.price = editPrice.text.toString().replace(",", "").toInt()
                    it.limit = editLimit.text.toString().replace(",", "").toInt()
                }

                setProductDetailRecyclerView(copyProductDetailList)
            }
        }

        btnSubmit.setOnClickListener {
            try {
                btnSubmit.startLoading()
                if (editId.text.isEmpty()) {
                    Toast.makeText(applicationContext, "暱稱/識別不得為空！！", Toast.LENGTH_SHORT).show()
                    btnSubmit.loadingFailed()
                    return@setOnClickListener
                }
                if (ddlChannelDetailList[ddlChannelDetailPosition].id.isEmpty()) {
                    Toast.makeText(applicationContext, "通路明細不得為空！！", Toast.LENGTH_SHORT)
                        .show()
                    btnSubmit.loadingFailed()
                    return@setOnClickListener
                }
                if (editPublish.text.isEmpty()) {
                    Toast.makeText(applicationContext, "發行日不得為空！！", Toast.LENGTH_SHORT)
                        .show()
                    btnSubmit.loadingFailed()
                    return@setOnClickListener
                }

                var formatId = editPublish.text.toString() + "_" + editId.text.toString()

                if (txtId.text.isNotEmpty()) {
                    formatId = txtId.text.toString()
                }

                var group = arrayOfNulls<String>(size = copyProductDetailList.size)
                val limit = arrayOfNulls<Int>(size = copyProductDetailList.size)
                val member = arrayOfNulls<String>(size = copyProductDetailList.size)
                val price = arrayOfNulls<Int>(size = copyProductDetailList.size)

                var index = 0
                copyProductDetailList.sortedBy { it.member }.forEach {

                    group[index] = it.group
                    limit[index] = it.limit
                    member[index] = it.member
                    price[index] = it.price

                    index++
                }

                val productData = ProductEn(
                    channelDetail = ddlChannelDetailList[ddlChannelDetailPosition].id,
                    group = group,
                    id = formatId,
                    limit = limit,
                    member = member,
                    name = editName.text.toString(),
                    onSell = switchOnSell.isChecked,
                    preOrder = switchPreOrder.isChecked,
                    price = price,
                    publish = editPublish.text.toString(),
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

    private fun setProductDetailRecyclerView(list: MutableList<ProductDetail>) {
        val vmProductSaveViewModel =
            ViewModelProvider(this)[VmProductSaveViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvAddPoolProduct.layoutManager = layoutManager
        rvAddPoolProduct.adapter = ProductSaveAdapter(list, vmProductSaveViewModel)
    }

    val calender = Calendar.getInstance()

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

        nowEditProductId = selectedProduct
        btnAddProduct.text = "NEW PRODUCT"
        linearDetail.isVisible = true

        val productInfo = ProductList.filter { it.id == selectedProduct }.toMutableList()[0]

        val res: Resources = this.resources
        //Img相關
        var img = "img_product_" + productInfo.id.toLowerCase().replace(" ", "")
        val resourceId: Int = res.getIdentifier(
            img, "drawable",
            "com.giby78king.merch"
        )
        imgDetailIcon.setImageResource(resourceId)

        linearTxtId.isVisible = true
        linearEditID.isVisible = false

        txtId.text = productInfo.id
        editId.setText(productInfo.id)

        editName.setText(productInfo.name)
        editPublish.setText(productInfo.publish)
        switchOnSell.isChecked = productInfo.onSell
        switchPreOrder.isChecked = productInfo.preOrder

        val channelDetailInfo = ChannelDetailList.filter { it.id == productInfo.channelDetail }[0]
        val channelInfo = ChannelList.filter { it.id == channelDetailInfo.belong }[0]

        spinnerChannel.setSelection(ChannelList.sortedBy { it.order }.indexOf(channelInfo) + 1)
        ddlChannelDetailPosition =
            ChannelDetailList.filter { it.belong == channelInfo.id }.indexOf(channelDetailInfo) + 1

        copyProductDetailList.clear()
        for (i in 0 until productInfo.member.size) {
            copyProductDetailList.add(
                ProductDetail(
                    count = i + 1,
                    group = productInfo.group[i],
                    limit = productInfo.limit[i]!!.toInt(),
                    member = productInfo.member[i],
                    price = productInfo.price[i]!!.toInt()
                )
            )
        }
        setProductDetailRecyclerView(copyProductDetailList)
    }
}