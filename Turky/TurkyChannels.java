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
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.moree.mytvapp1.Fragmentcontainer;
import com.example.moree.mytvapp1.MyCountries.MyCountries;
import com.example.moree.mytvapp1.MyCountries.MyCountryAdapter;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Video;

import java.util.ArrayList;

/**
 * Created by moree on 1/9/2017.
 */

public class TurkyChannels extends Fragment {
    private Context context;
    private ArrayList<String> Turlink = new ArrayList<>();
    private ArrayList<String> TurPics = new ArrayList<>();
    private ArrayList<String> TurNames = new ArrayList<>();
    private MyCountries myCountries;
    private GridView TURchannels;
    private Fragmentcontainer fragmentcontainer;

    public TurkyChannels() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //Save_TurLinks();
        context = container.getContext();
        myCountries = new MyCountries();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        Get_TurData();
        View TURChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        TURchannels = (GridView) TURChannelInf.findViewById(R.id.MyGridView);
        TURchannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context, Turlink.get(i), TurPics.get(i), TurNames.get(i));
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
                Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Get_TurData() {
        fragmentcontainer.porgressdialog(context, "Getting Data");
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
        try {
            Backendless.Persistence.of(TurkyData.class).find(dataQuery, new AsyncCallback<BackendlessCollection<TurkyData>>() {
                @Override
                public void handleResponse(BackendlessCollection<TurkyData> response) {
                    for (TurkyData item : response.getData()) {
                        Turlink.add(item.TurkyChannel_Link);
                        TurPics.add(item.TurkyChannel_Pic);
                        TurNames.add(item.TurkyChannel_Name);

                    }
                    TURchannels.setAdapter(new MyCountryAdapter(context, TurPics, TurNames));
                    fragmentcontainer.dismisprogress();
                    return;
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
        }
    }


}
