package com.example.myapplication.Models;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Settings {
    private static Settings INSTANCE;

    private String language;
    private int volume;

    private SharedPreferences spSettings;

    private static final String FILE_NAME_SETTINGS = "settings";

    public Settings(Context context) {
        spSettings = context.getSharedPreferences(FILE_NAME_SETTINGS, MODE_PRIVATE);
        language = spSettings.getString("language", "English");
        volume = spSettings.getInt("volume", MusicPlayer.BACKGROUND_VOLUME);
    }

    public static Settings getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new Settings(context);
        }
        return INSTANCE;
    }

    public String getLanguage(){
        return language;
    }

    public int getVolume(){
        return volume;
    }

    public void setLanguage(String language){
        this.language = language;
        SharedPreferences.Editor ed = spSettings.edit();
        ed.putString("language", language);
        ed.apply();
    }

    public void setVolume(int volume){
        this.volume = volume;
        SharedPreferences.Editor ed = spSettings.edit();
        ed.putInt("volume", volume);
        ed.apply();
    }
}
