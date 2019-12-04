package com.parasde.library.simpledatepicker.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.parasde.library.simpledatepicker.R;

abstract class CalendarFragment extends Fragment {
    protected abstract int layoutResId();
    View rootView;
    protected abstract void onCreateView(LinearLayout layout);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(layoutResId(), container, false);
        onCreateView((LinearLayout) rootView.findViewById(R.id.calendarLayout));
        return rootView;
    }
}
