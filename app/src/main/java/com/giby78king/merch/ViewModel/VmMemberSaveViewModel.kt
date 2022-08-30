package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VmMemberSaveViewModel : ViewModel() {

    companion object {
        var selectMember: MemberBelong = MemberBelong(
            "",
            "",
            ""
        )
    }

    class MemberBelong(
        var belong: String,
        var id: String,
        var icon: String,
    )


    private val _memberSelectInfo = MutableLiveData<MemberBelong>()
    val memberSelectInfo: LiveData<MemberBelong> = _memberSelectInfo

    fun setSelectMemberDatas(belong: String, id: String, icon: String) {
        val data = MemberBelong(
            belong,
            id,
            icon
        )
        selectMember = data
        _memberSelectInfo.value = data
    }

}