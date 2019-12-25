package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;

import com.example.myapplication.Models.ChoosedPrograms;
import com.example.myapplication.Models.DataAboutYou;
import com.example.myapplication.Models.MusicPlayer;
import com.example.myapplication.Models.MyDatabase;

public class MainActivity extends AppCompatActivity {

    private MyDatabase myDatabase;

    private SharedPreferences sharedPreferences = null;

    private static final String FILE_NAME_DATA_ABOUT_APP = "data_about_app";

    private MusicPlayer musicPlayer;

    private ChoosedPrograms choosedPrograms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(FILE_NAME_DATA_ABOUT_APP, MODE_PRIVATE);

        myDatabase = MyDatabase.getInstance();
        myDatabase.initDatabase(this);

        musicPlayer = MusicPlayer.getInstance(this);
        musicPlayer.start();

        DataAboutYou dataAboutYou = DataAboutYou.getInstance();
        dataAboutYou.loadData(this);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, MusicPlayer.BACKGROUND_VOLUME, 0);

        choosedPrograms = ChoosedPrograms.getInstance();
        readChoosedTrainginPrograms();

        if (sharedPreferences.getBoolean("firstrun", true) || choosedPrograms.isEmpty()) {
            sharedPreferences.edit().putBoolean("firstrun", false).apply();
            Intent chooseTrainingProgramIntent = new Intent(this, ChooseTrainingPrograms.class);
            chooseTrainingProgramIntent.putExtra("act", "new");
            startActivity(chooseTrainingProgramIntent);
        }
        else {
            //sharedPreferences.edit().putBoolean("firstrun", true).apply();
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

    private void readChoosedTrainginPrograms(){
        SQLiteDatabase mDb = myDatabase.getDatabase();

        Cursor cursor = mDb.rawQuery("SELECT * FROM trainingprograms WHERE ischoosed = 1", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String program = cursor.getString(1);
            String lvl = cursor.getString(2);

            choosedPrograms.setProgram(program, lvl);

            cursor.moveToNext();
        }
        cursor.close();
    }

}
