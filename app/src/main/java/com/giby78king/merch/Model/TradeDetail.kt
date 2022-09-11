package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

class tempPriceDetail(
    var price: Int,
    val rule: String,
)

data class TradeDetail(
    val accountDate: String,
    val channelDetail: String,
    val id: String,
    val modify: ArrayList<Int>,
    val modifyRule: Array<String>,
    val other: ArrayList<Int>,
    val otherRule: Array<String>,
    val price: Int,
    val processDate: String,
    val specification: String,
    val stockDate: String,
    val tradeId: String,
    val transType: String,
) {
    companion object {
        var dbTradeDetailList = mutableListOf<TradeDetail>()

        var tempSpecList = mutableListOf<TradeDetail>()
        var specModifyList = mutableListOf<tempPriceDetail>()
        var specOtherList = mutableListOf<tempPriceDetail>()
        var selectedTradeDetailSpcification = ""

        fun DocumentSnapshot.toTradeDetail(): TradeDetail {

            val accountDate = getString("accountDate")!!
            val channelDetail = getString("channelDetail")!!
            val id = getString("id")!!

            val modifyA =
                get("modify").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val modify = arrayListOf<Int>()
            for (i in modifyA.indices) {
                modify.add(modifyA[i].toInt())
            }

            val modifyRule =
                get("modifyRule").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()

            val otherA =
                get("other").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val other = arrayListOf<Int>()
            for (i in otherA.indices) {
                other.add(otherA[i].toInt())
            }

            val otherRule =
                get("otherRule").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val price = getLong("price")?.toInt()!!
            val processDate = getString("processDate")!!
            val specification = getString("specification")!!
            val stockDate = getString("stockDate")!!
            val tradeId = getString("tradeId")!!
            val transType = getString("transType")!!

            return TradeDetail(
                accountDate,
                channelDetail,
                id,
                modify,
                modifyRule,
                other,
                otherRule,
                price,
                processDate,
                specification,
                stockDate,
                tradeId,
                transType,
            )
        }
    }
}

