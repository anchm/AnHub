package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences = null;

    private static final String FILE_NAME_DATA_ABOUT_APP = "data_about_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(FILE_NAME_DATA_ABOUT_APP, MODE_PRIVATE);

        if (sharedPreferences.getBoolean("firstrun", true)) {
            sharedPreferences.edit().putBoolean("firstrun", false).apply();
            Intent chooseTrainingProgramIntent = new Intent(this, ChooseTrainingPrograms.class);
            startActivity(chooseTrainingProgramIntent);
        }
        else {
            Intent menuIntent = new Intent(this, ChooseTrainingPrograms.class);
            menuIntent.putExtra("activity", "ViewChoosedPrograms");
            startActivity(menuIntent);
        }
    }

}
