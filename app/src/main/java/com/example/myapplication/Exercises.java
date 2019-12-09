package com.example.myapplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Exercises {

    private static Exercises INSTANCE;

    private Queue<Exercise> exercises = new LinkedList<>();
    private String idProgram;

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

    public Queue<Exercise> getExercises() {
        return exercises;
    }

    public void setIdProgram(String idProgram){
        this.idProgram = idProgram;
    }

    public String getIdProgram(){
        return idProgram;
    }
}
