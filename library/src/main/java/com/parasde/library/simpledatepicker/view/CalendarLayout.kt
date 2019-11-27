package com.parasde.library.simpledatepicker.view

import android.widget.GridLayout
import com.parasde.library.simpledatepicker.data.CalendarClickData
import com.parasde.library.simpledatepicker.listener.CalendarClickListener
import java.util.*

interface CalendarLayout {
    fun setCalendarDateOnClickListener(listener: CalendarClickListener)
    fun onCreateLayout(calendar: Calendar, weekDay: Array<String>?, calendarClickData: CalendarClickData): GridLayout
}