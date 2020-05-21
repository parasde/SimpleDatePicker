package com.parasde.library.simpleweeklycalendar.data;

import android.widget.LinearLayout;

// month 는 +1 된 값으로 저장
public class WeeklyClickData {
    private LinearLayout layout = null;
    private int year;
    private int month;
    private int date;
    private int position;

    public WeeklyClickData() { }

    public WeeklyClickData(LinearLayout layout, int year, int month, int date) {
        this.layout = layout;
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
