package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_Product
import com.giby78king.merch.DataSource.FirebaseService_Trade

data class TradeEn(
    val channelDetail: String,
    val date: String,
    val id: String,
    val modifyRule: Array<String>,
    val otherRule: Array<String>,
    val tradeDetail: Array<String>,
    val transType: String,
    val ym: String,
) {
    suspend fun <T> updateOne(data: T) {
        data as TradeEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["channelDetail"] = data.channelDetail
        dbData["date"] = data.date
        dbData["id"] = data.id
        dbData["modifyRule"] = data.modifyRule
        dbData["otherRule"] = data.otherRule
        dbData["tradeDetail"] = data.tradeDetail
        dbData["transType"] = data.transType
        dbData["ym"] = data.ym
        FirebaseService_Trade.updateData(id, dbData)
    }

    suspend fun save() {
        val data = FirebaseService_Trade.getOne(id)
        val dbData = hashMapOf(
            "channelDetail" to channelDetail,
            "date" to date,
            "id" to id,
            "modifyRule" to modifyRule.toCollection(ArrayList()),
            "otherRule" to otherRule.toCollection(ArrayList()),
            "tradeDetail" to tradeDetail.toCollection(ArrayList()),
            "transType" to transType,
            "ym" to ym,
        )
        if (data.id == "newOne") {
            FirebaseService_Trade.insertData(id, dbData)
        } else {
            FirebaseService_Trade.updateData(id, dbData)
        }
    }

    fun delete() {
        FirebaseService_Trade.deleteData(id)
    }
}