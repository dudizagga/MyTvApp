package com.example.moree.mytvapp1.Spain;

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

public class SpainChannels extends Fragment {
    Context context;
    ArrayList<String> Spainlink = new ArrayList<>();
    ArrayList<String> SpainPics = new ArrayList<>();
    ArrayList<String> SpainNames = new ArrayList<>();
    GridView SPchannels;
    MyCountries myCountries;

    public SpainChannels() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //Save_SpainLinks();
        context = container.getContext();
        SpainPics.clear();
        Get_SpainData();
        myCountries = new MyCountries();
        myCountries.Find(context);
        View SPChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        SPchannels = (GridView) SPChannelInf.findViewById(R.id.MyGridView);
        SPchannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context,Spainlink.get(i),SpainPics.get(i));

//sport
                /*
                link.add("http://1.442244.info/sp_la_1/index.m3u8");
                link.add("http://1.442244.info/sp_la_2/index.m3u8");
                link.add("http://1.442244.info/sp_laligatv_bar/index.m3u8");
                link.add("http://1.442244.info/sp_bein_laliga/index.m3u8");
                link.add("http://1.442244.info/sp_m_fotbol/index.m3u8");
                link.add("http://1.442244.info/sp_m_dep_1/index.m3u8");
*/
            }
        });
        SPchannels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        return SPchannels;
    }

    private void Save_SpainLinks() {
        SpainData spainData = new SpainData();
        spainData.SpainChannel_Link = "http://146.185.243.250:8000/play/rossija1";
        Backendless.Persistence.of(SpainData.class).save(spainData, new AsyncCallback<SpainData>() {
            @Override
            public void handleResponse(SpainData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void Get_SpainData() {
        Backendless.Persistence.of(SpainData.class).find(new AsyncCallback<BackendlessCollection<SpainData>>() {
            @Override
            public void handleResponse(BackendlessCollection<SpainData> response) {
                for (SpainData item : response.getData()) {
                    Spainlink.add(item.SpainChannel_Link);
                    SpainPics.add(item.SpainChannel_Pic);
                    SpainNames.add(item.SpainChannel_Name);
                }
                SPchannels.setAdapter(new MyCountryAdapter(context, SpainPics, SpainNames));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
