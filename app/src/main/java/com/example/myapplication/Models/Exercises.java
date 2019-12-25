package com.example.myapplication.Models;

import java.util.LinkedList;
import java.util.Queue;

public class Exercises {

    private static Exercises INSTANCE;

    private static Queue<Exercise> exercises = new LinkedList<>();
    private String idProgram;
    private String nameProgram;
    private String lvlProgram;
    private String day;
    private String idDay;
    private String calories;

    private Exercises() {
    }

    public static Exercises getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Exercises();
        }
        return INSTANCE;
    }

    public void addExercise(Exercise exercise){
        exercises.add(exercise);
    }

    public void clear(){
        exercises.clear();
    }

    public void setDay(String day){
        this.day = day;
    }

    public void setIdDay(String idDay){
        this.idDay = idDay;
    }

    public String getDay() {return day;}

    public String getIdDay() {return idDay;}

    public Queue<Exercise> getExercises() {
        return exercises;
    }

    public void setIdProgram(String idProgram){
        this.idProgram = idProgram;
    }

    public String getIdProgram(){
        return idProgram;
    }

    public String getNameProgram() {return nameProgram;}

    public String getLvlProgram() {return lvlProgram;}

    public void setNameProgram(String nameProgram) {this.nameProgram = nameProgram;}

    public void setLvlProgram(String lvlProgram) {this.lvlProgram = lvlProgram;}

    public void setCalories(String calories){
        this.calories = calories;
    }

    public String getCalories() {
        return calories;
    }
}
