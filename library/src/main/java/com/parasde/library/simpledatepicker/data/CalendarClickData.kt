package com.parasde.library.simpledatepicker.data

import android.widget.TextView

/**
 * this model save clicked data
 */
data class CalendarClickData(var tv: TextView? = null,
                             var year: Int = -1,
                             var month: Int = -1,
                             var date: Int = -1)