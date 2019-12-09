package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EntryHeight extends AppCompatActivity {

    EditText etEntryHeight;
    Button btnEntryHeight;

    DataAboutYou dataAboutYou = DataAboutYou.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_height);

        etEntryHeight = findViewById(R.id.etEntryHeight);
        btnEntryHeight = findViewById(R.id.btnEntryHeight);

        View.OnClickListener oclBtnEntryHeight = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEntryHeight.getText().length()!=0){
                    dataAboutYou.setHeight(Integer.parseInt(etEntryHeight.getText().toString()));
                    finish();
                }
            }
        };

        btnEntryHeight.setOnClickListener(oclBtnEntryHeight);
    }

    @Override
    public void onBackPressed(){

    }
}
