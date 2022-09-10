package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Trade
import com.giby78king.merch.DataSource.FirebaseService_TradeRule
import com.giby78king.merch.Domain.MemberEn
import com.giby78king.merch.Model.Group
import com.giby78king.merch.Model.TradeRule
import com.giby78king.merch.Model.TradeRule.Companion.dbTradeRuleList
import kotlinx.coroutines.launch

class VmTradeRuleViewModel : ViewModel() {

    private val _tradeRuleDatas = MutableLiveData<MutableList<TradeRule>>()
    private val _upsertTradeDatas = MutableLiveData<MutableList<TradeRule>>()
    val tradeRuleDatas: LiveData<MutableList<TradeRule>> = _tradeRuleDatas
    val upsertTradeDatas: LiveData<MutableList<TradeRule>> = _upsertTradeDatas

    init {
        viewModelScope.launch {
            FirebaseService_TradeRule.getDatas()
            _tradeRuleDatas.value = dbTradeRuleList
        }
    }

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_TradeRule.getDatas()
            when (selection) {
                "editTradeRuleId" -> {
                    _tradeRuleDatas.value = dbTradeRuleList
                }
                "UpsertTradeRule" -> {
                    _upsertTradeDatas.value = dbTradeRuleList
                }
                else -> {
                    _tradeRuleDatas.value = dbTradeRuleList
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_TradeRule.insertData(id, data)
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