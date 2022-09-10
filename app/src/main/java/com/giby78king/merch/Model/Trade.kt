package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class Trade(
    val date: String,
    val id: String,
    val tradeDetail: Array<String>,
    val transType: String,
    val ym: String,
) {
    companion object {
        var dbTradeList = mutableListOf<Trade>()

        var ddlModifyList = ArrayList<DdlNormalModel>()
        var tradeModifyList = mutableListOf<String>()

        var ddlOtherList = ArrayList<DdlNormalModel>()
        var tradeOtherList = mutableListOf<String>()

        fun DocumentSnapshot.toTrade(): Trade {

            val date = getString("date")!!
            val id = getString("id")!!
            val tradeDetail =
                get("tradeDetail").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val transType = getString("transType")!!
            val ym = getString("ym")!!

            return Trade(
                date,
                id,
                tradeDetail,
                transType,
                ym,
            )
        }
    }
}

