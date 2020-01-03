package com.parasde.library.simpleweeklypicker.view;

import androidx.appcompat.app.AppCompatActivity;

import com.parasde.library.simpleweeklypicker.data.WeeklyOrientation;
import com.parasde.library.simpleweeklypicker.data.WeeklySize;
import com.parasde.library.simpleweeklypicker.listener.WeeklyClickListener;
import com.parasde.library.simpleweeklypicker.listener.WeeklyOnPageChangeListener;

public interface WeeklyPager {
    void init(AppCompatActivity activity, String[] dayOfWeek, WeeklySize size, WeeklyOrientation orientation);

    void init(AppCompatActivity activity, int year, int month, String[] dayOfWeek, WeeklySize size, WeeklyOrientation orientation);

    void init(AppCompatActivity activity, int year, int month, int date, String[] dayOfWeek, WeeklySize size, WeeklyOrientation orientation);

    void setWeeklyPageChangeListener(WeeklyOnPageChangeListener listener);
    void setWeeklyClickListener(WeeklyClickListener listener);
}
