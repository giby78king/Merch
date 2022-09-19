package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Trade
import com.giby78king.merch.Domain.TradeEn
import com.giby78king.merch.Model.Trade
import com.giby78king.merch.Model.Trade.Companion.dbTradeList
import com.giby78king.merch.Model.Trade.Companion.tradeModifyList
import com.giby78king.merch.Model.Trade.Companion.tradeOtherList
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.Model.TradeDetail.Companion.specModifyList
import com.giby78king.merch.Model.TradeDetail.Companion.specOtherList
import com.giby78king.merch.Model.tempPriceDetail
import kotlinx.coroutines.launch

class VmTradeViewModel : ViewModel() {

    private val _tradeDatas = MutableLiveData<MutableList<Trade>>()
    private val _upsertTradeDatas = MutableLiveData<MutableList<Trade>>()
    val tradeDatas: LiveData<MutableList<Trade>> = _tradeDatas
    val upsertTradeDatas: LiveData<MutableList<Trade>> = _upsertTradeDatas

    init {
        viewModelScope.launch {
            FirebaseService_Trade.getDatas()
            _tradeDatas.value = dbTradeList
        }
    }

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_Trade.getDatas()
            when (selection) {
                "editTradeId" -> {
                    _tradeDatas.value = dbTradeList
                }
                "UpsertTrade" -> {
                    _upsertTradeDatas.value = dbTradeList
                }
                else -> {
                    _tradeDatas.value = dbTradeList
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_Trade.insertData(id, data)
        }
    }

    fun upsertOne(data: TradeEn) {
        viewModelScope.launch {
            data.save()
        }
    }

    fun deleteOne(data: TradeEn) {
        viewModelScope.launch {
            data.delete()
        }
    }


    private val _selectedModifyDatas = MutableLiveData<MutableList<String>>().apply {
        value = tradeModifyList
    }
    val SelectedModifyDatas: LiveData<MutableList<String>> = _selectedModifyDatas
    fun setSelectedModify() {
        _selectedModifyDatas.value = tradeModifyList
    }

    private val _selectedOtherDatas = MutableLiveData<MutableList<String>>().apply {
        value = tradeOtherList
    }
    val SelectedOtherDatas: LiveData<MutableList<String>> = _selectedOtherDatas
    fun setSelectedOther() {
        _selectedOtherDatas.value = tradeOtherList
    }

    private val _processTradeDetailDatas = MutableLiveData<MutableList<TradeDetail>>()
    val ProcessTradeDetailDatas: LiveData<MutableList<TradeDetail>> = _processTradeDetailDatas
    fun setProcessTradeDetail(data:MutableList<TradeDetail>) {
        _processTradeDetailDatas.value = data
    }

    private val _selectedSpecDatas = MutableLiveData<TradeDetail>()
    val SelectedSpecDatas: LiveData<TradeDetail> = _selectedSpecDatas
    fun setSelectedSpecDatas(data: TradeDetail) {
        _selectedSpecDatas.value = data
    }

    private val _selectedSpecModify = MutableLiveData<MutableList<tempPriceDetail>>()
    val SelectedSpecModify: LiveData<MutableList<tempPriceDetail>> = _selectedSpecModify
    fun setSelectedSpecModify() {
        _selectedSpecModify.value = specModifyList
    }

    private val _selectedSpecOther = MutableLiveData<MutableList<tempPriceDetail>>()
    val SelectedSpecOther: LiveData<MutableList<tempPriceDetail>> = _selectedSpecOther
    fun setSelectedSpecOther() {
        _selectedSpecOther.value = specOtherList
    }

}