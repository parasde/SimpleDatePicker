package com.parasde.simpledatepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.parasde.library.simpledatepicker.data.CalendarSize
import com.parasde.library.simpledatepicker.listener.CalendarClickListener
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val calendar = Calendar.getInstance().clone() as Calendar

        /**
         * calendar init non parameter -> current month calendar
         * calendar init (year, month) -> year-month == calendar
         * calendar init (year, month, date) -> year-month-date == calendar / and add set date marker
         */
        cal.init(this as AppCompatActivity, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
            Calendar.DATE), null, CalendarSize.BIG)

        // pageChange -> callback change year, month value
        cal.setCalendarPageChangeListener(object:
            CalendarOnPageChangeListener {
            override fun onChange(year: Int, month: Int) {
                if(month < 9) calHeader.text = "$year - 0${month+1}"
                else calHeader.text = "$year - ${month+1}"
            }
        })

        // callback click item value
        cal.setCalendarClickListener(object: CalendarClickListener {
            override fun onClick(year: Int, month: Int, date: Int) {
                Toast.makeText(applicationContext, "$year ${month+1} $date", Toast.LENGTH_SHORT).show()
            }

        })


        val dayOfWeek: Array<String> = arrayOf("일", "월", "화", "수", "목", "금", "토")

        // scroll bar calendar test
        cal2.init(this as AppCompatActivity, dayOfWeek)
    }
}
