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

import java.util.ArrayList;

/**
 * Created by moree on 2/27/2017.
 */

public class SportChannels extends Fragment {
    private Context context;
    private ArrayList<String> Sportlink = new ArrayList<>();
    private ArrayList<String> SportPics = new ArrayList<>();
    private ArrayList<String> SportNames = new ArrayList<>();
    private GridView Sportchannels;
    private Fragmentcontainer fragmentcontainer;
    private MyCountries myCountries;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        myCountries = new MyCountries();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        getSportData();
        View SportInflate = inflater.inflate(R.layout.my_grid_view, container, false);
        Sportchannels = (GridView) SportInflate.findViewById(R.id.MyGridView);
        Sportchannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myCountries.MyAlertDialog1(context, Sportlink.get(position), SportPics.get(position), SportNames.get(position));

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
        fragmentcontainer.porgressdialog(context, "Getting Data");
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
        try {
            Backendless.Persistence.of(SportData.class).find(dataQuery,new AsyncCallback<BackendlessCollection<SportData>>() {
                @Override
                public void handleResponse(BackendlessCollection<SportData> response) {
                    for (SportData item : response.getData()) {
                        SportPics.add(item.SportChannel_Pic);
                        SportNames.add(item.Sport_Names);
                        Sportlink.add(item.SportChannel_Link);
                    }
                    Sportchannels.setAdapter(new MyCountryAdapter(context, SportPics, SportNames));
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
