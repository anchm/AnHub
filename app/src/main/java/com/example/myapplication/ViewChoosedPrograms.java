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

public class ViewChoosedPrograms extends AppCompatActivity {

    private MyDatabase myDatabase;

    ListView lvTrainingPrograms;

    static private final String SQL_REQUEST_TRAINING_PROGRAMS = "SELECT * FROM trainingprograms WHERE ischoosed = 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_choosed_programs);

        myDatabase = MyDatabase.getInstance();
        SQLiteDatabase mDb = myDatabase.getDatabase();

        ArrayList<HashMap<String, Object>> programs = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> program;

        Cursor cursor = mDb.rawQuery(SQL_REQUEST_TRAINING_PROGRAMS, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            program = new HashMap<String, Object>();

            program.put("id", cursor.getString(0));
            program.put("program",  cursor.getString(1));
            program.put("lvl",  cursor.getString(2));
            program.put("progress",  cursor.getString(4));

            programs.add(program);

            cursor.moveToNext();
        }
        cursor.close();

        String[] from = { "id", "program", "lvl", "progress"};
        int[] to = { R.id.tvIdProgram, R.id.tvProgram, R.id.tvLvl, R.id.tvProgress};

        SimpleAdapter adapter = new SimpleAdapter(this, programs, R.layout.adapter_training_program, from, to);
        lvTrainingPrograms = findViewById(R.id.lvTrainigPrograms);
        lvTrainingPrograms.setAdapter(adapter);

        final Intent viewDaysProgramIntent = new Intent(this, ViewDaysPrograms.class);

        lvTrainingPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvIdProgram = view.findViewById(R.id.tvIdProgram);
                TextView tvProgram = view.findViewById(R.id.tvProgram);
                TextView tvLvl = view.findViewById(R.id.tvLvl);
                viewDaysProgramIntent.putExtra("id", tvIdProgram.getText().toString());
                viewDaysProgramIntent.putExtra("program", tvProgram.getText().toString());
                viewDaysProgramIntent.putExtra("lvl", tvLvl.getText().toString());
                startActivity(viewDaysProgramIntent);
            }
        });
    }

    @Override
    public void onBackPressed(){

    }
}
