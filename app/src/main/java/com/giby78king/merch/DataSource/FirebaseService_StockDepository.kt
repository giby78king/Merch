package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.StockDepository
import com.giby78king.merch.Model.StockDepository.Companion.dbStockDepositoryList
import com.giby78king.merch.Model.StockDepository.Companion.toStockDepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_StockDepository {

    private val db = FirebaseFirestore.getInstance().collection("StockDepository")
    suspend fun getOne(id: String): StockDepository {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toStockDepository()
        }
        return StockDepository(
            favorite = false,
            group = arrayOf(),
            holdingCost = arrayListOf(),
            id = "newOne",
            imgUrl = "",
            member = arrayOf(),
            message = arrayOf(),
            print = false,
            profit = 0,
            specification = "",
            tradeDetailId = arrayOf(),
            sign = arrayOf(),
            valuation = 0,
        )
    }

    suspend fun getDatas(): MutableList<StockDepository> {
        dbStockDepositoryList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toStockDepository() }.sortedBy { it.valuation }
            .toMutableList()

        return dbStockDepositoryList
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