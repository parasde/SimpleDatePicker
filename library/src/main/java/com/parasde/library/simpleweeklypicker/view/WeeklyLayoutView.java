package com.parasde.library.simpleweeklypicker.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.parasde.library.simpledatepicker.R;
import com.parasde.library.simpleweeklypicker.data.WeeklyClickData;
import com.parasde.library.simpleweeklypicker.data.WeeklyData;
import com.parasde.library.simpleweeklypicker.data.WeeklySize;
import com.parasde.library.simpleweeklypicker.listener.WeeklyClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class WeeklyLayoutView implements WeeklyLayout {
    private String[] dayOfWeek = {"SUN", "MON", "TUE", "WED", "THE", "FRI", "SAT"};

    private WeeklyClickListener weeklyClickListener = null;

    private WeeklyClickData weeklyClickData;
    private ArrayList<WeeklyData> weeklyData;

    private Context context;

    WeeklyLayoutView(Context context) {
        this.context = context;
    }

    @Override
    public void setCalendarDateOnClickListener(WeeklyClickListener listener) {
        weeklyClickListener = listener;
    }

    @Override
    public GridLayout onCreateLayout(ArrayList<WeeklyData> weeklyData, String[] weekDay, WeeklyClickData weeklyClickData, WeeklySize size) {
        this.weeklyClickData = weeklyClickData;
        this.weeklyData = weeklyData;
        if(weekDay != null && weekDay.length == 7) {
            dayOfWeek = weekDay;
        }

        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size.rowSize()*7));
        gridLayout.setColumnCount(7);

        // weekly header
        for(int i = 0; i < dayOfWeek.length; i++) {
            TextView tv = new TextView(context);
            tv.setText(dayOfWeek[i]);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            );
            params.height = size.rowSize();
            tv.setLayoutParams(params);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            if(i == 0) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
            } else {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calHeader));
            }

            gridLayout.addView(tv);
        }

        // weekly date
        for(int i = 0; i < weeklyData.size(); i++) {
            TextView tv = new TextView(context);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            );
            params.height = size.rowSize();
            tv.setLayoutParams(params);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setTextColor(ContextCompat.getColor(context, R.color.calDate));
            tv.setText(String.valueOf(weeklyData.get(i).getDate()));

            tv.setOnClickListener(new CalendarDateOnClick(i));
            if(checkSunday(weeklyData.get(i).getYear(), weeklyData.get(i).getMonth()-1, weeklyData.get(i).getDate())) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
            }

            if(clickDataCheck(weeklyData.get(i).getYear(), weeklyData.get(i).getMonth()-1, weeklyData.get(i).getDate())) {
                tv.setBackgroundResource(R.color.skyBlue);
                weeklyClickData.setTv(tv);
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
        return weeklyClickData.getYear() == year && weeklyClickData.getMonth()-1 == month && weeklyClickData.getDate() == date;
    }

    private class CalendarDateOnClick implements View.OnClickListener {
        private int position;
        public CalendarDateOnClick(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View view) {
            if(weeklyClickListener != null)  {
                if(weeklyClickData.getTv() != null) {
                    weeklyClickData.getTv().setBackgroundResource(android.R.color.transparent);
                }

                view.setBackgroundResource(R.color.skyBlue);

                // click date set
                weeklyClickData.setTv((TextView)view);
                weeklyClickData.setYear(weeklyData.get(position).getYear());
                weeklyClickData.setMonth(weeklyData.get(position).getMonth()-1);
                weeklyClickData.setDate(weeklyData.get(position).getDate());

                // 2019.12.01 return month 1 ~ 12
                weeklyClickListener.onClick(weeklyData.get(position).getYear(), weeklyData.get(position).getMonth(), weeklyData.get(position).getDate());
            }
        }

    }
}
