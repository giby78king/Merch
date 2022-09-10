package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

class ProductDetail(
    var count: Int,
    var limit: Int,
    var member: String,
    var price: Int,
)

class OrderProductDetail(
    var count: Int,
    var limit: Int,
    var member: String,
    var price: Int,
    var number: String,
    var group: String
)

data class Product(
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
    companion object {
        var dbProductList = mutableListOf<Product>()
        var ddlProductList = arrayListOf<DdlNormalModel>()
        fun DocumentSnapshot.toProduct(): Product {

            val activity = getString("activity")!!
            val channelDetail =
                get("channelDetail").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val group =
                get("group").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!
            val name = getString("name")!!
            val onSell = getBoolean("onSell")!!
            val preOrder = getBoolean("preOrder")!!
            val productType = getString("productType")!!
            val publish = getString("publish")!!
            val specification =
                get("specification").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()

            return Product(
                activity,
                channelDetail,
                group,
                id,
                imgUrl,
                name,
                onSell,
                preOrder,
                productType,
                publish,
                specification,
            )
        }
    }
}

