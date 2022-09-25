package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Trade
import com.giby78king.merch.DataSource.FirebaseService_TradeDetail
import com.giby78king.merch.Domain.TradeDetailEn
import com.giby78king.merch.Domain.TradeEn
import com.giby78king.merch.Model.Trade
import com.giby78king.merch.Model.Trade.Companion.dbTradeList
import com.giby78king.merch.Model.Trade.Companion.tradeModifyList
import com.giby78king.merch.Model.Trade.Companion.tradeOtherList
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.Model.TradeDetail.Companion.dbTradeDetailList
import com.giby78king.merch.Model.TradeDetail.Companion.specModifyList
import com.giby78king.merch.Model.TradeDetail.Companion.specOtherList
import com.giby78king.merch.Model.tempPriceDetail
import kotlinx.coroutines.launch

class VmTradeDetailViewModel : ViewModel() {

    private val _tradeDetailDatas = MutableLiveData<MutableList<TradeDetail>>()
    private val _upsertTradeDatas = MutableLiveData<MutableList<TradeDetail>>()
    val tradeDetailDatas: LiveData<MutableList<TradeDetail>> = _tradeDetailDatas
    val upsertTradeDatas: LiveData<MutableList<TradeDetail>> = _upsertTradeDatas

//    init {
//        viewModelScope.launch {
//            FirebaseService_TradeDetail.getDatas()
//            _tradeDetailDatas.value = dbTradeDetailList
//        }
//    }

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_TradeDetail.getDatas()
            when (selection) {
                "editTradeDetailId" -> {
                    _tradeDetailDatas.value = dbTradeDetailList.toMutableList()
                }
                "UpsertTradeDetail" -> {
                    _upsertTradeDatas.value = dbTradeDetailList.toMutableList()
                }
                else -> {
                    _tradeDetailDatas.value = dbTradeDetailList.toMutableList()
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_TradeDetail.insertData(id, data)
        }
    }

    fun upsertOne(data: TradeDetailEn) {
        viewModelScope.launch {
            data.save()
        }
    }

    fun deleteOne(data: TradeDetailEn) {
        viewModelScope.launch {
            data.delete()
        }
    }

}