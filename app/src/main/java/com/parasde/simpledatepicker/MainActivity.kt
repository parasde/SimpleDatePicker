package com.parasde.simpledatepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.parasde.library.simpledatepicker.data.CalendarSize
import com.parasde.library.simpledatepicker.listener.CalendarClickListener
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cal.init(this as AppCompatActivity, 2019, 12, 2, null, CalendarSize.BIG)

        // pageChange -> callback change year, month value
        cal.setCalendarPageChangeListener(object:
            CalendarOnPageChangeListener {
            override fun onChange(year: Int, month: Int) {
                if(month < 10) calHeader.text = "$year - 0${month}"
                else calHeader.text = "$year - $month"
            }
        })

        // callback click item value
        cal.setCalendarClickListener(object: CalendarClickListener {
            override fun onClick(year: Int, month: Int, date: Int) {
                Toast.makeText(applicationContext, "$year $month $date", Toast.LENGTH_SHORT).show()
            }

        })


        val dayOfWeek: Array<String> = arrayOf("일", "월", "화", "수", "목", "금", "토")

        // scroll bar calendar test
        cal2.init(this as AppCompatActivity, dayOfWeek)
    }
}
