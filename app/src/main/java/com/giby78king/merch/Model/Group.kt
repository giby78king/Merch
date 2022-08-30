package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class Group(
    val chName: String,
    var id: String,
    val name: String,
) {

    companion object {

        var GroupList = mutableListOf<Group>()
        var ddlGroupList = java.util.ArrayList<DdlNormalModel>()

        fun DocumentSnapshot.toGroup(): Group {

            val chName = getString("chName")!!
            val id = getString("id")!!
            val name = getString("name")!!

            return Group(
                chName,
                id,
                name,
            )
        }
    }
}