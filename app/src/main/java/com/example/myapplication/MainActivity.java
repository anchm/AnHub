package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences = null;

    private static final String FILE_NAME_DATA_ABOUT_APP = "data_about_app";

    private MusicPlayer musicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(FILE_NAME_DATA_ABOUT_APP, MODE_PRIVATE);

        MyDatabase myDatabase = MyDatabase.getInstance();
        myDatabase.initDatabase(this);

        musicPlayer = MusicPlayer.getInstance(this);
        musicPlayer.start();

        DataAboutYou dataAboutYou = DataAboutYou.getInstance();
        dataAboutYou.loadData(this);


        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);

        if (sharedPreferences.getBoolean("firstrun", true)) {
            sharedPreferences.edit().putBoolean("firstrun", false).apply();
            Intent chooseTrainingProgramIntent = new Intent(this, ChooseTrainingPrograms.class);
            startActivity(chooseTrainingProgramIntent);
        }
        else {
            sharedPreferences.edit().putBoolean("firstrun", true).apply();
            Intent menuIntent = new Intent(this, Menu.class);
            menuIntent.putExtra("activity", "ViewChoosedPrograms");
            startActivity(menuIntent);
       }
    }

    @Override
    public void onBackPressed(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicPlayer.stop();
    }

}
