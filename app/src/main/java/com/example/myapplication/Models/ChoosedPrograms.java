package com.example.myapplication.Models;

import java.util.ArrayList;
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

    public void setProgram(String program, String lvl){
        List<String> lvls = programs.get(program);
        if (lvls == null) {
            lvls = new ArrayList<>();
        }
        lvls.add(lvl);
        programs.put(program,lvls);
    }

    public List<String> getLvls(String program){
        return programs.get(program);
    }

    public void clearProgram(String program){
        List<String> lvls = programs.get(program);
        if(lvls!=null){
            lvls.clear();
        }
    }

    public boolean isEmpty(){
        return programs.isEmpty();
    }
}
