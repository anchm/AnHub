package com.example.myapplication.Models;

public class Exercise {
    private String name;
    private String countRepeat;
    private String image;
    private String metrics;

    public void setName(String name){
        this.name = name;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setCountRepeat(String countRepeat){
        this.countRepeat = countRepeat;
    }

    public void setMetrics(String metrics) { this.metrics = metrics; }

    public String getName(){
        return name;
    }

    public String getImage(){
        return image;
    }

    public String getCountRepeat(){
        return countRepeat;
    }

    public String getMetrics() { return metrics; }

}
