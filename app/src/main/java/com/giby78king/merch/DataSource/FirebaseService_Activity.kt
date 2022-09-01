package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.Activity
import com.giby78king.merch.Model.Activity.Companion.dbActivityList
import com.giby78king.merch.Model.Activity.Companion.toActivity
import com.giby78king.merch.Model.ChannelDetail
import com.giby78king.merch.Model.ChannelDetail.Companion.dbChannelDetailList
import com.giby78king.merch.Model.ChannelDetail.Companion.toChannelDetail
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_Activity {

    private val db = FirebaseFirestore.getInstance().collection("Activity")
    suspend fun getOne(id: String): Activity {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toActivity()
        }
        return Activity(
            channelDetail = arrayOf(),
            endDate = "",
            id = "newOne",
            imgUrl = "",
            name = "",
            startDate = "",
        )
    }

    suspend fun getDatas(): MutableList<Activity> {
        dbActivityList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toActivity() }.toMutableList()

        return dbActivityList
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