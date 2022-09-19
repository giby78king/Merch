package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_StockDepository

data class StockDepositoryEn(
    val favorite: Boolean,
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
    suspend fun <T> updateOne(data: T) {
        data as StockDepositoryEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["favorite"] = data.favorite
        dbData["group"] = data.group
        dbData["holdingCost"] = data.holdingCost
        dbData["id"] = data.id
        dbData["imgUrl"] = data.imgUrl
        dbData["member"] = data.member
        dbData["message"] = data.message
        dbData["print"] = data.print
        dbData["specification"] = data.specification
        dbData["tradeDetailId"] = data.tradeDetailId
        dbData["profit"] = data.profit
        dbData["sign"] = data.sign
        dbData["valuation"] = data.valuation
        FirebaseService_StockDepository.updateData(id, dbData)
    }

    suspend fun save() {
        val data = FirebaseService_StockDepository.getOne(id)
        val dbData = hashMapOf(
            "favorite" to favorite,
            "group" to group.toCollection(ArrayList()),
            "holdingCost" to holdingCost.toCollection(ArrayList()),
            "id" to id,
            "imgUrl" to imgUrl,
            "member" to member.toCollection(ArrayList()),
            "message" to message.toCollection(ArrayList()),
            "print" to print,
            "specification" to specification,
            "tradeDetailId" to tradeDetailId.toCollection(ArrayList()),
            "profit" to profit,
            "sign" to sign.toCollection(ArrayList()),
            "valuation" to valuation,
        )
        if (data.id == "newOne") {
            FirebaseService_StockDepository.insertData(id, dbData)
        } else {
            FirebaseService_StockDepository.updateData(id, dbData)
        }
    }

    fun delete() {
        FirebaseService_StockDepository.deleteData(id)
    }
}