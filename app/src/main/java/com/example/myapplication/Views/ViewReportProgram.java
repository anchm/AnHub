package com.example.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.myapplication.Models.Exercises;
import com.example.myapplication.Models.MyDatabase;
import com.example.myapplication.R;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class ViewReportProgram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report_program);

        SQLiteDatabase database = MyDatabase.getInstance().getDatabase();

        Exercises exercises = Exercises.getInstance();

        String request = "SELECT COUNT(*) FROM daysprograms WHERE isCompleted = 1 AND program = " + exercises.getIdProgram();
        Cursor cursor = database.rawQuery(request, null);
        cursor.moveToFirst();
        int countCompletedDayValue = 0;
        if(!cursor.isAfterLast()){
            countCompletedDayValue = cursor.getInt(0);
        }
        cursor.close();

        request = "SELECT calories FROM daysprograms WHERE isCompleted = 1 AND program = " + exercises.getIdProgram();
        cursor = database.rawQuery(request, null);
        cursor.moveToFirst();
        List<Entry> listCalories = new ArrayList<>();
        int x=0;
        if(!cursor.isAfterLast()){
            x++;
            listCalories.add(new Entry(x, cursor.getInt(0)));
        }
        cursor.close();

        int ROWS = 6;
        int COLUMNS = 5;

        TableLayout tableLayout = findViewById(R.id.tableLayoutViewReportProgram);
        for (int i = 0; i < ROWS; i++) {

            TableRow tableRow = new TableRow(this);

            for (int j = 0; j < COLUMNS; j++) {
                RelativeLayout relativeLayout = new RelativeLayout(this);
                relativeLayout.setPadding(5,5,5,5);
                relativeLayout.setGravity(Gravity.CENTER);
                relativeLayout.setAlpha(0.55f);
                TextView tvNumber = new TextView(this);
                tvNumber.setTextSize(35);
                Integer num = i*COLUMNS+j+1;
                tvNumber.setText(num.toString());
                relativeLayout.addView(tvNumber);
                if(num<countCompletedDayValue+1) relativeLayout.setBackgroundResource(R.drawable.button_pressed);
                else relativeLayout.setBackgroundResource(R.drawable.button_not_pressed);
                tableRow.addView(relativeLayout, j);
            }

            tableLayout.addView(tableRow, i);
        }

        Button btnFinishTraining = findViewById(R.id.btnFinishTraining);
        final Intent viewHistory = new Intent(this, ViewHistory.class);

        btnFinishTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(viewHistory);
            }
        });
    }


}
