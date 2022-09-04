package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Specification
import com.giby78king.merch.Domain.SpecificationEn
import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.Model.Member
import com.giby78king.merch.Model.Specification
import com.giby78king.merch.Model.Specification.Companion.dbSpecificationList
import com.giby78king.merch.Model.Specification.Companion.tempSpecificationList
import kotlinx.coroutines.launch

class VmSpecificationViewModel : ViewModel() {

    private val _specificationDatas = MutableLiveData<MutableList<Specification>>()
    private val _upsertSpecificationDatas = MutableLiveData<MutableList<Specification>>()
    val specificationDatas: LiveData<MutableList<Specification>> = _specificationDatas
    val upsertSpecificationDatas: LiveData<MutableList<Specification>> = _upsertSpecificationDatas

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_Specification.getDatas()
            when (selection) {
                "editSpecificationId" -> {
                    _specificationDatas.value = dbSpecificationList
                }
                "upsertSpecification" -> {
                    _upsertSpecificationDatas.value = dbSpecificationList
                }
                else -> {
                    _specificationDatas.value = dbSpecificationList
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_Specification.insertData(id, data)
        }
    }

    fun upsertOne(data: SpecificationEn) {
        viewModelScope.launch {
            data.save()
        }
    }

    fun deleteOne(id: String) {
        viewModelScope.launch {
            val en = SpecificationEn.getOne(id)
            en.delete()
        }
    }

    private val _selectedMemberDatas = MutableLiveData<MutableList<Specification>>()
    val SelectedMemberDatas: LiveData<MutableList<Specification>> = _selectedMemberDatas
    fun setSelectedMember(list: MutableList<Specification>) {
        _selectedMemberDatas.value = list
    }
}