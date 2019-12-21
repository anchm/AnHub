package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EntryDataAboutYou extends AppCompatActivity {

    static final int ENTRY_WEIGHT_REQUEST = 0;
    static final int ENTRY_HEIGHT_REQUEST = 1;

    TextView tvWeight;
    TextView tvHeight;
    TextView tvBMI;
    Button btnGoToPrograms;

    DataAboutYou dataAboutYou = DataAboutYou.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent entryWeightIntent = new Intent(this, EntryWeight.class);
        startActivityForResult(entryWeightIntent, ENTRY_WEIGHT_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ENTRY_WEIGHT_REQUEST){
            Intent entryHeightIntent = new Intent(this, EntryHeight.class);
            startActivityForResult(entryHeightIntent, ENTRY_HEIGHT_REQUEST);
        }
        else {
            setContentView(R.layout.activity_entry_data_about_you);

            tvHeight = findViewById(R.id.tvHeightValue);
            tvWeight = findViewById(R.id.tvWeightValue);
            tvBMI = findViewById(R.id.tvBMIValue);

            int height = dataAboutYou.getHeight();
            int weight = dataAboutYou.getWeight();
            float dHeight = (float) height /100;
            float dWeight = (float) weight;
            int BMI = Math.round(dWeight/dHeight/dHeight);

            dataAboutYou.setBMI(BMI);

            dataAboutYou.writeData(this);

            tvHeight.setText(Integer.toString(height));
            tvWeight.setText(Integer.toString(weight));
            tvBMI.setText(Integer.toString((int)BMI));

            btnGoToPrograms = findViewById(R.id.btnGoToPrograms);

            final Intent menuIntent = new Intent(this, Menu.class);

            View.OnClickListener oclBtnGoToPrograms = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuIntent.putExtra("activity", "ViewChoosedPrograms");
                    startActivity(menuIntent);
                }
            };
            btnGoToPrograms.setOnClickListener(oclBtnGoToPrograms);

        }
    }

    @Override
    public void onBackPressed(){

    }


}
