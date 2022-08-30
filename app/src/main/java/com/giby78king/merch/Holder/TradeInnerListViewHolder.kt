package com.giby78king.merch.Holder

import android.annotation.SuppressLint
import android.content.res.Resources
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Model.Member.Companion.MemberList
import com.giby78king.merch.Model.Product.Companion.ProductList
import com.giby78king.merch.Model.TextAmountSetting
import com.giby78king.merch.Model.TradeInner
import com.giby78king.merch.R


class TradeInnerListViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    private val parentView = v

    private val imgEmployed: ImageView = v.findViewById(R.id.imgEmployed)
    private val imgProductDepository: ImageView = v.findViewById(R.id.imgProductDepository)
    private val txtName: TextView = v.findViewById(R.id.txtName)
    private val txtMember: TextView = v.findViewById(R.id.txtMember)
    private val txtAmount: TextView = v.findViewById(R.id.txtAmount)
    private val txtHoldingAmount: TextView = v.findViewById(R.id.txtHoldingAmount)


    val res: Resources = v.context.resources

    @SuppressLint("SetTextI18n")
    fun bind(data: TradeInner) {

        txtAmount.text = TextAmountSetting().formatAmountNoDollar(data.amount.toString())

        txtHoldingAmount.text =
            TextAmountSetting().formatAmountNoDollar(data.holdingAmount.toString())
//        txtChannelDetail.text = data.channelDetail


        val memberInfo = MemberList.filter { it.number == data.holdingMember }[0]
        txtMember.text = memberInfo.id


        val productInfo = ProductList.filter { it.id == data.product }[0]
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
        if (data.imgUrl != "") {
            var img = "img_productdepository_" + data.imgUrl.toLowerCase()
            var resourceId: Int = res.getIdentifier(
                img, "drawable",
                "com.giby78king.merch"
            )
            if (resourceId == 0) {
                var img = "img_product_" + productInfo.id.toLowerCase()
                resourceId = res.getIdentifier(
                    img, "drawable",
                    "com.giby78king.merch"
                )
            }
            imgProductDepository.setImageResource(resourceId)
        } else {
            var img = "img_product_" + productInfo.id.toLowerCase()
            val resourceId: Int = res.getIdentifier(
                img, "drawable",
                "com.giby78king.merch"
            )
            imgProductDepository.setImageResource(resourceId)
        }

    }
}