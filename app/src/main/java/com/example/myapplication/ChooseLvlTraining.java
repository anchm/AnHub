package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseLvlTraining extends AppCompatActivity {

    Button btnSimpleLvl;
    Button btnMediumLvl;
    Button btnHardLvl;
    Button btnSelectLvlTrain;

    HashMap<Button, Boolean> stateButtons = new HashMap<>();

    ChoosedPrograms choosedPrograms = ChoosedPrograms.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int trainId = getIntent().getIntExtra("trainId", 1);

        setContentView(R.layout.activity_choose_lvl_training);

        btnSimpleLvl = findViewById(R.id.btnSimpleLvl);
        btnMediumLvl = findViewById(R.id.btnMediumLvl);
        btnHardLvl = findViewById(R.id.btnHardLvl);
        btnSelectLvlTrain = findViewById(R.id.btnSelectLvlTrain);

        stateButtons.put(btnSimpleLvl,false);
        stateButtons.put(btnMediumLvl,false);
        stateButtons.put(btnHardLvl,false);

        btnSimpleLvl.setBackgroundResource(R.drawable.button_not_pressed);
        btnMediumLvl.setBackgroundResource(R.drawable.button_not_pressed);
        btnHardLvl.setBackgroundResource(R.drawable.button_not_pressed);

        final String nameTrain;
        if(trainId == R.id.btnWaist){
            nameTrain = "waist";
        }
        else nameTrain = "legs";

        View.OnClickListener oclBtnChooseLvl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Map.Entry<Button, Boolean> entry : stateButtons.entrySet()){
                    if(entry.getKey().getId() == v.getId()){
                        if (!entry.getValue()){
                            v.setBackgroundResource(R.drawable.button_pressed);
                            entry.setValue(true);
                        }
                        else {
                            v.setBackgroundResource(R.drawable.button_not_pressed);
                            entry.setValue(false);
                        }
                    }
                }
            }
        };

        btnSimpleLvl.setOnClickListener(oclBtnChooseLvl);
        btnMediumLvl.setOnClickListener(oclBtnChooseLvl);
        btnHardLvl.setOnClickListener(oclBtnChooseLvl);

        View.OnClickListener oclBtnSelect = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> lvls = new ArrayList<>();
                for(Map.Entry<Button, Boolean> entry : stateButtons.entrySet()){
                    if(entry.getValue()){
                        lvls.add(entry.getKey().getText().toString());
                    }
                }
                if(!lvls.isEmpty()){
                    choosedPrograms.setLvlPrograms(nameTrain, lvls);
                }
                finish();
            }
        };

        btnSelectLvlTrain.setOnClickListener(oclBtnSelect);
    }


}
