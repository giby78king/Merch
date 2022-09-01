package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.Group
import com.giby78king.merch.Model.Group.Companion.dbGroupList
import com.giby78king.merch.Model.Group.Companion.toGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_Group {

    private val db = FirebaseFirestore.getInstance().collection("Group")
    suspend fun getOne(id: String): Group {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toGroup()
        }
        return Group(
            id = "",
            imgUrl = "",
            league = arrayOf(),
            name = ""
        )
    }

    suspend fun getDatas(): MutableList<Group> {
        dbGroupList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toGroup() }.toMutableList()

        return dbGroupList
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