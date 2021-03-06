package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Models.Exercise;
import com.example.myapplication.Models.Exercises;
import com.example.myapplication.Models.MusicPlayer;
import com.example.myapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ControllerExercises extends AppCompatActivity {

    private TextView tvCurrentState;
    private TextView tvCountDownTimerRelaxation;
    private ProgressBar pbRelaxation;
    private TextView tvNameNextExercise;
    private TextView tvCountRepeatNextExercise;
    private TextView tvMetricsNextExercise;
    private ImageView ivNextExercise;
    private TextView tvSkipRelax;

    private Queue<Exercise> exercises;

    static final int EXECUTE_EXERCISES_REQUEST = 0;

    private Intent executeExercisesIntent;

    private Date date;

    private boolean isSkip;

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
        tvSkipRelax = findViewById(R.id.tvSkipRelax);

        pbRelaxation.setMax(30);

        tvSkipRelax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSkip = true;
                startActivityForResult(executeExercisesIntent, EXECUTE_EXERCISES_REQUEST);
            }
        });

        exercises = Exercises.getInstance().getExercises();

        fixedRelaxationWindow();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                for(int i = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); i< MusicPlayer.EXECUTE_EXERCISES_VOLUME; i++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
                }
            }
        };
        new Thread(runnable).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (exercises.size()>0){
            tvCurrentState.setText("Relaxation");
            isSkip = false;
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

        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                if(isSkip) {
                    cancel();
                }
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
