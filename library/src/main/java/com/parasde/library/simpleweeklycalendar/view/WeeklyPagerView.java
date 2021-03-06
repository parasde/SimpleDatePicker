package com.parasde.library.simpleweeklycalendar.view;

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

import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simpleweeklycalendar.data.WeeklyClickData;
import com.parasde.library.simpleweeklycalendar.data.WeeklyData;
import com.parasde.library.simpleweeklycalendar.data.WeeklyOrientation;
import com.parasde.library.simpleweeklycalendar.data.WeeklyStyle;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyClickListener;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyOnPageChangeListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * 2019. 1. 3.
 * Copy SimpleCalendar
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
                fragmentAdapter.addPrevItem(new WeeklyFragmentPager(onClickListener,
                        weeklyClickData, dayOfWeek, colHeight, prevWeeklyData, weeklyStyle,
                        dayOfWeekFontSize, dateFontSize, colorHex, textColorHex, memoItems, memoTextColor, memoFontSize, clickBgShape, dayOfWeekColor, dayOfWeekHeaderColor), minPage + "");
                WeeklyPagerView.super.invalidate();
                WeeklyPagerView.super.setCurrentItem(position+1, false);
            } else if(position == fragmentAdapter.getCount()-1) {    // last index..
                // right swipe
                nextCalendar.add(Calendar.DATE, 7);
                nextWeeklyData = getNextWeeklyData(nextCalendar);
                maxPage++;
                fragmentAdapter.addItem(new WeeklyFragmentPager(onClickListener,
                        weeklyClickData, dayOfWeek, colHeight, nextWeeklyData, weeklyStyle,
                        dayOfWeekFontSize, dateFontSize, colorHex, textColorHex, memoItems, memoTextColor, memoFontSize, clickBgShape, dayOfWeekColor, dayOfWeekHeaderColor), maxPage + "");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private Calendar calendar;
    private Calendar prevCalendar;
    private Calendar nextCalendar;
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

    private int maxPage = 2, minPage;
    private String[] dayOfWeek = null;

    private WeeklyStyle weeklyStyle = WeeklyStyle.DEFAULT;
    private float dateFontSize = 12f;
    private float dayOfWeekFontSize = 12f;

    private Integer colHeight = null;
    private ArrayList<CalendarMemo> memoItems = null;
    private String colorHex;
    private String textColorHex = null;
    private String memoTextColor = null;
    private CalendarClickShape clickBgShape = null;
    private float memoFontSize = 10f;
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
    public void init(@NotNull AppCompatActivity activity, @Nullable String[] dayOfWeek, @NonNull WeeklyOrientation orientation) {
        this.activity = activity;
        this.orientation = orientation;

        calendar = (Calendar)Calendar.getInstance().clone();

        prevCalendar = (Calendar) calendar.clone();
        nextCalendar = (Calendar) calendar.clone();

        weeklyClickData = new WeeklyClickData();
        this.dayOfWeek = dayOfWeek;
        onCreatePager();
    }

    // initialize pager, set calendar
    // input month 1 ~ 12 value
    @Override
    public void init(@NotNull AppCompatActivity activity, int year, int month, @Nullable String[] dayOfWeek, @NonNull WeeklyOrientation orientation) {
        this.activity = activity;
        this.orientation = orientation;
        int mMonth = month-1;

        calendar = (Calendar)Calendar.getInstance().clone();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DATE, 1);

        prevCalendar = (Calendar) calendar.clone();
        nextCalendar = (Calendar) calendar.clone();

        weeklyClickData = new WeeklyClickData();
        this.dayOfWeek = dayOfWeek;
        onCreatePager();
    }

    // parameter date set background color
    // input month 1 ~ 12 value
    @Override
    public void init(@NotNull AppCompatActivity activity, int year, int month, int date, @Nullable String[] dayOfWeek, @NonNull WeeklyOrientation orientation) {
        this.activity = activity;
        this.orientation = orientation;
        int mMonth = month-1;

        calendar = (Calendar)Calendar.getInstance().clone();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.DATE, date);

        prevCalendar = (Calendar) calendar.clone();
        nextCalendar = (Calendar) calendar.clone();

        weeklyClickData = new WeeklyClickData(null, year, mMonth, date);
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
    public void setWeeklyCalendarStyle(@NonNull WeeklyStyle weeklyStyle) {
        this.weeklyStyle = weeklyStyle;
    }

    @Override
    public void setDateFontSize(float size) {
        if(size > 0 && size <= 18) {
            this.dateFontSize = size;
        } else {
            Log.e("Invalid Range", "WeeklyCalendar FontSize Range : 1~18");
        }
    }

    @Override
    public void setDayOfWeekFontSize(float size) {
        if(size > 0 && size <= 18) {
            this.dayOfWeekFontSize = size;
        } else {
            Log.e("Invalid Range", "WeeklyCalendar FontSize Range : 1~18");
        }
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

    @Override
    public void setMemo(ArrayList<CalendarMemo> memoItems) {
        this.memoItems = memoItems;
    }

    @Override
    public void setMemoFontSize(float size) {
        this.memoFontSize = size;
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

    private void onCreatePager() {
        if(orientation == WeeklyOrientation.VERTICAL) {
            setPageTransformer(true, new VerticalPager());
        }

        nowWeeklyData = getWeeklyData(calendar);
        prevWeeklyData = getPrevWeeklyData(calendar);
        nextWeeklyData = getNextWeeklyData(calendar);

        FragmentManager fragment = activity.getSupportFragmentManager();
        fragmentAdapter = new WeeklyFragmentPagerAdapter(fragment, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentAdapter.addItem(new WeeklyFragmentPager(onClickListener,
                weeklyClickData, dayOfWeek, colHeight, prevWeeklyData, weeklyStyle,
                dayOfWeekFontSize, dateFontSize, colorHex, textColorHex, memoItems, memoTextColor, memoFontSize, clickBgShape, dayOfWeekColor, dayOfWeekHeaderColor), "prev");
        fragmentAdapter.addItem(new WeeklyFragmentPager(onClickListener,
                weeklyClickData, dayOfWeek, colHeight, nowWeeklyData, weeklyStyle,
                dayOfWeekFontSize, dateFontSize, colorHex, textColorHex, memoItems, memoTextColor, memoFontSize, clickBgShape, dayOfWeekColor, dayOfWeekHeaderColor), "main");
        fragmentAdapter.addItem(new WeeklyFragmentPager(onClickListener,
                weeklyClickData, dayOfWeek, colHeight, nextWeeklyData, weeklyStyle,
                dayOfWeekFontSize, dateFontSize, colorHex, textColorHex, memoItems, memoTextColor, memoFontSize, clickBgShape, dayOfWeekColor, dayOfWeekHeaderColor), "next");
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
