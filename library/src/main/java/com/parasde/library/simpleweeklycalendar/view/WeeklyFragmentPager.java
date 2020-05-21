package com.parasde.library.simpleweeklycalendar.view;

import android.widget.LinearLayout;

import com.parasde.library.simplecalendar.R;
import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simpleweeklycalendar.data.WeeklyClickData;
import com.parasde.library.simpleweeklycalendar.data.WeeklyData;
import com.parasde.library.simpleweeklycalendar.data.WeeklyStyle;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WeeklyFragmentPager extends WeeklyFragment {
    private WeeklyClickListener weeklyClickListener;
    private WeeklyClickData weeklyClickData;
    private String[] weekDay;
    private Integer colHeight;

    private ArrayList<WeeklyData> weeklyData;

    private WeeklyStyle weeklyStyle;
    private float dayOfWeekFontSize, dateFontSize;
    private String colorHex;
    private String textColorHex;

    private ArrayList<CalendarMemo> memoItems;
    private String memoTextColor;
    private float memoFontSize;

    private CalendarClickShape clickBgShape;

    public WeeklyFragmentPager(WeeklyClickListener weeklyClickListener, WeeklyClickData weeklyClickData,
                               String[] weekDay, Integer colHeight, ArrayList<WeeklyData> weeklyData,
                               WeeklyStyle weeklyStyle, float dayOfWeekFontSize, float dateFontSize,
                               String colorHex, String textColorHex,
                               ArrayList<CalendarMemo> memoItems, String memoTextColor, float memoFontSize,
                               CalendarClickShape clickBgShape) {
        this.weeklyClickListener = weeklyClickListener;
        this.weeklyClickData = weeklyClickData;
        this.weekDay = weekDay;
        this.colHeight = colHeight;
        this.weeklyData = weeklyData;
        this.weeklyStyle = weeklyStyle;
        this.dayOfWeekFontSize = dayOfWeekFontSize;
        this.dateFontSize = dateFontSize;
        this.colorHex = colorHex;
        this.textColorHex = textColorHex;
        this.memoItems = memoItems;
        this.memoTextColor = memoTextColor;
        this.memoFontSize = memoFontSize;
        this.clickBgShape = clickBgShape;
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
        layout.addView(calLayout.onCreateLayout(weeklyClickData, weekDay, colHeight, weeklyData,
                weeklyStyle, dayOfWeekFontSize, dateFontSize, colorHex, textColorHex,  memoItems, memoTextColor, memoFontSize,
                clickBgShape));
    }
}
