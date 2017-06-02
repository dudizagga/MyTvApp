package com.example.moree.mytvapp1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

public class Fragmentcontainer extends AppCompatActivity {
    //we call our fragment manager to mannage it
    private FragmentManager fm;
    //we call fragment transcation to change between layouts
    private FragmentTransaction ft;
    //our container that used to change with fragments to each class
    private LinearLayout conteiner;
    //image button to open wifi from phone incase there is no wifi
    private ImageButton wifi;
    private MainActivity activity;
    private utlShared ut;

    //context to use
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);
        setPointer();
    }

    private void setPointer() {
        this.context=this;
        conteiner=(LinearLayout)findViewById(R.id.fcontainer);
        wifi=(ImageButton)findViewById(R.id.btnwifi);
     //to use fragment manager
        fm = getFragmentManager();
        //fragment transaction will transite with fragment manager and set to begin transaction
        ft = fm.beginTransaction();
        //we add our layout that will transite throw it
        ft.add(R.id.fcontainer, new Panel());
        //save changes
        ft.commit();
        //set on click on wifi to show wifi settings to connect to wifi
        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent to open wifi settings
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));            }
        });
    }



    //shortcut to trainsit betweent fragmensts
    public void nextFragment(int container, Fragment className) {

        fm=getFragmentManager();
        ft=fm.beginTransaction();
        //everytime we need to replace we need container and class name that extends fragment
        ft.replace(container, className ).addToBackStack(null);
        //save changes
        ft.commit();
    }
}
