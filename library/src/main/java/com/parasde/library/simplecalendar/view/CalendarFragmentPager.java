package com.parasde.library.simplecalendar.view;

import android.widget.LinearLayout;

import com.parasde.library.simplecalendar.R;
import com.parasde.library.simplecalendar.data.CalendarClickData;
import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simplecalendar.listener.CalendarClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragmentPager extends CalendarFragment {
    private CalendarClickListener calendarClickListener;
    private CalendarClickData calendarClickData;
    private String[] weekDay;
    private Integer colHeight;

    private Calendar cal;

    private ArrayList<CalendarMemo> memoItems;
    private String colorHex;
    private String textColorHex;
    private String memoTextColor;
    private CalendarClickShape clickBgShape;
    private float memoFontSize, calendarFontSize;

    CalendarFragmentPager(Calendar cal, CalendarClickListener calendarClickListener,
                          CalendarClickData calendarClickData, String[] weekDay, Integer colHeight,
                          ArrayList<CalendarMemo> memoItems, String colorHex, String textColorHex, CalendarClickShape clickBgShape, float memoFontSize, String memoTextColor, float calendarFontSize) {
        this.calendarClickListener = calendarClickListener;
        this.calendarClickData = calendarClickData;
        this.weekDay = weekDay;
        this.cal = (Calendar)cal.clone();
        this.colHeight = colHeight;
        this.memoItems = memoItems;
        this.colorHex = colorHex;
        this.textColorHex = textColorHex;
        this.clickBgShape = clickBgShape;
        this.memoFontSize = memoFontSize;
        this.memoTextColor = memoTextColor;
        this.calendarFontSize = calendarFontSize;
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
    protected void initLayout(@NotNull LinearLayout layout) {
        CalendarLayoutView calLayout = new CalendarLayoutView(getContext());
        if(calendarClickListener != null) calLayout.setCalendarDateOnClickListener(calendarClickListener);
        layout.addView(calLayout.onCreateLayout(cal, weekDay, calendarClickData, colHeight, memoItems, colorHex, textColorHex, clickBgShape, memoFontSize, memoTextColor, calendarFontSize));
    }
}
