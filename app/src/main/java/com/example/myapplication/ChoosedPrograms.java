package com.example.myapplication;

import java.util.HashMap;
import java.util.List;

public class ChoosedPrograms {

    private HashMap<String, List<String>> programs = new HashMap<>();

    private static ChoosedPrograms INSTANCE;

    private ChoosedPrograms() {
    }

    public static ChoosedPrograms getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ChoosedPrograms();
        }
        return INSTANCE;
    }

    public void setLvlPrograms(String program, List<String> lvls){
        programs.put(program, lvls);
    }

    public HashMap<String, List<String>> getPrograms(){
        return programs;
    }
}
