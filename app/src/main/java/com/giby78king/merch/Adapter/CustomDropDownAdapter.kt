package com.giby78king.merch.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.giby78king.merch.Function.SpinnerSet
import com.giby78king.merch.Model.DdlNormalModel
import com.giby78king.merch.R


class CustomDropDownAdapter(
    val module: String,
    val context: Context?,
    private var dataSource: List<DdlNormalModel>
) :
    BaseAdapter() {

    private val inflater: LayoutInflater =
        context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_spinner_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = dataSource[position].name


        val icon = dataSource[position].icon
        val imgViewIcon: ImageView = vh.img
        if (context != null) {
            SpinnerSet().setSpinnerIcon(module, icon, imgViewIcon, context.resources)
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View) {
        val label: TextView = row.findViewById(R.id.text) as TextView
        val img: ImageView = row.findViewById(R.id.img) as ImageView
    }

}