package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.Channel
import com.giby78king.merch.Model.Channel.Companion.ChannelList
import com.giby78king.merch.Model.Channel.Companion.toChannel
import com.giby78king.merch.Model.Group
import com.giby78king.merch.Model.Group.Companion.GroupList
import com.giby78king.merch.Model.Group.Companion.toGroup
import com.giby78king.merch.Model.Member.Companion.MemberList
import com.giby78king.merch.Model.Member.Companion.toMember
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
            icon = "",
            id = "",
            name = "",
            order=0
        )
    }

    suspend fun getDatas(): MutableList<Channel> {
        ChannelList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toChannel() }.toMutableList()

        return ChannelList
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