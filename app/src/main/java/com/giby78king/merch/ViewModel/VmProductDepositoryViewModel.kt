package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_ProductDepository
import com.giby78king.merch.Domain.MemberEn
import com.giby78king.merch.Model.ProductDepository
import com.giby78king.merch.Model.ProductDepository.Companion.ProductDepositoryList
import kotlinx.coroutines.launch

class VmProductDepositoryViewModel : ViewModel() {

    private val _productDepositoryDatas = MutableLiveData<MutableList<ProductDepository>>()
    private val _upsertProductDepositoryDatas = MutableLiveData<MutableList<ProductDepository>>()
    val productDepositoryDatas: LiveData<MutableList<ProductDepository>> = _productDepositoryDatas
    val upsertProductDepositoryDatas: LiveData<MutableList<ProductDepository>> = _upsertProductDepositoryDatas

    init {
        viewModelScope.launch {
            FirebaseService_ProductDepository.getDatas()
            _productDepositoryDatas.value = ProductDepositoryList
        }
    }

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_ProductDepository.getDatas()
            when (selection) {
                "editProductDepositoryId" -> {
                    _productDepositoryDatas.value = ProductDepositoryList
                }
                "UpsertProductDepository" -> {
                    _upsertProductDepositoryDatas.value = ProductDepositoryList
                }
                else->{
                    _productDepositoryDatas.value = ProductDepositoryList
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_ProductDepository.insertData(id, data)
        }
    }

    fun upsertOne(data: MemberEn) {
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