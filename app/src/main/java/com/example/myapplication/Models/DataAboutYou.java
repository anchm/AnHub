package com.example.myapplication.Models;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class DataAboutYou {

    private int height;
    private int weight;
    private int BMI;

    private static DataAboutYou INSTANCE;

    private SharedPreferences spDataAboutYou;

    private static final String FILE_NAME_DATA_ABOUT_YOU = "data_about_yourself";

    private DataAboutYou() {
    }

    public static DataAboutYou getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataAboutYou();
        }
        return INSTANCE;
    }

    public void setHeight(int height){
        this.height=height;
    }

    public void setWeight(int weight){
        this.weight=weight;
    }

    public void setBMI(int BMI){
        this.BMI=BMI;
    }

    public void recalculateBMI(){
        float dHeight = (float) height /100;
        float dWeight = (float) weight;
        BMI = Math.round(dWeight/dHeight/dHeight);
    }

    public void writeData(Context context){
        spDataAboutYou = context.getSharedPreferences(FILE_NAME_DATA_ABOUT_YOU, MODE_PRIVATE);
        SharedPreferences.Editor ed = spDataAboutYou.edit();
        ed.putInt("height", height);
        ed.putInt("weight", weight);
        ed.putInt("BMI", BMI);
        ed.apply();
    }

    public void loadData(Context context){
        spDataAboutYou = context.getSharedPreferences(FILE_NAME_DATA_ABOUT_YOU, MODE_PRIVATE);
        height = spDataAboutYou.getInt("height", 0);
        weight = spDataAboutYou.getInt("weight", 0);
        BMI = spDataAboutYou.getInt("BMI", 0);
    }

    public int getHeight(){
        return height;
    }

    public int getWeight(){
        return weight;
    }

    public int getBMI(){
        return BMI;
    }

}
