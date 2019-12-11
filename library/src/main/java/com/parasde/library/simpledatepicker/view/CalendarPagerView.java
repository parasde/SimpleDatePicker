package com.parasde.library.simpledatepicker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.parasde.library.simpledatepicker.data.CalendarClickData;
import com.parasde.library.simpledatepicker.data.CalendarSize;
import com.parasde.library.simpledatepicker.listener.CalendarClickListener;
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Calendar;

public class CalendarPagerView extends ViewPager implements CalendarPager {
    public CalendarPagerView(@NonNull Context context) {
        super(context);
    }

    public CalendarPagerView(@NonNull Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, getHeightMeasureSpec(widthMeasureSpec, heightMeasureSpec));
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

    // initialize pager, add calendar fragment + draw calendar
    @Override
    public void init(@NotNull AppCompatActivity activity, @Nullable String[] dayOfWeek, CalendarSize size) {
        this.activity = activity;

        calendarClickData = new CalendarClickData();
        onCreatePager(dayOfWeek, size);
    }

    // initialize pager, set calendar
    // input month 1 ~ 12 value
    @Override
    public void init(@NotNull AppCompatActivity activity, int year, int month, @Nullable String[] dayOfWeek, CalendarSize size) {
        this.activity = activity;
        int mMonth = month-1;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mMonth);
        prevCalendar = prevMonthCalendar();
        nextCalendar = nextMonthCalendar();

        calendarClickData = new CalendarClickData();
        onCreatePager(dayOfWeek, size);
    }

    // parameter date set background color
    // input month 1 ~ 12 value
    @Override
    public void init(@NotNull AppCompatActivity activity, int year, int month, int date, @Nullable String[] dayOfWeek, CalendarSize size) {
        this.activity = activity;
        int mMonth = month-1;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mMonth);
        prevCalendar = prevMonthCalendar();
        nextCalendar = nextMonthCalendar();

        calendarClickData = new CalendarClickData(null, year, mMonth, date);
        onCreatePager(dayOfWeek, size);
    }

    private void onCreatePager(final String[] dayOfWeek, final CalendarSize size) {
        FragmentManager fragment = activity.getSupportFragmentManager();
        fragmentAdapter = new CalendarFragmentPagerAdapter(fragment, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentAdapter.addItem(new CalendarFragmentPager(prevCalendar, onClickListener, calendarClickData, dayOfWeek, size), "0");
        fragmentAdapter.addItem(new CalendarFragmentPager(calendar, onClickListener, calendarClickData, dayOfWeek, size), "1");
        fragmentAdapter.addItem(new CalendarFragmentPager(nextCalendar, onClickListener, calendarClickData, dayOfWeek, size), "2");
        this.setAdapter(fragmentAdapter);
        this.setCurrentItem(1, false);

        this.addOnPageChangeListener(new OnPageChangeListener() {
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
                    fragmentAdapter.addPrevItem(new CalendarFragmentPager(prevCalendar, onClickListener, calendarClickData, dayOfWeek, size), "$position");
                    CalendarPagerView.super.invalidate();
                    CalendarPagerView.super.setCurrentItem(position+1, false);
                } else if(position == fragmentAdapter.getCount()-1) {    // last index..
                    // right swipe
                    calendar.add(Calendar.MONTH, 2);
                    addNextMonth();
                    fragmentAdapter.addItem(new CalendarFragmentPager(nextCalendar, onClickListener, calendarClickData, dayOfWeek, size), "$position");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
