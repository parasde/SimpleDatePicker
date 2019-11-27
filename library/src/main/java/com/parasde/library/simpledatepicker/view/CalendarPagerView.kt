package com.parasde.library.simpledatepicker.view

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.parasde.library.simpledatepicker.data.CalendarClickData
import com.parasde.library.simpledatepicker.listener.CalendarClickListener
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener
import java.util.*


/**
 * calendar base pager
 */
class CalendarPagerView: ViewPager, CalendarPager {
    constructor(@NonNull context: Context): this(context, null)
    constructor(@NonNull context: Context, attrs: AttributeSet?): super(context, attrs)

    companion object {
        const val NORMAL = 900
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, NORMAL)
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
    override fun init(activity: AppCompatActivity) {
        this.activity = activity

        calendarClickData = CalendarClickData()
        onCreatePager()
    }

    // initialize pager, set calendar
    override fun init(activity: AppCompatActivity, year: Int, month: Int) {
        this.activity = activity

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        prevCalendar = prevMonthCalendar()
        nextCalendar = nextMonthCalendar()

        calendarClickData = CalendarClickData()
        onCreatePager()
    }

    // parameter date set background color
    override fun init(activity: AppCompatActivity, year: Int, month: Int, date: Int) {
        this.activity = activity

        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        prevCalendar = prevMonthCalendar()
        nextCalendar = nextMonthCalendar()

        calendarClickData = CalendarClickData(null, year, month, date)
        onCreatePager()
    }

    private fun onCreatePager() {
        this.fragment = activity.supportFragmentManager
        this.fragmentAdapter = CalendarFragmentPagerAdapter(fragment)

        fragmentAdapter.addItem(CalendarFragmentPager(prevCalendar, onClickListener, calendarClickData), "0")
        fragmentAdapter.addItem(CalendarFragmentPager(calendar, onClickListener, calendarClickData), "1")
        fragmentAdapter.addItem(CalendarFragmentPager(nextCalendar, onClickListener, calendarClickData), "2")
        this.adapter = fragmentAdapter
        this.currentItem = 1

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
                // current pager change. return year and month value
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
                    fragmentAdapter.addPrevItem(CalendarFragmentPager(prevCalendar, onClickListener, calendarClickData), "$position")
                    this@CalendarPagerView.invalidate()
                    this@CalendarPagerView.setCurrentItem(position+1, false)
                } else if(position == fragmentAdapter.count-1) {    // last index..
                    // right swipe
                    calendar.add(Calendar.MONTH, 2)
                    addNextMonth()
                    fragmentAdapter.addItem(CalendarFragmentPager(nextCalendar, onClickListener, calendarClickData), "$position")
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
}