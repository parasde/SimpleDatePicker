## SimpleDatePicker ##


### Overview ###

안드로이드 용 날짜 선택 라이브러리 (샘플 소스 포함)

Simple DatePicker for Android library.

you can download and run this source.

- Use java.util.Calendar
- Use ViewPager, FragmentStatePagerAdapter (Fragment)
- Target SDK 23 ~ 29
  
   
  
이 라이브러리는 년/월을 표시해주는 헤더가 없기 때문에 따로 TextView 등의 위젯을 추가하여 보여줘야한다.

this library not include calendar header(Year-Month)  
Use the following method to show the header  

![preview](https://raw.githubusercontent.com/parasde/SimpleDatePicker/master/preview.PNG)

---

### note ###

- version 1.0.2 release  
CalendarPagerView available in ScrollView     
Remove duplicate code  
change DayOfWeek name(Korean -> English)  
---

### Usage ###
__layout.xml__

```

implementation 'com.github.parasde:SimpleDatePicker:1.0.2'

```


```

<TextView
    android:id="@+id/calHeader"
    android:layout_width="match_parent"
    android:layout_height="48dp"
    android:textColor="@color/calHeader"
    android:layout_marginTop="10dp"
    android:textSize="24dp"
    android:textAlignment="center"/>

<com.parasde.library.simpledatepicker.view.CalendarPagerView
    android:id="@+id/cal"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
</com.parasde.library.simpledatepicker.view.CalendarPagerView>

```

---

__MainActivity.kt__

```
// Initialize
cal.init(this as AppCompatActivity)

// pageChange -> callback change year, month value
cal.setCalendarPageChangeListener(object:
    CalendarOnPageChangeListener {
        override fun onChange(year: Int, month: Int) {
            // calHeader (TextView) direct set header text
            if(month < 9) calHeader.text = "$year - 0${month+1}"
            else calHeader.text = "$year - ${month+1}"
        }
        })

// callback click item value
cal.setCalendarClickListener(object: CalendarClickListener {
    override fun onClick(year: Int, month: Int, date: Int) {
        Toast.makeText(applicationContext, "$year ${month+1} $date", Toast.LENGTH_SHORT).show()
    }
    })
```
---