package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

class tempPriceDetail(
    var price: Int,
    val rule: String,
)

data class TradeDetail(
    var accountDate: String,
    val id: String,
    var modify: ArrayList<Int>,
    var other: ArrayList<Int>,
    var price: Int,
    var processDate: String,
    var specification: String,
    var stockDate: String,
    val tradeId: String,

) {
    companion object {
        var dbTradeDetailList = mutableListOf<TradeDetail>()

        var tempSpecList = mutableListOf<TradeDetail>()
        var specModifyList = mutableListOf<tempPriceDetail>()
        var specOtherList = mutableListOf<tempPriceDetail>()
        var nowEditId = ""

        fun DocumentSnapshot.toTradeDetail(): TradeDetail {

            val accountDate = getString("accountDate")!!
            val id = getString("id")!!

            val modifyA =
                get("modify").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val modify = arrayListOf<Int>()
            for (i in modifyA.indices) {
                if(modifyA[i]!="") {
                    modify.add(modifyA[i].toInt())
                }
            }
            val otherA =
                get("other").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val other = arrayListOf<Int>()
            for (i in otherA.indices) {
                if(otherA[i]!="") {
                    other.add(otherA[i].toInt())
                }
            }
            val price = getLong("price")?.toInt()!!
            val processDate = getString("processDate")!!
            val specification = getString("specification")!!
            val stockDate = getString("stockDate")!!
            val tradeId = getString("tradeId")!!


            return TradeDetail(
                accountDate,
                id,
                modify,
                other,
                price,
                processDate,
                specification,
                stockDate,
                tradeId,

            )
        }
    }
}

