package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class Member(
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
    companion object {
        var MemberList = mutableListOf<Member>()
        var SelectGroupList = mutableListOf<String>()
        var ddlMemberList = mutableListOf<DdlNormalModel>()
        var selectedMemberList = mutableListOf<Member>()

        fun DocumentSnapshot.toMember(): Member {

            val birthday = getString("birthday")!!
            val employed = getBoolean("employed")!!
            val firstBoard = getString("firstBoard")!!
            val group = getString("group")!!
            val height = getLong("height")?.toInt()!!
            val icon = getString("icon")!!
            val id = getString("id")!!
            val ig = getString("ig")!!
            val name = getString("name")!!
            val number = getString("number")!!
            val sex = getString("sex")!!
            val weight = getLong("weight")?.toInt()!!

            return Member(
                birthday,
                employed,
                firstBoard,
                group,
                height,
                icon,
                id,
                ig,
                name,
                number,
                sex,
                weight,
            )
        }
    }
}

