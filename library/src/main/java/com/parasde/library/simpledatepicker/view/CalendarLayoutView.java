package com.parasde.library.simpledatepicker.view;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.parasde.library.simpledatepicker.R;
import com.parasde.library.simpledatepicker.data.CalendarClickData;
import com.parasde.library.simpledatepicker.data.CalendarData;
import com.parasde.library.simpledatepicker.data.CalendarMemo;
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
    private String colorHex;

    private final String numberRegExp = "^[0-9]+$";

    CalendarLayoutView(Context context) {
        this.context = context;
    }

    @Override
    public void setCalendarDateOnClickListener(CalendarClickListener listener) {
        calendarClickListener = listener;
    }

    @Override
    public GridLayout onCreateLayout(Calendar cal, String[] weekDay, CalendarClickData calendarClickData, CalendarSize size,
                                     ArrayList<CalendarMemo> memoItems, String colorHex) {
        Calendar calendar = (Calendar) cal.clone();
        calendarData = new ArrayList<>();
        this.calendarClickData = calendarClickData;
        int curYear = cal.get(Calendar.YEAR);
        int curMonth = cal.get(Calendar.MONTH)+1;

        ArrayList<CalendarMemo> filterMemoItems = new ArrayList<>();
        if(memoItems != null) {
            for(CalendarMemo memo: memoItems) {
                int memoYear = memo.getYear();
                int memoMonth = memo.getMonth();
                if(curYear == memoYear && curMonth == memoMonth) {
                    filterMemoItems.add(memo);
                }
            }
        }

        this.colorHex = colorHex;

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
            LinearLayout tvLinear = new LinearLayout(context);
            tvLinear.setOrientation(LinearLayout.VERTICAL);
            // set Linear Layout
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            );
            params.height = size.colSize();
            tvLinear.setLayoutParams(params);
            tvLinear.setPadding(3, 8, 3, 8);

            TextView memoTv = new TextView(context);
            // set TextView
            LinearLayout.LayoutParams memoTvParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            memoTvParam.weight = 1;

            memoTv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            memoTv.setTextColor(ContextCompat.getColor(context, R.color.calMemo));
            memoTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12f);
            if(dateArray.get(i).matches(numberRegExp)) {
                int memoCount = 0;
                for(CalendarMemo memo: filterMemoItems) {
                    int memoYear = memo.getYear();
                    int memoMonth = memo.getMonth();
                    int memoDate = memo.getDate();
                    if(curYear == memoYear && curMonth == memoMonth && memoDate == Integer.parseInt(dateArray.get(i))) {
                        memoCount++;
                        String text = memo.getContent();
                        if(text.length() > 5) {
                            text = text.substring(0, 5) + "...";
                        }
                        text += "\n" + memoTv.getText().toString();
                        memoTv.setText(text);
                        if(size == CalendarSize.BIG) {
                            if(memoCount == 2) {
                                break;
                            }
                        } else {
                            if(memoCount == 1) {
                                break;
                            }
                        }
                    }
                }
            }


            TextView tv = new TextView(context);
            tv.setText(dateArray.get(i));
            // set TextView
            LinearLayout.LayoutParams tvParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            tv.setLayoutParams(tvParam);

            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            tv.setGravity(Gravity.CENTER_VERTICAL);

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
//                    tv.setBackgroundResource(R.color.skyBlue);
                    if(colorHex != null) {
                        tvLinear.setBackgroundColor(Color.parseColor(colorHex));
                    } else {
                        tvLinear.setBackgroundResource(R.color.skyBlue);
                    }
                    calendarClickData.setLayout(tvLinear);
                }
            }

//            gridLayout.addView(tv);
            tvLinear.addView(tv);
            tvLinear.addView(memoTv);
            gridLayout.addView(tvLinear);
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
                if(calendarClickData.getLayout() != null) {
                    calendarClickData.getLayout().setBackgroundResource(android.R.color.transparent);
                }

                if(colorHex != null) {
                    ((LinearLayout)view.getParent()).setBackgroundColor(Color.parseColor(colorHex));
                } else {
                    ((LinearLayout)view.getParent()).setBackgroundResource(R.color.skyBlue);
                }

                // click date set
                calendarClickData.setLayout(((LinearLayout)view.getParent()));
                calendarClickData.setYear(calendarData.get(position).getYear());
                calendarClickData.setMonth(calendarData.get(position).getMonth());
                calendarClickData.setDate(calendarData.get(position).getDate());

                // 2019.12.01 return month 1 ~ 12
                calendarClickListener.onClick(calendarData.get(position).getYear(), calendarData.get(position).getMonth()+1, calendarData.get(position).getDate());
            }
        }

    }
}
