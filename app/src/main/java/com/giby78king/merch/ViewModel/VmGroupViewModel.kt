package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Group
import com.giby78king.merch.DataSource.FirebaseService_Member
import com.giby78king.merch.Model.Group.Companion.GroupList
import com.giby78king.merch.Domain.MemberEn
import com.giby78king.merch.Model.Group
import kotlinx.coroutines.launch

class VmGroupViewModel : ViewModel() {

    private val _groupDatas = MutableLiveData<MutableList<Group>>()
    private val _upsertGroupDatas = MutableLiveData<MutableList<Group>>()
    val groupDatas: LiveData<MutableList<Group>> = _groupDatas
    val upsertGroupDatas: LiveData<MutableList<Group>> = _upsertGroupDatas

    init {
        viewModelScope.launch {
            FirebaseService_Group.getDatas()
            _groupDatas.value = GroupList
        }
    }

    fun getDatas(selection:String) {
        viewModelScope.launch {
            FirebaseService_Group.getDatas()
            when(selection){
                "editGroupId"-> {
                    _groupDatas.value = GroupList
                }
                "UpsertGroup"->{
                    _upsertGroupDatas.value = GroupList
                }
            }
        }
    }
    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_Group.insertData(id,data)
        }
    }
    fun upsertOne(data: MemberEn) {
        viewModelScope.launch {
            data.save()
        }
    }
    fun deleteOne(data: MemberEn){
        viewModelScope.launch {
            data.delete()
        }
    }
}