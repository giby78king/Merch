package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.ImgSetting
import com.giby78king.merch.Model.Specification
import com.giby78king.merch.Model.TradeDetail
import com.giby78king.merch.R


class TradeDetailTradeFgListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val txtName: TextView = v.findViewById(R.id.txtName)
    private val imgSpec: ImageView = v.findViewById(R.id.imgSpec)


    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: TradeDetail) {

        if (data.id == "addOne") {
            val resources: Resources = itemView.context!!.resources
            val resourceId: Int = resources.getIdentifier(
                "ic_add", "drawable",
                itemView.context!!.packageName
            )
            imgSpec.setImageResource(resourceId)
        } else {
            ImgSetting().setImage("specification", res, imgSpec, data.specification)
        }

        if (Specification.dbSpecificationList.filter { it.id == data.specification }.isNotEmpty()) {
            txtName.text =
                Specification.dbSpecificationList.filter { it.id == data.specification }[0].title
        }

        itemView.setOnClickListener {
            val inflater =
                LayoutInflater.from(parentView.context).inflate(R.layout.fragment_dialog_tradedetail_specification, null)

            val dialog = AlertDialog.Builder(parentView.context)
                .setView(inflater)
                .show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            val lp = WindowManager.LayoutParams()
            val window: Window = dialog.window!!
            lp.copyFrom(window.attributes)
            lp.width = 750
            lp.height = 850
            window.attributes = lp

            val editLocation = inflater.findViewById<EditText>(R.id.editLocation)
            val imgSpecDio = inflater.findViewById<ImageView>(R.id.imgSpecDio)
            ImgSetting().setImage("specification", res, imgSpecDio, data.specification)

            editLocation.setText(data.price.toString())
            dialog.setOnDismissListener {

            }

        }


    }
}