package com.parasde.library.simpledatepicker.view;

import android.widget.GridLayout;

import com.parasde.library.simpledatepicker.data.CalendarClickData;
import com.parasde.library.simpledatepicker.data.CalendarMemo;
import com.parasde.library.simpledatepicker.listener.CalendarClickListener;

import java.util.ArrayList;
import java.util.Calendar;

public interface CalendarLayout {
    void setCalendarDateOnClickListener(CalendarClickListener listener);
    GridLayout onCreateLayout(Calendar calendar, String[] weekDay, CalendarClickData calendarClickData, Integer colHeight,
                              ArrayList<CalendarMemo> memoItems, String colorHex, String textColorHex, CalendarClickData.Shape clickBgShape, float memoFontSize, float calendarFontSize);
}
