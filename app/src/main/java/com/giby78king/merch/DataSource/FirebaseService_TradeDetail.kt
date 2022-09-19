package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.Trade.Companion.toTrade
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.Model.TradeDetail.Companion.dbTradeDetailList
import com.giby78king.merch.Model.TradeDetail.Companion.toTradeDetail
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_TradeDetail {

    private val db = FirebaseFirestore.getInstance().collection("TradeDetail")
    suspend fun getOne(id: String): TradeDetail {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toTradeDetail()
        }
        return TradeDetail(
            accountDate = "",
            id = "newOne",
            modify = arrayListOf(),
            other = arrayListOf(),
            price = 0,
            processDate = "",
            specification = "",
            stockDate = "",
            tradeId = "",
            )
    }

    suspend fun getDatas(): MutableList<TradeDetail> {
        dbTradeDetailList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toTradeDetail() }.toMutableList()

        return dbTradeDetailList
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