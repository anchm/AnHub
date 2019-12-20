package com.example.myapplication;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

public class Menu extends TabActivity {

    private TabHost tabHost = null;

    private final String VIEW_CHOOSED_PROGRAMS_TAG = "ViewChoosedPrograms";
    private final String VIEW_DATA_ABOUT_YOU_TAG = "ViewDataAboutYou";
    private final String VIEW_SETTINGS_TAG = "ViewSettings";

    private final int VIEW_CHOOSED_PROGRAMS_NUM = 0;
    private final int VIEW_DATA_ABOUT_YOU_NUM = 1;
    private final int VIEW_SETTINGS_NUM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        setTabSec(VIEW_CHOOSED_PROGRAMS_TAG, "Choosed programs", VIEW_CHOOSED_PROGRAMS_NUM, ViewChoosedPrograms.class);
        setTabSec(VIEW_DATA_ABOUT_YOU_TAG, "You data", VIEW_DATA_ABOUT_YOU_NUM, ViewDataAboutYou.class);
        setTabSec(VIEW_SETTINGS_TAG, "Settings", VIEW_SETTINGS_NUM, ViewSettings.class);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String arg0) {
                tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.button_not_pressed);
                tabHost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.button_not_pressed);
                tabHost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.button_not_pressed);

                tabHost.setCurrentTabByTag(arg0);
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.button_pressed);
            }
        });

        String activityName = getIntent().getStringExtra("activity");
        tabHost.setCurrentTabByTag(activityName);
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.button_pressed);
    }

    private void setTabSec(String nameTag, String textTab, int num, Class classActivity){
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(nameTag);
        tabSpec.setIndicator(textTab);
        tabSpec.setContent(new Intent(this, classActivity));
        tabHost.addTab(tabSpec);
        tabHost.getTabWidget().getChildAt(num).setBackgroundResource(R.drawable.button_not_pressed);
        tabHost.getTabWidget().getChildAt(num).setAlpha(0.6f);
    }

}
