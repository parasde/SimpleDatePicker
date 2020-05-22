package com.parasde.library.simpleweeklycalendar.view;

import android.widget.GridLayout;

import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simpleweeklycalendar.data.WeeklyClickData;
import com.parasde.library.simpleweeklycalendar.data.WeeklyData;
import com.parasde.library.simpleweeklycalendar.data.WeeklyStyle;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyClickListener;

import java.util.ArrayList;

public interface WeeklyLayout {
    void setCalendarDateOnClickListener(WeeklyClickListener listener);
    GridLayout onCreateLayout(WeeklyClickData weeklyClickData,
                              String[] weekDay, Integer colHeight, ArrayList<WeeklyData> weeklyData,
                              WeeklyStyle weeklyStyle, float dayOfWeekFontSize, float dateFontSize,
                              String colorHex, String textColorHex,
                              ArrayList<CalendarMemo> memoItems, String memoTextColor, float memoFontSize,
                              CalendarClickShape clickBgShape, String[] dayOfWeekColor, String[] dayOfWeekHeaderColor);
}
