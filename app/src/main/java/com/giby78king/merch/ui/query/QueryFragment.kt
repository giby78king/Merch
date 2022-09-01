package com.giby78king.merch.ui.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.giby78king.merch.R
import com.giby78king.merch.TabViewClass.TabViewPagerAdapter
import com.giby78king.merch.ui.fragment.TabQueryFragment
import com.giby78king.merch.ui.fragment.TabProductDepositoryFragment
import com.giby78king.merch.ui.fragment.TopProductDepositoryFragment
import com.google.android.material.tabs.TabLayout
import java.util.ArrayList

class QueryFragment : Fragment() {

    lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        root = inflater.inflate(R.layout.fragment_query, container, false)

//        val viewPager = root.findViewById(R.id.view_pager_item) as ViewPager
//
//        val adapter = TopFragmentAdapter(childFragmentManager)
//
//        viewPager.adapter = adapter
//        //圓點數量
//        val pageCount = (viewPager.adapter as TopFragmentAdapter).count
//        //viewPager下面的圓點，ViewGroup
//        val group = root.findViewById(R.id.viewGroup) as ViewGroup
//        //存放小圓點圖片
//        val tips = arrayOfNulls<ImageView>(pageCount)
//
//        for (i in 0 until pageCount) {
//            val imageView = ImageView(this.context)
//            imageView.layoutParams = ViewGroup.LayoutParams(18, 18)
//            imageView.setPadding(20, 0, 20, 0)
//            tips[i] = imageView
//
//            //預設第一張圖顯示為選中狀態
//            if (i == 0) {
//                tips[i]!!.setBackgroundResource(R.drawable.viewpager_selected_icon)
//            } else {
//                tips[i]!!.setBackgroundResource(R.drawable.viewpager_unselected_icon)
//            }
//            group.addView(tips[i])
//        }
//
//        class GuidePageChangeListener : ViewPager.OnPageChangeListener {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {}
//
//            //切換view時，下方圓點的變化。
//            override fun onPageSelected(position: Int) {
//                tips[position]?.setBackgroundResource(R.drawable.viewpager_selected_icon)
//                //這個圖片就是選中的view的圓點
//                for (i in 0 until pageCount) {
//                    if (position != i) {
//                        tips[i]?.setBackgroundResource(R.drawable.viewpager_unselected_icon)
//                        //這個圖片是未選中view的圓點
//                    }
//                }
//            }
//        }
//        viewPager.addOnPageChangeListener(GuidePageChangeListener())
//
//        val firstViewPager = root.findViewById(R.id.view_pager) as ViewPager
//        val tabLayout = root.findViewById(R.id.tabs) as TabLayout
//        tabLayout.setupWithViewPager(firstViewPager)
//        setupViewPager(firstViewPager, tabLayout)
//
//
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                firstViewPager.currentItem = tab.position
//                viewPager.currentItem = tab.position
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//        })

        return root
    }

    private fun setupViewPager(viewPager: ViewPager, tabLayout: TabLayout) {
        val adapter = TabViewPagerAdapter(childFragmentManager)
        adapter.addFragment(TabQueryFragment(), "條件")
        adapter.addFragment(TabProductDepositoryFragment(), "歷程")
        viewPager.adapter = adapter
    }

    internal class TopFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private var fragments: MutableList<Fragment> = ArrayList()

        init {
            fragments.add(TopProductDepositoryFragment())
            fragments.add(TabQueryFragment())
            //fragments.add(TopAssetFragment())
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int = fragments.size
    }

}