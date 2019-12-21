package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences = null;

    private static final String FILE_NAME_DATA_ABOUT_APP = "data_about_app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(FILE_NAME_DATA_ABOUT_APP, MODE_PRIVATE);

        MyDatabase myDatabase = MyDatabase.getInstance();
        myDatabase.initDatabase(this);

        DataAboutYou dataAboutYou = DataAboutYou.getInstance();
        dataAboutYou.loadData(this);

        if (sharedPreferences.getBoolean("firstrun", true)) {
            sharedPreferences.edit().putBoolean("firstrun", false).apply();
            Intent chooseTrainingProgramIntent = new Intent(this, ChooseTrainingPrograms.class);
            startActivity(chooseTrainingProgramIntent);
        }
        else {
            //sharedPreferences.edit().putBoolean("firstrun", true).apply();
            Intent menuIntent = new Intent(this, Menu.class);
            menuIntent.putExtra("activity", "ViewChoosedPrograms");
            startActivity(menuIntent);
        }
    }

}
