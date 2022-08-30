package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Trade
import com.giby78king.merch.Domain.MemberEn
import com.giby78king.merch.Model.Trade
import com.giby78king.merch.Model.Trade.Companion.TradeList
import kotlinx.coroutines.launch

class VmTradeViewModel : ViewModel() {

    private val _tradeDatas = MutableLiveData<MutableList<Trade>>()
    private val _upsertTradeDatas = MutableLiveData<MutableList<Trade>>()
    val tradeDatas: LiveData<MutableList<Trade>> = _tradeDatas
    val upsertTradeDatas: LiveData<MutableList<Trade>> = _upsertTradeDatas

    init {
        viewModelScope.launch {
            FirebaseService_Trade.getDatas()
            _tradeDatas.value = TradeList
        }
    }

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_Trade.getDatas()
            when (selection) {
                "editTradeId" -> {
                    _tradeDatas.value = TradeList
                }
                "UpsertTrade" -> {
                    _upsertTradeDatas.value = TradeList
                }
                else -> {
                    _tradeDatas.value = TradeList
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_Trade.insertData(id, data)
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