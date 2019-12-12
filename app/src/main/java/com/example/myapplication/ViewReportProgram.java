package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

        int ROWS = 6;
        int COLUMNS = 5;

        TableLayout tableLayout = findViewById(R.id.tableLayoutViewReportProgram);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < ROWS; i++) {

            TableRow tableRow = new TableRow(this);

            for (int j = 0; j < COLUMNS; j++) {
                RelativeLayout relativeLayout = new RelativeLayout(this);
                relativeLayout.setGravity(Gravity.CENTER);
                TextView tvNumber = new TextView(this);
                tvNumber.setTextSize(30);
                Integer num = i*COLUMNS+j+1;
                tvNumber.setText(num.toString());
                relativeLayout.addView(tvNumber);
                if(num<countCompletedDayValue+1) relativeLayout.setBackgroundResource(R.drawable.button_pressed);
                else relativeLayout.setBackgroundResource(R.drawable.button_not_pressed);
                tableRow.addView(relativeLayout, j);
            }

            tableLayout.addView(tableRow, i);
        }
    }
}
