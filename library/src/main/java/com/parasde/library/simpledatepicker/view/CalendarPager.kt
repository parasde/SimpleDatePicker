package com.parasde.library.simpledatepicker.view

import androidx.appcompat.app.AppCompatActivity
import com.parasde.library.simpledatepicker.listener.CalendarClickListener
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener

interface CalendarPager {
    fun init(activity: AppCompatActivity)
    fun init(activity: AppCompatActivity, year: Int, month: Int)
    fun init(activity: AppCompatActivity, year: Int, month: Int, date: Int)

    fun setCalendarPageChangeListener(listener: CalendarOnPageChangeListener)
    fun setCalendarClickListener(listener: CalendarClickListener)
}