package com.parasde.library.simpledatepicker.view

import android.widget.LinearLayout
import com.parasde.library.simpledatepicker.R
import com.parasde.library.simpledatepicker.data.CalendarClickData
import com.parasde.library.simpledatepicker.listener.CalendarClickListener
import java.util.*

/**
 * calendar -> current fragment calendar
 * calendarClickListener -> fragment calendar click event (return year, month, date and calendarClickData data set)
 * calendarClickData -> data class (calendar click value year, month, date)
 * weekDay -> preparing...
 */
class CalendarFragmentPager(calendar: Calendar,
                            private val calendarClickListener: CalendarClickListener?,
                            private val calendarClickData: CalendarClickData,
                            private val weekDay: Array<String>? = null): CalendarFragment() {
    private val cal = calendar.clone() as Calendar

    // select pager calendar
    fun getCalendar(): Calendar {
        return cal
    }

    override fun layoutResId(): Int {
        return R.layout.calendar_pager
    }

    override fun onCreateView(layout: LinearLayout) {
        val calLayout = CalendarLayoutView(rootView.context)
        calLayout.onCreateLayout(cal, weekDay, calendarClickData)
        if(calendarClickListener != null) calLayout.setCalendarDateOnClickListener(calendarClickListener)
        layout.addView(calLayout.onCreateLayout(cal, weekDay, calendarClickData))
    }
}