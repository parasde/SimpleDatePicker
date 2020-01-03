package com.parasde.library.simpleweeklypicker.view;

import android.widget.LinearLayout;

import com.parasde.library.simpledatepicker.R;
import com.parasde.library.simpleweeklypicker.data.WeeklyClickData;
import com.parasde.library.simpleweeklypicker.data.WeeklyData;
import com.parasde.library.simpleweeklypicker.data.WeeklySize;
import com.parasde.library.simpleweeklypicker.listener.WeeklyClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

public class WeeklyFragmentPager extends WeeklyFragment {
    private WeeklyClickListener weeklyClickListener;
    private WeeklyClickData weeklyClickData;
    private String[] weekDay;
    private ArrayList<WeeklyData> weeklyData;
    private WeeklySize size;

    WeeklyFragmentPager(ArrayList<WeeklyData> weeklyData, WeeklyClickListener weeklyClickListener, WeeklyClickData weeklyClickData, String[] weekDay, WeeklySize size) {
        this.weeklyClickListener = weeklyClickListener;
        this.weeklyClickData = weeklyClickData;
        this.weekDay = weekDay;
        this.weeklyData = weeklyData;
        this.size = size;
    }

    // select pager calendar
    ArrayList<WeeklyData> getWeeklyData() {
        return weeklyData;
    }

    @Override
    protected int layoutResId() {
        return R.layout.weekly_pager;
    }

    @Override
    protected void initLayout(@NotNull LinearLayout layout) {
        WeeklyLayoutView calLayout = new WeeklyLayoutView(getContext());
        if(weeklyClickListener != null) calLayout.setCalendarDateOnClickListener(weeklyClickListener);
        layout.addView(calLayout.onCreateLayout(weeklyData, weekDay, weeklyClickData, size));
    }
}
