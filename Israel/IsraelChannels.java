package com.example.moree.mytvapp1.Israel;

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
 * Created by moree on 1/5/2017.
 */

public class IsraelChannels extends Fragment {
    private Context context;
    private ArrayList<String> ILlink = new ArrayList<>();
    private ArrayList<String> ILPics = new ArrayList<>();
    private ArrayList<String> ILNames = new ArrayList<>();
    private MyCountries myCountries;
    private GridView ILchannels;
    private Fragmentcontainer fragmentcontainer;

    public IsraelChannels() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        //  Save_ILLinks();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        Get_ILData();
        myCountries = new MyCountries();
        View ILChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        ILchannels = (GridView) ILChannelInf.findViewById(R.id.MyGridView);
        ILchannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context, ILlink.get(i), ILPics.get(i), ILNames.get(i));
            }
        });


        ILchannels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        return ILChannelInf;
    }


    private void Save_ILLinks() {
        IsraelData israelData = new IsraelData();
        israelData.ILChannel_Link = "http://nana10-hdl-il.ctedgecdn.net/Nana10-Live/amlst:hd_,1000,1500,1800,/.m3u8";
        Backendless.Persistence.of(IsraelData.class).save(israelData, new AsyncCallback<IsraelData>() {
            @Override
            public void handleResponse(IsraelData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void Get_ILData() {
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
        fragmentcontainer.porgressdialog(context, "Getting Data");
        try {


            Backendless.Persistence.of(IsraelData.class).find(dataQuery,new AsyncCallback<BackendlessCollection<IsraelData>>() {
                @Override
                public void handleResponse(BackendlessCollection<IsraelData> response) {
                    for (IsraelData item : response.getData()) {
                        ILlink.add(item.ILChannel_Link);
                        ILPics.add(item.ILChannel_Pic);
                        ILNames.add(item.ILChannel_Name);
                    }
                    ILchannels.setAdapter(new MyCountryAdapter(context, ILPics, ILNames));
                    fragmentcontainer.dismisprogress();
                    return;
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
        }
    }


}
