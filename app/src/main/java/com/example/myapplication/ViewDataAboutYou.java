package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewDataAboutYou extends AppCompatActivity {

    private DataAboutYou dataAboutYou;

    private TextView tvValue;

    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_data_about_you);

        dataAboutYou = DataAboutYou.getInstance();

        TextView tvHeight = findViewById(R.id.tvHeightValue);
        TextView tvWeight = findViewById(R.id.tvWeightValue);
        final TextView tvBMI = findViewById(R.id.tvBMIValue);

        final RelativeLayout rlChange = findViewById(R.id.rlChange);

        final TableRow trHeight = findViewById(R.id.trHeight);
        final TableRow trWeight = findViewById(R.id.trWeight);
        final TableRow trBMI = findViewById(R.id.trBMI);

        final TextView tvChange = findViewById(R.id.tvChange);
        final EditText etChangeValue = findViewById(R.id.etChangeValue);
        Button btnChangeOK = findViewById(R.id.btnChangeOK);
        Button btnChangeCancel = findViewById(R.id.btnChangeCancel);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        trHeight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rlChange.setVisibility(View.VISIBLE);
               // etChangeValue.setHint(dataAboutYou.getHeight());
                etChangeValue.setText("");
                tvChange.setText("Change height");
                TableRow rowChange = (TableRow) v;
                tvValue = (TextView) rowChange.getChildAt(1);
                data = "height";
            }
        });

        trWeight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rlChange.setVisibility(View.VISIBLE);
               // etChangeValue.setHint(dataAboutYou.getWeight());
                etChangeValue.setText("");
                tvChange.setText("Change weight");
                TableRow rowChange = (TableRow) v;
                tvValue = (TextView) rowChange.getChildAt(1);
                data = "weight";
            }
        });

        final Context context = this;

        View.OnClickListener oclBtnChangeOK = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = etChangeValue.getText().toString();
                if (value.length() != 0){
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    rlChange.setVisibility(View.INVISIBLE);
                    tvValue.setText(value);
                    switch (data) {
                        case "height":
                            dataAboutYou.setHeight(Integer.parseInt(value));
                            break;
                        case "weight":
                            dataAboutYou.setWeight(Integer.parseInt(value));
                            break;
                    }
                    dataAboutYou.recalculateBMI();
                    tvBMI.setText(String.valueOf(dataAboutYou.getBMI()));
                    dataAboutYou.writeData(context);
                }
            }
        };
        btnChangeOK.setOnClickListener(oclBtnChangeOK);

        View.OnClickListener oclButtonChangeCancel = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                rlChange.setVisibility(View.GONE);
            }
        };
        btnChangeCancel.setOnClickListener(oclButtonChangeCancel);

        int height = dataAboutYou.getHeight();
        int weight = dataAboutYou.getWeight();
        float dHeight = (float) height /100;
        float dWeight = (float) weight;
        int BMI = Math.round(dWeight/dHeight/dHeight);

        dataAboutYou.setBMI(BMI);
        dataAboutYou.writeData(this);

        tvHeight.setText(Integer.toString(height));
        tvWeight.setText(Integer.toString(weight));
        tvBMI.setText(Integer.toString(BMI));

    }
}
