package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class ViewSettings extends AppCompatActivity {

    private final int ID_CHANGE_LANGUAGE = 0;
    private final int ID_CHANGE_VOLUME = 1;

    private Button btnChangeLanguage;
    private Button btnChangeVolume;

    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_settings);

        settings = Settings.getInstance(this);

        String language = "Language: " + settings.getLanguage();

        View.OnClickListener oclBtnSettings = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnChangeLanguage:
                        showDialog(ID_CHANGE_LANGUAGE);
                        break;
                    case R.id.btnChangeVolume:
                        showDialog(ID_CHANGE_VOLUME);
                        break;
                }
            }
        };

        btnChangeLanguage = findViewById(R.id.btnChangeLanguage);
        btnChangeLanguage.setText(language);
        btnChangeLanguage.setOnClickListener(oclBtnSettings);

        btnChangeVolume = findViewById(R.id.btnChangeVolume);
        btnChangeVolume.setText("volume: " + settings.getVolume());
        btnChangeVolume.setOnClickListener(oclBtnSettings);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==ID_CHANGE_LANGUAGE){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final String[] languages = {"English"};

            builder.setTitle("Set language");
            builder.setItems(languages, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    btnChangeLanguage.setText("Language: " + languages[item]);
                    settings.setLanguage(languages[item]);
                }
            });
            builder.setCancelable(false);
            return builder.create();
        }
        else if(id==ID_CHANGE_VOLUME){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_change_volume, null);
            builder.setView(view);
            SeekBar sbVolume = view.findViewById(R.id.sbVolume);
            final TextView tvVolumeValue = view.findViewById(R.id.tvVolumeValue);
            tvVolumeValue.setText(String.valueOf(settings.getVolume()));
            sbVolume.setProgress(settings.getVolume());

            final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            sbVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    tvVolumeValue.setText(String.valueOf(progress));
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    tvVolumeValue.setText(String.valueOf(seekBar.getProgress()));
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(), 0);
                }
            });
            final Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button button = view.findViewById(R.id.btnChangeVolumeOK);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            settings.setVolume(Integer.parseInt(tvVolumeValue.getText().toString()));
                            btnChangeVolume.setText("volume: " + settings.getVolume());
                            dialog.dismiss();
                        }
                    });
                }
            });
            dialog.show();
            return dialog;
        }
        return null;
    }

    @Override
    public void onBackPressed(){

    }

}
