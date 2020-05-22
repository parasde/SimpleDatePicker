package com.parasde.library.simpleweeklycalendar.view;

import androidx.appcompat.app.AppCompatActivity;

import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simpleweeklycalendar.data.WeeklyOrientation;
import com.parasde.library.simpleweeklycalendar.data.WeeklyStyle;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyClickListener;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyOnPageChangeListener;

import java.util.ArrayList;

public interface WeeklyPager {
    void init(AppCompatActivity activity, String[] dayOfWeek, WeeklyOrientation orientation);
    void init(AppCompatActivity activity, int year, int month, String[] dayOfWeek, WeeklyOrientation orientation);
    void init(AppCompatActivity activity, int year, int month, int date, String[] dayOfWeek, WeeklyOrientation orientation);


    void setColHeight(int height);
    void setDayOfWeekTextColor(String sundayColor, String weekdayColor, String saturdayColor);
    void setDayOfWeekHeaderTextColor(String sundayColor, String weekdayColor, String saturdayColor);
    void setWeeklyCalendarStyle(WeeklyStyle weeklyStyle);


    void setDateFontSize(float size);
    void setDayOfWeekFontSize(float size);


    void setTextColorOnClick(String colorHex);
    void setBackgroundColorOnClick(String colorHex);
    void setBackgroundColorOnClick(String colorHex, CalendarClickShape shape);


    void setMemo(ArrayList<CalendarMemo> memoItems);
    void setMemoFontSize(float size);
    void setMemoTextColor(String colorHex);


    void apply();


    void setWeeklyPageChangeListener(WeeklyOnPageChangeListener listener);
    void setWeeklyClickListener(WeeklyClickListener listener);
}
