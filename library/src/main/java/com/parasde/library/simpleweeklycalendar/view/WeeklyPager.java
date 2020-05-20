package com.parasde.library.simpleweeklycalendar.view;

import androidx.appcompat.app.AppCompatActivity;

import com.parasde.library.simpleweeklycalendar.data.WeeklyOrientation;
import com.parasde.library.simpleweeklycalendar.data.WeeklySize;
import com.parasde.library.simpleweeklycalendar.data.WeeklyStyle;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyClickListener;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyOnPageChangeListener;

public interface WeeklyPager {
    void init(AppCompatActivity activity, String[] dayOfWeek, WeeklySize size, WeeklyOrientation orientation);

    void init(AppCompatActivity activity, int year, int month, String[] dayOfWeek, WeeklySize size, WeeklyOrientation orientation);

    void init(AppCompatActivity activity, int year, int month, int date, String[] dayOfWeek, WeeklySize size, WeeklyOrientation orientation);

    void setWeeklyCalendarStyle(WeeklyStyle weeklyStyle);
    void setDateFontSize(float size);
    void setDayOfWeekFontSize(float size);
    void setBackgroundColorOnClick(String colorHex);

    void apply();

    void setWeeklyPageChangeListener(WeeklyOnPageChangeListener listener);
    void setWeeklyClickListener(WeeklyClickListener listener);
}
