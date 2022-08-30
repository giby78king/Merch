package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.Model.ChannelDetail.Companion.ChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.toChannelDetail
import com.google.firebase.firestore.FirebaseFirestore
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
            belong = "",
            endDate ="",
            id = "newOne",
            imgUrl = "",
            name = "",
            startDate = "",
        )
    }

    suspend fun getDatas(): MutableList<ChannelDetail> {
        ChannelDetailList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toChannelDetail() }.toMutableList()

        return ChannelDetailList
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