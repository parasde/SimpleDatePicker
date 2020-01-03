package com.parasde.library.simpleweeklypicker.data;

import android.widget.TextView;

public class WeeklyClickData {
    private TextView tv = null;
    private int year = -1;
    private int month = -1;
    private int date = -1;

    public WeeklyClickData() { }

    public WeeklyClickData(TextView tv, int year, int month, int date) {
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
