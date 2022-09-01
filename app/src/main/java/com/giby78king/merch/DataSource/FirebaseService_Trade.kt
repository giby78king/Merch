package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.Trade
import com.giby78king.merch.Model.Trade.Companion.dbTradeList
import com.giby78king.merch.Model.Trade.Companion.toTrade
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_Trade {

    private val db = FirebaseFirestore.getInstance().collection("Trade")
    suspend fun getOne(id: String): Trade {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toTrade()
        }
        return Trade(
            amount = arrayOf(),
            channelDetail = "",
            date = "",
            group = arrayOf(),
            holdingAmount = arrayOf(),
            holdingMember = arrayOf(),
            id = "",
            imgUrl = arrayOf(),
            product = arrayOf(),
            rules = arrayOf(),
            status = "",
            ym = "",
        )
    }

    suspend fun getDatas(): MutableList<Trade> {
        dbTradeList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toTrade() }.toMutableList()

        return dbTradeList
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