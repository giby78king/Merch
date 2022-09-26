package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_ChannelDetail

data class ChannelDetailEn(
    val channel: String,
    val id: String,
    val imgUrl: String,
    val name: String,
    val order: Int,
) {
    suspend fun <T> updateOne(data: T) {
        data as ChannelDetailEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["channel"] = data.channel
        dbData["id"] = data.id
        dbData["imgUrl"] = data.imgUrl
        dbData["name"] = data.name
        dbData["order"] = data.order
        FirebaseService_ChannelDetail.updateData(id, dbData)
    }

    suspend fun save() {
        val data = FirebaseService_ChannelDetail.getOne(id)
        val dbData = hashMapOf(
            "channel" to channel,
            "id" to id,
            "imgUrl" to imgUrl,
            "name" to name,
            "order" to order
        )
        if (data.id == "newOne") {
            FirebaseService_ChannelDetail.insertData(id, dbData)
        } else {
            FirebaseService_ChannelDetail.updateData(id, dbData)
        }
    }

    fun delete() {
        FirebaseService_ChannelDetail.deleteData(id)
    }
}