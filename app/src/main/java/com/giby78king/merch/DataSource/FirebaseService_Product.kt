package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.Product
import com.giby78king.merch.Model.Product.Companion.dbProductList
import com.giby78king.merch.Model.Product.Companion.toProduct
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_Product {

    private val db = FirebaseFirestore.getInstance().collection("Product")
    suspend fun getOne(id: String): Product {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toProduct()
        }
        return Product(
            channelDetail = arrayOf(),
            id = "newOne",
            limit = arrayOf(),
            member = arrayOf(),
            name = "",
            onSell = false,
            preOrder = false,
            price = arrayOf(),
            publish = "",
        )
    }

    suspend fun getDatas(): MutableList<Product> {
        dbProductList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toProduct() }
            .toMutableList()

        return dbProductList
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