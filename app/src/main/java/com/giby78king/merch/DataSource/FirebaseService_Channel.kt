package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.Channel
import com.giby78king.merch.Model.Channel.Companion.dbChannelList
import com.giby78king.merch.Model.Channel.Companion.toChannel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_Channel {

    private val db = FirebaseFirestore.getInstance().collection("Channel")
    suspend fun getOne(id: String): Channel {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toChannel()
        }
        return Channel(
            imgUrl = "",
            id = "",
            name = "",
            order=0
        )
    }

    suspend fun getDatas(): MutableList<Channel> {
        dbChannelList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toChannel() }.toMutableList()

        return dbChannelList
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