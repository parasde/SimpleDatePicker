## Simple Calendar


### Overview

안드로이드 용 캘린더 라이브러리 (샘플 소스 포함)  

Calendar library for Android  

- Use java.util.Calendar
- Use ViewPager, FragmentStatePagerAdapter (Fragment)
- Target SDK 23 ~ 29
  
   
  
*이 라이브러리는 년/월을 표시해주는 헤더가 없기때문에 직접추가해야한다.

this library not include calendar header(Year-Month)  
Use the following method to show the header  

![preview](https://github.com/parasde/simple-calendar/blob/master/preview.png)

---

### note

version 2.4.10 release  

Calendar, WeeklyCalendar 요일 색상변경 추가
 - setDayOfWeekTextColor
 - setDayOfWeekHeaderTextColor

---

### Usage

__Gradle__
```
implementation 'com.github.parasde:simple-calendar:2.4.10'
```

__activity_sample.xml__

```

    <TextView
        android:id="@+id/calHeader2"
        android:layout_width="match_parent"
        android:layout_height="48dp"
        android:textColor="@color/calHeader"
        android:layout_marginTop="10dp"
        android:textSize="24dp"
        android:textAlignment="center"/>


    <com.parasde.library.simplecalendar.view.CalendarPagerView
        android:id="@+id/cal2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </com.parasde.library.simplecalendar.view.CalendarPagerView>

    <com.parasde.library.simpleweeklycalendar.view.WeeklyPagerView
        android:id="@+id/weekly2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </com.parasde.library.simpleweeklycalendar.view.WeeklyPagerView>

    <com.parasde.library.simpleweeklycalendar.view.WeeklyPagerView
        android:id="@+id/weekly3"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </com.parasde.library.simpleweeklycalendar.view.WeeklyPagerView>

```

---

__SampleActivity.java__

```

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

```
---
