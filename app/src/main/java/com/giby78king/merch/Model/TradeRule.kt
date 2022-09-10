package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class TradeRule(
    val channelDetail: Array<String>,
    val default: Boolean,
    val id: String,
    val imgUrl: String,
    val name: String,
    val transType: String,
    val type: String,
) {
    companion object {
        var dbTradeRuleList = mutableListOf<TradeRule>()

        fun DocumentSnapshot.toTradeRule(): TradeRule {
            val channelDetail =
                get("channelDetail").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val default = getBoolean("default")!!
            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!
            val name = getString("name")!!
            val transType = getString("transType")!!
            val type = getString("type")!!

            return TradeRule(
                channelDetail,
                default,
                id,
                imgUrl,
                name,
                transType,
                type
            )
        }
    }
}

