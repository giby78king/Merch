package com.giby78king.merch.Model

import com.google.firebase.firestore.DocumentSnapshot

data class ProductType(
    val id: String,
) {
    companion object {
        var dbProductTypeList = mutableListOf<ProductType>()
        var ddlProductTypeList = arrayListOf<DdlNormalModel>()

        fun DocumentSnapshot.toSpecificationType(): ProductType {

            val id = getString("id")!!

            return ProductType(
                id,
            )
        }
    }
}

