package com.parasde.simpledatepicker;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parasde.library.simpledatepicker.data.CalendarSize;
import com.parasde.library.simpledatepicker.listener.CalendarOnPageChangeListener;
import com.parasde.library.simpledatepicker.view.CalendarPagerView;

public class SampleActivity extends AppCompatActivity {

    private TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        header = findViewById(R.id.calHeader2);
        CalendarPagerView pagerView = findViewById(R.id.cal3);

        pagerView.init(this, 2019, 12, 5, null, CalendarSize.NORMAL);
        pagerView.setCalendarPageChangeListener(new CalendarOnPageChangeListener() {
            @Override
            public void onChange(int year, int month) {
                if(month < 10) header.setText(year + "-" + "0" + month);
                else header.setText(year + "-" + month);
            }
        });
    }


}
