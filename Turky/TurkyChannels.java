package com.example.moree.mytvapp1.Turky;

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

public class TurkyChannels extends Fragment {
    Context context;
    ArrayList<String> Turlink = new ArrayList<>();
    ArrayList<String> TurPics = new ArrayList<>();
    ArrayList<String> TurNames = new ArrayList<>();
    MyCountries myCountries;
     GridView TURchannels;

    public TurkyChannels() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //Save_TurLinks();
        Turlink.clear();
        Get_TurData();
        context = container.getContext();
        myCountries = new MyCountries();
        myCountries.Find(context);
        View TURChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        TURchannels= (GridView) TURChannelInf.findViewById(R.id.MyGridView);
        TURchannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context,Turlink.get(i),TurPics.get(i));
                //sport
                /*
                link.add("http://1.442244.info/tur_ntv_spor/index.m3u8");
                link.add("http://1.442244.info/tur_trt_spor/index.m3u8");
                */
                //News

                //Music
            }
        });
        TURchannels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        return TURchannels;
    }


    private void Save_TurLinks() {
        TurkyData turkyData = new TurkyData();
        turkyData.TurkyChannel_Link = "http://146.185.243.250:8000/play/rossija1";
        Backendless.Persistence.of(TurkyData.class).save(turkyData, new AsyncCallback<TurkyData>() {
            @Override
            public void handleResponse(TurkyData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void Get_TurData() {

        Backendless.Persistence.of(TurkyData.class).find(new AsyncCallback<BackendlessCollection<TurkyData>>() {
            @Override
            public void handleResponse(BackendlessCollection<TurkyData> response) {
                for (TurkyData item : response.getData()) {
                    Turlink.add(item.TurkyChannel_Link);
                    TurPics.add(item.TurkyChannel_Pic);
                    TurNames.add(item.TurkyChannel_Name);

                }
                TURchannels.setAdapter(new MyCountryAdapter(context,TurPics,TurNames));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }


}
