package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseTrainingPrograms extends AppCompatActivity {

    private MyDatabase myDatabase;

    private String act;

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
        btnLegs.setOnClickListener(oclBtnWaistAndLegs);


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
