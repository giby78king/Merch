package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.Member
import com.giby78king.merch.Model.Member.Companion.MemberList
import com.giby78king.merch.Model.Member.Companion.toMember
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_Member {

    private val db = FirebaseFirestore.getInstance().collection("Member")
    suspend fun getOne(id: String): Member {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toMember()
        }
        return Member(
            birthday = "",
            employed = true,
            group = "",
            firstBoard = "",
            height = 0,
            icon = "",
            id = "newOne",
            ig = "",
            name = "",
            number = "",
            sex = "",
            weight = 0,
        )
    }

    suspend fun getDatas(): MutableList<Member> {
        MemberList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toMember() }.sortedBy { it.number }.toMutableList()

        return MemberList
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