package com.example.myapplication;

public class Exercise {
    private String name = "";
    private String countRepeat;
    private String image = "";

    public void setName(String name){
        this.name = name;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setCountRepeat(String countRepeat){
        this.countRepeat = countRepeat;
    }

    public String getName(){
        return name;
    }

    public String getImage(){
        return image;
    }

    public String getCountRepeat(){
        return countRepeat;
    }

}
