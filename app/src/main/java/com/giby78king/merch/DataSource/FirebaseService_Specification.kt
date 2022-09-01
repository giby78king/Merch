package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.Product
import com.giby78king.merch.Model.Product.Companion.dbProductList
import com.giby78king.merch.Model.Product.Companion.toProduct
import com.giby78king.merch.Model.Specification
import com.giby78king.merch.Model.Specification.Companion.dbSpecificationList
import com.giby78king.merch.Model.Specification.Companion.toSpecification
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_Specification {

    private val db = FirebaseFirestore.getInstance().collection("Specification")
    suspend fun getOne(id: String): Specification {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toSpecification()
        }
        return Specification(
            id = "newOne",
            imgUrl = "",
            limit = arrayOf(),
            member = arrayOf(),
            order = 0,
            price = arrayOf(),
            product = "",
            specificationId = "",
            specificationType = "",
            title = "",
        )
    }

    suspend fun getDatas(): MutableList<Specification> {
        dbSpecificationList = db
//            .orderBy("number", Query.Direction.DESCENDING)
            .get()
            .await()
            .documents.mapNotNull { it.toSpecification() }
            .toMutableList()

        return dbSpecificationList
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