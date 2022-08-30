package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VmChannelDetailSaveViewModel : ViewModel() {

    companion object {
        var selectChannel: ChannelDetailBelong =
            ChannelDetailBelong(
                "",
                ""
            )
    }

    class ChannelDetailBelong(
        var id: String,
        var icon: String,
    )

    var selectChannel: ChannelDetailBelong = ChannelDetailBelong(
        "",
        ""
    )


    private val _channelDetailSelectInfo = MutableLiveData<ChannelDetailBelong>()
    val channelDetailSelectInfo: LiveData<ChannelDetailBelong> = _channelDetailSelectInfo

    fun setSelectChannelDetailDatas(id: String, icon: String) {
        val data = ChannelDetailBelong(
            id,
            icon
        )
        selectChannel = data
        _channelDetailSelectInfo.value = data
    }

}