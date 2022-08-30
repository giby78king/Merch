package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class ChannelDetail(
    val belong: String,
    val endDate:String,
    var id: String,
    val imgUrl: String,
    val name: String,
    val startDate:String,
) {

    companion object {

        var ChannelDetailList = mutableListOf<ChannelDetail>()
        var ddlChannelDetailList = java.util.ArrayList<DdlNormalModel>()

        fun DocumentSnapshot.toChannelDetail(): ChannelDetail {
            val belong = getString("belong")!!
            val endDate = if (getString("endDate") == null) "" else getString("endDate")!!
            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!
            val name = getString("name")!!
            val startDate = if (getString("startDate") == null) "" else getString("startDate")!!

            return ChannelDetail(
                belong,
                endDate,
                id,
                imgUrl,
                name,
                startDate,
            )
        }
    }
}