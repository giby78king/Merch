package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class StockDepository(
    val favorite:Boolean,
    val group: Array<String>,
    val holdingCost: ArrayList<Int>,
    val id: String,
    val imgUrl: String,
    val member: Array<String>,
    val message: Array<String>,
    val print: Boolean,
    val specification: String,
    val tradeDetailId: Array<String>,
    val profit: Int,
    val sign: Array<String>,
    val valuation: Int,
) {
    companion object {
        var dbStockDepositoryList = mutableListOf<StockDepository>()

        fun DocumentSnapshot.toStockDepository(): StockDepository {

            val favorite = getBoolean("favorite")!!
            val group =
                get("group").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()

            val holdingCostA =
                get("holdingCost").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val holdingCost = arrayListOf<Int>()
            for (i in holdingCostA.indices) {
                if(holdingCostA[i]!="") {
                    holdingCost.add(holdingCostA[i].toInt())
                }
            }

            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!
            val member = get("member").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val message = get("message").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val print = getBoolean("print")!!
            val specification = getString("specification")!!
            val tradeDetailId = get("tradeDetailId").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val profit = getLong("profit")?.toInt()!!
            val sign = get("sign").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val valuation = getLong("valuation")?.toInt()!!

            return StockDepository(
                favorite,
                group,
                holdingCost,
                id,
                imgUrl,
                member,
                message,
                print,
                specification,
                tradeDetailId,
                profit,
                sign,
                valuation,
            )
        }
    }
}

