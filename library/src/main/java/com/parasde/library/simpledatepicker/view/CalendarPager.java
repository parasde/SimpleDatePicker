package com.parasde.library.simpledatepicker.view;

import androidx.appcompat.app.AppCompatActivity;

import com.parasde.library.simpledatepicker.data.CalendarClickData;
import com.parasde.library.simpledatepicker.data.CalendarMemo;
import com.parasde.library.simpledatepicker.listener.CalendarClickListener;
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener;

import java.util.ArrayList;

public interface CalendarPager {
    void init(AppCompatActivity activity, String[] dayOfWeek);

    void init(AppCompatActivity activity, int year, int month, String[] dayOfWeek);

    void init(AppCompatActivity activity, int year, int month, int date, String[] dayOfWeek);
    void setColHeight(int height);

    void setMemo(ArrayList<CalendarMemo> memoItems);
    void setTextColorOnClick(String colorHex);
    void setBackgroundColorOnClick(String colorHex);
    void setBackgroundColorOnClick(String colorHex, CalendarClickData.Shape shape);

    void setCalendarPageChangeListener(CalendarOnPageChangeListener listener);
    void setCalendarClickListener(CalendarClickListener listener);

    void setCalendarFontSize(float size);
    void setMemoFontSize(float size);
    void apply();

    int getClickYear();
    int getClickMonth();
    int getClickDate();

    void onTouchEnable(boolean enable);
}
