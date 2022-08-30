package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class ProductDepository(
    val channelDetail: String,
    val cost: Int,
    val date: String,
    val favorite:Boolean,
    val group: Array<String>,
    val holdingAmount: Int,
    val id: String,
    val imgUrl: String,
    val member: String,
    val print: Boolean,
    val product: String,
    val profit: Int,
    val purchaseId: String,
    val saleId: String,
    val sign: Boolean,
    val valuation: Int,
    val ym: String,
) {
    companion object {
        var ProductDepositoryList = mutableListOf<ProductDepository>()

        fun DocumentSnapshot.toProductDepository(): ProductDepository {

            val channelDetail = getString("channelDetail")!!
            val cost = getLong("cost")?.toInt()!!
            val date = getString("date")!!
            val favorite = getBoolean("favorite")!!
            val group = get("group").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val holdingAmount = getLong("holdingAmount")?.toInt()!!
            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!
            val member = getString("member")!!
            val print = getBoolean("print")!!
            val product = getString("product")!!
            val profit = getLong("profit")?.toInt()!!
            val purchaseId = getString("purchaseId")!!
            val saleId = getString("saleId")!!
            val sign = getBoolean("sign")!!
            val valuation = getLong("valuation")?.toInt()!!
            val ym = getString("ym")!!

            return ProductDepository(
                channelDetail,
                cost,
                date,
                favorite,
                group,
                holdingAmount,
                id,
                imgUrl,
                member,
                print,
                product,
                profit,
                purchaseId,
                saleId,
                sign,
                valuation,
                ym,
            )
        }
    }
}

