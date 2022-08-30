package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_ChannelDetail

data class ChannelDetailEn(
    val belong: String,
    val endDate: String,
    val id: String,
    val imgUrl: String,
    val name: String,
    val startDate: String,
) {
    suspend fun <T> updateOne(data: T) {
        data as ChannelDetailEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["belong"] = data.belong
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
                "belong" to belong,
                "endDate" to endDate,
                "id" to id,
                "imgUrl" to imgUrl,
                "name" to name,
                "startDate" to startDate,
            )
            FirebaseService_ChannelDetail.insertData(id, dbData)
        } else {
            val dbData = hashMapOf(
                "belong" to belong,
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