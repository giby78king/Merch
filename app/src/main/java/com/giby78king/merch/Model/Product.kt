package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

class ProductDetail(
    var count: Int,
    var group: String,
    var limit: Int,
    var member: String,
    var price: Int,
)

data class Product(
    val channelDetail: String,
    val group: Array<String>,
    val id: String,
    val limit: Array<Int?>,
    val member: Array<String>,
    val name: String,
    val onSell: Boolean,
    val preOrder: Boolean,
    val price: Array<Int?>,
    val publish: String,
) {
    companion object {
        var ProductList = mutableListOf<Product>()
        var productDetailList = mutableListOf<ProductDetail>()
        val copyProductDetailList= mutableListOf<ProductDetail>()
        var nowEditProductId = ""

        fun DocumentSnapshot.toProduct(): Product {

            val channelDetail = getString("channelDetail")!!
            val group = get("group").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val id = getString("id")!!
            val limitA =
                get("limit").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val limit = arrayOfNulls<Int>(size = limitA.size)
            for (i in limitA.indices) {
                limit[i] = limitA[i].toInt()
            }

            val member = get("member").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val name = getString("name")!!
            val onSell = getBoolean("onSell")!!
            val preOrder = getBoolean("preOrder")!!
            val priceA =
                get("price").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val price = arrayOfNulls<Int>(size = priceA.size)
            for (i in priceA.indices) {
                price[i] = priceA[i].toInt()
            }

            val publish = getString("publish")!!


            return Product(
                channelDetail,
                group,
                id,
                limit,
                member,
                name,
                onSell,
                preOrder,
                price,
                publish,
            )
        }
    }
}

