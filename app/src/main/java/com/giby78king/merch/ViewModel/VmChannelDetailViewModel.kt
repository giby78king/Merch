package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_ChannelDetail
import com.giby78king.merch.Domain.ChannelDetailEn
import com.giby78king.merch.Domain.MemberEn
import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.Model.ChannelDetail.Companion.ChannelDetailList
import kotlinx.coroutines.launch

class VmChannelDetailViewModel : ViewModel() {

    private val _channelDetailDatas = MutableLiveData<MutableList<ChannelDetail>>()
    private val _upsertChannelDetailDatas = MutableLiveData<MutableList<ChannelDetail>>()
    val channelDetailDatas: LiveData<MutableList<ChannelDetail>> = _channelDetailDatas
    val upsertChannelDetailDatas: LiveData<MutableList<ChannelDetail>> = _upsertChannelDetailDatas

    fun getDatas(selection: String) {
        viewModelScope.launch {
            FirebaseService_ChannelDetail.getDatas()
            when (selection) {
                "editChannelId" -> {
                    _channelDetailDatas.value = ChannelDetailList
                }
                "UpsertChannel" -> {
                    _upsertChannelDetailDatas.value = ChannelDetailList
                }
                else ->
                {
                    _channelDetailDatas.value = ChannelDetailList
                }
            }
        }
    }

    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_ChannelDetail.insertData(id, data)
        }
    }

    fun upsertOne(data: ChannelDetailEn) {
        viewModelScope.launch {
            data.save()
        }
    }

    fun deleteOne(data: ChannelDetailEn) {
        viewModelScope.launch {
            data.delete()
        }
    }
}