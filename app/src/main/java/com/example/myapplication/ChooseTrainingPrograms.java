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

    Button btnWaist;
    Button btnLegs;
    Button btnComplete;
    RelativeLayout layoutChooseLvl;

    private MyDatabase myDatabase;

    private Intent entryDataAboutYouIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_training_programs);

        btnWaist = findViewById(R.id.btnWaist);
        btnLegs = findViewById(R.id.btnLegs);
        btnComplete = findViewById(R.id.btnComplete);
        layoutChooseLvl = findViewById(R.id.layoutChooseLvl);

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

        entryDataAboutYouIntent = new Intent(this, EntryDataAboutYou.class);

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
                    String request = "UPDATE trainingprograms SET ischoosed = 1 WHERE program = \"" + entry.getKey() + "\" AND lvl = \"" + lvl + "\"";
                    mDb.execSQL(request);
                }
            }
            startActivity(entryDataAboutYouIntent);
        }
    }

    @Override
    public void onBackPressed(){

    }

}
