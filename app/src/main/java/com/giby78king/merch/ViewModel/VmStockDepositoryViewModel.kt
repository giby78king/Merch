package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_StockDepository
import com.giby78king.merch.Domain.MemberEn
import com.giby78king.merch.Domain.StockDepositoryEn
import com.giby78king.merch.Model.StockDepository
import com.giby78king.merch.Model.StockDepository.Companion.dbStockDepositoryList
import kotlinx.coroutines.launch

class VmStockDepositoryViewModel : ViewModel() {

    private val _stockDepositoryDatas = MutableLiveData<MutableList<StockDepository>>()
    private val _upsertStockDepositoryDatas = MutableLiveData<MutableList<StockDepository>>()
    val stockDepositoryDatas: LiveData<MutableList<StockDepository>> = _stockDepositoryDatas
    val upsertStockDepositoryDatas: LiveData<MutableList<StockDepository>> = _upsertStockDepositoryDatas

//    init {
//        viewModelScope.launch {
//            FirebaseService_StockDepository.getDatas()
//            _stockDepositoryDatas.value = dbStockDepositoryList
//        }
//    }

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_StockDepository.getDatas()
            when (selection) {
                "editStockDepositoryId" -> {
                    _stockDepositoryDatas.value = dbStockDepositoryList
                }
                "UpsertStockDepository" -> {
                    _upsertStockDepositoryDatas.value = dbStockDepositoryList
                }
                else->{
                    _stockDepositoryDatas.value = dbStockDepositoryList
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_StockDepository.insertData(id, data)
        }
    }

    fun upsertOne(data: StockDepositoryEn) {
        viewModelScope.launch {
            data.save()
        }
    }

    fun deleteOne(data: StockDepositoryEn) {
        viewModelScope.launch {
            data.delete()
        }
    }
}