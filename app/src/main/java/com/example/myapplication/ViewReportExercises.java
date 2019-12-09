package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        tvTimeSpentValue.setText(Integer.toString((int) timeSpent / 1000));

        exercises = Exercises.getInstance();

        database = MyDatabase.getInstance().getDatabase();

        String request = "UPDATE daysprograms SET isCompleted = 1 WHERE id = " + exercises.getIdProgram();

        database.execSQL(request);

        final Intent viewReportProgramIntent = new Intent(this, ViewReportProgram.class);

        btnViewReportProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(viewReportProgramIntent);
            }
        });
    }
}
