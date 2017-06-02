package com.example.moree.mytvapp1.Denmark;

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
import com.example.moree.mytvapp1.Video;

import java.util.ArrayList;

/**
 * Created by moree on 1/9/2017.
 */

public class DenmarkChannels extends Fragment {
    Context context;
    ArrayList<String> Denlink = new ArrayList<>();
    ArrayList<String> DenPics1 = new ArrayList<>();
    ArrayList<String> DenNames = new ArrayList<>();
    MyCountries myCountries;
    GridView myDENList;

    public DenmarkChannels() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        myCountries = new MyCountries();
        //Save_DenLinks();
        Denlink.clear();
        DenPics1.clear();
        Get_DenData();
        myCountries.Find(context);
        View DENChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        myDENList = (GridView) DENChannelInf.findViewById(R.id.MyGridView);
        myDENList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context,Denlink.get(i),DenPics1.get(i));
            }
        });
        myDENList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        return DENChannelInf;
    }

    private void Save_DenLinks() {
        DenmarkData denmarkData = new DenmarkData();
        denmarkData.ChannelDenLink = "http://1.442244.info/de_sky_sport_1/index.m3u8";
        Backendless.Persistence.of(DenmarkData.class).save(denmarkData, new AsyncCallback<DenmarkData>() {
            @Override
            public void handleResponse(DenmarkData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void Get_DenData() {
        Backendless.Persistence.of(DenmarkData.class).find(new AsyncCallback<BackendlessCollection<DenmarkData>>() {
            @Override
            public void handleResponse(BackendlessCollection<DenmarkData> response) {
                for (DenmarkData item : response.getData()) {
                    Denlink.add(item.ChannelDenLink);
                    DenPics1.add(item.ChannelDenPic);
                    DenNames.add(item.ChannelDenName);
                }
                myDENList.setAdapter(new MyCountryAdapter(context, DenPics1,Denlink));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
