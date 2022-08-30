package com.giby78king.merch.Domain

import com.giby78king.merch.DataSource.FirebaseService_Member
import com.giby78king.merch.TimeFormat

data class MemberEn(
    val birthday: String,
    val employed: Boolean,
    val firstBoard: String,
    val group: String,
    val height: Int,
    val icon: String,
    val id: String,
    val ig: String,
    val name: String,
    val number: String,
    val sex: String,
    val weight: Int,
) {
    suspend fun <T> updateOne(data: T) {
        data as MemberEn
        val dbData: HashMap<String, Any> = hashMapOf()
        dbData["birthday"] = data.birthday
        dbData["employed"] = data.employed
        dbData["firstBoard"] = data.firstBoard
        dbData["group"] = data.group
        dbData["icon"] = data.icon
        dbData["height"] = data.height
        dbData["id"] = data.id
        dbData["ig"] = data.ig
        dbData["name"] = data.name
        dbData["number"] = data.number
        dbData["sex"] = data.sex
        dbData["weight"] = data.weight
        FirebaseService_Member.updateData(id, dbData)
    }

    suspend fun save() {
        val data = FirebaseService_Member.getOne(id)
        if (data.id == "newOne") {
            val dbData = hashMapOf(
                "birthday" to birthday,
                "employed" to employed,
                "firstBoard" to firstBoard,
                "group" to group,
                "height" to height,
                "icon" to icon,
                "id" to id,
                "ig" to ig,
                "name" to name,
                "number" to number,
                "sex" to sex,
                "weight" to weight,
            )
            FirebaseService_Member.insertData(id, dbData)
        } else {
            val dbData = hashMapOf(
                "birthday" to birthday,
                "employed" to employed,
                "firstBoard" to firstBoard,
                "group" to group,
                "height" to height,
                "icon" to icon,
                "id" to id,
                "ig" to ig,
                "name" to name,
                "number" to number,
                "sex" to sex,
                "weight" to weight,
            )
            FirebaseService_Member.updateData(id, dbData)
        }
    }

    fun delete() {
        FirebaseService_Member.deleteData(id)
    }
}