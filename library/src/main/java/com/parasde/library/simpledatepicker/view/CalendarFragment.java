package com.parasde.library.simpledatepicker.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.parasde.library.simpledatepicker.R;

abstract class CalendarFragment extends Fragment {
    protected abstract int layoutResId();
    protected abstract void onCreate(LinearLayout layout);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layoutResId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        onCreate((LinearLayout) view.findViewById(R.id.calendarLayout));
    }
}
