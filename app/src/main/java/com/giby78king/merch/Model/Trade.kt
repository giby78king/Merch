package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class Trade(
    val channelDetail: String,
    val date: String,
    val id: String,
    val modifyRule: Array<String>,
    val otherRule: Array<String>,
    val tradeDetail: Array<String>,
    val transType: String,
    val ym: String,
) {
    companion object {
        var dbTradeList = mutableListOf<Trade>()
        var ddlTransType = java.util.ArrayList<DdlNormalModel>()

        var ddlModifyList = ArrayList<DdlNormalModel>()
        var tradeModifyList = mutableListOf<String>()

        var ddlOtherList = ArrayList<DdlNormalModel>()
        var tradeOtherList = mutableListOf<String>()

        fun DocumentSnapshot.toTrade(): Trade {
            val channelDetail = getString("channelDetail")!!
            val date = getString("date")!!
            val id = getString("id")!!
            val modifyRule =
                get("modifyRule").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val otherRule =
                get("otherRule").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val tradeDetail =
                get("tradeDetail").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val transType = getString("transType")!!
            val ym = getString("ym")!!

            return Trade(
                channelDetail,
                date,
                id,
                modifyRule,
                otherRule,
                tradeDetail,
                transType,
                ym,
            )
        }
    }
}

