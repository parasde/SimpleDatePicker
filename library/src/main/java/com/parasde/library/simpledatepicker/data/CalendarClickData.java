package com.parasde.library.simpledatepicker.data;

import android.widget.TextView;

public class CalendarClickData {
    private TextView tv = null;
    private int year;
    private int month;
    private int date;

    public CalendarClickData() { }

    public CalendarClickData(TextView tv, int year, int month, int date) {
        this.tv = tv;
        this.year = year;
        this.month = month;
        this.date = date;
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
