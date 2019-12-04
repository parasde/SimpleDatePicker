package com.parasde.library.simpledatepicker.data;

public enum CalendarSize {
    SMALL(84),
    NORMAL(108),
    BIG(144);

    private int size;

    public int colSize() {
        return size;
    }

    CalendarSize(int size) {
        this.size = size;
    }
}
