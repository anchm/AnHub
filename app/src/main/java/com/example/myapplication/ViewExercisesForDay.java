package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewExercisesForDay extends AppCompatActivity {


    private MyDatabase myDatabase;

    TextView tvCurrentProgram;
    TextView tvCurrentLvl;
    TextView tvCurrentDay;
    ListView lvExercisesForDay;
    Button btnStartTrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercises_for_day);

        Bundle arguments = getIntent().getExtras();

        String idProgramDay = arguments.getString("id");
        String program = arguments.getString("program");
        String lvl = arguments.getString("lvl");
        String day = arguments.getString("day");

        tvCurrentProgram = findViewById(R.id.tvCurrentProgramInExercises);
        tvCurrentLvl = findViewById(R.id.tvCurrentLvlInExercises);
        tvCurrentDay = findViewById(R.id.tvCurrentDayValue);

        btnStartTrain = findViewById(R.id.btnStartTrain);

        tvCurrentProgram.setText(program);
        tvCurrentLvl.setText(lvl);
        tvCurrentDay.setText(day);

        myDatabase = MyDatabase.getInstance();
        SQLiteDatabase mDb = myDatabase.getDatabase();

        ArrayList<HashMap<String, Object>> exercises = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> exercise;

        String sql_request_exercises_for_day = String.format("SELECT * FROM exercisesforday LEFT JOIN exercisesinfo USING (name) WHERE programDay = %s", idProgramDay);

        Cursor cursor = mDb.rawQuery(sql_request_exercises_for_day, null);
        cursor.moveToFirst();

        Exercises exercisesClass = Exercises.getInstance();
        exercisesClass.clear();

        exercisesClass.setIdProgram(idProgramDay);

        while (!cursor.isAfterLast()) {
            exercise = new HashMap<String, Object>();

            String nameExercise = cursor.getString(2);
            String countRepeatExercise = cursor.getString(3);
            String imageExercise = cursor.getString(4);
            exercise.put("nameExercise", nameExercise);
            exercise.put("countRepeatExercise", countRepeatExercise);
            exercise.put("imageExercise", imageExercise);

            exercises.add(exercise);

            Exercise exerciseClass = new Exercise();
            exerciseClass.setName(nameExercise);
            exerciseClass.setCountRepeat(countRepeatExercise);
            exerciseClass.setImage(imageExercise);
            exercisesClass.addExercise(exerciseClass);

            cursor.moveToNext();
        }
        cursor.close();

        String[] from = { "nameExercise", "countRepeatExercise"};
        int[] to = { R.id.tvNameExercise, R.id.tvCountRepeatExercise };

        SimpleAdapter adapter = new SimpleAdapter(this, exercises, R.layout.adapter_exercises_for_day, from, to);
        lvExercisesForDay = findViewById(R.id.lvExercisesForDay);
        lvExercisesForDay.setAdapter(adapter);

        final Intent controllerExercisesIntent = new Intent(this, ControllerExercises.class);

        btnStartTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(controllerExercisesIntent);
            }
            }
        );

    }
}
