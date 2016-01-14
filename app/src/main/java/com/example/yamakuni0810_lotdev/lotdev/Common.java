package com.example.yamakuni0810_lotdev.lotdev;

import android.app.Application;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by 国広 on 2015/11/20.
 */

//共通の変数宣言
public class Common extends Application {
    MainChara mainChara = new MainChara(); //メインキャラ
    Character mold = new Character();      //カビ
    Character mold1 = new Character();
    Character mold2 = new Character();
    Character mite = new Character();//ダニ
    Character mite1 = new Character();
    Character mite2 = new Character();
    Character virus = new Character();    //ウイルス
    Character virus1 = new Character();
    Character virus2 = new Character();

    LogManagement logs = new LogManagement();

    float humidity = 50,temp = 20;  //湿度、温度

    public Common(){
        mainChara = new MainChara();
        mold = new Character();
        mold1 = new Character();
        mold2 = new Character();
        mite = new Character();
        mite1 = new Character();
        mite2 = new Character();
        virus = new Character();
        virus1 = new Character();
        virus2 = new Character();

        logs = new LogManagement();
    }

    public void AllAnimeSet(){
        //アニメ情報ファイル読み込み
        mainChara.Anime_Info = readFile("MainCharaAnime.csv");
        mold.Anime_Info = readFile("MoldAnime.csv");
        mite.Anime_Info = readFile("MiteAnime.csv");
        virus.Anime_Info = readFile("VirusAnime.csv");
        //読み込んだ情報をもとにアニメ作成
        mainChara.AnimeNormal = createAnime(mainChara.Anime_Info, "Normal");
        mainChara.AnimeFine = createAnime(mainChara.Anime_Info, "Fine");
        mainChara.AnimeCold = createAnime(mainChara.Anime_Info,"Cold");
        mainChara.AnimeHot = createAnime(mainChara.Anime_Info,"Hot");
        mainChara.AnimeDry = createAnime(mainChara.Anime_Info,"Dry");
        mainChara.AnimeMite = createAnime(mainChara.Anime_Info,"Mite");
        mainChara.AnimeMold = createAnime(mainChara.Anime_Info,"Mold");
        mainChara.AnimeVirus = createAnime(mainChara.Anime_Info,"Virus");

        mold.AnimeNormal = createAnime(mold.Anime_Info,"Normal");
        mold1.AnimeNormal = createAnime(mold.Anime_Info,"Normal");
        mold2.AnimeNormal = createAnime(mold.Anime_Info,"Normal");
        mite.AnimeNormal = createAnime(mite.Anime_Info,"Normal");
        mite1.AnimeNormal = createAnime(mite.Anime_Info,"Normal");
        mite2.AnimeNormal = createAnime(mite.Anime_Info,"Normal");
        virus.AnimeNormal = createAnime(virus.Anime_Info,"Normal");
        virus1.AnimeNormal = createAnime(virus.Anime_Info,"Normal");
        virus2.AnimeNormal = createAnime(virus.Anime_Info,"Normal");

    }

    //アニメ情報とグループ名からアニメ作成
    public AnimationDrawable createAnime(ArrayList<ArrayList<String>> info, String state){

        int resId;
        //列登録
        int staterow = info.get(0).indexOf("Group");
        int imagerow = info.get(0).indexOf("Image");
        int timerow = info.get(0).indexOf("Time");
        AnimationDrawable anime = new AnimationDrawable();

//読み込んだファイル情報をもとにフレームアニメ作成
        Drawable frame;
        for(int i = 1; i < info.size(); i++){
            if(info.get(i).get(staterow).equals(state)) {
                //StringからResouceID取得
                resId = getResources().getIdentifier(info.get(i).get(imagerow), "drawable", getPackageName());

                //フレーム追加
                frame = ResourcesCompat.getDrawable(getResources(), resId, null);
                anime.addFrame(frame, Integer.parseInt(info.get(i).get(timerow)));

            }
        }
        return anime;
    }

    public ArrayList<ArrayList<String>> readFile(String filename){
        ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
        ArrayList<String> val;

        try{

            //ファイルを読み込む
            InputStream is = null;
            is = this.getAssets().open(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //読み込んだファイルを1行ずつ処理する
            String line;
            StringTokenizer token;

            while((line = br.readLine()) != null){
                val = new ArrayList<String>();

                //区切り文字","で分割
                token = new StringTokenizer(line, ",");

                //分割した文字をcontentsに入れる

                while(token.hasMoreTokens()){
                    val.add(token.nextToken());

                }
                info.add(val);

            }
            //終了
            is.close();
            br.close();

        } catch(IOException ex){
            ex.printStackTrace();
        }
        return  info;

    }

}
