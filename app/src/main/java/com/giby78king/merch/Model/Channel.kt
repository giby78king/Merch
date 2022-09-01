package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class Channel(
    val imgUrl: String,
    var id: String,
    val name: String,
    val order: Int,
) {

    companion object {

        var dbChannelList = mutableListOf<Channel>()
        var ddlChannelList = java.util.ArrayList<DdlNormalModel>()

        fun DocumentSnapshot.toChannel(): Channel {

            val imgUrl = getString("imgUrl")!!
            val id = getString("id")!!
            val name = getString("name")!!
            val order = if(getLong("order")==null) 0 else getLong("order")?.toInt()!!

            return Channel(
                imgUrl,
                id,
                name,
                order,
            )
        }
    }
}