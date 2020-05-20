package com.parasde.library.simplecalendar.data;

public class CalendarMemo {
    private int year;
    private int month;
    private int date;
    private String content;

    public CalendarMemo(int year, int month, int date, String content) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
