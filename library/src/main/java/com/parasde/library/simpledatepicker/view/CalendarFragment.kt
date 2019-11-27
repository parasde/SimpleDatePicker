package com.parasde.library.simpledatepicker.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.parasde.library.simpledatepicker.R

abstract class CalendarFragment: Fragment() {
    protected abstract fun layoutResId(): Int
    protected lateinit var rootView: View
    protected abstract fun onCreateView(layout: LinearLayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(layoutResId(), container, false)
        onCreateView(rootView.findViewById(R.id.calendarLayout))
        return rootView
    }
}