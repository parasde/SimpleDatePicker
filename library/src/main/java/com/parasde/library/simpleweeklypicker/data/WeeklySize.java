package com.parasde.library.simpleweeklypicker.data;

public enum WeeklySize {
    SMALL(16),
    NORMAL(32),
    BIG(64);

    private int size;

    public int rowSize() {
        return size;
    }

    WeeklySize(int size) {
        this.size = size;
    }
}
