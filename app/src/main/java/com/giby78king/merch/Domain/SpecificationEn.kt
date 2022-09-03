package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_Specification

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
    suspend fun <T> updateOne(data: T) {
        data as SpecificationEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["id"] = data.id
        dbData["imgUrl"] = data.imgUrl
        dbData["limit"] = data.limit
        dbData["member"] = data.member
        dbData["order"] = data.order
        dbData["price"] = data.price
        dbData["product"] = data.product
        dbData["specificationType"] = data.specificationType
        dbData["title"] = data.title
        FirebaseService_Specification.updateData(id, dbData)
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