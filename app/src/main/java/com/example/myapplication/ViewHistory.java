package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent menuIntent = new Intent(this, Menu.class);
        menuIntent.putExtra("activity", "ViewDataAboutYou");

        setContentView(R.layout.activity_view_history);

        Button btnViewDataAboutYou = findViewById(R.id.btnViewDataAboutYou);

        btnViewDataAboutYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(menuIntent);
            }
        });
    }
}