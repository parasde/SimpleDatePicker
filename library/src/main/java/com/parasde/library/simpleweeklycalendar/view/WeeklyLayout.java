package com.parasde.library.simpleweeklycalendar.view;

import android.widget.GridLayout;

import com.parasde.library.simpleweeklycalendar.data.WeeklyClickData;
import com.parasde.library.simpleweeklycalendar.data.WeeklyData;
import com.parasde.library.simpleweeklycalendar.data.WeeklySize;
import com.parasde.library.simpleweeklycalendar.data.WeeklyStyle;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyClickListener;

import java.util.ArrayList;

public interface WeeklyLayout {
    void setCalendarDateOnClickListener(WeeklyClickListener listener);
    GridLayout onCreateLayout(ArrayList<WeeklyData> weeklyData, String[] weekDay, WeeklyClickData weeklyClickData, WeeklySize size, WeeklyStyle weeklyStyle, float dayOfWeekFontSize, float dateFontSize, String colorHex);
}
