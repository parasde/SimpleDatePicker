package com.parasde.library.simpledatepicker.view;

import android.widget.LinearLayout;

import com.parasde.library.simpledatepicker.R;
import com.parasde.library.simpledatepicker.data.CalendarClickData;
import com.parasde.library.simpledatepicker.data.CalendarSize;
import com.parasde.library.simpledatepicker.listener.CalendarClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class CalendarFragmentPager extends CalendarFragment {
    private CalendarClickListener calendarClickListener;
    private CalendarClickData calendarClickData;
    private String[] weekDay;
    private CalendarSize size;

    private Calendar cal;

    CalendarFragmentPager(Calendar cal, CalendarClickListener calendarClickListener, CalendarClickData calendarClickData, String[] weekDay, CalendarSize size) {
        this.calendarClickListener = calendarClickListener;
        this.calendarClickData = calendarClickData;
        this.weekDay = weekDay;
        this.size = size;
        this.cal = (Calendar)cal.clone();
    }

    // select pager calendar
    Calendar getCalendar() {
        return cal;
    }

    @Override
    protected int layoutResId() {
        return R.layout.calendar_pager;
    }

    @Override
    protected void onCreateView(@NotNull LinearLayout layout) {
        CalendarLayoutView calLayout = new CalendarLayoutView(rootView.getContext());
        if(calendarClickListener != null) calLayout.setCalendarDateOnClickListener(calendarClickListener);
        layout.addView(calLayout.onCreateLayout(cal, weekDay, calendarClickData, size));
    }
}
