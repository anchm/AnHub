package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewChoosedPrograms extends AppCompatActivity {

    ListView lvTrainingPrograms;

    ArrayList<HashMap<String, Object>> programs = new ArrayList<HashMap<String, Object>>();

    static private final String SQL_REQUEST_TRAINING_PROGRAMS = "SELECT * FROM trainingprograms WHERE ischoosed = 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_choosed_programs);

        MyDatabase myDatabase = MyDatabase.getInstance();
        SQLiteDatabase mDb = myDatabase.getDatabase();

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

        ChoosedProgramsAdapter adapter = new ChoosedProgramsAdapter(this);

        lvTrainingPrograms = findViewById(R.id.lvTrainingPrograms);
        lvTrainingPrograms.setAdapter(adapter);

        final Intent viewDaysProgramIntent = new Intent(this, ViewDaysPrograms.class);

        final Exercises exercises = Exercises.getInstance();

        lvTrainingPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(viewDaysProgramIntent);

                TextView tvIdProgram = view.findViewById(R.id.tvIdProgram);
                TextView tvProgram = view.findViewById(R.id.tvProgram);
                TextView tvLvl = view.findViewById(R.id.tvLvl);

                exercises.setIdProgram(tvIdProgram.getText().toString());
                exercises.setNameProgram(tvProgram.getText().toString());
                exercises.setLvlProgram(tvLvl.getText().toString());

            }
        });


        final Intent chooseTrainingProgramIntent = new Intent(this, ChooseTrainingPrograms.class);
        Button btnAddPrograms = findViewById(R.id.btnAddPrograms);

        btnAddPrograms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTrainingProgramIntent.putExtra("act", "add");
                startActivity(chooseTrainingProgramIntent);
            }
        });
    }

    class ChoosedProgramsAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;

        ChoosedProgramsAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return programs.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.adapter_training_program, null);

            TextView idProgram = convertView.findViewById(R.id.tvIdProgram);
            TextView tvProgram = convertView.findViewById(R.id.tvProgram);
            TextView tvLvl = convertView.findViewById(R.id.tvLvl);
            ProgressBar pbProgress = convertView.findViewById(R.id.pbProgress);

            HashMap program = programs.get(position);
            tvProgram.setText(program.get("program").toString());
            tvLvl.setText(program.get("lvl").toString());
            idProgram.setText(program.get("id").toString());

            int progress = Integer.parseInt(program.get("progress").toString());
            pbProgress.setProgress(progress);

            return convertView;
        }
    }

    @Override
    public void onBackPressed(){

    }


}
