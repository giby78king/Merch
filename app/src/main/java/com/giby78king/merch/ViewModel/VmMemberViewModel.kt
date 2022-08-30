package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Member
import com.giby78king.merch.Model.Member
import com.giby78king.merch.Model.Member.Companion.MemberList
import com.giby78king.merch.Domain.MemberEn
import com.giby78king.merch.Model.Member.Companion.SelectGroupList
import kotlinx.coroutines.launch

class VmMemberViewModel : ViewModel() {

    private val _memberDatas = MutableLiveData<MutableList<Member>>()
    private val _upsertMemberDatas = MutableLiveData<MutableList<Member>>()
    val memberDatas: LiveData<MutableList<Member>> = _memberDatas
    val upsertMemberDatas: LiveData<MutableList<Member>> = _upsertMemberDatas

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_Member.getDatas()
            when (selection) {
                "editMemberId" -> {
                    _memberDatas.value = MemberList
                }
                "UpsertMember" -> {
                    _upsertMemberDatas.value = MemberList
                }
                else -> {
                    _memberDatas.value = MemberList
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_Member.insertData(id, data)
        }
    }

    fun upsert(data: MemberEn) {
        viewModelScope.launch {
            data.save()
        }
    }

    fun deleteOne(data: MemberEn) {
        viewModelScope.launch {
            data.delete()
        }
    }
}