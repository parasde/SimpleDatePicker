package com.parasde.library.simpleweeklycalendar.listener;

import com.parasde.library.simpleweeklycalendar.data.WeeklyData;

import java.util.List;

/**
 * return current calendar year and month value
 */
public interface WeeklyOnPageChangeListener {
    void onChange(List<WeeklyData> weeklyData);
}
