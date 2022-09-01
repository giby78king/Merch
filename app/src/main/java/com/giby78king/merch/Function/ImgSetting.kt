package com.giby78king.merch

import android.content.res.Resources
import android.util.Log
import android.widget.ImageView

class ImgSetting {

    fun setImage(module: String, res: Resources, imgView: ImageView, imgUrl: String) {

        var img = "img_"
        when (module) {
            "activity" -> img += module
            "channeldetail" -> img += module
        }

        img = img + "_" + imgUrl.toLowerCase().replace(" ", "")
        Log.d(":", ":" + img)
        val resourceId: Int = res.getIdentifier(
            img, "drawable",
            "com.giby78king.merch"
        )

        imgView.setImageResource(resourceId)

    }

}