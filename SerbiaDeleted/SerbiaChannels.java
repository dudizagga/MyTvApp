package com.example.moree.mytvapp1.SerbiaDeleted;

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
import com.example.moree.mytvapp1.MyFavorite.Favorite;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moree on 1/9/2017.
 */

public class SerbiaChannels extends Fragment {
    Context context;
    List<String> Serlink = new ArrayList<>();
    List<String> SerPics1 = new ArrayList<>();
    MyCountries myCountries;

    public SerbiaChannels() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //Save_SerLinks();
        Serlink.clear();
        SerPics1.clear();
        Get_SerData();
        myCountries = new MyCountries();
        context = container.getContext();
        View SERChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        final GridView myGBList = (GridView) SERChannelInf.findViewById(R.id.MyGridView);
        myGBList.setAdapter(new SerbiaBaseAdapter(context));
        myGBList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context,Serlink.get(i),SerPics1.get(i));
                //sport
                /*
                link.add("http://1.442244.info/ser_arena_sport_1/index.m3u8");
                link.add("http://1.442244.info/ser_arena_sport_2/index.m3u8");
                link.add("http://1.442244.info/ser_arena_sport_3/index.m3u8");
                link.add("http://1.442244.info/ser_arena_sport_4/index.m3u8");
                link.add("http://1.442244.info/ser_arena_sport_5/index.m3u8");
                */

            }
        });
        myGBList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }

        });
        return SERChannelInf;
    }


    private void Save_SerLinks() {
        SerbiaData serbiaData = new SerbiaData();
        serbiaData.SerbiaChannel_Link = "http://146.185.243.250:8000/play/rossija1";
        Backendless.Persistence.of(SerbiaData.class).save(serbiaData, new AsyncCallback<SerbiaData>() {
            @Override
            public void handleResponse(SerbiaData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void Get_SerData() {
        Backendless.Persistence.of(SerbiaData.class).find(new AsyncCallback<BackendlessCollection<SerbiaData>>() {
            @Override
            public void handleResponse(BackendlessCollection<SerbiaData> response) {
                for (SerbiaData item : response.getData()) {
                    Serlink.add(item.SerbiaChannel_Link);
                    SerPics1.add(item.SerbiaChannel_Pic);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
