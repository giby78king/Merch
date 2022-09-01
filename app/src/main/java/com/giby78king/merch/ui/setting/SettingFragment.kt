package com.giby78king.merch.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.giby78king.merch.R
import com.giby78king.merch.ui.*


class SettingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        val button = root.findViewById<Button>(R.id.button)
        button.setOnClickListener{
            val intent = Intent(context, MemberEditPage::class.java)
            context?.startActivity(intent)
        }
        val button2 = root.findViewById<Button>(R.id.button2)
        button2.setOnClickListener{
            val intent = Intent(context, ChannelDetailEditPage::class.java)
            context?.startActivity(intent)
        }

        val button3 = root.findViewById<Button>(R.id.button3)
        button3.setOnClickListener{
            val intent = Intent(context, ActivityEditPage::class.java)
            context?.startActivity(intent)
        }

        val button4 = root.findViewById<Button>(R.id.button4)
        button4.setOnClickListener{
            val intent = Intent(context, ProductSelectPage::class.java)
            context?.startActivity(intent)
        }

        return root
    }


}