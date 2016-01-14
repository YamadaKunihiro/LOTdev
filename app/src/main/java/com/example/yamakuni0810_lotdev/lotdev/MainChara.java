package com.example.yamakuni0810_lotdev.lotdev;

import android.graphics.drawable.AnimationDrawable;
import java.util.ArrayList;

/**
 * Created by 国広 on 2015/11/20.
 */
public class MainChara extends Character{

    public AnimationDrawable AnimeFine = new AnimationDrawable();  //元気状態
    public AnimationDrawable AnimeHot = new AnimationDrawable();  //熱い状態
    public AnimationDrawable AnimeCold = new AnimationDrawable();  //寒い状態
    public AnimationDrawable AnimeDry = new AnimationDrawable();  //乾燥状態
    public AnimationDrawable AnimeMold = new AnimationDrawable();  //カビ状態
    public AnimationDrawable AnimeMite = new AnimationDrawable();  //ダニ状態
    public AnimationDrawable AnimeVirus = new AnimationDrawable();  //ウイルス状態

    public MainChara(){
        Anime_Info = new ArrayList<ArrayList<String>>();
        AnimeFine = new AnimationDrawable();
        AnimeNormal = new AnimationDrawable();
        AnimeHot = new AnimationDrawable();
        AnimeCold = new AnimationDrawable();
        AnimeDry = new AnimationDrawable();
        AnimeMold = new AnimationDrawable();
        AnimeMite = new AnimationDrawable();
        AnimeVirus = new AnimationDrawable();

    }
    public void Init(){
        super.Init();
        Anime_Info.clear();
    }

}
