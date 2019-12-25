package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Models.DataAboutYou;
import com.example.myapplication.R;

public class EntryHeight extends AppCompatActivity {

    private EditText etEntryHeight;
    private Button btnEntryHeight;

    private TextView tvIncorrectData;

    DataAboutYou dataAboutYou = DataAboutYou.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_height);

        etEntryHeight = findViewById(R.id.etEntryHeight);
        btnEntryHeight = findViewById(R.id.btnEntryHeight);

        tvIncorrectData = findViewById(R.id.tvIncorrectData);

        View.OnClickListener oclBtnEntryHeight = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = etEntryHeight.getText().toString();
                if (height.length()!=0){
                    int heightValue = Integer.parseInt(height);
                    if(heightValue>100 && heightValue<250) {
                        dataAboutYou.setHeight(heightValue);
                        finish();
                    }
                    else{
                        tvIncorrectData.setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        btnEntryHeight.setOnClickListener(oclBtnEntryHeight);
    }

    @Override
    public void onBackPressed(){

    }


}
