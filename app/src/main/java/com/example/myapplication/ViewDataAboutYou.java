package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;

public class ViewDataAboutYou extends AppCompatActivity {

    private DataAboutYou dataAboutYou;

    private TextView tvValue;

    private String data;

    private final int ID_CHANGE_HEIGHT = 0;
    private final int ID_CHANGE_WEIGHT = 1;

    private TextView tvHeight;
    private TextView tvWeight;
    private TextView tvBMI;
    private TextView tvIncorrectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_data_about_you);

        dataAboutYou = DataAboutYou.getInstance();

        tvHeight = findViewById(R.id.tvHeightValue);
        tvWeight = findViewById(R.id.tvWeightValue);
        tvBMI = findViewById(R.id.tvBMIValue);

        final TableRow trHeight = findViewById(R.id.trHeight);
        final TableRow trWeight = findViewById(R.id.trWeight);

        trHeight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(ID_CHANGE_HEIGHT);
            }
        });

        trWeight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(ID_CHANGE_WEIGHT);
            }
        });

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

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==ID_CHANGE_HEIGHT){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_change_data_about_you, null);
            builder.setView(view);
            final EditText etChangeDataAboutYouValue = view.findViewById(R.id.etChangeDataAboutYouValue);
            TextView tvChangeDataAboutYou = view.findViewById(R.id.tvChangeDataAboutYou);
            tvChangeDataAboutYou.setText("Change height");
            final Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            final Context context = this;
            tvIncorrectData = view.findViewById(R.id.tvIncorrectData);
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button buttonOK = view.findViewById(R.id.btnChangeDataAboutYouOK);
                    etChangeDataAboutYouValue.setText("");
                    tvIncorrectData.setVisibility(View.INVISIBLE);
                    buttonOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String height = etChangeDataAboutYouValue.getText().toString();
                            int heightValue = Integer.parseInt(height);
                            if(heightValue>0 && heightValue<250) {
                                tvHeight.setText(height);
                                dataAboutYou.setHeight(heightValue);
                                dataAboutYou.recalculateBMI();
                                tvBMI.setText(String.valueOf(dataAboutYou.getBMI()));
                                dataAboutYou.writeData(context);
                                dialog.dismiss();
                            }
                            else {
                                tvIncorrectData.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    Button btnCancel = view.findViewById(R.id.btnChangeDataAboutYouCancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
            });
            dialog.show();
            return dialog;
        }
        else if(id==ID_CHANGE_WEIGHT){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_change_data_about_you, null);
            builder.setView(view);
            final EditText etChangeDataAboutYouValue = view.findViewById(R.id.etChangeDataAboutYouValue);
            TextView tvChangeDataAboutYou = view.findViewById(R.id.tvChangeDataAboutYou);
            tvChangeDataAboutYou.setText("Change weight");
            final Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            final Context context = this;
            tvIncorrectData = view.findViewById(R.id.tvIncorrectData);
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button btnOK = view.findViewById(R.id.btnChangeDataAboutYouOK);
                    tvIncorrectData.setVisibility(View.INVISIBLE);
                    etChangeDataAboutYouValue.setText("");
                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String weight = etChangeDataAboutYouValue.getText().toString();
                            if (weight.length() != 0) {
                                int weightValue = Integer.parseInt(weight);
                                if(weightValue>0 && weightValue<500) {
                                    tvWeight.setText(weight);
                                    dataAboutYou.setWeight(weightValue);
                                    dataAboutYou.recalculateBMI();
                                    tvBMI.setText(String.valueOf(dataAboutYou.getBMI()));
                                    dataAboutYou.writeData(context);
                                    dialog.dismiss();
                                }
                                else {
                                    tvIncorrectData.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
                    Button btnCancel = view.findViewById(R.id.btnChangeDataAboutYouCancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
            });
            dialog.show();
            return dialog;
        }
        return null;
    }
}
