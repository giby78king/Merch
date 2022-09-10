package com.giby78king.merch.DataSource

import com.giby78king.merch.Model.Trade
import com.giby78king.merch.Model.Trade.Companion.dbTradeList
import com.giby78king.merch.Model.Trade.Companion.toTrade
import com.giby78king.merch.Model.TradeRule
import com.giby78king.merch.Model.TradeRule.Companion.dbTradeRuleList
import com.giby78king.merch.Model.TradeRule.Companion.toTradeRule
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseService_TradeRule {

    private val db = FirebaseFirestore.getInstance().collection("TradeRule")
    suspend fun getOne(id: String): TradeRule {
        val data = db.document(id)
            .get()
            .await()
        if (data.getString("id") != null) {
            return data.toTradeRule()
        }
        return TradeRule(
            channelDetail = arrayOf(),
            default = true,
            id = "newOne",
            imgUrl = "",
            name = "",
            transType = "",
            type ="",
        )
    }

    suspend fun getDatas(): MutableList<TradeRule> {
        dbTradeRuleList = db
            .get()
            .await()
            .documents.mapNotNull { it.toTradeRule() }.toMutableList()

        return dbTradeRuleList
    }

    fun insertData(id: String, data: Map<String, Any>) {
        db.document(id)
            .set(data).addOnSuccessListener {
            }
    }

    fun updateData(id: String, data: Map<String, Any>) {

        db.document(id)
            .update(data).addOnSuccessListener {
            }
    }

    fun deleteData(id: String) {
        db.document(id)
            .delete()
            .addOnSuccessListener {
            }
    }

}