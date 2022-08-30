package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_Product

data class ProductEn(
    val channelDetail: String,
    val group: Array<String?>,
    val id: String,
    val limit: Array<Int?>,
    val member: Array<String?>,
    val name: String,
    val onSell: Boolean,
    val preOrder: Boolean,
    val price: Array<Int?>,
    val publish: String,
) {
    suspend fun <T> updateOne(data: T) {
        data as ProductEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["channelDetail"] = data.channelDetail
        dbData["group"] = data.group
        dbData["id"] = data.id
        dbData["limit"] = data.limit
        dbData["member"] = data.member
        dbData["name"] = data.name
        dbData["onSell"] = data.onSell
        dbData["preOrder"] = data.preOrder
        dbData["price"] = data.price
        dbData["publish"] = data.publish
        FirebaseService_Product.updateData(id, dbData)
    }

    suspend fun save() {
        val data = FirebaseService_Product.getOne(id)
        if (data.id == "newOne") {
            val dbData = hashMapOf(
                "channelDetail" to channelDetail,
                "group" to group.toCollection(java.util.ArrayList()),
                "id" to id,
                "limit" to limit.toCollection(java.util.ArrayList()),
                "member" to member.toCollection(java.util.ArrayList()),
                "name" to name,
                "onSell" to onSell,
                "preOrder" to preOrder,
                "price" to price.toCollection(java.util.ArrayList()),
                "publish" to publish,
            )
            FirebaseService_Product.insertData(id, dbData)
        } else {
            val dbData = hashMapOf(
                "channelDetail" to channelDetail,
                "group" to group.toCollection(java.util.ArrayList()),
                "id" to id,
                "limit" to limit.toCollection(java.util.ArrayList()),
                "member" to member.toCollection(java.util.ArrayList()),
                "name" to name,
                "onSell" to onSell,
                "preOrder" to preOrder,
                "price" to price.toCollection(java.util.ArrayList()),
                "publish" to publish,
            )
            FirebaseService_Product.updateData(id, dbData)
        }
    }

    fun delete() {
        FirebaseService_Product.deleteData(id)
    }
}