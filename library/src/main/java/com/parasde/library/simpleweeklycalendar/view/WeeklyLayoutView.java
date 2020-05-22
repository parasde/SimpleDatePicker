package com.parasde.library.simpleweeklycalendar.view;

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
import com.parasde.library.simplecalendar.data.CalendarClickShape;
import com.parasde.library.simplecalendar.data.CalendarMemo;
import com.parasde.library.simpleweeklycalendar.data.WeeklyClickData;
import com.parasde.library.simpleweeklycalendar.data.WeeklyData;
import com.parasde.library.simpleweeklycalendar.data.WeeklyStyle;
import com.parasde.library.simpleweeklycalendar.listener.WeeklyClickListener;

import java.util.ArrayList;
import java.util.Calendar;

public class WeeklyLayoutView implements WeeklyLayout {
    private String[] dayOfWeek = {"SUN", "MON", "TUE", "WED", "THE", "FRI", "SAT"};

    private WeeklyClickListener weeklyClickListener = null;

    private WeeklyClickData weeklyClickData;
    private ArrayList<WeeklyData> weeklyData;

    private Context context;

    private WeeklyStyle weeklyStyle;
    private String colorHex;
    private String textColorHex;
    private CalendarClickShape clickBgShape;

    private boolean isMemo = false;

    private final int COL_MIN = 40;

    private String[] dayOfWeekColor = new String[3];
    private String[] dayOfWeekHeaderColor = new String[3];

    WeeklyLayoutView(Context context) {
        this.context = context;
    }

    @Override
    public void setCalendarDateOnClickListener(WeeklyClickListener listener) {
        weeklyClickListener = listener;
    }

    @Override
    public GridLayout onCreateLayout(WeeklyClickData weeklyClickData, String[] weekDay, Integer colHeight,
                                     ArrayList<WeeklyData> weeklyData, WeeklyStyle weeklyStyle,
                                     float dayOfWeekFontSize, float dateFontSize, String colorHex,
                                     String textColorHex, ArrayList<CalendarMemo> memoItems, String memoTextColor,
                                     float memoFontSize, CalendarClickShape clickBgShape, String[] dayOfWeekColor, String[] dayOfWeekHeaderColor) {
        this.weeklyClickData = weeklyClickData;
        this.weeklyData = weeklyData;
        if(weekDay != null && weekDay.length == 7) {
            dayOfWeek = weekDay;
        }

        if(weeklyStyle == null) {
            this.weeklyStyle  = WeeklyStyle.DEFAULT;
        } else {
            this.weeklyStyle = weeklyStyle;
        }
        this.colorHex = colorHex;
        this.textColorHex = textColorHex;
        this.clickBgShape = clickBgShape;
        if(clickBgShape == null) {
            this.clickBgShape = CalendarClickShape.RECTANGLE;
        }
        this.dayOfWeekColor = dayOfWeekColor;
        this.dayOfWeekHeaderColor = dayOfWeekHeaderColor;

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

        if(memoItems != null && memoItems.size() > 0) {
            isMemo = true;
        }

        GridLayout gridLayout = new GridLayout(context);
        if(weeklyStyle == WeeklyStyle.STYLE_1) {
            int gridHeight = isMemo ? dpToPx(colHeight * 2) : dpToPx(colHeight);
            gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, gridHeight));
            gridLayout.setColumnCount(7);
            style_1(weeklyData, weeklyClickData, colHeight, gridLayout, dayOfWeekFontSize, dateFontSize, memoItems, memoTextColor, memoFontSize);
        } else {
            int gridHeight = isMemo ? dpToPx(colHeight * 3) : dpToPx(colHeight * 2);
            gridLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, gridHeight));
            gridLayout.setColumnCount(7);
            defaultStyle(weeklyData, weeklyClickData, colHeight, gridLayout, dayOfWeekFontSize, dateFontSize, memoItems, memoTextColor, memoFontSize);
        }

        return gridLayout;
    }

    private int dpToPx(int dp) {
        return (int)(dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private int pxToDp(int px){
        return (int)(px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    // linearLayout(parent) -> add TextView x 2
    private void defaultStyle(ArrayList<WeeklyData> weeklyData, WeeklyClickData weeklyClickData,
                              int colHeight, GridLayout gridLayout, float dayOfWeekFontSize, float dateFontSize,
                              ArrayList<CalendarMemo> memoItems, String memoTextColor, float memoFontSize) {
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                dpToPx(colHeight),
                dpToPx(colHeight)
        );

        // weekly header
        for(int i = 0; i < 7; i++) {
            LinearLayout contentLayout = new LinearLayout(context);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            );
            params.width = 0;
            params.height = isMemo ? dpToPx(colHeight * 3) : dpToPx(colHeight * 2);
            contentLayout.setLayoutParams(params);
            contentLayout.setGravity(Gravity.CENTER);
            contentLayout.setOrientation(LinearLayout.VERTICAL);

            TextView dayOfWeekTv = new TextView(context);
            dayOfWeekTv.setText(dayOfWeek[i]);
            dayOfWeekTv.setLayoutParams(tvParams);
            dayOfWeekTv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dayOfWeekTv.setGravity(Gravity.CENTER_VERTICAL);
            dayOfWeekTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dayOfWeekFontSize);

            TextView dateTv = new TextView(context);
            dateTv.setLayoutParams(tvParams);
            dateTv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dateTv.setGravity(Gravity.CENTER_VERTICAL);
            dateTv.setText(String.valueOf(weeklyData.get(i).getDate()));
            dateTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dateFontSize);

            dateViewStyle(i, dayOfWeekTv, dateTv);

            contentLayout.setOnClickListener(new CalendarDateOnClick(i));
            if(clickDataCheck(weeklyData.get(i).getYear(), weeklyData.get(i).getMonth()-1, weeklyData.get(i).getDate())) {
                clickViewStyle(dateTv);
                weeklyClickData.setLayout(contentLayout);
                weeklyClickData.setPosition(i);
            }

            // memo 가 있을 경우
            TextView memoTv = null;
            if(isMemo) {
                memoTv = new TextView(context);
                memoTv.setLayoutParams(tvParams);
                memoTv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                if(memoTextColor == null) {
                    memoTv.setTextColor(ContextCompat.getColor(context, R.color.calMemo));
                } else {
                    memoTv.setTextColor(Color.parseColor(memoTextColor));
                }
                memoTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, memoFontSize);
                for(CalendarMemo memo: memoItems) {
                    int memoYear = memo.getYear();
                    int memoMonth = memo.getMonth();
                    int memoDate = memo.getDate();
                    if(weeklyData.get(i).getYear() == memoYear && weeklyData.get(i).getMonth() == memoMonth && weeklyData.get(i).getDate() == memoDate) {
                        String text = memo.getContent();
                        text += "\n" + memoTv.getText().toString();
                        memoTv.setText(text);
                    }
                }
            }


            contentLayout.addView(dayOfWeekTv);
            contentLayout.addView(dateTv);
            if(isMemo) contentLayout.addView(memoTv);
            gridLayout.addView(contentLayout);
        }
    }

    /**
     * Style_1
     */
    private void style_1(ArrayList<WeeklyData> weeklyData,  WeeklyClickData weeklyClickData,
                         int colHeight, GridLayout gridLayout, float dayOfWeekFontSize, float dateFontSize,
                         ArrayList<CalendarMemo> memoItems, String memoTextColor, float memoFontSize) {

        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                dpToPx(colHeight)
        );
        for(int i = 0; i < 7; i++) {
            LinearLayout contentLayout = new LinearLayout(context);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f),
                    GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            );
            params.width = 0;
            params.height = isMemo ? dpToPx(colHeight * 2) : dpToPx(colHeight);
            contentLayout.setLayoutParams(params);
            contentLayout.setOrientation(LinearLayout.VERTICAL);
            contentLayout.setGravity(Gravity.CENTER);

            LinearLayout dateLayout = new LinearLayout(context);
            LinearLayout.LayoutParams dateLayoutParam = new LinearLayout.LayoutParams(
                    dpToPx(colHeight),
                    dpToPx(colHeight)
            );

            dateLayout.setLayoutParams(dateLayoutParam);
            dateLayout.setOrientation(LinearLayout.HORIZONTAL);
            dateLayout.setGravity(Gravity.CENTER);

            TextView dayOfWeekTv = new TextView(context);
            dayOfWeekTv.setLayoutParams(tvParams);
            dayOfWeekTv.setText(dayOfWeek[i]);
            dayOfWeekTv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dayOfWeekTv.setGravity(Gravity.CENTER_VERTICAL);
            dayOfWeekTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dayOfWeekFontSize);

            TextView dateTv = new TextView(context);
            dateTv.setLayoutParams(tvParams);
            dateTv.setText(String.valueOf(weeklyData.get(i).getDate()));
            dateTv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            dateTv.setGravity(Gravity.CENTER_VERTICAL);
            dateTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dateFontSize);

            dateViewStyle(i, dayOfWeekTv, dateTv);

            dateLayout.addView(dateTv);
            dateLayout.addView(dayOfWeekTv);

            dateLayout.setOnClickListener(new CalendarDateOnClick(i));
            if(clickDataCheck(weeklyData.get(i).getYear(), weeklyData.get(i).getMonth()-1, weeklyData.get(i).getDate())) {
                clickViewStyle(dateLayout);
                weeklyClickData.setLayout(dateLayout);
                weeklyClickData.setPosition(i);
            }

            // memo 가 있을 경우
            TextView memoTv = null;
            if(isMemo) {
                memoTv = new TextView(context);
                memoTv.setLayoutParams(tvParams);
                memoTv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                if(memoTextColor == null) {
                    memoTv.setTextColor(ContextCompat.getColor(context, R.color.calMemo));
                } else {
                    memoTv.setTextColor(Color.parseColor(memoTextColor));
                }
                memoTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, memoFontSize);
                for(CalendarMemo memo: memoItems) {
                    int memoYear = memo.getYear();
                    int memoMonth = memo.getMonth();
                    int memoDate = memo.getDate();
                    if(weeklyData.get(i).getYear() == memoYear && weeklyData.get(i).getMonth() == memoMonth && weeklyData.get(i).getDate() == memoDate) {
                        String text = memo.getContent();
                        text += "\n" + memoTv.getText().toString();
                        memoTv.setText(text);
                    }
                }
            }

            contentLayout.addView(dateLayout);
            if(isMemo) contentLayout.addView(memoTv);
            gridLayout.addView(contentLayout);
        }
    }

    // click date check
    private boolean clickDataCheck(int year, int month, int date) {
        return weeklyClickData.getYear() == year && weeklyClickData.getMonth() == month && weeklyClickData.getDate() == date;
    }

    private class CalendarDateOnClick implements View.OnClickListener {
        private int position;
        CalendarDateOnClick(int position) {
            this.position = position;
        }
        @Override
        public void onClick(View view) {
            if(weeklyClickListener != null)  {
                if(weeklyClickData.getLayout() != null) {
                    if(weeklyStyle == WeeklyStyle.DEFAULT) {
                        ((TextView)weeklyClickData.getLayout().getChildAt(1)).setBackgroundResource(android.R.color.transparent);
                        dateViewStyle(weeklyClickData.getPosition(), ((TextView)weeklyClickData.getLayout().getChildAt(0)), ((TextView)weeklyClickData.getLayout().getChildAt(1)));

                        clickViewStyle(((LinearLayout)view).getChildAt(1));
                    } else {
                        weeklyClickData.getLayout().setBackgroundResource(android.R.color.transparent);
                        dateViewStyle(weeklyClickData.getPosition(), ((TextView)weeklyClickData.getLayout().getChildAt(1)), ((TextView)weeklyClickData.getLayout().getChildAt(0)));

                        clickViewStyle(view);
                    }
                }

                // click date set
                weeklyClickData.setLayout((LinearLayout) view);
                weeklyClickData.setYear(weeklyData.get(position).getYear());
                weeklyClickData.setMonth(weeklyData.get(position).getMonth()-1);
                weeklyClickData.setDate(weeklyData.get(position).getDate());
                weeklyClickData.setPosition(position);

                // 2019.12.01 return month 1 ~ 12
                weeklyClickListener.onClick(weeklyData.get(position).getYear(), weeklyData.get(position).getMonth(), weeklyData.get(position).getDate());
            }
        }
    }

    private void  dateViewStyle(int position, TextView headerTv, TextView dateTv) {
        if(position == 0) {
            if(dayOfWeekHeaderColor[0] != null) {
                headerTv.setTextColor(Color.parseColor(dayOfWeekHeaderColor[0]));
            } else {
                headerTv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
            }

            if(dayOfWeekColor[0] != null) {
                dateTv.setTextColor(Color.parseColor(dayOfWeekColor[0]));
            } else {
                dateTv.setTextColor(ContextCompat.getColor(context, R.color.calSunday));
            }
        } else if(position == 6) {
            if(dayOfWeekHeaderColor[2] != null) {
                headerTv.setTextColor(Color.parseColor(dayOfWeekHeaderColor[2]));
            } else {
                headerTv.setTextColor(ContextCompat.getColor(context, R.color.calSaturday));
            }

            if(dayOfWeekColor[2] != null) {
                dateTv.setTextColor(Color.parseColor(dayOfWeekColor[2]));
            } else {
                dateTv.setTextColor(ContextCompat.getColor(context, R.color.calSaturday));
            }
        } else {
            if(dayOfWeekHeaderColor[1] != null) {
                headerTv.setTextColor(Color.parseColor(dayOfWeekHeaderColor[1]));
            } else {
                headerTv.setTextColor(ContextCompat.getColor(context, R.color.calHeader));
            }

            if(dayOfWeekColor[1] != null) {
                dateTv.setTextColor(Color.parseColor(dayOfWeekColor[1]));
            } else {
                dateTv.setTextColor(ContextCompat.getColor(context, R.color.calDate));
            }
        }

    }

    private void clickViewStyle(View view) {
        if(colorHex != null) {
            if(clickBgShape == CalendarClickShape.CIRCLE) {
                view.setBackground(circle(Color.parseColor(colorHex)));
            } else {
                view.setBackgroundColor(Color.parseColor(colorHex));
            }
        } else {
            if(clickBgShape == CalendarClickShape.CIRCLE) {
                view.setBackground(circle(context.getColor(R.color.skyBlue)));
            } else {
                view.setBackgroundResource(R.color.skyBlue);
            }
        }

        if(WeeklyStyle.DEFAULT == weeklyStyle) {
            if(textColorHex != null) {
                ((TextView)view).setTextColor(Color.parseColor(textColorHex));
            }
        } else if(WeeklyStyle.STYLE_1 == weeklyStyle){
            if(textColorHex != null) {
                ((TextView)((LinearLayout)view).getChildAt(0)).setTextColor(Color.parseColor(textColorHex));
                ((TextView)((LinearLayout)view).getChildAt(1)).setTextColor(Color.parseColor(textColorHex));
            }
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
