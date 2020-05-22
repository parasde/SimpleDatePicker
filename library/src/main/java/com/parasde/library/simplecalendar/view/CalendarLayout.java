package com.parasde.library.simplecalendar.view;

import android.widget.GridLayout;

import com.parasde.library.simplecalendar.data.CalendarClickData;
import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simplecalendar.listener.CalendarClickListener;

import java.util.ArrayList;
import java.util.Calendar;

public interface CalendarLayout {
    void setCalendarDateOnClickListener(CalendarClickListener listener);
    GridLayout onCreateLayout(Calendar calendar, String[] weekDay, CalendarClickData calendarClickData,
                              Integer colHeight, ArrayList<CalendarMemo> memoItems, String colorHex,
                              String textColorHex, CalendarClickShape clickBgShape, float memoFontSize,
                              String memoTextColor, float calendarFontSize, String[] dayOfWeekColor, String[] dayOfWeekHeaderColor);
}
