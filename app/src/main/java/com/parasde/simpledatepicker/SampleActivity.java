package com.parasde.simpledatepicker;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parasde.library.simpledatepicker.data.CalendarMemo;
import com.parasde.library.simpledatepicker.data.CalendarSize;
import com.parasde.library.simpledatepicker.listener.CalendarClickListener;
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener;
import com.parasde.library.simpledatepicker.view.CalendarPagerView;
import com.parasde.library.simpleweeklypicker.data.WeeklyData;
import com.parasde.library.simpleweeklypicker.data.WeeklyOrientation;
import com.parasde.library.simpleweeklypicker.data.WeeklySize;
import com.parasde.library.simpleweeklypicker.listener.WeeklyClickListener;
import com.parasde.library.simpleweeklypicker.listener.WeeklyOnPageChangeListener;
import com.parasde.library.simpleweeklypicker.view.WeeklyPagerView;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {

    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        header = findViewById(R.id.calHeader2);
        CalendarPagerView pagerView = findViewById(R.id.cal3);

        pagerView.init(this, 2020, 2, 26, null, CalendarSize.BIG);
        pagerView.setCalendarPageChangeListener(new CalendarOnPageChangeListener() {
            @Override
            public void onChange(int year, int month) {
                if(month < 10) header.setText(year + "-" + "0" + month);
                else header.setText(year + "-" + month);
            }
        });

        pagerView.setCalendarClickListener(new CalendarClickListener() {
            @Override
            public void onClick(int year, int month, int date) {
                Log.d("Calendar Click", year + "-" + month + "-" + date);
            }
        });

        pagerView.setBackgroundColorOnClick("#fff780");
        ArrayList<CalendarMemo> memoList = new ArrayList<>();
        memoList.add(new CalendarMemo(2020, 2, 25, "Hello World, Hello World"));
        memoList.add(new CalendarMemo(2020, 2, 25, "0225"));
        memoList.add(new CalendarMemo(2020, 2, 25, "0225"));

        memoList.add(new CalendarMemo(2020, 2, 26, "World"));
        memoList.add(new CalendarMemo(2020, 2, 27, "Hello"));
        memoList.add(new CalendarMemo(2020, 2, 27, "CalendarPicker"));
        pagerView.setMemo(memoList);
        pagerView.setCalendarFontSize(18);
        pagerView.setMemoFontSize(11);
        pagerView.apply();


        WeeklyPagerView weeklyPagerView = findViewById(R.id.weekly2);
        weeklyPagerView.init(this, null, WeeklySize.NORMAL, WeeklyOrientation.VERTICAL);
        weeklyPagerView.setWeeklyClickListener(new WeeklyClickListener() {
            @Override
            public void onClick(int year, int month, int date) {
                Log.i("Weekly Click", year + ", " + month + ", " + date);
            }
        });
        weeklyPagerView.setWeeklyPageChangeListener(new WeeklyOnPageChangeListener() {
            @Override
            public void onChange(List<WeeklyData> weeklyData) {
                for(WeeklyData data: weeklyData) {
                    Log.i("Weekly Info", data.getYear() + "-" + data.getMonth() + "-" + data.getDate());
                    Log.i("week of month", data.getWeekOfMonth() + "");
                }
            }
        });
    }


}
