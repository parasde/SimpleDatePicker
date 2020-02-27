package com.parasde.library.simpledatepicker.view;

import android.widget.GridLayout;

import com.parasde.library.simpledatepicker.data.CalendarClickData;
import com.parasde.library.simpledatepicker.data.CalendarMemo;
import com.parasde.library.simpledatepicker.data.CalendarSize;
import com.parasde.library.simpledatepicker.listener.CalendarClickListener;

import java.util.ArrayList;
import java.util.Calendar;

public interface CalendarLayout {
    void setCalendarDateOnClickListener(CalendarClickListener listener);
    GridLayout onCreateLayout(Calendar calendar, String[] weekDay, CalendarClickData calendarClickData, CalendarSize size,
                              ArrayList<CalendarMemo> memoItems, String colorHex, float memoFontSize, float calendarFontSize);
}
