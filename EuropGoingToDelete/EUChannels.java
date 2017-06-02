package com.example.moree.mytvapp1.EuropGoingToDelete;

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
import java.util.List;

public class EUChannels extends Fragment {
    Context context;
    List<String> Greeklink = new ArrayList<>();
    List<String> GreekPics1 = new ArrayList<>();
    List<String> GreekNames = new ArrayList<>();
    GridView EUchannels;
    MyCountries myCountries;

    public EUChannels() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        myCountries = new MyCountries();
        //Save_EuLinks();
        Get_EuData();
        myCountries.Find(context);
        View EUChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        EUchannels = (GridView) EUChannelInf.findViewById(R.id.MyGridView);
        EUchannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context,Greeklink.get(i),GreekPics1.get(i));
            }
        });
        EUchannels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
        return EUchannels;
    }

    private void Save_EuLinks() {
        EuData euData = new EuData();
        euData.EuChannel_Link = "http://1.442244.info/tur_ntv_spor/index.m3u8";

        Backendless.Persistence.of(EuData.class).save(euData, new AsyncCallback<EuData>() {
            @Override
            public void handleResponse(EuData response) {
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }


    private void Get_EuData() {
        Backendless.Persistence.of(EuData.class).find(new AsyncCallback<BackendlessCollection<EuData>>() {
            @Override
            public void handleResponse(BackendlessCollection<EuData> response) {
                for (EuData item : response.getData()) {
                    Greeklink.add(item.EuChannel_Link);
                    GreekPics1.add(item.EuChannel_Pic);
                    GreekNames.add(item.EuChannel_Name);

                }
                EUchannels.setAdapter(new MyCountryAdapter(context, GreekPics1, GreekNames));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }

        });
    }

}
