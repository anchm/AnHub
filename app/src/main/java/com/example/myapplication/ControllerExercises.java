package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Queue;

import static java.lang.Math.toIntExact;

public class ControllerExercises extends AppCompatActivity {

    private TextView tvCurrentState;
    private TextView tvCountDownTimerRelaxation;
    private ProgressBar pbRelaxation;
    private TextView tvNameNextExercise;
    private TextView tvCountRepeatNextExercise;
    private TextView tvMetricsNextExercise;
    private ImageView ivNextExercise;

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
        tvMetricsNextExercise = findViewById(R.id.tvMetricsNextExercise);
        ivNextExercise = findViewById(R.id.ivNextExercise);

        pbRelaxation.setMax(5);


        exercises = Exercises.getInstance().getExercises();

        fixedRelaxationWindow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (exercises.size()>0){
            tvCurrentState.setText("Relaxation");

            fixedRelaxationWindow();
        }
        else {
            Intent viewReportExercisesIntent = new Intent(this, ViewReportExercises.class);
            viewReportExercisesIntent.putExtra("timeSpent", new Date().getTime() - date.getTime());
            startActivity(viewReportExercisesIntent);
        }
    }

    private void fixedRelaxationWindow(){
        Exercise exercise = exercises.peek();
        tvNameNextExercise.setText(exercise.getName());
        tvCountRepeatNextExercise.setText(exercise.getCountRepeat());
        tvMetricsNextExercise.setText(exercise.getMetrics());

        String filename = exercise.getImage();
        InputStream inputStream = null;
        try{
            inputStream = getApplicationContext().getAssets().open(filename);
            Drawable d = Drawable.createFromStream(inputStream, null);
            ivNextExercise.setImageDrawable(d);
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

        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                int time = new BigDecimal(millisUntilFinished/1000).intValueExact();
                tvCountDownTimerRelaxation.setText("" + time);
                pbRelaxation.setProgress(time);
            }

            public void onFinish() {
                startActivityForResult(executeExercisesIntent, EXECUTE_EXERCISES_REQUEST);
            }
        }
                .start();

    }

    @Override
    public void onBackPressed(){

    }
}
