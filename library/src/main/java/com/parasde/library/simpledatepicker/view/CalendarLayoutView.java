package com.parasde.library.simpledatepicker.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.parasde.library.simpledatepicker.R;
import com.parasde.library.simpledatepicker.data.CalendarClickData;
import com.parasde.library.simpledatepicker.data.CalendarData;
import com.parasde.library.simpledatepicker.data.CalendarSize;
import com.parasde.library.simpledatepicker.listener.CalendarClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class CalendarLayoutView implements CalendarLayout {
    private String[] dayOfWeek = {"SUN", "MON", "TUE", "WED", "THE", "FRI", "SAT"};

    private CalendarClickListener calendarClickListener = null;

    private CalendarClickData calendarClickData;
    private ArrayList<CalendarData> calendarData;

    private Context context;

    CalendarLayoutView(Context context) {
        this.context = context;
    }

    @Override
    public void setCalendarDateOnClickListener(CalendarClickListener listener) {
        calendarClickListener = listener;
    }

    @Override
    public GridLayout onCreateLayout(Calendar cal, String[] weekDay, CalendarClickData calendarClickData, CalendarSize size) {
        Calendar calendar = (Calendar) cal.clone();
        calendarData = new ArrayList<>();
        this.calendarClickData = calendarClickData;

        if(weekDay != null && weekDay.length == 7) {
            dayOfWeek = weekDay;
        }

        if(size == null) {
            size = CalendarSize.NORMAL;
        }

        // add date
        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size.colSize()*7));
        gridLayout.setColumnCount(7);

        // get first date of month
        Calendar firstDayCalendar = (Calendar) calendar.clone();
        firstDayCalendar.set(Calendar.DATE, 1);
        int firstWeekDay = firstDayCalendar.get(Calendar.DAY_OF_WEEK);

        // get last date of month
        Calendar lastDayCalendar = (Calendar) calendar.clone();
        lastDayCalendar.add(Calendar.MONTH, 1);
        lastDayCalendar.set(Calendar.DATE, 1);
        lastDayCalendar.add(Calendar.DATE, -1);
        int lastDate = lastDayCalendar.get(Calendar.DATE);

        int blankDate = firstWeekDay - 1;

        // sun ~ sat day
        ArrayList<String> dateArray = new ArrayList<>(Arrays.asList(dayOfWeek));

        // prev calendar blank date
        for(int i = 0; i < blankDate; i++) {
            dateArray.add(" ");
        }

        // calendar date
        for(int i = 1; i <=lastDate; i++) {
            dateArray.add(i + "");
        }

        for(int i = 0; i < dateArray.size(); i++) {
            TextView tv = new TextView(context);
            tv.setText(dateArray.get(i));
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            );
            params.height = size.colSize();
            tv.setLayoutParams(params);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setGravity(Gravity.CENTER_VERTICAL);

            if(i == 0) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
            } else if(i < 7) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calHeader));
            } else if(i >= (blankDate+7)) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calDate));
                tv.setOnClickListener(new CalendarDateOnClick(i-blankDate-7));
                calendarData.add(new CalendarData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(dateArray.get(i))));
                if(checkSunday(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(dateArray.get(i)))) {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
                }

                if(clickDataCheck(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(dateArray.get(i)))) {
                    tv.setBackgroundResource(R.color.skyBlue);
                    calendarClickData.setTv(tv);
                }
            }

            gridLayout.addView(tv);
        }

        return gridLayout;
    }

    // sunday check
    private boolean checkSunday(int year, int month, int date) {
        Calendar cal = (Calendar)Calendar.getInstance().clone();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, date);
        return cal.get(Calendar.DAY_OF_WEEK) == 1;
    }

    // click date check
    private boolean clickDataCheck(int year, int month, int date) {
        return calendarClickData.getYear() == year && calendarClickData.getMonth() == month && calendarClickData.getDate() == date;
    }

    private class CalendarDateOnClick implements View.OnClickListener {
        private int position;
        public CalendarDateOnClick(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View view) {
            if(calendarClickListener != null)  {
                if(calendarClickData.getTv() != null) {
                    calendarClickData.getTv().setBackgroundResource(android.R.color.transparent);
                }

                view.setBackgroundResource(R.color.skyBlue);

                // click date set
                calendarClickData.setTv((TextView)view);
                calendarClickData.setYear(calendarData.get(position).getYear());
                calendarClickData.setMonth(calendarData.get(position).getMonth());
                calendarClickData.setDate(calendarData.get(position).getDate());

                // 2019.12.01 return month 1 ~ 12
                calendarClickListener.onClick(calendarData.get(position).getYear(), calendarData.get(position).getMonth()+1, calendarData.get(position).getDate());
            }
        }

    }
}
