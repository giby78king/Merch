package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class Group(
    val id: String,
    var imgUrl: String,
    var league: Array<String>,
    val name: String,
) {

    companion object {

        var dbGroupList = mutableListOf<Group>()
        var ddlGroupList = java.util.ArrayList<DdlNormalModel>()
        var productGroupList = mutableListOf<String>()

        fun DocumentSnapshot.toGroup(): Group {

            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!
            val league = get("league").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val name = getString("name")!!

            return Group(
                id,
                imgUrl,
                league,
                name,
            )
        }
    }
}