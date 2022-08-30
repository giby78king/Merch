package com.giby78king.merch.Model

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot

class TradeInner(
    val amount: Int,
    val holdingAmount: Int,
    val holdingMember: String,
    val imgUrl: String,
    val product: String,
)

data class Trade(
    val amount: Array<Int?>,
    val channelDetail: String,
    val date: String,
    val group: Array<String>,
    val holdingAmount: Array<Int?>,
    val holdingMember: Array<String>,
    val id: String,
    val imgUrl: Array<String>,
    val product: Array<String>,
    val rules: Array<String>,
    val status: String,
    val ym: String,
) {
    companion object {
        var TradeList = mutableListOf<Trade>()

        fun DocumentSnapshot.toTrade(): Trade {

            val amountA = get("amount").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()

            var amount = arrayOfNulls<Int>(size = amountA.size)
            for (i in amountA.indices) {
                amount[i] = amountA[i].toInt()
            }

            val channelDetail = getString("channelDetail")!!

            val date = getString("date")!!
            val group = get("group").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val holdingAmountA =
                get("holdingAmount").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val holdingAmount = arrayOfNulls<Int>(size = holdingAmountA.size)
            for (i in holdingAmountA.indices) {
                holdingAmount[i] = holdingAmountA[i].toInt()
            }

            val holdingMember =
                get("holdingMember").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val id = getString("id")!!
            val imgUrl = get("imgUrl").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val product = get("product").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val rules = get("rules").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val status = getString("status")!!
            val ym = getString("ym")!!

            return Trade(
                amount,
                channelDetail,
                date,
                group,
                holdingAmount,
                holdingMember,
                id,
                imgUrl,
                product,
                rules,
                status,
                ym,
            )
        }
    }
}

