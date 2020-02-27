package com.parasde.library.simpledatepicker.view;

import androidx.appcompat.app.AppCompatActivity;

import com.parasde.library.simpledatepicker.data.CalendarMemo;
import com.parasde.library.simpledatepicker.data.CalendarSize;
import com.parasde.library.simpledatepicker.listener.CalendarClickListener;
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener;

import java.util.ArrayList;

public interface CalendarPager {
    void init(AppCompatActivity activity, String[] dayOfWeek, CalendarSize size);

    void init(AppCompatActivity activity, int year, int month, String[] dayOfWeek, CalendarSize size);

    void init(AppCompatActivity activity, int year, int month, int date, String[] dayOfWeek, CalendarSize size);

    void setMemo(ArrayList<CalendarMemo> memoItems);
    void setBackgroundColorOnClick(String colorHex);

    void setCalendarPageChangeListener(CalendarOnPageChangeListener listener);
    void setCalendarClickListener(CalendarClickListener listener);

    void apply();

    int getClickYear();
    int getClickMonth();
    int getClickDate();

    void onTouchEnable(boolean enable);
}
