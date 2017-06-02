package com.example.moree.mytvapp1.NetherlandDeleted;

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

/**
 * Created by moree on 1/9/2017.
 */

public class NetherlandChannels extends Fragment {
    Context context;
    List<String> Netlink = new ArrayList<>();
    List<String> NETPics1 = new ArrayList<>();
    GridView myNLList;
    MyCountries myCountries;

    public NetherlandChannels() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //Save_NetLinks();
        Netlink.clear();
        NETPics1.clear();
        Get_NetData();
        context = container.getContext();
        myCountries = new MyCountries();
        View NLChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        myNLList = (GridView) NLChannelInf.findViewById(R.id.MyGridView);
        myNLList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context,Netlink.get(i),NETPics1.get(i));
                //sport
                /*
                link.add("http://1.442244.info/nl_ziggo_sport_voetbal/index.m3u8");
                link.add("http://1.442244.info/nl_fox_sports_edl_3/index.m3u8");
                */
                //news
                //music
            }
        });
        myNLList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        return NLChannelInf;
    }


    private void Save_NetLinks() {
        NetherLandData netherLandData = new NetherLandData();
        netherLandData.NetChannel_Link = "";
        Backendless.Persistence.of(NetherLandData.class).save(netherLandData, new AsyncCallback<NetherLandData>() {
            @Override
            public void handleResponse(NetherLandData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void Get_NetData() {
        Backendless.Persistence.of(NetherLandData.class).find(new AsyncCallback<BackendlessCollection<NetherLandData>>() {
            @Override
            public void handleResponse(BackendlessCollection<NetherLandData> response) {
                for (NetherLandData item : response.getData()) {
                    Netlink.add(item.NetChannel_Link);
                    NETPics1.add(item.NetChannel_Pic);
                }
                myNLList.setAdapter(new MyCountryAdapter(context, NETPics1, Netlink));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
