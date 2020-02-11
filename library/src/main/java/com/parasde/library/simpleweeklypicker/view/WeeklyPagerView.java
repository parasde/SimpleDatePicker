package com.parasde.library.simpleweeklypicker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.parasde.library.simpleweeklypicker.data.WeeklyClickData;
import com.parasde.library.simpleweeklypicker.data.WeeklyData;
import com.parasde.library.simpleweeklypicker.data.WeeklyOrientation;
import com.parasde.library.simpleweeklypicker.data.WeeklySize;
import com.parasde.library.simpleweeklypicker.listener.WeeklyClickListener;
import com.parasde.library.simpleweeklypicker.listener.WeeklyOnPageChangeListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 2019. 1. 3.
 * Copy SimpleDatePicker
 */
public class WeeklyPagerView extends ViewPager implements WeeklyPager {
    private WeeklyPagerAdapterChangerListener weeklyPagerAdapterChangerListener = new WeeklyPagerAdapterChangerListener();

    public WeeklyPagerView(@NonNull Context context) {
        super(context);
        // add Page Change Listener
        this.addOnPageChangeListener(weeklyPagerAdapterChangerListener);
    }

    public WeeklyPagerView(@NonNull Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        super(context, attrs);
        // add Page Change Listener
        this.addOnPageChangeListener(weeklyPagerAdapterChangerListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, getHeightMeasureSpec(widthMeasureSpec, heightMeasureSpec));
    }

    // vertical pager
    private class VerticalPager implements ViewPager.PageTransformer {
        @Override
        public void transformPage(@NonNull View page, float position) {
            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 1) {
                page.setAlpha(1);
                page.setTranslationX(page.getWidth() * -position);
                float yPosition = position * page.getHeight();
                page.setTranslationY(yPosition);
            } else {
                page.setAlpha(0);
            }
        }
    }

    /**
     * Page Change Listener
     */
    private class WeeklyPagerAdapterChangerListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(position != 0) {
                if(onPageChangeListener != null) {
                    ArrayList<WeeklyData> fragmentWeeklyData = ((WeeklyFragmentPager)(fragmentAdapter.getItem(position))).getWeeklyData();
                    onPageChangeListener.onChange(fragmentWeeklyData);
                }
            }

            if(position == 0) { // first index..
                // left swipe
                prevCalendar.add(Calendar.DATE, -7);
                prevWeeklyData = getPrevWeeklyData(prevCalendar);
                minPage--;
                fragmentAdapter.addPrevItem(new WeeklyFragmentPager(prevWeeklyData, onClickListener, weeklyClickData, dayOfWeek, weeklySize), minPage + "");
                WeeklyPagerView.super.invalidate();
                WeeklyPagerView.super.setCurrentItem(position+1, false);
            } else if(position == fragmentAdapter.getCount()-1) {    // last index..
                // right swipe
                nextCalendar.add(Calendar.DATE, 7);
                nextWeeklyData = getNextWeeklyData(nextCalendar);
                maxPage++;
                fragmentAdapter.addItem(new WeeklyFragmentPager(nextWeeklyData, onClickListener, weeklyClickData, dayOfWeek, weeklySize), maxPage + "");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private Calendar calendar = (Calendar)Calendar.getInstance().clone();
    private Calendar prevCalendar = (Calendar) calendar.clone();
    private Calendar nextCalendar = (Calendar) calendar.clone();
    private ArrayList<WeeklyData> nowWeeklyData;
    private ArrayList<WeeklyData> prevWeeklyData;
    private ArrayList<WeeklyData> nextWeeklyData;
    private AppCompatActivity activity;
    private WeeklyFragmentPagerAdapter fragmentAdapter;

    private WeeklyOnPageChangeListener onPageChangeListener = null;
    private WeeklyDateOnClick onClickListener = new WeeklyDateOnClick();
    private WeeklyClickListener weeklyClickListener = null;
    private WeeklyClickData weeklyClickData;

    private boolean swipeEnable = true;

    private WeeklyOrientation orientation = WeeklyOrientation.HORIZONTAL;
    private WeeklySize weeklySize = WeeklySize.NORMAL;

    private int maxPage = 2, minPage;
    private String[] dayOfWeek = null;

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

    @Override
    public void setWeeklyPageChangeListener(@NonNull WeeklyOnPageChangeListener listener) {
        onPageChangeListener = listener;
        ArrayList<WeeklyData> fragmentWeeklyData = ((WeeklyFragmentPager)(fragmentAdapter.getItem(this.getCurrentItem()))).getWeeklyData();
        onPageChangeListener.onChange(fragmentWeeklyData);
    }
    @Override
    public void setWeeklyClickListener(@NonNull WeeklyClickListener listener) {
        weeklyClickListener = listener;
    }

    // initialize pager, add calendar fragment + draw calendar
    @Override
    public void init(@NotNull AppCompatActivity activity, @Nullable String[] dayOfWeek, WeeklySize size, @NonNull WeeklyOrientation orientation) {
        this.activity = activity;
        this.weeklySize = size;
        this.orientation = orientation;

        weeklyClickData = new WeeklyClickData();
        onCreatePager(dayOfWeek);
    }

    // initialize pager, set calendar
    // input month 1 ~ 12 value
    @Override
    public void init(@NotNull AppCompatActivity activity, int year, int month, @Nullable String[] dayOfWeek, WeeklySize size, @NonNull WeeklyOrientation orientation) {
        this.activity = activity;
        this.weeklySize = size;
        this.orientation = orientation;
        int mMonth = month-1;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mMonth);

        prevCalendar = (Calendar) calendar.clone();
        nextCalendar = (Calendar) calendar.clone();

        weeklyClickData = new WeeklyClickData();
        onCreatePager(dayOfWeek);
    }

    // parameter date set background color
    // input month 1 ~ 12 value
    @Override
    public void init(@NotNull AppCompatActivity activity, int year, int month, int date, @Nullable String[] dayOfWeek, WeeklySize size, @NonNull WeeklyOrientation orientation) {
        this.activity = activity;
        this.weeklySize = size;
        this.orientation = orientation;
        int mMonth = month-1;

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DATE, date);

        prevCalendar = (Calendar) calendar.clone();
        nextCalendar = (Calendar) calendar.clone();

        weeklyClickData = new WeeklyClickData(null, year, mMonth, date);
        onCreatePager(dayOfWeek);
    }

    private void onCreatePager(String[] dayOfWeek) {
        this.dayOfWeek = dayOfWeek;

        if(orientation == WeeklyOrientation.VERTICAL) {
            setPageTransformer(true, new VerticalPager());
        }

        nowWeeklyData = getWeeklyData(calendar);
        prevWeeklyData = getPrevWeeklyData(calendar);
        nextWeeklyData = getNextWeeklyData(calendar);

        FragmentManager fragment = activity.getSupportFragmentManager();
        fragmentAdapter = new WeeklyFragmentPagerAdapter(fragment, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentAdapter.addItem(new WeeklyFragmentPager(prevWeeklyData, onClickListener, weeklyClickData, dayOfWeek, weeklySize), "prev");
        fragmentAdapter.addItem(new WeeklyFragmentPager(nowWeeklyData, onClickListener, weeklyClickData, dayOfWeek, weeklySize), "main");
        fragmentAdapter.addItem(new WeeklyFragmentPager(nextWeeklyData, onClickListener, weeklyClickData, dayOfWeek, weeklySize), "next");
        this.setAdapter(fragmentAdapter);
        this.setCurrentItem(1, false);
    }

    // initialize current month calendar
    private ArrayList<WeeklyData> getWeeklyData(Calendar cal) {
        Calendar nowMonthCal = (Calendar)cal.clone();
        int nowDate = nowMonthCal.get(Calendar.DATE);
        int nowWeekOfMonth = nowMonthCal.get(Calendar.WEEK_OF_MONTH);
        Calendar lastDayMonth = (Calendar) nowMonthCal.clone();
        lastDayMonth.add(Calendar.MONTH, 1);
        lastDayMonth.set(Calendar.DATE, 1);
        lastDayMonth.add(Calendar.DATE, -1);
//        int nowLastDate = nextMonth.get(Calendar.DATE);
        int lastWeekOfMonth = lastDayMonth.get(Calendar.WEEK_OF_MONTH);

        int firstDayOfWeek;
        ArrayList<WeeklyData> weeklyDataArrayList = new ArrayList<>();
        // 해당 달의 첫째주 일 경우
        if(nowWeekOfMonth == 1) {
            Calendar tempCal = (Calendar) nowMonthCal.clone();
            tempCal.set(Calendar.DATE, 1);
            // 1일의 요일 (1 ~ 7)
            firstDayOfWeek = tempCal.get(Calendar.DAY_OF_WEEK);

            // 이전달 마지막날짜
            tempCal.add(Calendar.DATE, -1);
            int prevMonthLastDate = tempCal.get(Calendar.DATE);
            int prevMonthLastWeekOfMonth = tempCal.get(Calendar.WEEK_OF_MONTH);
            // 이전달 날짜
            for(int i = 1; i < firstDayOfWeek; i ++) {
                weeklyDataArrayList.add(new WeeklyData(tempCal.get(Calendar.YEAR), tempCal.get(Calendar.MONTH), prevMonthLastDate - firstDayOfWeek + 1 + i, prevMonthLastWeekOfMonth));
            }

            // 이번달 날짜
            int date = 1;
            for(int i = firstDayOfWeek; i <= 7; i++) {
                weeklyDataArrayList.add(new WeeklyData(nowMonthCal.get(Calendar.YEAR), nowMonthCal.get(Calendar.MONTH), date, nowWeekOfMonth));
                date++;
            }
        } else if(nowWeekOfMonth == lastWeekOfMonth) {    // 해당 달의 마지막 주
            Calendar nextCal = (Calendar) nowMonthCal.clone();
            nextCal.add(Calendar.MONTH, 1);
            nextCal.set(Calendar.DATE, 1);
            int nextWeeOfMonth = nextCal.get(Calendar.WEEK_OF_MONTH);

            Calendar curCal = (Calendar) nextCal.clone();
            curCal.add(Calendar.DATE, -1);
            // 이번달의 마지막 날
            int lastDate = curCal.get(Calendar.DATE);

            firstDayOfWeek = curCal.get(Calendar.DAY_OF_WEEK);

            // 이번달 날짜
            for(int i = 0; i < firstDayOfWeek; i ++) {
                weeklyDataArrayList.add(new WeeklyData(nowMonthCal.get(Calendar.YEAR), nowMonthCal.get(Calendar.MONTH), lastDate - firstDayOfWeek + i + 1, lastWeekOfMonth));
            }

            // 다음달 날짜
            int date = 1;
            for(int i = firstDayOfWeek; i < 7; i++) {
                weeklyDataArrayList.add(new WeeklyData(nextCal.get(Calendar.YEAR), nextCal.get(Calendar.MONTH), date, nextWeeOfMonth));
                date++;
            }
        } else {
            firstDayOfWeek = nowMonthCal.get(Calendar.DAY_OF_WEEK);

            // 이번달 날짜
            for(int i = 0; i < firstDayOfWeek; i ++) {
                weeklyDataArrayList.add(new WeeklyData(nowMonthCal.get(Calendar.YEAR), nowMonthCal.get(Calendar.MONTH), nowDate - firstDayOfWeek + i + 1, nowWeekOfMonth));
            }

            // 이번달 날짜
            int date = nowDate;
            for(int i = firstDayOfWeek; i < 7; i++) {
                weeklyDataArrayList.add(new WeeklyData(nowMonthCal.get(Calendar.YEAR), nowMonthCal.get(Calendar.MONTH), date+1, nowWeekOfMonth));
                date++;
            }
        }

        return weeklyDataArrayList;
    }

    // initialize prev weekly calendar
    private ArrayList<WeeklyData> getPrevWeeklyData(Calendar cal) {
        Calendar prevWeekCal = (Calendar)cal.clone();
        prevWeekCal.add(Calendar.DATE, -7);
        return getWeeklyData(prevWeekCal);
    }

    // initialize next weekly calendar
    private ArrayList<WeeklyData> getNextWeeklyData(Calendar cal) {
        Calendar nextWeekCal = (Calendar)cal.clone();
        nextWeekCal.add(Calendar.DATE, 7);
        return getWeeklyData(nextWeekCal);
    }

    private class WeeklyDateOnClick implements WeeklyClickListener {
        @Override
        public void onClick(int year, int month, int date) {
            if(weeklyClickListener != null) weeklyClickListener.onClick(year, month, date);
        }
    }

    private MotionEvent swipe(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();
        float x = (ev.getY() / height) * width;
        float y = (ev.getX() / width) * height;
        ev.setLocation(x, y);
        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(swipeEnable) {
            boolean intercepted = super.onInterceptTouchEvent(ev);
            float row = getHeight();
            if(orientation == WeeklyOrientation.VERTICAL && row / 2 > ev.getY()) {
                intercepted = super.onInterceptTouchEvent(swipe(ev));
                swipe(ev);
            }

            return intercepted;
        }else {
            return false;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(swipeEnable) {
            if(orientation == WeeklyOrientation.VERTICAL) {
                return super.onTouchEvent(swipe(ev));
            }else {
                return super.onTouchEvent(ev);
            }
        }else {
            return false;
        }
    }
}
