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

public class SpainChannels extends Fragment {
    private Context context;
    private ArrayList<String> Spainlink = new ArrayList<>();
    private ArrayList<String> SpainPics = new ArrayList<>();
    private ArrayList<String> SpainNames = new ArrayList<>();
    private GridView SPchannels;
    private MyCountries myCountries;
    private Fragmentcontainer fragmentcontainer;

    public SpainChannels() {
    }

    @Override
    public void onStop() {
        super.onStop();
        SpainNames.clear();
        Spainlink.clear();
        SpainPics.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //Save_SpainLinks();
        context = container.getContext();
        fragmentcontainer=(Fragmentcontainer)getActivity();
        Get_SpainData();
        myCountries = new MyCountries();
        View SPChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        SPchannels = (GridView) SPChannelInf.findViewById(R.id.MyGridView);
        SPchannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context, Spainlink.get(i), SpainPics.get(i), SpainNames.get(i));
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
        fragmentcontainer.porgressdialog(context,"Getting Data");
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
        try {
            Backendless.Persistence.of(SpainData.class).find(dataQuery, new AsyncCallback<BackendlessCollection<SpainData>>() {
                @Override
                public void handleResponse(BackendlessCollection<SpainData> response) {
                    for (SpainData item : response.getData()) {
                        Spainlink.add(item.SpainChannel_Link);
                        SpainPics.add(item.SpainChannel_Pic);
                        SpainNames.add(item.SpainChannel_Name);
                    }
                    SPchannels.setAdapter(new MyCountryAdapter(context, SpainPics, SpainNames));
                    fragmentcontainer.dismisprogress();
                    return;
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e)
        {
            Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
        }
    }
}
