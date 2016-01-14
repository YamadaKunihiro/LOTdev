package com.example.yamakuni0810_lotdev.lotdev;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class Load extends AppCompatActivity implements Runnable {
//グローバル変数
    Common common = new Common();

    //通信用変数
    private ServerSocket mServer;
    private Socket mSocket;
    int port = 59216;
    private Thread connect = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT); //立て画面固定
        //グローバル変数取得
        common = (Common)getApplication();
        //アニメーションセット
        common.AllAnimeSet();

        //データを受け取る準備
        connect = new Thread(this);
        connect.start();

        //画面切り替え
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }

    public void run(){
        try {
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                System.out.println("Thread sleep error");
            }

            mServer = new ServerSocket(port);

            mSocket = mServer.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            String message;
            message = in.readLine();

            //区切り文字","で分割
            StringTokenizer token = new StringTokenizer(message, ",");
            //時間
            token.nextToken();
            //温度
            common.temp = Float.valueOf(token.nextToken());
            //湿度
            common.humidity = Float.valueOf(token.nextToken());
            connect = new Thread(this);
            connect.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (mSocket != null) mSocket.close();
            if(mServer != null) mServer.close();
        }catch(IOException e) {
            System.out.println("Error");
        }

    }
}
