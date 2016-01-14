package com.example.yamakuni0810_lotdev.lotdev;

import android.app.Application;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by 国広 on 2015/11/29.
 */
public class LogManagement extends Application{
    private String MonthLog;
    private String DayLog;

    public LogManagement(){
        MonthLog = "";
        DayLog = "";
    }

    public void setMonthLog(String string){
        this.MonthLog = string;
    }

    public void writeMonthLog(String date){
        OutputStream out;
        try {
            out = openFileOutput(MonthLog,MODE_PRIVATE|MODE_APPEND);//このアプリだけアクセス許可、追記で保存設定
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"));

            //追記する
            writer.append(date);
            writer.close();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

    }

}