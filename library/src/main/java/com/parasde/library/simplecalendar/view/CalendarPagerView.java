package com.parasde.library.simplecalendar.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.parasde.library.simplecalendar.data.CalendarClickData;
import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simplecalendar.listener.CalendarClickListener;
import com.parasde.library.simplecalendar.listener.CalendarOnPageChangeListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarPagerView extends ViewPager implements CalendarPager {
    private CalendarPagerAdapterChangeListener calendarPagerAdapterChangeListener = new CalendarPagerAdapterChangeListener();

    public CalendarPagerView(@NonNull Context context) {
        super(context);
        // add Page Change Listener
        this.addOnPageChangeListener(calendarPagerAdapterChangeListener);
    }

    public CalendarPagerView(@NonNull Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        // add Page Change Listener
        this.addOnPageChangeListener(calendarPagerAdapterChangeListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, getHeightMeasureSpec(widthMeasureSpec, heightMeasureSpec));
    }

    private class CalendarPagerAdapterChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * left and right swipe pager selected listener
         * first index pager -> prev month calendar fragment add and remake adapter
         * last index pager -> next month calendar fragment add and notifyDataSetChanged . apply
         */
        @Override
        public void onPageSelected(int position) {
            if(position != 0) {
                if(onPageChangeListener != null) {
                    Calendar fragmentCalendar = ((CalendarFragmentPager)(fragmentAdapter.getItem(position))).getCalendar();
                    // 2019.12.01 return month 1 ~ 12
                    onPageChangeListener.onChange(
                            fragmentCalendar.get(Calendar.YEAR),
                            fragmentCalendar.get(Calendar.MONTH)+1  // return - 1 ~ 12 month
                    );
                }
            }

            if(position == 0) { // first index..
                // left swipe
                calendar.add(Calendar.MONTH, -1);
                minPrevMonth();
                fragmentAdapter.addPrevItem(new CalendarFragmentPager(prevCalendar, onClickListener,
                        calendarClickData, dayOfWeek, colHeight, memoItems, colorHex, textColorHex,
                        clickBgShape, memoFontSize, memoTextColor, calendarFontSize, dayOfWeekColor, dayOfWeekHeaderColor), position + "");
                CalendarPagerView.super.invalidate();
                CalendarPagerView.super.setCurrentItem(position+1, false);
            } else if(position == fragmentAdapter.getCount()-1) {    // last index..
                // right swipe
                calendar.add(Calendar.MONTH, 2);
                addNextMonth();
                fragmentAdapter.addItem(new CalendarFragmentPager(nextCalendar, onClickListener,
                        calendarClickData, dayOfWeek, colHeight, memoItems, colorHex, textColorHex,
                        clickBgShape, memoFontSize, memoTextColor, calendarFontSize, dayOfWeekColor, dayOfWeekHeaderColor), position + "");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private Calendar calendar = (Calendar)Calendar.getInstance().clone();
    private Calendar prevCalendar = prevMonthCalendar();
    private Calendar nextCalendar = nextMonthCalendar();
    private AppCompatActivity activity;
    private CalendarFragmentPagerAdapter fragmentAdapter;

    private CalendarOnPageChangeListener onPageChangeListener = null;
    private CalendarDateOnClick onClickListener = new CalendarDateOnClick();

    private CalendarClickListener calendarOnClickListener = null;

    private CalendarClickData calendarClickData;

    private boolean swipeEnable = true;

    private String[] dayOfWeek = null;
    private Integer colHeight = null;

    private ArrayList<CalendarMemo> memoItems = null;
    private String colorHex = null;
    private String textColorHex = null;
    private String memoTextColor = null;
    private CalendarClickShape clickBgShape = null;
    private float memoFontSize = 10f;
    private float calendarFontSize = 17f;
    private String[] dayOfWeekColor = new String[3];
    private String[] dayOfWeekHeaderColor = new String[3];

    // get child view height
    private int getHeightMeasureSpec(int widthMeasureSpec, int heightMeasureSpec) {
        int hm = heightMeasureSpec;
        for(int i = 0; i < this.getChildCount(); i++) {
            View v = this.getChildAt(i);
            if(v != null) {
                int height = 0;
                v.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.UNSPECIFIED));
                int viewHeight = v.getMeasuredHeight();
                if(viewHeight > height) {
                    height = viewHeight;
                }
                hm = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            }
        }
        return hm;
    }

    // pager select calendar year, month
    // 2019.12.01 return month 1 ~ 12
    // select calendar year, month, date
    @Override
    public void setCalendarPageChangeListener(@NonNull CalendarOnPageChangeListener listener) {
        onPageChangeListener = listener;
        Calendar fragmentCalendar = ((CalendarFragmentPager)(fragmentAdapter.getItem(this.getCurrentItem()))).getCalendar();
        onPageChangeListener.onChange(
                fragmentCalendar.get(Calendar.YEAR),
                fragmentCalendar.get(Calendar.MONTH)+1  // return - 1 ~ 12 month
        );
    }
    @Override
    public void setCalendarClickListener(@NonNull CalendarClickListener listener) {
        calendarOnClickListener = listener;
    }

    @Override
    public void setCalendarFontSize(float size) {
        if(size > 0 && size <= 18) {
            calendarFontSize = size;
        } else {
            Log.e("Invalid Range", "Calendar FontSize Range : 1~18");
        }
    }

    @Override
    public void setMemoFontSize(float size) {
        if(size > 0 && size <= 10) {
            memoFontSize = size;
        } else {
            Log.e("Invalid Range", "Memo FontSize Range : 1~10");
        }
    }

    @Override
    public void setMemoTextColor(String colorHex) {
        try {
            Color.parseColor(colorHex);
            this.memoTextColor = colorHex;
        } catch (IllegalArgumentException e) {
            Log.e("ColorParse Error", "Unknown color");
        }
    }

    @Override
    public void apply() {
        onCreatePager();
    }

    @Override
    public int getClickYear() {
        return calendarClickData.getYear();
    }

    @Override
    public int getClickMonth() {
        return calendarClickData.getMonth()+1;
    }

    @Override
    public int getClickDate() {
        return calendarClickData.getDate();
    }

    // initialize pager, add calendar fragment + draw calendar
    @Override
    public void init(@NotNull AppCompatActivity activity, @Nullable String[] dayOfWeek) {
        this.activity = activity;

        calendar = (Calendar)Calendar.getInstance().clone();
        prevCalendar = (Calendar) calendar.clone();
        nextCalendar = (Calendar) calendar.clone();

        calendarClickData = new CalendarClickData();
        this.dayOfWeek = dayOfWeek;
        onCreatePager();
    }

    // initialize pager, set calendar
    // input month 1 ~ 12 value
    @Override
    public void init(@NotNull AppCompatActivity activity, int year, int month, @Nullable String[] dayOfWeek) {
        this.activity = activity;
        int mMonth = month-1;

        calendar = (Calendar)Calendar.getInstance().clone();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DATE, 1);

        prevCalendar = prevMonthCalendar();
        nextCalendar = nextMonthCalendar();

        calendarClickData = new CalendarClickData();

        this.dayOfWeek = dayOfWeek;
        onCreatePager();
    }

    // parameter date set background color
    // input month 1 ~ 12 value
    @Override
    public void init(@NotNull AppCompatActivity activity, int year, int month, int date, @Nullable String[] dayOfWeek) {
        this.activity = activity;
        int mMonth = month-1;

        calendar = (Calendar)Calendar.getInstance().clone();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DATE, date);

        prevCalendar = prevMonthCalendar();
        nextCalendar = nextMonthCalendar();

        calendarClickData = new CalendarClickData(null, year, mMonth, date);

        this.dayOfWeek = dayOfWeek;
        onCreatePager();
    }

    @Override
    public void setColHeight(int height) {
        this.colHeight = height;
    }

    @Override
    public void setDayOfWeekTextColor(String sundayColor, String weekdayColor, String saturdayColor) {
        try {
            if(sundayColor != null) {
                Color.parseColor(sundayColor);
                dayOfWeekColor[0] = sundayColor;
            }
            if(weekdayColor != null) {
                Color.parseColor(weekdayColor);
                dayOfWeekColor[1] = weekdayColor;
            }
            if(saturdayColor != null) {
                Color.parseColor(saturdayColor);
                dayOfWeekColor[2] = saturdayColor;
            }
        } catch (IllegalArgumentException e) {
            Log.e("ColorParse Error", "Unknown color");
        }
    }

    @Override
    public void setDayOfWeekHeaderTextColor(String sundayColor, String weekdayColor, String saturdayColor) {
        try {
            if(sundayColor != null) {
                Color.parseColor(sundayColor);
                dayOfWeekHeaderColor[0] = sundayColor;
            }
            if(weekdayColor != null) {
                Color.parseColor(weekdayColor);
                dayOfWeekHeaderColor[1] = weekdayColor;
            }
            if(saturdayColor != null) {
                Color.parseColor(saturdayColor);
                dayOfWeekHeaderColor[2] = saturdayColor;
            }
        } catch (IllegalArgumentException e) {
            Log.e("ColorParse Error", "Unknown color");
        }
    }

    @Override
    public void setMemo(ArrayList<CalendarMemo> memoItems) {
        this.memoItems = memoItems;
    }

    @Override
    public void setTextColorOnClick(String colorHex) {
        try {
            Color.parseColor(colorHex);
            this.textColorHex = colorHex;
        } catch (IllegalArgumentException e) {
            Log.e("ColorParse Error", "Unknown color");
        }
    }

    @Override
    public void setBackgroundColorOnClick(String colorHex) {
        try {
            Color.parseColor(colorHex);
            this.colorHex = colorHex;
        } catch (IllegalArgumentException e) {
            Log.e("ColorParse Error", "Unknown color");
        }
    }

    @Override
    public void setBackgroundColorOnClick(String colorHex, CalendarClickShape shape) {
        try {
            Color.parseColor(colorHex);
            this.colorHex = colorHex;
            this.clickBgShape = shape;
        } catch (IllegalArgumentException e) {
            Log.e("ColorParse Error", "Unknown color");
        }
    }

    private void onCreatePager() {
        FragmentManager fragment = activity.getSupportFragmentManager();
        fragmentAdapter = new CalendarFragmentPagerAdapter(fragment, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentAdapter.addItem(new CalendarFragmentPager(prevCalendar, onClickListener, calendarClickData, dayOfWeek, colHeight,
                memoItems, colorHex, textColorHex, clickBgShape, memoFontSize, memoTextColor, calendarFontSize, dayOfWeekColor, dayOfWeekHeaderColor), "0");

        fragmentAdapter.addItem(new CalendarFragmentPager(calendar, onClickListener, calendarClickData, dayOfWeek, colHeight,
                memoItems, colorHex, textColorHex, clickBgShape, memoFontSize, memoTextColor, calendarFontSize, dayOfWeekColor, dayOfWeekHeaderColor), "1");

        fragmentAdapter.addItem(new CalendarFragmentPager(nextCalendar, onClickListener, calendarClickData, dayOfWeek, colHeight,
                memoItems, colorHex, textColorHex, clickBgShape, memoFontSize, memoTextColor, calendarFontSize, dayOfWeekColor, dayOfWeekHeaderColor), "2");
        this.setAdapter(fragmentAdapter);
        this.setCurrentItem(1, false);
    }

    // initialize prev month calendar
    private Calendar prevMonthCalendar() {
        Calendar prevMontCal = (Calendar)calendar.clone();
        prevMontCal.add(Calendar.MONTH, -1);
        return prevMontCal;
    }

    // initialize next month calendar
    private Calendar nextMonthCalendar() {
        Calendar nextMontCal = (Calendar)calendar.clone();
        nextMontCal.add(Calendar.MONTH, 1);
        return nextMontCal;
    }

    // first index pager -> get prev month calendar cast
    private void minPrevMonth() {
        prevCalendar.add(Calendar.MONTH, -1);
    }

    // last index pager -> get next month calendar cast
    private void addNextMonth() {
        nextCalendar.add(Calendar.MONTH, 1);
    }

    private class CalendarDateOnClick implements CalendarClickListener {
        @Override
        public void onClick(int year, int month, int date) {
            if(calendarOnClickListener != null) calendarOnClickListener.onClick(year, month, date);
        }
    }


    @Override
    public void onSwipeEnable(boolean enable) {
        swipeEnable = enable;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(swipeEnable) {
            return super.onInterceptTouchEvent(ev);
        }else {
            return false;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(swipeEnable) {
            return super.onTouchEvent(ev);
        }else {
            return false;
        }
    }
}
