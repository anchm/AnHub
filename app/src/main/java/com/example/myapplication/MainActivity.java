package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent chooseTrainingProgramIntent = new Intent(this, ChooseTrainingPrograms.class);
        startActivity(chooseTrainingProgramIntent);
    }
}
