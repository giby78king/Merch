package com.giby78king.merch.ui

import android.app.DatePickerDialog
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giby78king.merch.Adapter.ChannelDetailSaveAdapter
import com.giby78king.merch.Adapter.CustomDropDownAdapter
import com.giby78king.merch.Domain.ChannelDetailEn
import com.giby78king.merch.Model.Activity
import com.giby78king.merch.Model.Channel.Companion.dbChannelList
import com.giby78king.merch.Model.Channel.Companion.ddlChannelList
import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.DdlNormalModel
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmChannelDetailSaveViewModel
import com.giby78king.merch.ViewModel.VmChannelDetailViewModel
import com.giby78king.merch.ViewModel.VmChannelViewModel
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.*
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.editId
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.editName
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.imgDetailIcon
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.linearEditID
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.linearTxtId
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.rvListItem
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.txtId
import java.text.SimpleDateFormat
import java.util.*


class ChannelDetailEditPage : AppCompatActivity() {

    private lateinit var nowEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channeldetail_edit_page)

        var ddlChannelPosition = 0
        var ddlQueryChannelPosition = 0

        val vmChannelViewModel =
            ViewModelProvider(this)[VmChannelViewModel::class.java]
        val vmChannelDetailViewModel =
            ViewModelProvider(this)[VmChannelDetailViewModel::class.java]

        vmChannelViewModel.channelDatas.observe(this) {
            ddlChannelList.clear()
            ddlChannelList.add(
                DdlNormalModel(
                    "請選擇",
                    "",
                    ""
                )
            )
            dbChannelList.sortedBy { it.order }.forEach {
                ddlChannelList.add(
                    DdlNormalModel(
                        it.name,
                        it.imgUrl,
                        it.id
                    )
                )
            }

            spinnerChannel.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ddlChannelPosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            val customDropDownAdapter =
                CustomDropDownAdapter("channel", this,"small", ddlChannelList)
            spinnerChannel.adapter = customDropDownAdapter
            spinnerChannel.setSelection(ddlChannelPosition)


            spinnerQueryChannelDetail.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        ddlQueryChannelPosition = position


                        var filterList = dbChannelDetailList.sortedByDescending { it.channel }.toMutableList()
                        if(ddlQueryChannelPosition != 0){

                            filterList =
                                filterList.filter { it.channel == ddlChannelList[position].id }.toMutableList()
                        }

                        setChannelDetailRecyclerView(filterList)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

            spinnerQueryChannelDetail.adapter = customDropDownAdapter
            spinnerQueryChannelDetail.setSelection(ddlQueryChannelPosition)

            vmChannelDetailViewModel.getDatas("")
            vmChannelDetailViewModel.channelDetailDatas.observe(this) {
                var filterList = dbChannelDetailList
                    .sortedByDescending { it.channel }.toMutableList()
                setChannelDetailRecyclerView(filterList)
            }
        }


        val vmChannelDetailSaveViewModel =
            ViewModelProvider(this)[VmChannelDetailSaveViewModel::class.java]

        vmChannelDetailSaveViewModel.channelDetailSelectInfo.observe(this) {
            val selected = it
            val channelDetailInfo =
                dbChannelDetailList.filter { it.id == selected.id }
                    .toMutableList()[0]
            editId.setText(channelDetailInfo.id)
            editName.setText(channelDetailInfo.name)

            val ddlIndex = ddlChannelList.indexOfFirst { it.id == channelDetailInfo.channel }
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

        btnAddOne.setOnClickListener {
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
        }

        btnSubmit.setOnClickListener {
            try {
                btnSubmit.startLoading()
                if (editId.text.isEmpty()) {
                    Toast.makeText(applicationContext, "暱稱/識別不得為空！！", Toast.LENGTH_SHORT).show()
                    btnSubmit.loadingFailed()
                    return@setOnClickListener
                }
                if (ddlChannelList[ddlChannelPosition].id == "") {
                    Toast.makeText(applicationContext, "通路主類別不得為空！！", Toast.LENGTH_SHORT)
                        .show()
                    btnSubmit.loadingFailed()
                    return@setOnClickListener
                }

                var formatId = editId.text.toString()


                if(txtId.text.isNotEmpty())
                {
                    formatId = txtId.text.toString()
                }

                val channelDetailData = ChannelDetailEn(
                    channel = ddlChannelList[ddlChannelPosition].id,
                    id = formatId,
                    imgUrl = formatId,
                    name = editName.text.toString(),
                )
                vmChannelDetailViewModel.upsertOne(channelDetailData)

                btnSubmit.loadingSuccessful()

                btnSubmit.animationEndAction = {
                    vmChannelDetailViewModel.getDatas("")
                    btnSubmit.reset()
                }

            } catch (ex: Exception) {
                Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
                btnSubmit.loadingFailed()
            }
        }
    }

    fun setChannelDetailRecyclerView(list: MutableList<ChannelDetail>) {
        val vmChannelDetailSaveViewModel: VmChannelDetailSaveViewModel =
            ViewModelProvider(this)[VmChannelDetailSaveViewModel::class.java]
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvListItem.layoutManager = layoutManager
        rvListItem.adapter = ChannelDetailSaveAdapter(list, vmChannelDetailSaveViewModel)
    }

    val calender = Calendar.getInstance()

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

}