package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Member
import com.giby78king.merch.Model.Member
import com.giby78king.merch.Model.Member.Companion.dbMemberList
import com.giby78king.merch.Domain.MemberEn
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
                    _memberDatas.value = dbMemberList
                }
                "UpsertMember" -> {
                    _upsertMemberDatas.value = dbMemberList
                }
                else -> {
                    _memberDatas.value = dbMemberList
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