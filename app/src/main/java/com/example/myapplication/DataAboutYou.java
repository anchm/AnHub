package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class DataAboutYou {

    private int height;
    private int weight;
    private float BMI;

    private static DataAboutYou INSTANCE;

    private SharedPreferences spDataAboutYou;

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

    public void setBMI(float BMI){
        this.BMI=BMI;
    }

    public void writeData(Context context){
        spDataAboutYou = context.getSharedPreferences(EntryDataAboutYou.FILE_NAME_DATA_ABOUT_YOU, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = spDataAboutYou.edit();
        ed.putInt("height", height);
        ed.putInt("weight", weight);
        ed.putFloat("BMI", BMI);
        ed.apply();
    }

    public int getHeight(){
        return height;
    }

    public int getWeight(){
        return weight;
    }

    public float getBMI(){
        return BMI;
    }

}
