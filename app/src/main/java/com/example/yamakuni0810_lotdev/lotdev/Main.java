package com.example.yamakuni0810_lotdev.lotdev;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends AppCompatActivity {
    //グローバル変数
    Common common;
    Handler UiHandler = new Handler(); //別スレッドからUIを操作するためのハンドラー

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); //立て画面固定

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout);
        layout.setBackgroundResource(R.drawable.background);
        //グローバル変数取得
        common = (Common)getApplication();

        //ディスプレイサイズ取得
        Point size = new Point();
        Display d = getWindowManager().getDefaultDisplay();
        d.getSize(size);


        // ビューの背景画像にアニメーションを設定
        common.mainChara.setState("Normal");
        ImageView v = (ImageView)findViewById(R.id.mainCharaView);
        v.setImageDrawable(common.mainChara.AnimeNormal);
        common.mainChara.AnimeNormal.setOneShot(false);
        common.mainChara.AnimeNormal.start();

        //Timerセット
        Timer mainTimer = new Timer();
        mainTimer.schedule(new MainTimerTask(), 0, 1000);//実行処理、開始時間、周期時間

    }

    //Timerの処理
    public class MainTimerTask extends TimerTask {
        //キャラ表示用のビュー
        ImageView mainView = (ImageView)findViewById(R.id.mainCharaView);
        ImageView enemyViewR = (ImageView)findViewById(R.id.enemyViewR);
        ImageView enemyViewL = (ImageView)findViewById(R.id.enemyViewL);
        ImageView enemyViewR2 = (ImageView)findViewById(R.id.enemyViewR2);
        ImageView enemyViewL2 = (ImageView)findViewById(R.id.enemyViewL2);
        ImageView enemyViewC = (ImageView)findViewById(R.id.enemyViewC);
        ImageView enemyViewC2 = (ImageView)findViewById(R.id.enemyViewC2);

        //温度、湿度表示用のビュー
        TextView dispTemp = (TextView)findViewById(R.id.tempView);
        TextView dispHudity = (TextView)findViewById(R.id.hudiView);

        //状態の変更を検知するための変数
        String mainCharaStateCopy;
        String moldStateCopy;
        String miteStateCopy;
        String virusStateCopy;

        @Override
        public void run() {  //定周期で実行する処理
            //ログをデータベースに追加
            //今の時間calendar型からstring型に変換
            Calendar toDay = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-hh:mm:ss");
            //データベースに追加

            //現在の温度、湿度の表示
            UiHandler.post(new Runnable() {//別スレッドからUIの操作
                               public void run() {
                                   dispTemp.setText(String.valueOf(common.temp));
                                   dispHudity.setText(String.valueOf(common.humidity));
                               }
                           });

            //湿度、温度、ポイントによって状態の変更とポイントの調整を行う処理
            //メインキャラ
            mainCharaStateCopy = common.mainChara.getState();
            if(common.temp >=18 && common.temp<=28
                    && common.humidity >=40 && common.humidity <=70
                    && common.mainChara.getPoint() < 50){
                common.mainChara.setState("Normal");
                if(common.mite.getState().equals("null") &&
                        common.mold.getState().equals("null") &&
                        common.virus.getState().equals("null")) common.mainChara.pointChange(1);
                common.virus.pointChange(-1);
                common.mite.pointChange(-1);
                common.mold.pointChange(-1);
            }
            else if(common.temp >=18 && common.temp<=28
                    && common.humidity >=40 && common.humidity <=70
                    && common.mainChara.getPoint() >=50){
                common.mainChara.setState("Fine");
                if(common.mite.getState().equals("null") &&
                        common.mold.getState().equals("null") &&
                        common.virus.getState().equals("null")) common.mainChara.pointChange(1);
                common.virus.pointChange(-1);
                common.mite.pointChange(-1);
                common.mold.pointChange(-1);
            }
            else if(common.humidity < 40 && common.temp < 25){
                common.mainChara.setState("Virus");
                common.mainChara.pointChange(-1);
                common.virus.pointChange(1);
                common.mite.pointChange(-1);
                common.mold.pointChange(-1);
            }
            else if(common.humidity < 40){
                common.mainChara.setState("Dry");
                common.mainChara.pointChange(-1);
                common.virus.pointChange(-1);
                common.mite.pointChange(-1);
                common.mold.pointChange(-1);
            }
            else if(common.humidity > 70 && common.temp >= 20){
                common.mainChara.setState("Mite");
                common.mainChara.pointChange(-1);
                common.virus.pointChange(-1);
                common.mite.pointChange(1);
                common.mold.pointChange(1);
            }
            else if(common.humidity > 70){
                common.mainChara.setState("Mold");
                common.mainChara.pointChange(-1);
                common.virus.pointChange(-1);
                common.mold.pointChange(1);
            }
            else if(common.temp < 18){
                common.mainChara.setState("Cold");
                common.mainChara.pointChange(-1);
                common.virus.pointChange(-1);
                common.mite.pointChange(-1);
                common.mold.pointChange(-1);
            }
            else if(common.temp > 28){
                common.mainChara.setState("Hot");
                common.mainChara.pointChange(-1);
                common.virus.pointChange(-1);
                common.mite.pointChange(-1);
                common.mold.pointChange(-1);
            }

            //カビ
            moldStateCopy = common.mold.getState();
            if(common.mold.getPoint() < 50) common.mold.setState("null");
            else if(common.mold.getPoint() > 50 && common.mainChara.getPoint() < 50) common.mold.setState("Normal");

            //ダニ
            miteStateCopy = common.mite.getState();
            if(common.mite.getPoint() < 50) common.mite.setState("null");
            else if(common.mite.getPoint() > 50 && common.mainChara.getPoint() < 50) common.mite.setState("Normal");

            //ウイルス
            virusStateCopy = common.virus.getState();
            if(common.virus.getPoint() < 50) common.virus.setState("null");
            else if(common.virus.getPoint() > 50 && common.mainChara.getPoint() < 50) common.virus.setState("Normal");

            //状態、ポイントによって表示するアニメーションの変更処理
            //メインキャラ
            if(!mainCharaStateCopy.equals(common.mainChara.getState())) {//状態の変更があれば
                switch (common.mainChara.getState()) {
                    case "Cold":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                mainView.setImageDrawable(common.mainChara.AnimeCold);
                                common.mainChara.AnimeCold.setOneShot(false);
                                common.mainChara.AnimeCold.start();
                            }
                        });
                        break;
                    //熱い状態
                    case "Hot":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                mainView.setImageDrawable(common.mainChara.AnimeHot);
                                common.mainChara.AnimeHot.setOneShot(false);
                                common.mainChara.AnimeHot.start();
                            }
                        });
                        break;
                    //乾燥状態
                    case "Dry":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                mainView.setImageDrawable(common.mainChara.AnimeDry);
                                common.mainChara.AnimeDry.setOneShot(false);
                                common.mainChara.AnimeDry.start();
                            }
                        });
                        break;

                    //カビ状態
                    case "Mold":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                mainView.setImageDrawable(common.mainChara.AnimeMold);
                                common.mainChara.AnimeMold.setOneShot(false);
                                common.mainChara.AnimeMold.start();
                            }
                        });
                        break;

                    //ダニ状態
                    case "Mite":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                mainView.setImageDrawable(common.mainChara.AnimeMite);
                                common.mainChara.AnimeMite.setOneShot(false);
                                common.mainChara.AnimeMite.start();
                            }
                        });
                        break;

                    //ウイルス状態
                    case "Virus":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                mainView.setImageDrawable(common.mainChara.AnimeVirus);
                                common.mainChara.AnimeVirus.setOneShot(false);
                                common.mainChara.AnimeVirus.start();
                            }
                        });
                        break;

                    //普通状態
                    case "Normal":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                mainView.setImageDrawable(common.mainChara.AnimeNormal);
                                common.mainChara.AnimeNormal.setOneShot(false);
                                common.mainChara.AnimeNormal.start();
                            }
                        });
                        break;
                    //元気状態
                    case "Fine":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                mainView.setImageDrawable(common.mainChara.AnimeFine);
                                common.mainChara.AnimeFine.setOneShot(false);
                                common.mainChara.AnimeFine.start();
                            }
                        });
                        break;
                }
            }


            //カビ
            if(!moldStateCopy.equals(common.mold.getState())) {//状態の変更があれば
                switch (common.mold.getState()) {
                    case "null":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                enemyViewR.setVisibility(View.INVISIBLE);
                                enemyViewC2.setVisibility(View.INVISIBLE);
                                enemyViewL.setVisibility(View.INVISIBLE);
                            }
                        });
                        break;
                    case "Normal":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                enemyViewR.setVisibility(View.VISIBLE);
                                enemyViewC2.setVisibility(View.VISIBLE);
                                enemyViewL.setVisibility(View.VISIBLE);
                                enemyViewR.setImageDrawable(common.mold.AnimeNormal);
                                common.mold.AnimeNormal.setOneShot(false);
                                common.mold.AnimeNormal.start();
                                enemyViewC2.setImageDrawable(common.mold1.AnimeNormal);
                                common.mold1.AnimeNormal.setOneShot(false);
                                common.mold1.AnimeNormal.start();
                                enemyViewL.setImageDrawable(common.mold2.AnimeNormal);
                                common.mold2.AnimeNormal.setOneShot(false);
                                common.mold2.AnimeNormal.start();


                            }
                        });
                        break;
                }
            }

            //ダニ

            if(!miteStateCopy.equals(common.mite.getState())) {//状態の変更があれば
                switch (common.mite.getState()) {
                    case "null":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                enemyViewR2.setVisibility(View.INVISIBLE);
                                enemyViewC.setVisibility(View.INVISIBLE);
                                enemyViewL2.setVisibility(View.INVISIBLE);
                            }
                        });
                        break;
                    case "Normal":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                enemyViewR2.setVisibility(View.VISIBLE);
                                enemyViewR2.setImageDrawable(common.mite.AnimeNormal);
                                common.mite.AnimeNormal.setOneShot(false);
                                common.mite.AnimeNormal.start();

                                enemyViewC.setVisibility(View.VISIBLE);
                                enemyViewC.setImageDrawable(common.mite1.AnimeNormal);
                                common.mite1.AnimeNormal.setOneShot(false);
                                common.mite1.AnimeNormal.start();

                                enemyViewL2.setVisibility(View.VISIBLE);
                                enemyViewL2.setImageDrawable(common.mite2.AnimeNormal);
                                common.mite2.AnimeNormal.setOneShot(false);
                                common.mite2.AnimeNormal.start();
                            }
                        });
                        break;
                }
            }
            //ウイルス
            if(!virusStateCopy.equals(common.virus.getState())) {//状態の変更があれば
                switch (common.virus.getState()) {
                    case "null":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                enemyViewR2.setVisibility(View.INVISIBLE);
                                enemyViewC.setVisibility(View.INVISIBLE);
                                enemyViewL2.setVisibility(View.INVISIBLE);
                            }
                        });
                        break;
                    case "Normal":
                        UiHandler.post(new Runnable() {//別スレッドからUIの操作
                            public void run() {
                                enemyViewR2.setVisibility(View.VISIBLE);
                                enemyViewR2.setImageDrawable(common.virus.AnimeNormal);
                                common.virus.AnimeNormal.setOneShot(false);
                                common.virus.AnimeNormal.start();

                                enemyViewC.setVisibility(View.VISIBLE);
                                enemyViewC.setImageDrawable(common.virus1.AnimeNormal);
                                common.virus1.AnimeNormal.setOneShot(false);
                                common.virus1.AnimeNormal.start();

                                enemyViewL2.setVisibility(View.VISIBLE);
                                enemyViewL2.setImageDrawable(common.virus2.AnimeNormal);
                                common.virus2.AnimeNormal.setOneShot(false);
                                common.virus2.AnimeNormal.start();
                            }
                        });
                        break;
                }
            }

        }
    }
//-----------------------------------------------------------------------------------------------------------------
//テスト用ボタン
    public void onClick(View view){
        TextView tv;
        switch (view.getId()) {
            case R.id.toCal:
                Intent intent = new Intent(this, CalendarScreen.class);
                startActivity(intent);
                break;

            case R.id.button:
                tv = (TextView)findViewById(R.id.tempView);
                common.temp++;
                tv.setText(String.valueOf(common.temp));
                break;
            case R.id.button2:
                tv = (TextView)findViewById(R.id.hudiView);
                common.humidity++;
                tv.setText(String.valueOf(common.humidity));
                break;
            case R.id.button3:
                tv = (TextView)findViewById(R.id.tempView);
                common.temp--;
                tv.setText(String.valueOf(common.temp));
                break;
            case R.id.button4:
                tv = (TextView)findViewById(R.id.hudiView);
                common.humidity--;
                tv.setText(String.valueOf(common.humidity));
                break;
        }

    }

}
