package com.parasde.library.simpleweeklypicker.data;

public enum WeeklySize {
    SMALL(20),
    NORMAL(34),
    BIG(46);

    private int size;

    public int rowSize() {
        return size;
    }

    WeeklySize(int size) {
        this.size = size;
    }
}
