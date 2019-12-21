package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ExecuteExercises extends AppCompatActivity {

    private TextView tvNameExercise;
    private ImageView ivImageExercise;
    private TextView tvCountDownTimerExecuteExercise;
    private TextView tvCountRepeatCurrentExercise;
    private TextView tvMetricsCurrentExercise;
    private ProgressBar pbExecuteExercise;
    private TextView tvSkipExercise;
    private RelativeLayout rlCounter;
    private RelativeLayout rlOK;
    private TextView tvOK;

    private Queue<Exercise> exercises;

    private boolean isSkip;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_exercises);

        exercises = Exercises.getInstance().getExercises();

        tvNameExercise = findViewById(R.id.tvNameCurrentExercise);
        ivImageExercise = findViewById(R.id.ivImageExercise);
        tvCountDownTimerExecuteExercise = findViewById(R.id.tvCountDownTimerExecuteExercise);
        pbExecuteExercise = findViewById(R.id.pbExecuteExercise);
        tvCountRepeatCurrentExercise = findViewById(R.id.tvCountRepeatCurrentExercise);
        tvMetricsCurrentExercise = findViewById(R.id.tvMetricsCurrentExercise);
        tvSkipExercise = findViewById(R.id.tvSkipExercise);
        tvOK = findViewById(R.id.tvOK);

        rlCounter = findViewById(R.id.rlCounter);
        rlOK = findViewById(R.id.rlOK);

        final Exercise exercise = exercises.poll();

        tvNameExercise.setText(exercise.getName());
        tvCountRepeatCurrentExercise.setText(exercise.getCountRepeat());
        tvMetricsCurrentExercise.setText(exercise.getMetrics());

        String c = exercise.getCountRepeat();
        int count = Integer.parseInt(c);

        String filename = exercise.getImage();
        InputStream inputStream = null;
        try{
            inputStream = getApplicationContext().getAssets().open(filename);
            Drawable d = Drawable.createFromStream(inputStream, null);
            ivImageExercise.setImageDrawable(d);
            ivImageExercise.setScaleType(ImageView.ScaleType.FIT_XY);
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

        tvSkipExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exercise.getMetrics().equals("s")) countDownTimer.cancel();
                finish();
            }
        });

        tvOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(exercise.getMetrics().equals("s")) {
            rlCounter.setVisibility(View.VISIBLE);
            pbExecuteExercise.setMax(count);
            countDownTimer = new CountDownTimer(count*1000, 1000) {

                public void onTick(long millisUntilFinished) {
                    if (isSkip) {
                        finish();
                    }
                    int time = new BigDecimal(millisUntilFinished / 1000).intValueExact();
                    tvCountDownTimerExecuteExercise.setText("" + time);
                    pbExecuteExercise.setProgress(time);
                }

                public void onFinish() {
                    finish();
                }
            }
                    .start();
        }
        else if(exercise.getMetrics().equals("x")){
            rlOK.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed(){

    }


}
