package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class ChannelDetail(
    val channel: String,
    var id: String,
    val imgUrl: String,
    val name: String,
) {

    companion object {

        var dbChannelDetailList = mutableListOf<ChannelDetail>()
        var ddlChannelDetailList = java.util.ArrayList<DdlNormalModel>()
        var productChannelDetailList = mutableListOf<String>()

        fun DocumentSnapshot.toChannelDetail(): ChannelDetail {
            val channel = getString("channel")!!
            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!
            val name = getString("name")!!

            return ChannelDetail(
                channel,
                id,
                imgUrl,
                name,
            )
        }
    }
}