package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Model.Member.Companion.dbMemberList
import com.giby78king.merch.Model.Product.Companion.dbProductList
import com.giby78king.merch.Model.ProductDepository
import com.giby78king.merch.Model.TextAmountSetting
import com.giby78king.merch.R


class ProductDepositoryListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val imgEmployed: ImageView = v.findViewById(R.id.imgEmployed)
    private val imgProductDepository: ImageView = v.findViewById(R.id.imgProductDepository)
    private val txtName: TextView = v.findViewById(R.id.txtName)
    private val txtMember: TextView = v.findViewById(R.id.txtMember)
    private val txtDate: TextView = v.findViewById(R.id.txtDate)
    private val txtValuation: TextView = v.findViewById(R.id.txtValuation)
    private val txtHoldingAmount: TextView = v.findViewById(R.id.txtHoldingAmount)
    private val txtCost: TextView = v.findViewById(R.id.txtCost)
    private val txtChannelDetail: TextView = v.findViewById(R.id.txtChannelDetail)

    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: ProductDepository) {

        txtDate.text = data.date
        txtValuation.text =  TextAmountSetting().formatAmountNoDollar(data.valuation.toString())
        txtCost.text =  TextAmountSetting().formatAmountNoDollar(data.cost.toString())
        txtHoldingAmount.text = TextAmountSetting().formatAmountNoDollar(data.holdingAmount.toString())
        txtChannelDetail.text = data.channelDetail

        if (data.member.split(',').size == 1) {
            val memberInfo = dbMemberList.filter { it.number == data.member }[0]
            txtMember.text = memberInfo.id
        }
        else
        {
            txtMember.text = data.group[0]
        }

        val productInfo = dbProductList.filter { it.id == data.product }[0]
        txtName.text = productInfo.name

        itemView.setOnClickListener {
            Toast.makeText(it.context, "fsafsafa", Toast.LENGTH_LONG).show()

//            var intent = Intent(itemView.context, MemberInfo::class.java)
//
//            val bundle = Bundle()
//            bundle.putString("selectNumber", data.number.toString())
//            intent.putExtras(bundle)
//
//            itemView.context.startActivity(intent)
        }


        //Img相關
        if(data.imgUrl!="")
        {
            var img = "img_productdepository_" + data.imgUrl.toLowerCase()
            val resourceId: Int = res.getIdentifier(
                img, "drawable",
                "com.giby78king.merch"
            )
            imgProductDepository.setImageResource(resourceId)
        }
        else
        {
            var img = "img_product_" + productInfo.id.toLowerCase()
            val resourceId: Int = res.getIdentifier(
                img, "drawable",
                "com.giby78king.merch"
            )
            imgProductDepository.setImageResource(resourceId)
        }


        if (data.favorite) {
            ImageViewCompat.setImageTintList(
                imgEmployed, ColorStateList.valueOf(
                    Color.parseColor(
                        "#ffd1dc"
                    )
                )
            )
        }

//        imgMember.setImageResource(resourceId)
    }
}