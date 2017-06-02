package com.example.moree.mytvapp1.Sport;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.moree.mytvapp1.MyCountries.MyCountries;
import com.example.moree.mytvapp1.MyCountries.MyCountryAdapter;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Spain.SpainData;
import com.example.moree.mytvapp1.Video;

import java.util.ArrayList;

/**
 * Created by moree on 2/27/2017.
 */

public class SportChannels extends Fragment {
Context context;
    ArrayList<String> Sportlink = new ArrayList<>();
    ArrayList<String> SportPics = new ArrayList<>();
    ArrayList<String> SportNames = new ArrayList<>();
    GridView Sportchannels;
    MyCountries myCountries;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
context=container.getContext();
myCountries=new MyCountries();
getSportData();
        View SportInflate=inflater.inflate(R.layout.my_grid_view,container,false);
Sportchannels=(GridView)SportInflate.findViewById(R.id.MyGridView);
        Sportchannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               myCountries.MyAlertDialog1(context,Sportlink.get(position),SportPics.get(position));

            }
        });
        Sportchannels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });


        return SportInflate;
    }

    private void getSportData() {
Backendless.Persistence.of(SportData.class).find(new AsyncCallback<BackendlessCollection<SportData>>() {
    @Override
    public void handleResponse(BackendlessCollection<SportData> response) {
        for (SportData item:response.getData())
        {
            SportPics.add(item.SportChannel_Pic);
            SportNames.add(item.Sport_Names);
            Sportlink.add(item.SportChannel_Link);
        }
        Sportchannels.setAdapter(new MyCountryAdapter(context,SportPics,SportNames));
    }

    @Override
    public void handleFault(BackendlessFault fault) {

    }
});
    }

}
