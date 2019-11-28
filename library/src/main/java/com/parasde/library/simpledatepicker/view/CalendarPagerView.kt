package com.parasde.library.simpledatepicker.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.parasde.library.simpledatepicker.data.CalendarClickData
import com.parasde.library.simpledatepicker.data.CalendarSize
import com.parasde.library.simpledatepicker.listener.CalendarClickListener
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener
import java.util.*


/**
 * calendar base pager
 */
class CalendarPagerView: ViewPager, CalendarPager {
    constructor(@NonNull context: Context): this(context, null)
    constructor(@NonNull context: Context, attrs: AttributeSet?): super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, getHeightMeasureSpec(widthMeasureSpec, heightMeasureSpec))
    }

    private val calendar = Calendar.getInstance().clone() as Calendar
    private var prevCalendar = prevMonthCalendar()
    private var nextCalendar = nextMonthCalendar()
    private lateinit var activity: AppCompatActivity
    private lateinit var fragment: FragmentManager
    private lateinit var fragmentAdapter: CalendarFragmentPagerAdapter

    private var onPageChangeListener: CalendarOnPageChangeListener? = null
    private var onClickListener = CalendarDateOnClick()

    private var calendarOnClickListener: CalendarClickListener? = null

    private lateinit var calendarClickData: CalendarClickData

    private var swipeEnable = true

    // get child view height
    private fun getHeightMeasureSpec(widthMeasureSpec: Int, heightMeasureSpec: Int): Int {
        var hm = heightMeasureSpec
        for(i in 0 until childCount) {
            val v: View? = getChildAt(i)
            if(v != null) {
                var height = 0
                v.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.UNSPECIFIED))
                val viewHeight = v.measuredHeight
                if(viewHeight > height) {
                    height = viewHeight
                }
                hm = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            }
        }

        return hm
    }

    // pager select calendar year, month
    override fun setCalendarPageChangeListener(listener: CalendarOnPageChangeListener) {
        onPageChangeListener = listener
        val fragmentCalendar = (fragmentAdapter.getItem(this.currentItem) as CalendarFragmentPager).getCalendar()
        onPageChangeListener!!.onChange(
            fragmentCalendar.get(Calendar.YEAR),
            fragmentCalendar.get(Calendar.MONTH)
        )
    }

    // select calendar year, month, date
    override fun setCalendarClickListener(listener: CalendarClickListener) {
        calendarOnClickListener = listener
    }

    // initialize pager, add calendar fragment + draw calendar
    override fun init(activity: AppCompatActivity, dayOfWeek: Array<String>?, size: CalendarSize) {
        this.activity = activity

        calendarClickData = CalendarClickData()
        onCreatePager(dayOfWeek, size)
    }

    // initialize pager, set calendar
    override fun init(activity: AppCompatActivity, year: Int, month: Int, dayOfWeek: Array<String>?, size: CalendarSize) {
        this.activity = activity

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        prevCalendar = prevMonthCalendar()
        nextCalendar = nextMonthCalendar()

        calendarClickData = CalendarClickData()
        onCreatePager(dayOfWeek, size)
    }

    // parameter date set background color
    override fun init(activity: AppCompatActivity, year: Int, month: Int, date: Int, dayOfWeek: Array<String>?, size: CalendarSize) {
        this.activity = activity

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        prevCalendar = prevMonthCalendar()
        nextCalendar = nextMonthCalendar()

        calendarClickData = CalendarClickData(null, year, month, date)
        onCreatePager(dayOfWeek, size)
    }

    private fun onCreatePager(dayOfWeek: Array<String>?, size: CalendarSize) {
        this.fragment = activity.supportFragmentManager
        this.fragmentAdapter = CalendarFragmentPagerAdapter(fragment)

        fragmentAdapter.addItem(CalendarFragmentPager(prevCalendar, onClickListener, calendarClickData, dayOfWeek, size), "0")
        fragmentAdapter.addItem(CalendarFragmentPager(calendar, onClickListener, calendarClickData, dayOfWeek, size), "1")
        fragmentAdapter.addItem(CalendarFragmentPager(nextCalendar, onClickListener, calendarClickData, dayOfWeek, size), "2")
        this.adapter = fragmentAdapter
        this.setCurrentItem(1, false)

        this.addOnPageChangeListener(object: OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                /**/
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                /**/
            }

            /**
             * left and right swipe pager selected listener
             * first index pager -> prev month calendar fragment add and remake adapter
             * last index pager -> next month calendar fragment add and notifyDataSetChanged . apply
             */
            override fun onPageSelected(position: Int) {
                if(onPageChangeListener != null) {
                    val fragmentCalendar = (fragmentAdapter.getItem(position) as CalendarFragmentPager).getCalendar()
                    onPageChangeListener!!.onChange(
                        fragmentCalendar.get(Calendar.YEAR),
                        fragmentCalendar.get(Calendar.MONTH)
                    )
                }

                if(position == 0) { // first index..
                    // left swipe
                    calendar.add(Calendar.MONTH, -1)
                    minPrevMonth()
                    fragmentAdapter.addPrevItem(CalendarFragmentPager(prevCalendar, onClickListener, calendarClickData, dayOfWeek, size), "$position")
                    this@CalendarPagerView.invalidate()
                    this@CalendarPagerView.setCurrentItem(position+1, false)
                } else if(position == fragmentAdapter.count-1) {    // last index..
                    // right swipe
                    calendar.add(Calendar.MONTH, 2)
                    addNextMonth()
                    fragmentAdapter.addItem(CalendarFragmentPager(nextCalendar, onClickListener, calendarClickData, dayOfWeek, size), "$position")
                }
            }

        })
    }

    // initialize prev month calendar
    private fun prevMonthCalendar(): Calendar {
        val prevMontCal = calendar.clone() as Calendar
        prevMontCal.add(Calendar.MONTH, -1)
        return prevMontCal
    }

    // initialize next month calendar
    private fun nextMonthCalendar(): Calendar {
        val nextMontCal = calendar.clone() as Calendar
        nextMontCal.add(Calendar.MONTH, 1)
        return nextMontCal
    }

    // first index pager -> get prev month calendar cast
    private fun minPrevMonth() {
        prevCalendar.add(Calendar.MONTH, -1)
    }

    // last index pager -> get next month calendar cast
    private fun addNextMonth() {
        nextCalendar.add(Calendar.MONTH, 1)
    }

    private inner class CalendarDateOnClick: CalendarClickListener {
        override fun onClick(year: Int, month: Int, date: Int) {
            if(calendarOnClickListener != null) calendarOnClickListener!!.onClick(year, month, date)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if(swipeEnable) {
            super.onInterceptTouchEvent(ev)
        }else {
            false
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return if(swipeEnable) {
            super.onTouchEvent(ev)
        }else {
            false
        }
    }
}