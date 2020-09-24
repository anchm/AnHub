package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.myapplication.Models.Settings;
import com.example.myapplication.R;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.Thread.sleep;

public class Advertising extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising);

        ImageView imageView = findViewById(R.id.advertising_image_view);
        InputStream inputStream = null;
        try {
            inputStream = getApplicationContext().getAssets().open("kitekat.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(inputStream, null);
        imageView.setImageDrawable(d);

        final Intent chooseTrainingProgramsIntent = new Intent(this, ChooseTrainingPrograms.class);

        final Settings settings = Settings.getInstance(this);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        Runnable runnable = new Runnable() {
            public void run() {
                int progress = 0;
                while(progressBar.getProgress() < 100){
                    progress += 10;
                    try {
                        sleep(1000);
                        progressBar.setProgress(progress);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                settings.setViewAdvertising(true);
                startActivity(chooseTrainingProgramsIntent);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}