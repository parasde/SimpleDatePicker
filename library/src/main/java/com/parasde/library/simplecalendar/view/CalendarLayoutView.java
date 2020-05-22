package com.parasde.library.simplecalendar.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.parasde.library.simplecalendar.R;
import com.parasde.library.simplecalendar.data.CalendarClickData;
import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarData;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simplecalendar.listener.CalendarClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class CalendarLayoutView implements CalendarLayout {
    private String[] dayOfWeek = {"SUN", "MON", "TUE", "WED", "THE", "FRI", "SAT"};

    private CalendarClickListener calendarClickListener = null;

    private CalendarClickData calendarClickData;
    private ArrayList<CalendarData> calendarData;

    private Context context;
    private String colorHex;
    private String textColorHex;
    private CalendarClickShape clickBgShape;

    private final String numberRegExp = "^[0-9]+$";

    private final int COL_MIN = 40;
    private String[] dayOfWeekColor;
    private String[] dayOfWeekHeaderColor;

    CalendarLayoutView(Context context) {
        this.context = context;
    }

    @Override
    public void setCalendarDateOnClickListener(CalendarClickListener listener) {
        calendarClickListener = listener;
    }

    @Override
    public GridLayout onCreateLayout(Calendar cal, String[] weekDay, CalendarClickData calendarClickData,
                                     Integer colHeight, ArrayList<CalendarMemo> memoItems,
                                     String colorHex, String textColorHex,
                                     CalendarClickShape clickBgShape,
                                     float memoFontSize, String memoTextColor, float calendarFontSize,
                                     String[] dayOfWeekColor, String[] dayOfWeekHeaderColor) {
        Calendar calendar = (Calendar) cal.clone();
        calendarData = new ArrayList<>();
        this.calendarClickData = calendarClickData;
        int curYear = cal.get(Calendar.YEAR);
        int curMonth = cal.get(Calendar.MONTH)+1;

        boolean isMemo = false;
        if(memoItems != null && memoItems.size() > 0) {
            isMemo = true;
        }

        this.colorHex = colorHex;
        this.textColorHex = textColorHex;
        this.clickBgShape = clickBgShape;
        this.dayOfWeekColor = dayOfWeekColor;
        this.dayOfWeekHeaderColor = dayOfWeekHeaderColor;

        if(clickBgShape == null) {
            this.clickBgShape = CalendarClickShape.RECTANGLE;
        }

        if(weekDay != null && weekDay.length == 7) {
            dayOfWeek = weekDay;
        }


        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        if(colHeight == null) {
            colHeight = COL_MIN;
        } else {
            if(colHeight < 40) {    // min width
                colHeight = COL_MIN;
            } else {
                // 컬럼 높이 * 6 값이 디바이스 너비를 넘어가면 디바이스 너비 / 7의 값으로 고정한다. (클릭 시 배경 색상 모양을 유지하기 위함)
                int deviceWidth = displayMetrics.widthPixels;
                if(dpToPx(colHeight) * 7 > deviceWidth) {
                    colHeight = pxToDp(deviceWidth / 7);
                }
            }
        }

        // add date
        GridLayout gridLayout = new GridLayout(context);
        gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(colHeight * 7)));
        gridLayout.setColumnCount(7);

        // get first date of month
        Calendar firstDayCalendar = (Calendar) calendar.clone();
        firstDayCalendar.set(Calendar.DATE, 1);
        int firstWeekDay = firstDayCalendar.get(Calendar.DAY_OF_WEEK);

        // get last date of month
        Calendar lastDayCalendar = (Calendar) calendar.clone();
        lastDayCalendar.add(Calendar.MONTH, 1);
        lastDayCalendar.set(Calendar.DATE, 1);
        lastDayCalendar.add(Calendar.DATE, -1);
        int lastDate = lastDayCalendar.get(Calendar.DATE);

        int blankDate = firstWeekDay - 1;

        // sun ~ sat day
        ArrayList<String> dateArray = new ArrayList<>(Arrays.asList(dayOfWeek));

        // prev calendar blank date
        for(int i = 0; i < blankDate; i++) {
            dateArray.add(" ");
        }

        // calendar date
        for(int i = 1; i <=lastDate; i++) {
            dateArray.add(i + "");
        }

        LinearLayout.LayoutParams memoTvParam = new LinearLayout.LayoutParams(dpToPx(colHeight / 2), dpToPx(colHeight / 2));
        for(int i = 0; i < dateArray.size(); i++) {
            LinearLayout contentLayout = new LinearLayout(context);
            // set Linear Layout
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            );
            params.width = dpToPx(colHeight);
            params.height = dpToPx(colHeight);
            contentLayout.setLayoutParams(params);
            contentLayout.setOrientation(LinearLayout.VERTICAL);
            contentLayout.setGravity(Gravity.CENTER);

            // memo 가 있을 경우
            TextView memoTv = null;
            if(isMemo) {
                memoTv = new TextView(context);
                memoTv.setLayoutParams(memoTvParam);
                memoTv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                if(memoTextColor == null) {
                    memoTv.setTextColor(ContextCompat.getColor(context, R.color.calMemo));
                } else {
                    memoTv.setTextColor(Color.parseColor(memoTextColor));
                }
                memoTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, memoFontSize);
                if(dateArray.get(i).matches(numberRegExp)) {
                    for(CalendarMemo memo: memoItems) {
                        int memoYear = memo.getYear();
                        int memoMonth = memo.getMonth();
                        int memoDate = memo.getDate();
                        if(curYear == memoYear && curMonth == memoMonth && memoDate == Integer.parseInt(dateArray.get(i))) {
                            String text = memo.getContent();
                            text += "\n" + memoTv.getText().toString();
                            memoTv.setText(text);
                        }
                    }
                }
            }


            TextView tv = new TextView(context);
            tv.setText(dateArray.get(i));
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, calendarFontSize);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setGravity(Gravity.CENTER);

            LinearLayout.LayoutParams tvParam;
            if(isMemo) {    // 메모가 있을 경우 높이를 2로 나눈다.
                tvParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(colHeight / 2));
            } else {
                tvParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(colHeight));
            }
            if(i == 0) {
                if(dayOfWeekHeaderColor[0] != null) {
                    tv.setTextColor(Color.parseColor(dayOfWeekHeaderColor[0]));
                } else {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
                }
            } else if(i == 6) {
                if(dayOfWeekHeaderColor[2] != null) {
                    tv.setTextColor(Color.parseColor(dayOfWeekHeaderColor[2]));
                } else {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.calSaturday));
                }
            } else if(i < 7) {
                if(dayOfWeekHeaderColor[1] != null) {
                    tv.setTextColor(Color.parseColor(dayOfWeekHeaderColor[1]));
                } else {
                    tv.setTextColor(ContextCompat.getColor(context, R.color.calHeader));
                }
            } else if(i >= (blankDate+7)) {
                if(isMemo) {    // 일~월 날짜 뷰 및, 빈 뷰가 끝나면 layout param 값 조절 / 메모가 있고 bg 모양이 circle 일 경우 높이, 너비를 2로 나누어 사이즈 조절
                    if(clickBgShape == CalendarClickShape.CIRCLE) {
                        tvParam = new LinearLayout.LayoutParams(dpToPx(colHeight / 2), dpToPx(colHeight / 2));
                    }
                } else {
                    tvParam = new LinearLayout.LayoutParams(dpToPx(colHeight), dpToPx(colHeight));
                }

                contentLayout.setOnClickListener(new CalendarDateOnClick(i-blankDate-7));
                calendarData.add(new CalendarData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(dateArray.get(i))));

                dateViewStyle(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(dateArray.get(i)), tv);

                if(clickDataCheck(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(dateArray.get(i)))) {
                    clickViewStyle(tv);
                    calendarClickData.setLayout(contentLayout);
                }
            }
            tv.setLayoutParams(tvParam);

            contentLayout.addView(tv);
            if(isMemo) contentLayout.addView(memoTv);
            gridLayout.addView(contentLayout);
        }

        return gridLayout;
    }

    private int dpToPx(int dp) {
        return (int)(dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private int pxToDp(int px){
        return (int)(px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    // sunday check
    private boolean checkSunday(int year, int month, int date) {
        Calendar cal = (Calendar)Calendar.getInstance().clone();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    // saturday check
    private boolean checkSaturday(int year, int month, int date) {
        Calendar cal = (Calendar)Calendar.getInstance().clone();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }

    // click date check
    private boolean clickDataCheck(int year, int month, int date) {
        return calendarClickData.getYear() == year && calendarClickData.getMonth() == month && calendarClickData.getDate() == date;
    }

    private class CalendarDateOnClick implements View.OnClickListener {
        private int position;
        CalendarDateOnClick(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View view) {
            if(calendarClickListener != null)  {
                if(calendarClickData.getLayout() != null) {
                    // 이전 레이아웃 클릭 색상 초기화
                    TextView prevTv = ((TextView)calendarClickData.getLayout().getChildAt(0));
                    prevTv.setBackgroundResource(android.R.color.transparent);
                    dateViewStyle(calendarClickData.getYear(), calendarClickData.getMonth(), calendarClickData.getDate(), prevTv);
                }
                clickViewStyle((TextView)((LinearLayout)view).getChildAt(0));

                // click date set
                calendarClickData.setLayout((LinearLayout)view);
                calendarClickData.setYear(calendarData.get(position).getYear());
                calendarClickData.setMonth(calendarData.get(position).getMonth());
                calendarClickData.setDate(calendarData.get(position).getDate());

                // 2019.12.01 return month 1 ~ 12
                calendarClickListener.onClick(calendarData.get(position).getYear(), calendarData.get(position).getMonth()+1, calendarData.get(position).getDate());
            }
        }

    }

    private void dateViewStyle(int year, int month, int date, TextView tv) {
        if(dayOfWeekColor[1] != null) {
            tv.setTextColor(Color.parseColor(dayOfWeekColor[1]));
        } else {
            tv.setTextColor(ContextCompat.getColor(context, R.color.calDate));
        }

        if(checkSunday(year, month, date)) {
            if(dayOfWeekColor[0] != null) {
                tv.setTextColor(Color.parseColor(dayOfWeekColor[0]));
            } else {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
            }
        } else if(checkSaturday(year, month, date)) {
            if(dayOfWeekColor[2] != null) {
                tv.setTextColor(Color.parseColor(dayOfWeekColor[2]));
            } else {
                tv.setTextColor(ContextCompat.getColor(context, R.color.calSaturday));
            }
        }
    }

    private void clickViewStyle(TextView textView) {
        if(colorHex != null) {
            if(clickBgShape == CalendarClickShape.CIRCLE) {
                textView.setBackground(circle(Color.parseColor(colorHex)));
            } else {
                textView.setBackgroundColor(Color.parseColor(colorHex));
            }
        } else {
            if(clickBgShape == CalendarClickShape.CIRCLE) {
                textView.setBackground(circle(context.getColor(R.color.skyBlue)));
            } else {
                textView.setBackgroundResource(R.color.skyBlue);
            }
        }

        if(textColorHex != null) {
            textView.setTextColor(Color.parseColor(textColorHex));
        }
    }

    private GradientDrawable circle(int bgColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        shape.setColor(bgColor);
        return shape;
    }
}
