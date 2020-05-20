package com.parasde.library.simpleweeklycalendar.data;

public class WeeklyData {
    private int year;
    private int month;
    private int date;
    private int weekOfMonth;

    public WeeklyData(int year, int month, int date, int weekOfMonth) {
        this.year = year;
        this.month = month+1;
        this.date = date;
        this.weekOfMonth = weekOfMonth;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public int getWeekOfMonth() {
        return weekOfMonth;
    }
}
