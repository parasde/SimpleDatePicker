package com.parasde.library.simpledatepicker.data;

public enum CalendarSize {
    SMALL(100),
    NORMAL(140),
    BIG(180);

    private int size;

    public int colSize() {
        return size;
    }

    CalendarSize(int size) {
        this.size = size;
    }
}
