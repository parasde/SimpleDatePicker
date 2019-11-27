package com.parasde.library.simpledatepicker.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CalendarFragmentPagerAdapter(fm: FragmentManager, behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) : FragmentStatePagerAdapter(fm, behavior) {
    private var fragmentPagerArray = ArrayList<Fragment>()
    private var fragmentTag = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return fragmentPagerArray[position]
    }

    override fun getCount(): Int {
        return fragmentPagerArray.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun addPrevItem(fragment: Fragment, tag: String) {
        fragmentPagerArray.add(0, fragment)
        fragmentTag.add(0, tag)
        notifyDataSetChanged()
    }

    fun addItem(fragment: Fragment, tag: String) {
        fragmentPagerArray.add(fragment)
        fragmentTag.add(tag)
        notifyDataSetChanged()
    }
}