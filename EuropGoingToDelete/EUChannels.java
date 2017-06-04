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
import java.util.List;

public class EUChannels extends Fragment {
    private Context context;
    private List<String> Eulink = new ArrayList<>();
    private List<String> EuPics = new ArrayList<>();
    private List<String> EuNames = new ArrayList<>();
    private Fragmentcontainer fragmentcontainer;
    private GridView EUchannels;
    private MyCountries myCountries;

    public EUChannels() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        myCountries = new MyCountries();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        //Save_EuLinks();
        Get_EuData();
        View EUChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        EUchannels = (GridView) EUChannelInf.findViewById(R.id.MyGridView);
        EUchannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context, Eulink.get(i), EuPics.get(i), EuNames.get(i));
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
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
       fragmentcontainer.porgressdialog(context,"Getting Data");
        try {
            Backendless.Persistence.of(EuData.class).find(dataQuery,new AsyncCallback<BackendlessCollection<EuData>>() {
                @Override
                public void handleResponse(BackendlessCollection<EuData> response) {
                    for (EuData item : response.getData()) {
                        Eulink.add(item.EuChannel_Link);
                        EuPics.add(item.EuChannel_Pic);
                        EuNames.add(item.EuChannel_Name);

                    }
                    EUchannels.setAdapter(new MyCountryAdapter(context, EuPics, EuNames));
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
