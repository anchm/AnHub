package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EntryWeight extends AppCompatActivity {

    private EditText etEntryWeight;
    private Button btnEntryWeight;

    private TextView tvIncorrectData;

    DataAboutYou dataAboutYou = DataAboutYou.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_weight);

        etEntryWeight = findViewById(R.id.etEntryWeight);
        btnEntryWeight = findViewById(R.id.btnEntryWeight);

        tvIncorrectData = findViewById(R.id.tvIncorrectData);

        View.OnClickListener oclBtnEntryWeight = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight = etEntryWeight.getText().toString();
                if (weight.length()!=0){
                    int weightValue = Integer.parseInt(weight);
                    if(weightValue>30 && weightValue<500) {
                        dataAboutYou.setWeight(weightValue);
                        finish();
                    }
                    else{
                        tvIncorrectData.setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        btnEntryWeight.setOnClickListener(oclBtnEntryWeight);
    }

    @Override
    public void onBackPressed(){

    }

}
