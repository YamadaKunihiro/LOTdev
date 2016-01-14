package com.example.yamakuni0810_lotdev.lotdev;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class CalendarScreen extends AppCompatActivity {

    private int year;
    private int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_screen);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); //立て画面固定

        Calendar day = Calendar.getInstance();
        this.year = day.get(Calendar.YEAR);
        this.month = day.get(Calendar.MONTH);
        setCalenderText();

    }

    //yearとmonthの値によって表示内容を決める
    private void setCalenderText(){

        //年と月から日付計算用
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        // 月の初めの曜日を求める
        calendar.set(this.year, this.month, 1);
        int startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//曜日を取得

        //月の最後の日付
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DATE,-1);
        int lastDay = calendar.get(Calendar.DATE);

        //年と月、表示
        TextView setView = (TextView)findViewById(R.id.yearText);
        setView.setText(String.valueOf(this.year) + " 年");
        setView = (TextView)findViewById(R.id.MonthText);
        setView.setText(String.valueOf(this.month + 1) + " 月");

        int countDay = 1;
        int textViewId;
        boolean startDayOfWeekFlag = false;//最初の曜日判定用
        for(int i = 1; i <= 37; i++){
            //StringからdayのtextViewのResouceID取得
            textViewId = getResources().getIdentifier("day" + String.valueOf(i), "id", getPackageName());
            setView = (TextView)findViewById(textViewId);
            if(!startDayOfWeekFlag && i < startDayOfWeek  || countDay > lastDay) setView.setText("");
            else{
                setView.setText(String.valueOf(countDay));
                countDay++;
                startDayOfWeekFlag = true;
            }
        }

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.lastMonth:
                this.month--;
                if(this.month < 0){
                    this.month = 11;
                    this.year--;
                }
                setCalenderText();
                break;
            case R.id.nextMonth:
                this.month++;
                if(this.month > 11){
                    this.month = 0;
                    this.year++;
                }
                setCalenderText();
        }

    }

}
