package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EntryWeight extends AppCompatActivity {

    EditText etEntryWeight;
    Button btnEntryWeight;

    DataAboutYou dataAboutYou = DataAboutYou.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_weight);

        etEntryWeight = findViewById(R.id.etEntryWeight);
        btnEntryWeight = findViewById(R.id.btnEntryWeight);

        View.OnClickListener oclBtnEntryWeight = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etEntryWeight.getText().length()!=0){
                    dataAboutYou.setWeight(Integer.parseInt(etEntryWeight.getText().toString()));
                    finish();
                }
            }
        };

        btnEntryWeight.setOnClickListener(oclBtnEntryWeight);
    }

    @Override
    public void onBackPressed(){

    }
}
