package com.parasde.library.simpledatepicker.data

enum class CalendarSize(private val size: Int) {
    SMALL(84),
    NORMAL(108),
    BIG(144);

    fun colSize() = size
}