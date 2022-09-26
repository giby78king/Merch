package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class ChannelDetail(
    val channel: String,
    var id: String,
    val imgUrl: String,
    val name: String,
    val order: Int,
    val type: String,
) {

    companion object {

        var dbChannelDetailList = mutableListOf<ChannelDetail>()
        var ddlChannelDetailList = arrayListOf<DdlNormalModel>()
        var productChannelDetailList = mutableListOf<String>()

        fun DocumentSnapshot.toChannelDetail(): ChannelDetail {
            val channel = getString("channel")!!
            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!
            val name = getString("name")!!
            val order = if(getLong("order")==null) 0 else getLong("order")?.toInt()!!
            val type = getString("type")!!

            return ChannelDetail(
                channel,
                id,
                imgUrl,
                name,
                order,
                type
            )
        }
    }
}