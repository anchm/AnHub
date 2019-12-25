package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Models.Exercises;
import com.example.myapplication.Models.MusicPlayer;
import com.example.myapplication.Models.MyDatabase;
import com.example.myapplication.R;

import static java.lang.Integer.parseInt;

public class ViewReportExercises extends AppCompatActivity {

    private TextView tvTimeSpentValue;
    private TextView tvCaloriesBurned;
    private Button btnViewReportProgram;

    private Exercises exercises;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report_exercises);

        long timeSpent = getIntent().getLongExtra("timeSpent", 0);

        tvTimeSpentValue = findViewById(R.id.tvTimeSpentValue);
        tvCaloriesBurned = findViewById(R.id.tvCaloriesBurnedValue);
        btnViewReportProgram = findViewById(R.id.btnViewReportProgram);

        exercises = Exercises.getInstance();

        tvTimeSpentValue.setText(Integer.toString((int) timeSpent / 1000));
        tvCaloriesBurned.setText(exercises.getCalories());

        database = MyDatabase.getInstance().getDatabase();

        String request = "SELECT isCompleted FROM daysprograms WHERE id = " + exercises.getIdDay();
        Cursor cursor = database.rawQuery(request, null);
        cursor.moveToFirst();

        String newExecuted = new String();
        if(!cursor.isAfterLast()){
            newExecuted = cursor.getString(0);
        }
        cursor.close();


        if(newExecuted.equals("0")) {
            request = "UPDATE daysprograms SET isCompleted = 1 WHERE id = " + exercises.getIdDay();
            database.execSQL(request);

            request = "SELECT progress FROM trainingprograms WHERE id = " + exercises.getIdProgram();

            cursor = database.rawQuery(request, null);
            cursor.moveToFirst();

            String oldProgress = new String();
            if(!cursor.isAfterLast()){
                oldProgress = cursor.getString(0);
            }
            Integer newProgressValue = Integer.parseInt(oldProgress)+1;
            String newProgress = newProgressValue.toString();

            request = "UPDATE trainingprograms SET progress = " + newProgress + " WHERE id = " + exercises.getIdProgram();
            database.execSQL(request);
        }

        final Intent viewReportProgramIntent = new Intent(this, ViewReportProgram.class);

        btnViewReportProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(viewReportProgramIntent);
            }
        });


        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, MusicPlayer.BACKGROUND_VOLUME, 0);
    }

    @Override
    public void onBackPressed(){

    }

}
