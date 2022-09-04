package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_Specification
import com.giby78king.merch.Model.Specification
import com.giby78king.merch.TimeFormat

data class SpecificationEn(
    val id: String,
    val imgUrl: String,
    val limit: Array<Int?>,
    val member: Array<String>,
    val order: Int,
    val price: Array<Int?>,
    val product: String,
    val specificationType: String,
    val title: String,
) {
    companion object {
        suspend fun getOne(id: String): SpecificationEn {
            val data = FirebaseService_Specification.getOne(id)
            val en = SpecificationEn(
                id = data.id,
                imgUrl = data.imgUrl,
                limit = data.limit.toTypedArray(),
                member = data.member,
                order = data.order,
                price = data.price.toTypedArray(),
                product = data.product,
                specificationType = data.specificationType,
                title = data.title,
            )
            return en
        }
    }

    suspend fun save() {
        val data = FirebaseService_Specification.getOne(id)
        val dbData = hashMapOf(
            "id" to id,
            "imgUrl" to imgUrl,
            "limit" to limit.toCollection(java.util.ArrayList()),
            "member" to member.toCollection(java.util.ArrayList()),
            "order" to order,
            "price" to price.toCollection(java.util.ArrayList()),
            "product" to product,
            "specificationType" to specificationType,
            "title" to title,
        )
        if (data.id == "newOne") {
            FirebaseService_Specification.insertData(id, dbData)
        } else {
            FirebaseService_Specification.updateData(id, dbData)
        }
    }

    fun delete() {
        FirebaseService_Specification.deleteData(id)
    }
}