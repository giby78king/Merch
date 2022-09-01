package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_Product

data class ProductEn(
    val activity: String,
    val channelDetail: Array<String>,
    val group: Array<String>,
    val id: String,
    val imgUrl: String,
    val name: String,
    val onSell: Boolean,
    val preOrder: Boolean,
    val productType: String,
    val publish: String,
    val specification: Array<String>,
) {
    suspend fun <T> updateOne(data: T) {
        data as ProductEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["activity"] = data.activity
        dbData["channelDetail"] = data.channelDetail
        dbData["group"] = data.group
        dbData["id"] = data.id
        dbData["imgUrl"] = data.imgUrl
        dbData["name"] = data.name
        dbData["onSell"] = data.onSell
        dbData["preOrder"] = data.preOrder
        dbData["productType"] = data.productType
        dbData["publish"] = data.publish
        dbData["specification"] = data.specification
        FirebaseService_Product.updateData(id, dbData)
    }

    suspend fun save() {
        val data = FirebaseService_Product.getOne(id)
        val dbData = hashMapOf(
            "activity" to activity,
            "channelDetail" to channelDetail.toCollection(java.util.ArrayList()),
            "group" to group.toCollection(java.util.ArrayList()),
            "id" to id,
            "imgUrl" to imgUrl,
            "name" to name,
            "onSell" to onSell,
            "preOrder" to preOrder,
            "productType" to productType,
            "publish" to publish,
            "specification" to specification.toCollection(java.util.ArrayList()),
        )
        if (data.id == "newOne") {
            FirebaseService_Product.insertData(id, dbData)
        } else {
            FirebaseService_Product.updateData(id, dbData)
        }
    }

    fun delete() {
        FirebaseService_Product.deleteData(id)
    }
}