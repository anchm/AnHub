package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ExecuteExercises extends AppCompatActivity {

    private TextView tvNameExercise;
    private ImageView ivImageExercise;
    private TextView tvCountDownTimer;

    private Queue<Exercise> exercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_exercises);

        exercises = Exercises.getInstance().getExercises();

        tvNameExercise = findViewById(R.id.tvNameCurrentExercise);
        ivImageExercise = findViewById(R.id.ivImageExercise);
        tvCountDownTimer = findViewById(R.id.tvCountDownTimer);

        Exercise exercise = exercises.poll();
        tvNameExercise.setText(exercise.getName());
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

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCountDownTimer.setText("Осталось: "
                        + millisUntilFinished / 1000);
            }
            public void onFinish() {
                finish();
            }
        }
                .start();
    }

}
