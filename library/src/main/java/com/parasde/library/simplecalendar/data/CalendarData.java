package com.parasde.library.simplecalendar.data;

public class CalendarData {
    private int year;
    private int month;
    private int date;

    public CalendarData(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
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
}
