## SimpleDatePicker


### Overview

안드로이드 용 날짜 선택 라이브러리 (샘플 소스 포함)

Simple DatePicker for Android library.

you can download and run this source.

- Use java.util.Calendar
- Use ViewPager, FragmentStatePagerAdapter (Fragment)
- Target SDK 23 ~ 29
  
   
  
*이 라이브러리는 년/월을 표시해주는 헤더가 없기때문에 직접추가해야한다.

this library not include calendar header(Year-Month)  
Use the following method to show the header  

![preview](https://raw.githubusercontent.com/parasde/SimpleDatePicker/master/preview.PNG)

---

### note

version 1.3.8 release  
setBackgroundColorOnClick 예외처리 추가

---

### Usage

__Gradle__
```
implementation 'com.github.parasde:SimpleDatePicker:1.3.8'
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


    <com.parasde.library.simpledatepicker.view.CalendarPagerView
        android:id="@+id/cal3"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

    </com.parasde.library.simpledatepicker.view.CalendarPagerView>
    <com.parasde.library.simpleweeklypicker.view.WeeklyPagerView
        android:id="@+id/weekly2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </com.parasde.library.simpleweeklypicker.view.WeeklyPagerView>

```

---

__SampleActivity.java__

```
// Initialize

public class SampleActivity extends AppCompatActivity {

    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        header = findViewById(R.id.calHeader2);
        CalendarPagerView pagerView = findViewById(R.id.cal3);

        pagerView.init(this, 2019, 12, 5, null, CalendarSize.NORMAL);
        pagerView.setCalendarPageChangeListener(new CalendarOnPageChangeListener() {
            @Override
            public void onChange(int year, int month) {
                if(month < 10) header.setText(year + "-" + "0" + month);
                else header.setText(year + "-" + month);
            }
        });
        
        // touch control
        // cal.onTouchEnable(false)
        
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



```
---