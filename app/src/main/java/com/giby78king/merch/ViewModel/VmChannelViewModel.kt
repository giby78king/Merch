package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giby78king.merch.DataSource.FirebaseService_Channel
import com.giby78king.merch.Domain.MemberEn
import com.giby78king.merch.Model.Channel
import com.giby78king.merch.Model.Channel.Companion.dbChannelList
import kotlinx.coroutines.launch

class VmChannelViewModel : ViewModel() {

    private val _channelDatas = MutableLiveData<MutableList<Channel>>()
    private val _upsertChannelDatas = MutableLiveData<MutableList<Channel>>()
    val channelDatas: LiveData<MutableList<Channel>> = _channelDatas
    val upsertChannelDatas: LiveData<MutableList<Channel>> = _upsertChannelDatas

    init {
        viewModelScope.launch {
            FirebaseService_Channel.getDatas()
            _channelDatas.value = dbChannelList
        }
    }

    fun getDatas(selection:String) {
        viewModelScope.launch {
            FirebaseService_Channel.getDatas()
            when(selection){
                "editChannelId"-> {
                    _channelDatas.value = dbChannelList
                }
                "UpsertChannel"->{
                    _upsertChannelDatas.value = dbChannelList
                }
            }
        }
    }
    fun insertOne(id: String, data: Map<String, Any>) {
        viewModelScope.launch {
            FirebaseService_Channel.insertData(id,data)
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