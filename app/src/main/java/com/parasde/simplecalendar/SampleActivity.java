package com.parasde.simplecalendar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simplecalendar.listener.CalendarClickListener;
import com.parasde.library.simplecalendar.listener.CalendarOnPageChangeListener;
import com.parasde.library.simplecalendar.view.CalendarPagerView;
import com.parasde.library.simpleweeklycalendar.data.WeeklyData;
import com.parasde.library.simpleweeklycalendar.data.WeeklyOrientation;
import com.parasde.library.simpleweeklycalendar.data.WeeklyStyle;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyClickListener;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyOnPageChangeListener;
import com.parasde.library.simpleweeklycalendar.view.WeeklyPagerView;

import java.util.ArrayList;
import java.util.List;

public class SampleActivity extends AppCompatActivity {

    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        header = findViewById(R.id.calHeader2);
        CalendarPagerView pagerView = findViewById(R.id.cal2);

        pagerView.init(this, 2020, 5, 21, null);
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

        pagerView.setColHeight(100);
        pagerView.setTextColorOnClick("#ffffff");

        pagerView.setBackgroundColorOnClick("#4781FF", CalendarClickShape.CIRCLE);
        ArrayList<CalendarMemo> memoList = new ArrayList<>();
        memoList.add(new CalendarMemo(2020, 5, 21, "Hello World"));
        memoList.add(new CalendarMemo(2020, 5, 20, "Hello..."));
        memoList.add(new CalendarMemo(2020, 5, 23, "World..."));
        pagerView.setMemo(memoList);
        pagerView.setMemoTextColor("#000000");
        pagerView.apply();


        WeeklyPagerView weeklyPagerView = findViewById(R.id.weekly2);
        weeklyPagerView.init(this, 2020, 5, 21, null, WeeklyOrientation.VERTICAL);
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

        weeklyPagerView.setColHeight(50);
        weeklyPagerView.setTextColorOnClick("#ffffff");
        weeklyPagerView.setBackgroundColorOnClick("#4781FF", CalendarClickShape.CIRCLE);
        weeklyPagerView.setMemo(memoList);
        weeklyPagerView.setMemoTextColor("#000000");
        weeklyPagerView.apply();


        WeeklyPagerView weeklyPagerView3 = findViewById(R.id.weekly3);
        weeklyPagerView3.init(this, 2020, 5, 21,  new String[]{"일", "월", "화", "수", "목", "금", "토"}, WeeklyOrientation.VERTICAL);
        weeklyPagerView3.setColHeight(50);
        weeklyPagerView3.setTextColorOnClick("#ffffff");
        weeklyPagerView3.setWeeklyCalendarStyle(WeeklyStyle.STYLE_1);
        weeklyPagerView3.setBackgroundColorOnClick("#4781FF", CalendarClickShape.CIRCLE);
        weeklyPagerView3.setMemoTextColor("#000000");
        weeklyPagerView3.apply();
    }
}
