//package com.giby78king.merch.Module.TagManager.Domain
//
//import com.finance.firstmile.Module.Record.Interface.DefaultAble
//import com.finance.firstmile.Module.TagManager.Domain.RecordTag
//import com.finance.firstmile.Service.FirebaseService_RecordTagManager
//import com.giby78king.merch.Module.Record.Interface.DefaultAble
//import java.util.*
//
//abstract class TagType(
//) : DefaultAble {
//    abstract val ID: String
//    abstract val TagText: String
//    abstract val Count: Int
//
//    companion object {
//        suspend fun <T> create(data: T) {
//            if (data is RecordTag) {
//                val enRecordTag = RecordTag(
//                    Count = data.Count,
//                    ID = data.ID,
//                    TagText = data.TagText,
//                    LastClass = data.LastClass,
//                    LastMST = data.LastMST,
//                    LastTime = "",
//                )
//                enRecordTag.save()
//            }
//        }
//        suspend fun getOne(id: String,type: String): TagType {
//            val data = FirebaseService_RecordTagManager.getOne(id)
//            val tagType: TagType
//            when (type) {
//                "Record" -> {
//                    tagType = RecordTag(
//                        Count = data.Count,
//                        ID = data.ID,
//                        LastClass = data.LastClass,
//                        LastMST = data.LastMST,
//                        LastTime = data.LastTime,
//                        TagText = data.TagText,
//                    )
//                }
//                else -> {
//                    tagType = RecordTag(
//                        Count = data.Count,
//                        ID = data.ID,
//                        LastClass = data.LastClass,
//                        LastMST = data.LastMST,
//                        LastTime = data.LastTime,
//                        TagText = data.TagText,
//                    )
//                }
//            }
//            return tagType
//        }
//    }
//
//    open suspend fun <T> updateOne(data: T) {
//        val dbData: HashMap<String, Any> = hashMapOf()
//        if (data is TagType) {
//            dbData["ID"] = data.ID
//            dbData["Count"] = data.Count
//            dbData["TagText"] = data.TagText
//        }
//        FirebaseService_RecordTagManager.updateData(ID, dbData)
//    }
//}
