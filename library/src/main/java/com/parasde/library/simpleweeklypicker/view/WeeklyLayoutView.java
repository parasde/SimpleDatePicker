package com.parasde.library.simpleweeklypicker.view;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
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
import com.parasde.library.simpleweeklypicker.data.WeeklyStyle;
import com.parasde.library.simpleweeklypicker.listener.WeeklyClickListener;

import java.util.ArrayList;
import java.util.Calendar;

public class WeeklyLayoutView implements WeeklyLayout {
    private String[] dayOfWeek = {"SUN", "MON", "TUE", "WED", "THE", "FRI", "SAT"};

    private WeeklyClickListener weeklyClickListener = null;

    private WeeklyClickData weeklyClickData;
    private ArrayList<WeeklyData> weeklyData;

    private Context context;

    private WeeklyStyle weeklyStyle = WeeklyStyle.DEFAULT;
    private String colorHex;

    WeeklyLayoutView(Context context) {
        this.context = context;
    }

    @Override
    public void setCalendarDateOnClickListener(WeeklyClickListener listener) {
        weeklyClickListener = listener;
    }

    @Override
    public GridLayout onCreateLayout(ArrayList<WeeklyData> weeklyData, String[] weekDay, WeeklyClickData weeklyClickData, WeeklySize size, WeeklyStyle weeklyStyle, float dayOfWeekFontSize, float dateFontSize, String colorHex) {
        this.weeklyClickData = weeklyClickData;
        this.weeklyData = weeklyData;
        if(weekDay != null && weekDay.length == 7) {
            dayOfWeek = weekDay;
        }

        this.colorHex = colorHex;

        this.weeklyStyle = weeklyStyle;

        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, size.rowSize()*7));
        gridLayout.setColumnCount(7);
        if(weeklyStyle == WeeklyStyle.STYLE_1) {
            style_1(weeklyData, weeklyClickData, size, gridLayout, dayOfWeekFontSize, dateFontSize);
        } else {
            defaultStyle(weeklyData, weeklyClickData, size, gridLayout, dayOfWeekFontSize, dateFontSize);
        }

        return gridLayout;
    }

    private void defaultStyle(ArrayList<WeeklyData> weeklyData, WeeklyClickData weeklyClickData, WeeklySize size, GridLayout gridLayout, float dayOfWeekFontSize, float dateFontSize) {
        // weekly header
        for(int i = 0; i < 7; i++) {
            TextView tv = new TextView(context);
            tv.setText(dayOfWeek[i]);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            );
            params.width = 0;
            params.height = size.rowSize();
            tv.setLayoutParams(params);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dayOfWeekFontSize);
            if(i == 0) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
            } else if(i == 6) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calSaturday));
            } else {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calHeader));
            }

            gridLayout.addView(tv);
        }

        // weekly date
        for(int i = 0; i < 7; i++) {
            TextView tv = new TextView(context);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            );
            params.width = 0;
            params.height = size.rowSize();
            tv.setLayoutParams(params);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setTextColor(ContextCompat.getColor(context, R.color.calDate));
            tv.setText(String.valueOf(weeklyData.get(i).getDate()));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dateFontSize);

            tv.setOnClickListener(new CalendarDateOnClick(i));
            if(checkSunday(weeklyData.get(i).getYear(), weeklyData.get(i).getMonth()-1, weeklyData.get(i).getDate())) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
            } else if(i == 6) {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calSaturday));
            }

            if(clickDataCheck(weeklyData.get(i).getYear(), weeklyData.get(i).getMonth()-1, weeklyData.get(i).getDate())) {
                if(colorHex != null) {
                    tv.setBackgroundColor(Color.parseColor(colorHex));
                } else {
                    tv.setBackgroundResource(R.color.skyBlue);
                }
                weeklyClickData.setTv(tv);
            }
            gridLayout.addView(tv);
        }
    }

    /**
     * Style_1
     */
    private void style_1(ArrayList<WeeklyData> weeklyData,  WeeklyClickData weeklyClickData, WeeklySize size, GridLayout gridLayout, float dayOfWeekFontSize, float dateFontSize) {
        for(int i = 0; i < 7; i++) {
            LinearLayout contentLayout = new LinearLayout(context);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            );
            params.width = 0;
            params.height = size.rowSize();
            contentLayout.setLayoutParams(params);
            contentLayout.setGravity(Gravity.CENTER);

            TextView dayOfWeekTv = new TextView(context);
            dayOfWeekTv.setText(dayOfWeek[i]);
            dayOfWeekTv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dayOfWeekTv.setGravity(Gravity.CENTER_VERTICAL);
            dayOfWeekTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dayOfWeekFontSize);
            dayOfWeekTv.setTextColor(ContextCompat.getColor(context, R.color.calDate));
            dayOfWeekTv.setPadding(0, 0, 2, 0);

            TextView dateTv = new TextView(context);
            dateTv.setText(String.valueOf(weeklyData.get(i).getDate()));
            dateTv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dateTv.setGravity(Gravity.CENTER_VERTICAL);
            dateTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dateFontSize);
            dateTv.setTextColor(ContextCompat.getColor(context, R.color.calDate));
            dateTv.setPadding(2, 0, 0, 0);

            if(i == 0) {
                dayOfWeekTv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
                dateTv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
            } else if(i == 6) {
                dayOfWeekTv.setTextColor(ContextCompat.getColor(context, R.color.calSaturday));
                dateTv.setTextColor(ContextCompat.getColor(context, R.color.calSaturday));
            }

            contentLayout.setOnClickListener(new CalendarDateOnClick(i));
            if(clickDataCheck(weeklyData.get(i).getYear(), weeklyData.get(i).getMonth()-1, weeklyData.get(i).getDate())) {
                if(colorHex != null) {
                    contentLayout.setBackgroundColor(Color.parseColor(colorHex));
                } else {
                    contentLayout.setBackgroundResource(R.color.skyBlue);
                }
                weeklyClickData.setLl(contentLayout);
            }

            contentLayout.addView(dayOfWeekTv);
            contentLayout.addView(dateTv);
            gridLayout.addView(contentLayout);
        }
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
        return weeklyClickData.getYear() == year && weeklyClickData.getMonth() == month && weeklyClickData.getDate() == date;
    }

    private class CalendarDateOnClick implements View.OnClickListener {
        private int position;
        CalendarDateOnClick(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View view) {
            if(weeklyClickListener != null)  {
                if(weeklyStyle == WeeklyStyle.STYLE_1) {
                    if(weeklyClickData.getLl() != null) {
                        weeklyClickData.getLl().setBackgroundResource(android.R.color.transparent);
                    }
                    weeklyClickData.setLl((LinearLayout) view);
                } else {
                    if(weeklyClickData.getTv() != null) {
                        weeklyClickData.getTv().setBackgroundResource(android.R.color.transparent);
                    }
                    weeklyClickData.setTv((TextView)view);
                }

                if(colorHex != null) {
                    view.setBackgroundColor(Color.parseColor(colorHex));
                } else {
                    view.setBackgroundResource(R.color.skyBlue);
                }

                // click date set
                weeklyClickData.setYear(weeklyData.get(position).getYear());
                weeklyClickData.setMonth(weeklyData.get(position).getMonth()-1);
                weeklyClickData.setDate(weeklyData.get(position).getDate());

                // 2019.12.01 return month 1 ~ 12
                weeklyClickListener.onClick(weeklyData.get(position).getYear(), weeklyData.get(position).getMonth(), weeklyData.get(position).getDate());
            }
        }

    }
}
