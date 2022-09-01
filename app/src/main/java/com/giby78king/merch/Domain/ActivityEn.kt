package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_ChannelDetail

data class ActivityEn(
    val channelDetail: Array<String>,
    val endDate: String,
    var id: String,
    val imgUrl: String,
    val name: String,
    val startDate: String,
) {
    suspend fun <T> updateOne(data: T) {
        data as ActivityEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["channelDetail"] = data.channelDetail
        dbData["endDate"] = data.endDate
        dbData["id"] = data.id
        dbData["imgUrl"] = data.imgUrl
        dbData["name"] = data.name
        dbData["startDate"] = data.startDate
        FirebaseService_ChannelDetail.updateData(id, dbData)
    }

    suspend fun save() {
        val data = FirebaseService_ChannelDetail.getOne(id)
        if (data.id == "newOne") {
            val dbData = hashMapOf(
                "channelDetail" to channelDetail.toCollection(java.util.ArrayList()),
                "endDate" to endDate,
                "id" to id,
                "imgUrl" to imgUrl,
                "name" to name,
                "startDate" to startDate,
            )
            FirebaseService_ChannelDetail.insertData(id, dbData)
        } else {
            val dbData = hashMapOf(
                "channelDetail" to channelDetail.toCollection(java.util.ArrayList()),
                "endDate" to endDate,
                "id" to id,
                "imgUrl" to imgUrl,
                "name" to name,
                "startDate" to startDate,
            )
            FirebaseService_ChannelDetail.updateData(id, dbData)
        }
    }

    fun delete() {
        FirebaseService_ChannelDetail.deleteData(id)
    }
}