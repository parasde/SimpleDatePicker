package com.parasde.library.simpleweeklycalendar.data;

import android.widget.LinearLayout;
import android.widget.TextView;

// month 는 +1 된 값으로 저장
public class WeeklyClickData {
    private LinearLayout ll = null;
    private TextView tv = null;
    private int year;
    private int month;
    private int date;

    public WeeklyClickData() { }

    public WeeklyClickData(LinearLayout ll, TextView tv, int year, int month, int date) {
        this.ll = ll;
        this.tv = tv;
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public LinearLayout getLl() {
        return ll;
    }

    public void setLl(LinearLayout ll) {
        this.ll = ll;
    }

    public TextView getTv() {
        return tv;
    }

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
