package com.example.myapplication.Views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Models.ChoosedPrograms;
import com.example.myapplication.Models.MyDatabase;
import com.example.myapplication.Models.Settings;
import com.example.myapplication.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseTrainingPrograms extends AppCompatActivity {

    private MyDatabase myDatabase;

    private String act;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_training_programs);

        act = getIntent().getStringExtra("act");

        Button btnWaist = findViewById(R.id.btnWaist);
        Button btnLegs = findViewById(R.id.btnLegs);
        Button btnComplete = findViewById(R.id.btnComplete);

        final Intent chooseLvlTrainingIntent = new Intent(this, ChooseLvlTraining.class);

        View.OnClickListener oclBtnWaistAndLegs = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseLvlTrainingIntent.putExtra("trainId", v.getId());
                startActivity(chooseLvlTrainingIntent);
            }
        };
        btnWaist.setOnClickListener(oclBtnWaistAndLegs);

        final Intent viewAdvertisingIntent = new Intent(this, Advertising.class);
        viewAdvertisingIntent.putExtra("act", act);

        if(Settings.getInstance(this).getIsViewAdvertising()){
            btnLegs.setOnClickListener(oclBtnWaistAndLegs);
            btnLegs.setBackground(getResources().getDrawable(R.drawable.button_choose_program_background));
        }
        else{
            View.OnClickListener oclBtnViewAdvertising = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(viewAdvertisingIntent);
                }
            };
            btnLegs.setOnClickListener(oclBtnViewAdvertising);
            btnLegs.setBackground(getResources().getDrawable(R.drawable.button_choose_program_background_closed));
        }


        View.OnClickListener oclBtnComplete = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeChoosedPrograms();
            }
        };
        btnComplete.setOnClickListener(oclBtnComplete);

    }

    private void writeChoosedPrograms() {
        myDatabase = MyDatabase.getInstance();
        SQLiteDatabase mDb = myDatabase.getDatabase();

        String request = "UPDATE trainingprograms SET ischoosed = 0";
        mDb.execSQL(request);

        HashMap<String, List<String>> programs = ChoosedPrograms.getInstance().getPrograms();

        if (programs.isEmpty()){
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Please, select at least one program!",
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        else {
            for (Map.Entry<String, List<String>> entry : programs.entrySet()) {
                for (String lvl : entry.getValue()) {
                    request = "UPDATE trainingprograms SET ischoosed = 1 WHERE program = \"" + entry.getKey() + "\" AND lvl = \"" + lvl + "\"";
                    mDb.execSQL(request);
                }
            }
            if(act.equals("new")){
                Intent entryDataAboutYouIntent = new Intent(this, EntryDataAboutYou.class);
                startActivity(entryDataAboutYouIntent);
            }
            else if(act.equals("add")){
                Intent menuIntent = new Intent(this, Menu.class);
                menuIntent.putExtra("activity", "ViewChoosedPrograms");
                startActivity(menuIntent);
            }
        }
    }

    @Override
    public void onBackPressed(){
        if(act.equals("add")){
            super.onBackPressed();
        }
    }

}
