package com.parasde.library.simpledatepicker.view

import androidx.appcompat.app.AppCompatActivity
import com.parasde.library.simpledatepicker.data.CalendarSize
import com.parasde.library.simpledatepicker.listener.CalendarClickListener
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener

interface CalendarPager {
    fun init(activity: AppCompatActivity,
             dayOfWeek: Array<String>?=null,
             size: CalendarSize= CalendarSize.NORMAL)

    fun init(activity: AppCompatActivity, year: Int, month: Int,
             dayOfWeek: Array<String>?=null,
             size: CalendarSize= CalendarSize.NORMAL)

    fun init(activity: AppCompatActivity, year: Int, month: Int, date: Int,
             dayOfWeek: Array<String>?=null,
             size: CalendarSize= CalendarSize.NORMAL)

    fun setCalendarPageChangeListener(listener: CalendarOnPageChangeListener)
    fun setCalendarClickListener(listener: CalendarClickListener)
}