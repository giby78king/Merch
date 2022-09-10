package com.giby78king.merch.Function

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView

class SpinnerSet {
    fun setSpinnerIcon(module: String, icon: String, imageView: ImageView, resources: Resources) {
        var fileName = ""

        fileName = when (module) {
            "group" -> {
                "img_group_"
            }
            "member" -> {
                "img_member_"
            }
            "channeldetail" -> {
                "img_channeldetail_"
            }
            "activity" -> {
                "img_activity_"
            }
            "product" -> {
                "img_product_"
            }
            "specification" -> {
                "img_specification_"
            }
            "traderule"-> {
                "img_traderule_"
            }
            else -> {
                "ic_" + module.toLowerCase() + "_"
            }
        }
        fileName += icon.replace("'", "").toLowerCase().replace(" ", "")

        //Icon相關ic_mst_
        val resourceId: Int = resources.getIdentifier(
            fileName, "drawable",
            "com.giby78king.merch"
        )
        if (resourceId == 0 && icon != "") {
            imageView.setImageResource(0)
        } else {
            imageView.setImageBitmap(null)
            imageView.setImageResource(resourceId)
        }
    }
}