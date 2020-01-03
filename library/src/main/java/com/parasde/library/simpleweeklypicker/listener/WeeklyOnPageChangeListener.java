package com.parasde.library.simpleweeklypicker.listener;

import com.parasde.library.simpleweeklypicker.data.WeeklyData;

import java.util.List;

/**
 * return current calendar year and month value
 */
public interface WeeklyOnPageChangeListener {
    void onChange(List<WeeklyData> weeklyData);
}
