package com.parasde.library.simpleweeklypicker.view;

import android.widget.LinearLayout;

import com.parasde.library.simpledatepicker.R;
import com.parasde.library.simpleweeklypicker.data.WeeklyClickData;
import com.parasde.library.simpleweeklypicker.data.WeeklyData;
import com.parasde.library.simpleweeklypicker.data.WeeklySize;
import com.parasde.library.simpleweeklypicker.data.WeeklyStyle;
import com.parasde.library.simpleweeklypicker.listener.WeeklyClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WeeklyFragmentPager extends WeeklyFragment {
    private WeeklyClickListener weeklyClickListener;
    private WeeklyClickData weeklyClickData;
    private String[] weekDay;
    private ArrayList<WeeklyData> weeklyData;
    private WeeklySize size;
    private WeeklyStyle weeklyStyle;
    private float dayOfWeekFontSize, dateFontSize;
    private String colorHex;

    WeeklyFragmentPager(ArrayList<WeeklyData> weeklyData, WeeklyClickListener weeklyClickListener, WeeklyClickData weeklyClickData, String[] weekDay, WeeklySize size, WeeklyStyle weeklyStyle, float dayOfWeekFontSize, float dateFontSize, String colorHex) {
        this.weeklyClickListener = weeklyClickListener;
        this.weeklyClickData = weeklyClickData;
        this.weekDay = weekDay;
        this.weeklyData = weeklyData;
        this.size = size;
        this.weeklyStyle = weeklyStyle;
        this.dayOfWeekFontSize = dayOfWeekFontSize;
        this.dateFontSize = dateFontSize;
        this.colorHex = colorHex;
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
        layout.addView(calLayout.onCreateLayout(weeklyData, weekDay, weeklyClickData, size, weeklyStyle, dayOfWeekFontSize, dateFontSize, colorHex));
    }
}
