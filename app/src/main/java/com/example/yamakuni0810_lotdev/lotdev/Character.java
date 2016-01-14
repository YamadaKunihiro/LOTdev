package com.example.yamakuni0810_lotdev.lotdev;

import android.app.Application;
import android.graphics.drawable.AnimationDrawable;

import java.util.ArrayList;

/**
 * Created by 国広 on 2015/11/20.
 */
public class Character extends Application{
    private int point = 0;
    private String state = null;
    public ArrayList<ArrayList<String>> Anime_Info = new ArrayList<ArrayList<String>>();
    public AnimationDrawable AnimeNormal = new AnimationDrawable(); //普通状態

    public Character(){
        point = 0;
        state = "null";
        Anime_Info = new ArrayList<ArrayList<String>>();
        AnimeNormal = new AnimationDrawable();

    }

    public void Init(){
        point = 0;
        state = "null";
        Anime_Info.clear();
        AnimeNormal = new AnimationDrawable();
    }

    public void initPoint() {
        point = 0;
    }
    public int getPoint(){
        return point;
    }

    public void setState(String val){
        this.state = val;
    }

    public String getState(){
        return state;
    }
    public void pointChange(int val){
        point += val;
        if(point < 0) point = 0;
        if(point > 100) point = 100;
    }


}
