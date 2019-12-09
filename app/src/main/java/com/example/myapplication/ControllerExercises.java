package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Date;
import java.util.Queue;

public class ControllerExercises extends AppCompatActivity {

    private TextView tvCurrentState;
    private TextView tvCountDownTimerRelaxation;
    private ProgressBar pbRelaxation;
    private TextView tvNameNextExercise;
    private TextView tvCountRepeatNextExercise;

    private Queue<Exercise> exercises;

    static final int EXECUTE_EXERCISES_REQUEST = 0;

    private Intent executeExercisesIntent;

    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_exercises);

        date = new Date();

        executeExercisesIntent = new Intent(this, ExecuteExercises.class);

        tvCurrentState = findViewById(R.id.tvCurrentState);
        tvCountDownTimerRelaxation = findViewById(R.id.tvCountDownTimerRelaxation);
        pbRelaxation = findViewById(R.id.pbRelaxation);
        tvNameNextExercise = findViewById(R.id.tvNameNextExercise);
        tvCountRepeatNextExercise = findViewById(R.id.tvCountRepeatNextExercise);

        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvCountDownTimerRelaxation.setText("" + millisUntilFinished/1000);
            }

            public void onFinish() {
                startActivityForResult(executeExercisesIntent, EXECUTE_EXERCISES_REQUEST);
            }
        }
                .start();

        exercises = Exercises.getInstance().getExercises();

        Exercise exercise = exercises.peek();
        tvNameNextExercise.setText(exercise.getName());
        tvCountRepeatNextExercise.setText(exercise.getCountRepeat());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (exercises.size()>0){
            tvCurrentState.setText("Relaxation");

            Exercise exercise = exercises.peek();
            tvNameNextExercise.setText(exercise.getName());
            tvCountRepeatNextExercise.setText(exercise.getCountRepeat());

            new CountDownTimer(5000, 1000) {
                public void onTick(long millisUntilFinished) {
                    tvCountDownTimerRelaxation.setText("" + millisUntilFinished/1000);
                }

                public void onFinish() {
                    startActivityForResult(executeExercisesIntent, EXECUTE_EXERCISES_REQUEST);
                }
            }
                    .start();

        }
        else {
            Intent viewReportExercisesIntent = new Intent(this, ViewReportExercises.class);
            viewReportExercisesIntent.putExtra("timeSpent", new Date().getTime() - date.getTime());
            startActivity(viewReportExercisesIntent);
        }
    }
}
