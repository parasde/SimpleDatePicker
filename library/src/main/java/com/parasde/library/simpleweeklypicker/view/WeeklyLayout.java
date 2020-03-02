package com.parasde.library.simpleweeklypicker.view;

import android.widget.GridLayout;

import com.parasde.library.simpleweeklypicker.data.WeeklyClickData;
import com.parasde.library.simpleweeklypicker.data.WeeklyData;
import com.parasde.library.simpleweeklypicker.data.WeeklySize;
import com.parasde.library.simpleweeklypicker.data.WeeklyStyle;
import com.parasde.library.simpleweeklypicker.listener.WeeklyClickListener;

import java.util.ArrayList;

public interface WeeklyLayout {
    void setCalendarDateOnClickListener(WeeklyClickListener listener);
    GridLayout onCreateLayout(ArrayList<WeeklyData> weeklyData, String[] weekDay, WeeklyClickData weeklyClickData, WeeklySize size, WeeklyStyle weeklyStyle, float dayOfWeekFontSize, float dateFontSize, String colorHex);
}
