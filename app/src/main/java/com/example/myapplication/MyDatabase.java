package com.example.myapplication;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

public class MyDatabase {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private static MyDatabase INSTANCE;

    private MyDatabase() {
    }

    public static MyDatabase getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MyDatabase();
        }
        return INSTANCE;
    }

    public void initDatabase(Context context){
        databaseHelper = new DatabaseHelper(context);

        try {
            databaseHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        database = databaseHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }
}
