package com.example.myapplication.Views;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Models.Exercise;
import com.example.myapplication.Models.Exercises;
import com.example.myapplication.Models.MyDatabase;
import com.example.myapplication.R;
import com.example.myapplication.Views.ControllerExercises;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewExercisesForDay extends AppCompatActivity {


    private MyDatabase myDatabase;

    TextView tvCurrentProgram;
    TextView tvCurrentLvl;
    TextView tvCurrentDay;
    ListView lvExercisesForDay;
    Button btnStartTrain;

    ArrayList<HashMap<String, Object>> exercises = new ArrayList<HashMap<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercises_for_day);

        tvCurrentProgram = findViewById(R.id.tvCurrentProgramInExercises);
        tvCurrentLvl = findViewById(R.id.tvCurrentLvlInExercises);
        tvCurrentDay = findViewById(R.id.tvCurrentDayValue);

        btnStartTrain = findViewById(R.id.btnStartTrain);

        Exercises exercisesClass = Exercises.getInstance();

        tvCurrentProgram.setText(exercisesClass.getNameProgram());
        tvCurrentLvl.setText(exercisesClass.getLvlProgram());
        tvCurrentDay.setText(exercisesClass.getDay());

        myDatabase = MyDatabase.getInstance();
        SQLiteDatabase mDb = myDatabase.getDatabase();

        HashMap<String, Object> exercise;

        String sql_request_exercises_for_day = String.format("SELECT * FROM exercisesforday LEFT JOIN exercisesinfo USING (name) WHERE programDay = %s", exercisesClass.getIdDay());

        Cursor cursor = mDb.rawQuery(sql_request_exercises_for_day, null);
        cursor.moveToFirst();

        exercisesClass.clear();

        while (!cursor.isAfterLast()) {
            exercise = new HashMap<String, Object>();

            String nameExercise = cursor.getString(2);
            String countRepeatExercise = cursor.getString(3);
            String imageExercise = cursor.getString(4);
            String metricsExercise = cursor.getString(5);
            exercise.put("nameExercise", nameExercise);
            exercise.put("countRepeatExercise", countRepeatExercise);
            exercise.put("imageExercise", imageExercise);
            exercise.put("metricsExercise", metricsExercise);

            exercises.add(exercise);

            Exercise exerciseClass = new Exercise();

            exerciseClass.setName(nameExercise);
            exerciseClass.setCountRepeat(countRepeatExercise);
            exerciseClass.setImage(imageExercise);
            exerciseClass.setMetrics(metricsExercise);

            exercisesClass.addExercise(exerciseClass);

            cursor.moveToNext();
        }
        cursor.close();

        ExercisesForDayAdapter adapter = new ExercisesForDayAdapter(this);

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

    class ExercisesForDayAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;

        ExercisesForDayAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return exercises.size();
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
                convertView = mLayoutInflater.inflate(R.layout.adapter_exercises_for_day, null);

            TextView tvNameExercise = convertView.findViewById(R.id.tvNameExercise);
            TextView tvCountRepeatExercise = convertView.findViewById(R.id.tvCountRepeatExercise);
            TextView tvMetricsExercise = convertView.findViewById(R.id.tvMetricsExercise);
            ImageView ivExercise = convertView.findViewById(R.id.ivExercise);

            HashMap exercise = exercises.get(position);
            tvNameExercise.setText(exercise.get("nameExercise").toString());
            tvCountRepeatExercise.setText(exercise.get("countRepeatExercise").toString());
            tvMetricsExercise.setText(exercise.get("metricsExercise").toString());

            String filename = exercise.get("imageExercise").toString();
            InputStream inputStream = null;
            try{
                inputStream = getApplicationContext().getAssets().open(filename);
                Drawable d = Drawable.createFromStream(inputStream, null);
                ivExercise.setImageDrawable(d);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            finally {
                try{
                    if(inputStream!=null)
                        inputStream.close();
                }
                catch (IOException ex){
                    ex.printStackTrace();
                }
            }

            return convertView;
        }
    }
}
