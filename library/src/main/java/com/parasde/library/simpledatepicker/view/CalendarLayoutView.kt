package com.parasde.library.simpledatepicker.view

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.parasde.library.simpledatepicker.R
import com.parasde.library.simpledatepicker.data.CalendarClickData
import com.parasde.library.simpledatepicker.data.CalendarData
import com.parasde.library.simpledatepicker.data.CalendarSize
import com.parasde.library.simpledatepicker.listener.CalendarClickListener
import java.util.*
import kotlin.collections.ArrayList


/**
 * calendar content
 * day of week, blank, date ...
 */
class CalendarLayoutView(private val context: Context): CalendarLayout {
    private var dayOfWeek: Array<String> = arrayOf("SUN", "MON", "TUE", "WED", "THE", "FRI", "SAT")

    private var calendarClickListener: CalendarClickListener? = null

    private lateinit var cal: Calendar
    private lateinit var calendarClickData: CalendarClickData
    private lateinit var calendarData: ArrayList<CalendarData>


    override fun setCalendarDateOnClickListener(listener: CalendarClickListener) {
        calendarClickListener = listener
    }

    override fun onCreateLayout(calendar: Calendar, weekDay: Array<String>?, calendarClickData: CalendarClickData, size: CalendarSize): GridLayout {
        cal = calendar.clone() as Calendar
        calendarData = ArrayList()
        this.calendarClickData = calendarClickData

        if(!weekDay.isNullOrEmpty() && weekDay.size == 7) {
            dayOfWeek = weekDay
        }

        // add date
        val gridLayout = GridLayout(context)
        gridLayout.layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size.colSize()*7)
        gridLayout.columnCount = 7

        // get first date of month
        val firstDayCalendar = calendar.clone() as Calendar
        firstDayCalendar.set(Calendar.DATE, 1)
        val firstWeekDay = firstDayCalendar.get(Calendar.DAY_OF_WEEK)

        // get last date of month
        val lastDayCalendar = calendar.clone() as Calendar
        lastDayCalendar.add(Calendar.MONTH, 1)
        lastDayCalendar.set(Calendar.DATE, 1)
        lastDayCalendar.add(Calendar.DATE, -1)
        val lastDate = lastDayCalendar.get(Calendar.DATE)

        val blankDate = firstWeekDay - 1
        val dateArray: ArrayList<String> = ArrayList()

        // sun ~ sat day
        for(i in dayOfWeek.indices) {
            dateArray.add(dayOfWeek[i])
        }

        // prev calendar blank date
        for(i in 0 until blankDate) {
            dateArray.add(" ")
        }

        // calendar date
        for(i in 1..lastDate) {
            dateArray.add("$i")
        }

        for(i in 0 until dateArray.size) {
            val tv = TextView(context)
            tv.text = dateArray[i]
            val params: GridLayout.LayoutParams = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            )
            params.height = size.colSize()
            tv.layoutParams = params
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv.gravity = Gravity.CENTER_VERTICAL

            if(i == 0) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calSunday))
            } else if(i < 7) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calHeader))
            } else if(i >= (blankDate+7)) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calDate))
                tv.setOnClickListener(CalendarDateOnClick(i-blankDate-7))
                calendarData.add(CalendarData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), dateArray[i].toInt()))
                if(checkSunday(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), dateArray[i].toInt())) {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.calSunday))
                }

                if(clickDataCheck(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), dateArray[i].toInt())) {
                    tv.setBackgroundResource(R.color.skyBlue)
                    calendarClickData.tv = tv
                }
            }

            gridLayout.addView(tv)
        }

        return gridLayout
    }

    // sunday check
    private fun checkSunday(year: Int, month: Int, date: Int): Boolean {
        val cal = Calendar.getInstance().clone() as Calendar
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DATE, date)
        return cal.get(Calendar.DAY_OF_WEEK) == 1
    }

    // click date check
    private fun clickDataCheck(year: Int, month: Int, date: Int): Boolean {
        return calendarClickData.year == year && calendarClickData.month == month && calendarClickData.date == date
    }

    private inner class CalendarDateOnClick(val position: Int): View.OnClickListener {
        override fun onClick(view: View?) {
            if(calendarClickListener != null)  {
                if(calendarClickData.tv != null) {
                    calendarClickData.tv!!.setBackgroundResource(android.R.color.transparent)
                }

                view!!.setBackgroundResource(R.color.skyBlue)

                // click date set
                calendarClickData.tv = view as TextView
                calendarClickData.year = calendarData[position].year
                calendarClickData.month = calendarData[position].month
                calendarClickData.date = calendarData[position].date

                // 2019.12.01 return month 1 ~ 12
                calendarClickListener!!.onClick(calendarData[position].year, calendarData[position].month+1, calendarData[position].date)
            }
        }

    }
}