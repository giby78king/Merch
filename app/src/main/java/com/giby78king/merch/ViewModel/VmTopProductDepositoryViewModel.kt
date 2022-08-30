package com.giby78king.merch.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VmTopProductDepositoryViewModel : ViewModel() {

    companion object {
        var selectMember: MemberBelong = MemberBelong(
            "",
            "",
            ""
        )
        var selectChannel: ChannelDetailBelong= ChannelDetailBelong(
            "",
            ""
        )
    }

    class MemberBelong(
        var belong: String,
        var id: String,
        var icon: String,
    )


    private val _memberTopInfo = MutableLiveData<MemberBelong>()
    val memberTopInfo: LiveData<MemberBelong> = _memberTopInfo

    fun setTopMemberDatas(belong: String, id: String, icon: String) {
        val data = MemberBelong(
            belong,
            id,
            icon
        )
        selectMember = data
        _memberTopInfo.value = data
    }

    class ChannelDetailBelong(
        var id: String,
        var icon: String,
    )

    private val _channelDetailTopInfo = MutableLiveData<ChannelDetailBelong>()
    val channelDetailTopInfo: LiveData<ChannelDetailBelong> = _channelDetailTopInfo

    fun setTopChannelDetailDatas(id: String, icon: String) {
        val data = ChannelDetailBelong(
            id,
            icon
        )
        selectChannel = data
        _channelDetailTopInfo.value = data
    }

    private val _depoToDetail = MutableLiveData<String>()
    val depoToDetail: LiveData<String> = _depoToDetail

    fun setDepoToDetailDatas(id: String) {
        _depoToDetail.value = id
    }
}