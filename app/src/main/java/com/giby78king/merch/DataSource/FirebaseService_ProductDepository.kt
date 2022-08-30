package com.giby78king.merch.DataSource

import android.util.Log
import com.giby78king.merch.Model.ProductDepository
import com.giby78king.merch.Model.ProductDepository.Companion.ProductDepositoryList
import com.giby78king.merch.Model.ProductDepository.Companion.toProductDepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_ProductDepository {

    private val db = FirebaseFirestore.getInstance().collection("ProductDepository")
    suspend fun getOne(id: String): ProductDepository {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toProductDepository()
        }
        return ProductDepository(
            channelDetail = "",
            cost = 0,
            date = "",
            favorite = false,
            group = arrayOf(),
            holdingAmount = 0,
            id = "",
            imgUrl = "",
            member = "",
            print = false,
            product = "",
            profit = 0,
            purchaseId = "",
            saleId = "",
            sign = false,
            valuation = 0,
            ym = "",
        )
    }

    suspend fun getDatas(): MutableList<ProductDepository> {
        ProductDepositoryList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toProductDepository() }.sortedBy { it.valuation }
            .toMutableList()

        return ProductDepositoryList
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