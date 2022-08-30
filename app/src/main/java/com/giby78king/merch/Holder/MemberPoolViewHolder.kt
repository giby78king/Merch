package com.giby78king.merch.Holder

import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.giby78king.merch.Model.Group.Companion.ddlGroupList
import com.giby78king.merch.R


class MemberPoolViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    val holderText: TextView = v.findViewById(R.id.tag_text)

    fun bind(item: String) {

       val data = ddlGroupList.filter { it.id == item }[0]

        holderText.text = data.name

        itemView.setOnLongClickListener {

            val copy: ClipboardManager =
                it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            copy.setText(data.name)

            Toast.makeText(
                it.context,
                "已複製:"+data.name,
                Toast.LENGTH_SHORT
            )
                .show()
            true
        }

//        itemView.setOnClickListener {
//
//            if (SelectGroupList.contains(data.id))            //排除重複點選
//            {
//                SelectGroupList.remove(data.id)
//            }
//            val rvAddPoolRemark = itemView.rootView.findViewById<RecyclerView>(R.id.rvAddPoolGroup)
//            rvAddPoolRemark.adapter = PoolGroupAdapter(SelectGroupList)
//        }
    }
}