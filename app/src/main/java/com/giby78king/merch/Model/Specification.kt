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
    val specificationType: String,
    var title: String,
) {
    companion object {
        var dbSpecificationList = mutableListOf<Specification>()
        var tempSpecificationList = mutableListOf<Specification>()

        fun DocumentSnapshot.toSpecification(): Specification {

            val id = getString("id")!!
            val imgUrl = getString("imgUrl")!!

            val limitA =
                get("limit").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val limit = arrayListOf<Int>()
            for (i in limitA.indices) {
                limit.add(limitA[i].toInt())
            }

            val member = get("member").toString().replace("[", "").replace("]", "").split(", ")
                .toTypedArray()
            val order = getLong("order")?.toInt()!!
            val priceA =
                get("price").toString().replace("[", "").replace("]", "").split(", ")
                    .toTypedArray()
            val price = arrayListOf<Int>()
            for (i in priceA.indices) {
                price.add(priceA[i].toInt())
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

