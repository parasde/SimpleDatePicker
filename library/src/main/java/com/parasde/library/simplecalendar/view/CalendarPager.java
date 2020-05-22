package com.parasde.library.simplecalendar.view;

import androidx.appcompat.app.AppCompatActivity;

import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simplecalendar.listener.CalendarClickListener;
import com.parasde.library.simplecalendar.listener.CalendarOnPageChangeListener;

import java.util.ArrayList;

public interface CalendarPager {
    void init(AppCompatActivity activity, String[] dayOfWeek);
    void init(AppCompatActivity activity, int year, int month, String[] dayOfWeek);
    void init(AppCompatActivity activity, int year, int month, int date, String[] dayOfWeek);

    // column height -> col height * 6 = calendar height
    void setColHeight(int height);
    // sunday weekday, saturday text color
    void setDayOfWeekTextColor(String sundayColor, String weekdayColor, String saturdayColor);
    void setDayOfWeekHeaderTextColor(String sundayColor, String weekdayColor, String saturdayColor);

    // date color on click
    void setTextColorOnClick(String colorHex);
    // date background color on click
    void setBackgroundColorOnClick(String colorHex);
    // date background color, shape(rectangle, circle) on click
    void setBackgroundColorOnClick(String colorHex, CalendarClickShape shape);
    // callback on swipe
    void setCalendarPageChangeListener(CalendarOnPageChangeListener listener);
    // callback date on click
    void setCalendarClickListener(CalendarClickListener listener);


    // add memo
    void setMemo(ArrayList<CalendarMemo> memoItems);
    // calendar text size -> dp
    void setCalendarFontSize(float size);
    // memo text size -> dp
    void setMemoFontSize(float size);
    // memo text color
    void setMemoTextColor(String colorHex);
    // call set method apply


    void apply();


    // currently selected year
    int getClickYear();
    // currently selected month
    int getClickMonth();
    // currently selected date
    int getClickDate();


    // default true
    void onSwipeEnable(boolean enable);
}
