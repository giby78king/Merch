package com.giby78king.merch.ui

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.giby78king.merch.Adapter.CustomDropDownAdapter
import com.giby78king.merch.Adapter.MemberSaveAdapter
import com.giby78king.merch.Domain.MemberEn
import com.giby78king.merch.Model.DdlNormalModel
import com.giby78king.merch.Model.Group.Companion.GroupList
import com.giby78king.merch.Model.Group.Companion.ddlGroupList
import com.giby78king.merch.Model.Member
import com.giby78king.merch.Model.Member.Companion.dbMemberList
import com.giby78king.merch.Model.Member.Companion.SelectGroupList
import com.giby78king.merch.R
import com.giby78king.merch.ViewModel.VmGroupViewModel
import com.giby78king.merch.ViewModel.VmMemberSaveViewModel
import com.giby78king.merch.ViewModel.VmMemberViewModel
import kotlinx.android.synthetic.main.activity_member_edit_page.*

class MemberEditPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_edit_page)

        SelectGroupList.clear()

        var ddlGroupPosition = 0
        var ddlQueryGroupPosition = 0
        val vmGroupViewModel =
            ViewModelProvider(this)[VmGroupViewModel::class.java]
        val vmMemberViewModel =
            ViewModelProvider(this)[VmMemberViewModel::class.java]

        vmGroupViewModel.groupDatas.observe(this) {
            ddlGroupList.clear()
            ddlGroupList.add(
                DdlNormalModel(
                    "請選擇",
                    "",
                    ""
                )
            )
            GroupList.forEach {
                ddlGroupList.add(
                    DdlNormalModel(
                        it.name,
                        it.id,
                        it.id
                    )
                )
            }

            spinnerGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ddlGroupPosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

//            vmMemberViewModel.selectedGroupDatas.observe(this) {
//                val layoutManager = LinearLayoutManager(this)
//                layoutManager.orientation = LinearLayoutManager.VERTICAL
//                rvAddPoolGroup.layoutManager = layoutManager
//                rvAddPoolGroup.adapter = PoolGroupAdapter(SelectGroupList)
//            }

            //新增相關(尚未入檔)
//            btnAddGroup.setOnClickListener {
//                if (!SelectGroupList.contains(ddlGroupList[ddlGroupPosition].id))            //排除重複點選
//                {
//                    SelectGroupList.add(ddlGroupList[ddlGroupPosition].id)
//                }
//                vmMemberViewModel.setSelectedGroup()
//            }

            val customDropDownAdapter =
                CustomDropDownAdapter("group", this, ddlGroupList)
            spinnerGroup.adapter = customDropDownAdapter
            spinnerGroup.setSelection(ddlGroupPosition)

            spinnerQueryGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ddlQueryGroupPosition = position

                    var filterList =
                        Member.dbMemberList.filter { it.group.contains(ddlGroupList[position].id) }
                            .toMutableList()
                    setMemberRecyclerView(filterList)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            spinnerQueryGroup.adapter = customDropDownAdapter
            spinnerQueryGroup.setSelection(ddlQueryGroupPosition)

            val vmMemberViewModel =
                ViewModelProvider(this)[VmMemberViewModel::class.java]

            vmMemberViewModel.getDatas("")
            vmMemberViewModel.memberDatas.observe(this) {
                setMemberRecyclerView(dbMemberList)
            }
        }


        val vmMemberSaveViewModel =
            ViewModelProvider(this)[VmMemberSaveViewModel::class.java]

        vmMemberSaveViewModel.memberSelectInfo.observe(this) {
            val selected = it
            val memberInfo =
                dbMemberList.filter { it.number == selected.id && it.group == selected.belong }
                    .toMutableList()[0]
            editId.setText(memberInfo.id)
            editName.setText(memberInfo.name)
            switchSex.isChecked = memberInfo.sex == "male"
            editBirthday.setText(memberInfo.birthday)
            editHeight.setText(memberInfo.height.toString())
            editWeight.setText(memberInfo.weight.toString())
            editNumber.setText(memberInfo.number)
            editFirstBoard.setText(memberInfo.firstBoard)
            switchEmployed.isChecked = memberInfo.employed

            linearEditID.isVisible = false
            linearTxtId.isVisible = true
            txtId.text = memberInfo.id

            spinnerGroup.setSelection(ddlGroupList.indexOf(ddlGroupList.filter { it.id == memberInfo.group }[0]))

            editIg.setText(memberInfo.ig)

            val res: Resources = this.resources
            //Img相關
            var img = "img_member_" + memberInfo.imgUrl.toLowerCase()
            val resourceId: Int = res.getIdentifier(
                img, "drawable",
                "com.giby78king.merch"
            )
            imgDetailIcon.setImageResource(resourceId)
        }

        btnAddMember.setOnClickListener {
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
                if (editNumber.text.toString() == "") {
                    Toast.makeText(applicationContext, "背號不得為空！！", Toast.LENGTH_SHORT)
                        .show()
                    btnSubmit.loadingFailed()
                    return@setOnClickListener
                }
                if (ddlGroupPosition == 0) {
                    Toast.makeText(applicationContext, "團體不得為空！！", Toast.LENGTH_SHORT)
                        .show()
                    btnSubmit.loadingFailed()
                    return@setOnClickListener
                }

                var iconId = ""
                val groupFirst = ddlGroupList[ddlGroupPosition].id.split(" ")

                groupFirst.forEach {
                    iconId += it.substring(0, 1)
                }

                val memoData = MemberEn(

                    birthday = editBirthday.text.toString(),
                    employed = switchEmployed.isChecked,
                    firstBoard = editFirstBoard.text.toString(),
                    group = ddlGroupList[ddlGroupPosition].id,
                    height = editHeight.text.toString().toInt(),
                    imgUrl = iconId.toLowerCase() + editFirstBoard.text.toString() + editNumber.text.toString(),
                    id = editId.text.toString(),
                    ig = editIg.text.toString(),
                    name = editName.text.toString(),
                    number = editNumber.text.toString(),
                    sex = if (switchSex.isChecked) "male" else "female",
                    weight = editWeight.text.toString().toInt(),
                )
                vmMemberViewModel.upsert(memoData)

                btnSubmit.loadingSuccessful()

                btnSubmit.animationEndAction = {
                    vmMemberViewModel.getDatas("")
                    btnSubmit.reset()
                }

            } catch (ex: Exception) {
                Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
                btnSubmit.loadingFailed()
            }
        }
    }

    fun setMemberRecyclerView(list: MutableList<Member>) {
        val vmMemberSaveViewModel: VmMemberSaveViewModel =
            ViewModelProvider(this)[VmMemberSaveViewModel::class.java]
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rvListItem.layoutManager = layoutManager
        rvListItem.adapter = MemberSaveAdapter(list, vmMemberSaveViewModel)
    }
}