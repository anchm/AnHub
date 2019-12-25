package com.example.myapplication.Models;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.myapplication.R;

public class MusicPlayer{

    private MediaPlayer mPlayer;

    private static MusicPlayer INSTANCE;

    public static final int BACKGROUND_VOLUME = 1;
    public static final int EXECUTE_EXERCISES_VOLUME = 30;

    private MusicPlayer(Context context){
        mPlayer= MediaPlayer.create(context, R.raw.music);
        mPlayer.setLooping(true);
        mPlayer.start();
    }

    public static MusicPlayer getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new MusicPlayer(context);
        }
        return INSTANCE;
    }

    public void start(){
        mPlayer.start();
    }

    public void stop(){
        mPlayer.stop();
    }

}
