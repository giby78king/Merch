package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class Specification(
    var id: String,
    val imgUrl: String,
    var limit: ArrayList<Int>,
    var member: Array<String>,
    val order: Int,
    val price: ArrayList<Int>,
    val product: String,
    var specificationType: String,
    var title: String,
) {
    companion object {
        var dbSpecificationList = mutableListOf<Specification>()
        var ddlSpecificationList = arrayListOf<DdlNormalModel>()
        var tempSpecificationList = mutableListOf<Specification>()
        var tempDeleteSpecificationList = mutableListOf<String>()

        fun DocumentSnapshot.toSpecification(): Specification {

            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!

            val limitA =
                get("limit").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val limit = arrayListOf<Int>()
            for (i in limitA.indices) {
                if(limitA[i]!="") {
                    limit.add(limitA[i].toInt())
                }
            }
            val member = get("member").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val order = getLong("order")?.toInt()!!
            val priceA =
                get("price").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val price = arrayListOf<Int>()
            for (i in priceA.indices) {
                if(priceA[i]!="") {
                    price.add(priceA[i].toInt())
                }
            }

            val product = getString("product")!!
            val specificationType = getString("specificationType")!!
            val title = getString("title")!!

            return Specification(
                id,
                imgUrl,
                limit,
                member,
                order,
                price,
                product,
                specificationType,
                title,
            )
        }
    }
}

