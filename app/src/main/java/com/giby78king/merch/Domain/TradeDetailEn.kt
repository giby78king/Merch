package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_TradeDetail
import com.giby78king.merch.Model.TradeDetail

data class TradeDetailEn(
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
        suspend fun getOne(id: String): TradeDetailEn {
            val data = FirebaseService_TradeDetail.getOne(id)
            val en: TradeDetailEn
            en = TradeDetailEn(
                accountDate = data.accountDate,
                id = data.id,
                modify = data.modify,
                other = data.other,
                price = data.price,
                processDate = data.processDate,
                specification = data.specification,
                stockDate = data.stockDate,
                tradeId = data.tradeId,
            )
            return en
        }
    }
    suspend fun <T> updateOne(data: T) {
        data as TradeDetailEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["accountDate"] = data.accountDate
        dbData["id"] = data.id
        dbData["modify"] = data.modify
        dbData["other"] = data.other
        dbData["price"] = data.price
        dbData["processDate"] = data.processDate
        dbData["specification"] = data.specification
        dbData["stockDate"] = data.stockDate
        dbData["tradeId"] = data.tradeId
        FirebaseService_TradeDetail.updateData(id, dbData)
    }

    suspend fun save() {
        val data = FirebaseService_TradeDetail.getOne(id)
        val dbData = hashMapOf(
            "accountDate" to accountDate,
            "id" to id,
            "modify" to modify.toCollection(ArrayList()),
            "other" to other.toCollection(ArrayList()),
            "price" to price,
            "processDate" to processDate,
            "specification" to specification,
            "stockDate" to stockDate,
            "tradeId" to tradeId,
        )
        if (data.id == "newOne") {
            FirebaseService_TradeDetail.insertData(id, dbData)
        } else {
            FirebaseService_TradeDetail.updateData(id, dbData)
        }
    }

    fun delete() {
        FirebaseService_TradeDetail.deleteData(id)
    }
}