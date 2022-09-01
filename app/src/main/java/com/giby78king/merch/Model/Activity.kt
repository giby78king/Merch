package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class Activity(
    val channelDetail: Array<String>,
    val endDate: String,
    var id: String,
    val imgUrl: String,
    val name: String,
    val startDate: String,
) {

    companion object {

        var dbActivityList = mutableListOf<Activity>()
        var ddlActivityList = java.util.ArrayList<DdlNormalModel>()

        fun DocumentSnapshot.toActivity(): Activity {
            val channelDetail = get("channelDetail").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val endDate = if (getString("endDate") == null) "" else getString("endDate")!!
            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!
            val name = getString("name")!!
            val startDate = if (getString("startDate") == null) "" else getString("startDate")!!

            return Activity(
                channelDetail,
                endDate,
                id,
                imgUrl,
                name,
                startDate,
            )
        }
    }
}