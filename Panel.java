package com.example.moree.mytvapp1;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.example.moree.mytvapp1.Catagory.Categories;
import com.example.moree.mytvapp1.MyCountries.MyCountries;
import com.example.moree.mytvapp1.MyFavorite.Favorite;

import java.util.List;

/**
 * Created by moree on 12/31/2016.
 */

public class Panel extends Fragment {
    private Context context;
    private LinearLayout container1;
    private splashScreen splashScreen;
    private Fragmentcontainer fragmentcontainer;
    private utlShared ut;
    private MainActivity activity;

    public Panel() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
    //we call fragmentcontainer to use its data
        fragmentcontainer = (Fragmentcontainer) getActivity();
        final View panel = inflater.inflate(R.layout.panel, container, false);

        ImageView btnCategories = (ImageView) panel.findViewById(R.id.btnCategories);
        ImageView btnFavorites = (ImageView) panel.findViewById(R.id.btnFavorites);
        ImageView btnCountries = (ImageView) panel.findViewById(R.id.btnCountries);
        ImageView btnLogout = (ImageView) panel.findViewById(R.id.btnLogout);

        container1 = (LinearLayout) panel.findViewById(R.id.container1);

//we have 4 image views with set onclick
        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //method from fragmentcontainer
                //method that replace fragment with the id of container and its new fragments
                fragmentcontainer.nextFragment(R.id.fcontainer, new Categories());
            }
        });
        btnCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentcontainer.nextFragment(R.id.fcontainer, new MyCountries());
                Toast.makeText(context, "moving to  My countries", Toast.LENGTH_SHORT).show();
            }
        });
        btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentcontainer.nextFragment(R.id.fcontainer, new Favorite());
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we call utlshared that hold sharedpreferences
                activity = new MainActivity();
                ut = new utlShared(context);
                //we call our main activity to set the logged use is false
                activity.setLogged(ut.putBol(false));
 //when  user log out the own id that was saved will be deleted
               String id=ut.getId("");
                //Toast.makeText(context, "Key Deleted: "+id, Toast.LENGTH_SHORT).show();
         //this method that delete keys
                ut.DeleteKey(id);
//after it done all this it will changed screen to login screen
                startActivity(new Intent(context,MainActivity.class));
                Toast.makeText(context, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
//return the view of fragment
        return panel;
    }






}