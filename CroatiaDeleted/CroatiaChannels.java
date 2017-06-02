package com.example.moree.mytvapp1.CroatiaDeleted;

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
import com.example.moree.mytvapp1.MyFavorite.Favorite;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Video;

import java.util.ArrayList;

/**
 * Created by moree on 1/9/2017.
 */

public class CroatiaChannels extends Fragment {
    Context context;
    ArrayList<String> Crolinks = new ArrayList<>();
    ArrayList<String> CroPics1 = new ArrayList<>();
    MyCountries myCountries;
    GridView myCORist;

    public CroatiaChannels() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        //Save_CroLinks();
        Crolinks.clear();
        CroPics1.clear();
        // Get_CroData();
        myCountries = new MyCountries();
        View CORChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        myCORist = (GridView) CORChannelInf.findViewById(R.id.MyGridView);
        myCORist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context,Crolinks.get(i),CroPics1.get(i));

            }
        });
        myCORist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
        return CORChannelInf;
    }

    private void Save_CroLinks() {
        CROData croData = new CROData();
        croData.CroChannel_Link = "http://1.442244.info/cro_arena_sport_1/index.m3u8";
        Backendless.Persistence.of(CROData.class).save(croData, new AsyncCallback<CROData>() {
            @Override
            public void handleResponse(CROData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void Get_CroData() {
        Backendless.Persistence.of(CROData.class).find(new AsyncCallback<BackendlessCollection<CROData>>() {
            @Override
            public void handleResponse(BackendlessCollection<CROData> response) {
                for (CROData item : response.getData()) {
                    Crolinks.add(item.CroChannel_Link);
                    CroPics1.add(item.CroChannel_Pics);

                }
                myCORist.setAdapter(new MyCountryAdapter(context, CroPics1, Crolinks));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

}
