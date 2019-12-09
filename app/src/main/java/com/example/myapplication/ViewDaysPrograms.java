package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewDaysPrograms extends AppCompatActivity {

    private MyDatabase myDatabase;

    ListView lvDaysPrograms;
    TextView tvCurrentProgram;
    TextView tvCurrentLvl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_days_programs);

        Bundle arguments = getIntent().getExtras();

        String idProgram = arguments.getString("id");
        final String program = arguments.getString("program");
        final String lvl = arguments.getString("lvl");

        tvCurrentProgram = findViewById(R.id.tvCurrentProgram);
        tvCurrentLvl = findViewById(R.id.tvCurrentLvl);

        tvCurrentProgram.setText(program);
        tvCurrentLvl.setText(lvl);

        myDatabase = MyDatabase.getInstance();
        SQLiteDatabase mDb = myDatabase.getDatabase();

        ArrayList<HashMap<String, Object>> exercises = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> exercise;

        String sql_request_days_program = String.format("SELECT * FROM daysprograms WHERE program = %s", idProgram);

        Cursor cursor = mDb.rawQuery(sql_request_days_program, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            exercise = new HashMap<String, Object>();

            exercise.put("id", cursor.getString(0));
            exercise.put("day", cursor.getString(2));

            exercises.add(exercise);

            cursor.moveToNext();
        }
        cursor.close();

        String[] from = { "id", "day"};
        int[] to = { R.id.tvIdDayProgram, R.id.tvDayValue};

        SimpleAdapter adapter = new SimpleAdapter(this, exercises, R.layout.adapter_days_program, from, to);
        lvDaysPrograms = findViewById(R.id.lvDaysPrograms);
        lvDaysPrograms.setAdapter(adapter);

        final Intent viewExercisesForDayIntent = new Intent(this, ViewExercisesForDay.class);

        lvDaysPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvIdDayProgram = view.findViewById(R.id.tvIdDayProgram);
                TextView tvDayValue = view.findViewById(R.id.tvDayValue);

                viewExercisesForDayIntent.putExtra("id", tvIdDayProgram.getText().toString());
                viewExercisesForDayIntent.putExtra("day", tvDayValue.getText().toString());
                viewExercisesForDayIntent.putExtra("program", program);
                viewExercisesForDayIntent.putExtra("lvl", lvl);

                startActivity(viewExercisesForDayIntent);
            }
        });

    }
}
