package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.toChannelDetail
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

object FirebaseService_ChannelDetail {

    private val db = FirebaseFirestore.getInstance().collection("ChannelDetail")
    suspend fun getOne(id: String): ChannelDetail {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toChannelDetail()
        }
        return ChannelDetail(
            channel = "",
            id = "newOne",
            imgUrl = "",
            name = "",
        )
    }

    suspend fun getDatas(): MutableList<ChannelDetail> {
        dbChannelDetailList = db
            .orderBy("order")
            .get()
            .await()
            .documents.mapNotNull { it.toChannelDetail() }.toMutableList()

        return dbChannelDetailList
    }

    fun insertData(id: String, data: Map<String, Any>) {
        db.document(id)
            .set(data).addOnSuccessListener {
            }
    }

    fun updateData(id: String, data: Map<String, Any>) {

        db.document(id)
            .update(data).addOnSuccessListener {
            }
    }

    fun deleteData(id: String) {
        db.document(id)
            .delete()
            .addOnSuccessListener {
            }
    }

}