package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewDaysPrograms extends AppCompatActivity {

    ListView lvDaysPrograms;
    TextView tvCurrentProgram;
    TextView tvCurrentLvl;

    ArrayList<HashMap<String, Object>> days = new ArrayList<>();

    private SQLiteDatabase mDb = MyDatabase.getInstance().getDatabase();

    private final Exercises exercisesClass = Exercises.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_days_programs);

        tvCurrentProgram = findViewById(R.id.tvCurrentProgram);
        tvCurrentLvl = findViewById(R.id.tvCurrentLvl);

        tvCurrentProgram.setText(exercisesClass.getNameProgram());
        tvCurrentLvl.setText(exercisesClass.getLvlProgram());

        HashMap<String, Object> day;

        int idProgram = Integer.parseInt(exercisesClass.getIdProgram());

        String sql_request_days_program = String.format("SELECT * FROM daysprograms WHERE program = %s", idProgram);

        Cursor cursor = mDb.rawQuery(sql_request_days_program, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            day = new HashMap<String, Object>();

            day.put("id", cursor.getString(0));
            day.put("day", cursor.getString(2));
            day.put("isCompleted", cursor.getString(3));
            day.put("calories", cursor.getString(4));

            days.add(day);

            cursor.moveToNext();
        }
        cursor.close();

        DaysProgramAdapter adapter = new DaysProgramAdapter(this);

        lvDaysPrograms = findViewById(R.id.lvDaysPrograms);
        lvDaysPrograms.setAdapter(adapter);

        final Intent viewExercisesForDayIntent = new Intent(this, ViewExercisesForDay.class);

        lvDaysPrograms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(viewExercisesForDayIntent);

                TextView tvIdDayProgram = view.findViewById(R.id.tvIdDayProgram);
                TextView tvDayValue = view.findViewById(R.id.tvDayValue);
                TextView tvCalories = view.findViewById(R.id.tvCalories);

                exercisesClass.setIdDay(tvIdDayProgram.getText().toString());
                exercisesClass.setDay(tvDayValue.getText().toString());
                exercisesClass.setCalories(tvCalories.getText().toString());
            }
        });

    }

    class DaysProgramAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;

        DaysProgramAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return days.size();
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
                convertView = mLayoutInflater.inflate(R.layout.adapter_days_program, null);

            TextView tvIdDayProgram = convertView.findViewById(R.id.tvIdDayProgram);
            TextView tvDayValue = convertView.findViewById(R.id.tvDayValue);
            ImageView ivIsCompletedDay = convertView.findViewById(R.id.ivIsCompletedDay);
            TextView tvCalories = convertView.findViewById(R.id.tvCalories);

            HashMap day = days.get(position);

            tvIdDayProgram.setText(day.get("id").toString());
            tvDayValue.setText(day.get("day").toString());
            tvCalories.setText(day.get("calories").toString());

            Drawable isCompletedDrawable;
            if(Integer.parseInt(day.get("isCompleted").toString()) != 0){
                isCompletedDrawable = getResources().getDrawable(R.drawable.is_completed);
            }
            else isCompletedDrawable = getResources().getDrawable(R.drawable.is_not_completed);

            ivIsCompletedDay.setImageDrawable(isCompletedDrawable);

            return convertView;
        }
    }
}
