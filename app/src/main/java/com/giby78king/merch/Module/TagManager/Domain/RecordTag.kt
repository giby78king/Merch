//package com.finance.firstmile.Module.TagManager.Domain
//
//import com.finance.firstmile.Model.TimeFormat
//import com.finance.firstmile.Service.FirebaseService_RecordTagManager
//import java.util.*
//
//data class RecordTag(
//    override val ID: String,
//    override val Count: Int,
//    override val TagText: String,
//    val LastClass: Array<String>,
//    val LastMST: Array<String>,
//    val LastTime: String,
//) : TagType() {
//    override suspend fun <T> updateOne(data: T) {
//        data as RecordTag
//        val dbData: HashMap<String, Any> = hashMapOf()
//        dbData["ID"] = data.ID
//        dbData["Count"] = data.Count
//        dbData["TagText"] = data.TagText
//        dbData["LastClass"] = data.LastClass
//        dbData["LastMST"] = data.LastMST
//        dbData["LastTime"] = data.LastTime
//        FirebaseService_RecordTagManager.updateData(ID, dbData)
//    }
//
//    override suspend fun save() {
//        val data = FirebaseService_RecordTagManager.getOne(ID)
//        val lastTime = TimeFormat().TodayDate().yyyyMMddHHmmss()
//        if (data.ID == "newOne") {
//            val dbData = hashMapOf(
//                "ID" to ID,
//                "Count" to 1,
//                "TagText" to TagText,
//                "LastClass" to LastClass.toCollection(java.util.ArrayList()),
//                "LastMST" to LastMST.toCollection(java.util.ArrayList()),
//                "LastTime" to lastTime,
//            )
//            FirebaseService_RecordTagManager.insertData(ID, dbData)
//        } else {
//            val newLastClass: MutableList<String> = java.util.ArrayList()
//            val newLastMST: MutableList<String> = java.util.ArrayList()
//
//            val chooseClassDetail = LastClass[0]
//            val chooseMST = LastMST[0]
//
//            if (data.LastClass.size > 2) {
//                if (data.LastClass.contains(chooseClassDetail)) {
//
//                    val indexNowClassDetail =
//                        data.LastClass.lastIndexOf(chooseClassDetail)
//                    for (i in 0..1) {
//                        newLastClass.add(data.LastClass[i])
//                    }
//                    newLastClass.drop(indexNowClassDetail)
//                    newLastClass.add(0, chooseClassDetail)
//
//                    val indexNowMST =
//                        data.LastMST.lastIndexOf(chooseMST)
//                    for (i in 0..1) {
//                        newLastMST.add(data.LastMST[i])
//                    }
//                    newLastMST.drop(indexNowMST)
//                    newLastMST.add(0, chooseMST)
//                } else {
//
//                    newLastClass.add(chooseClassDetail)
//                    for (i in 0..1) {
//                        newLastClass.add(data.LastClass[i])
//                    }
//                    newLastMST.add(chooseMST)
//                    for (i in 0..1) {
//                        newLastMST.add(data.LastMST[i])
//                    }
//                }
//            } else {
//                data.LastClass.forEach { it ->
//                    newLastClass.add(it)
//                }
//                newLastClass.add(0, chooseClassDetail)
//
//                data.LastMST.forEach { it ->
//                    newLastMST.add(it)
//                }
//                newLastMST.add(0, chooseMST)
//            }
//
//            val dbData = hashMapOf(
//                "ID" to ID,
//                "Count" to Count,
//                "TagText" to TagText,
//                "LastClass" to newLastClass.toCollection(java.util.ArrayList()),
//                "LastMST" to newLastMST.toCollection(java.util.ArrayList()),
//                "LastTime" to lastTime,
//            )
//            FirebaseService_RecordTagManager.insertData(ID, dbData)
//        }
//    }
//}