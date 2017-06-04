package com.example.moree.mytvapp1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Fragmentcontainer extends AppCompatActivity {
    //we call our fragment manager to mannage it
    private FragmentManager fm;
    //we call fragment transcation to change between layouts
    private FragmentTransaction ft;
    //our container that used to change with fragments to each class
    private LinearLayout conteiner;
    private WifiManager wifiManager;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    //image button to open wifi from phone incase there is no wifi
    private ImageButton btnwifi;
    private MainActivity activity;
    private utlShared ut;
    private SweetAlertDialog sweetAlertDialog;
    //context to use
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);
        setPointer();
    }

    private void setPointer() {
        this.context=this;
        conteiner=(LinearLayout)findViewById(R.id.fcontainer);
        btnwifi=(ImageButton)findViewById(R.id.btnwifi);
     //to use fragment manager
        fm = getFragmentManager();
        //fragment transaction will transite with fragment manager and set to begin transaction
        ft = fm.beginTransaction();
        //we add our layout that will transite throw it
        ft.add(R.id.fcontainer, new Panel());
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //save changes
        ft.commit();//set on click on wifi to show wifi settings to connect to wifi
        btnwifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent to open wifi settings
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        NetWorkName();
    }
    public void porgressdialog(Context context, String message) {
       this.context=context;
         sweetAlertDialog= new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText(message);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
            }
            public void dismisprogress()
            {
            sweetAlertDialog.dismiss();
            }

    private void NetWorkName() {
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                return;
            }
        } else {
            Toast.makeText(context, "Error Getting Data", Toast.LENGTH_SHORT).show();
            return;

        }
        if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            Toast.makeText(context, "connect2 to" + activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
            return;
        }
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
