package com.giby78king.merch.ui

import android.app.DatePickerDialog
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giby78king.merch.Adapter.ActivitySaveAdapter
import com.giby78king.merch.Adapter.CustomDropDownAdapter
import com.giby78king.merch.Domain.ChannelDetailEn
import com.giby78king.merch.Model.Activity
import com.giby78king.merch.Model.Activity.Companion.dbActivityList
import com.giby78king.merch.Model.Activity.Companion.ddlActivityList
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.ddlChannelDetailList
import com.giby78king.merch.Model.DdlNormalModel
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmActivitylViewModel
import com.giby78king.merch.ViewModel.VmChannelDetailSaveViewModel
import com.giby78king.merch.ViewModel.VmChannelDetailViewModel
import kotlinx.android.synthetic.main.activity_activity_edit_page.*
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.*
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.btnAddOne
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.btnSubmit
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.editId
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.editName
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.imgDetailIcon
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.linearEditID
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.linearTxtId
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.rvListItem
import kotlinx.android.synthetic.main.activity_channeldetail_edit_page.txtId
import java.text.SimpleDateFormat
import java.util.*


class ActivityEditPage : AppCompatActivity() {

    private lateinit var nowEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity_edit_page)

        var ddlPositionChannelDetail = 0
        var ddlPositionQueryChannelDetail = 0

        val vmChannelDetailViewModel =
            ViewModelProvider(this)[VmChannelDetailViewModel::class.java]
        val vmActivitylViewModel =
            ViewModelProvider(this)[VmActivitylViewModel::class.java]

        vmChannelDetailViewModel.getDatas("")
        vmChannelDetailViewModel.channelDetailDatas.observe(this) {
            ddlChannelDetailList.clear()
            ddlChannelDetailList.add(
                DdlNormalModel(
                    "請選擇",
                    "",
                    ""
                )
            )
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
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }
            spinnerChannelDetail.setOnTouchListener(View.OnTouchListener { v, event ->
                linearStartDate.isVisible = switchPeriod.isChecked
                false
            })
            val customDropDownAdapter =
                CustomDropDownAdapter("channelDetail", this, ddlChannelDetailList)
            spinnerChannelDetail.adapter = customDropDownAdapter
            spinnerChannelDetail.setSelection(ddlPositionChannelDetail)


            spinnerQueryActivity.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        ddlPositionQueryChannelDetail = position

                        var filterList =
                            dbActivityList.sortedByDescending { it.endDate }.toMutableList()
                        if (ddlPositionQueryChannelDetail != 0) {

                            filterList =
                                filterList.filter {
                                    it.channelDetail.contains(
                                        ddlChannelDetailList[position].id
                                    )
                                }.toMutableList()
                        }
                        setActivityRecyclerView(filterList)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

            spinnerQueryActivity.adapter = customDropDownAdapter
            spinnerQueryActivity.setSelection(ddlPositionQueryChannelDetail)

            vmActivitylViewModel.getDatas("")
            vmActivitylViewModel.activityDatas.observe(this) {
                var filterList = dbActivityList
                    .sortedByDescending { it.endDate }.toMutableList()
                setActivityRecyclerView(filterList)
            }
        }


        val vmChannelDetailSaveViewModel =
            ViewModelProvider(this)[VmChannelDetailSaveViewModel::class.java]

        vmChannelDetailSaveViewModel.channelDetailSelectInfo.observe(this) {
            val selected = it
            val activityInfo =
                dbActivityList.filter { it.id == selected.id }
                    .toMutableList()[0]
            editId.setText(activityInfo.id)
            editName.setText(activityInfo.name)

            if (activityInfo.startDate.isNotEmpty()) {
                switchPeriod.isChecked = true
                editStartDate.setText(activityInfo.startDate)
            }
            else
            {
                editStartDate.setText("")
                switchPeriod.isChecked = false
            }

            editEndDate.setText(activityInfo.endDate)

            val ddlIndex =
                ddlChannelDetailList.indexOfFirst { it.id == activityInfo.channelDetail[0] }
            spinnerChannelDetail.setSelection(ddlIndex)

            linearEditID.isVisible = false
            linearTxtId.isVisible = true
            txtId.text = activityInfo.id


            val res: Resources = this.resources
            //Img相關
            var img = "img_activity_" + activityInfo.imgUrl.toLowerCase().replace(" ", "")
            val resourceId: Int = res.getIdentifier(
                img, "drawable",
                "com.giby78king.merch"
            )
            imgDetailIcon.setImageResource(resourceId)
        }

        switchPeriod.setOnCheckedChangeListener { _, _ ->
            linearStartDate.isVisible = switchPeriod.isChecked
        }

        editEndDate.setOnClickListener(listener)
        editStartDate.setOnClickListener(listener)

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
                if (ddlChannelDetailList[ddlPositionChannelDetail].id == "") {
                    Toast.makeText(applicationContext, "通路主類別不得為空！！", Toast.LENGTH_SHORT)
                        .show()
                    btnSubmit.loadingFailed()
                    return@setOnClickListener
                }

                var formatId = editId.text.toString()

                if (ddlChannelDetailList[ddlPositionChannelDetail].id == "Activity" || ddlChannelDetailList[ddlPositionChannelDetail].id == "Seller") {
                    if (editEndDate.text.isEmpty()) {
                        Toast.makeText(applicationContext, "迄止時間不得為空！！", Toast.LENGTH_SHORT)
                            .show()
                        btnSubmit.loadingFailed()
                        return@setOnClickListener
                    }
                    if (switchPeriod.isChecked && editStartDate.text.isEmpty()) {
                        Toast.makeText(applicationContext, "起始時間不得為空！！", Toast.LENGTH_SHORT)
                            .show()
                        btnSubmit.loadingFailed()
                        return@setOnClickListener
                    }

                    formatId = editEndDate.text.toString() + " " + editId.text.toString()
                }

                if (txtId.text.isNotEmpty()) {
                    formatId = txtId.text.toString()
                }

                val channelDetailData = ChannelDetailEn(
//                    endDate = editEndDate.text.toString(),
                    channel = ddlChannelDetailList[ddlPositionChannelDetail].id,
                    id = formatId,
                    imgUrl = formatId,
                    name = editName.text.toString(),
//                    startDate = if (switchPeriod.isChecked) editStartDate.text.toString() else "",
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

    fun setActivityRecyclerView(list: MutableList<Activity>) {
        val vmChannelDetailSaveViewModel: VmChannelDetailSaveViewModel =
            ViewModelProvider(this)[VmChannelDetailSaveViewModel::class.java]
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvListItem.layoutManager = layoutManager
        rvListItem.adapter = ActivitySaveAdapter(list, vmChannelDetailSaveViewModel)
    }

    val calender = Calendar.getInstance()

    private val listener = View.OnClickListener {
        when (it) {
            editEndDate -> {
                nowEdit = editEndDate
                datePicker()
            }
            editStartDate -> {
                nowEdit = editStartDate
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

}