package com.example.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;

public class MusicPlayer{

    private MediaPlayer mPlayer;

    private static MusicPlayer INSTANCE;

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
