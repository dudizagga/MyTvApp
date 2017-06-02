package com.example.moree.mytvapp1.Bulgaria;

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

public class BulgariaChannels extends Fragment {
    Context context;
    ArrayList<String> BgLink = new ArrayList<>();
    ArrayList<String> BgPics1 = new ArrayList<>();
    ArrayList<String> Bgnames = new ArrayList<>();
    GridView myBGgrid;
    MyCountries myCountries;

    public BulgariaChannels() {
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        myCountries = new MyCountries();
        BgLink.clear();
        BgPics1.clear();
        // Save_BgLinks();
        GetBgData();
        myCountries.Find(context);
        View BGChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        myBGgrid = (GridView) BGChannelInf.findViewById(R.id.MyGridView);
        myBGgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context,BgLink.get(i),BgPics1.get(i));
                //sport
/*
                link.add("http://1.442244.info/bg_diema_sport/index.m3u8");
                link.add("http://1.442244.info/bg_diema_sport_2/index.m3u8");
                link.add("http://1.442244.info/bg_nova_sport/index.m3u8");
                link.add("http://1.442244.info/bg_sport_plus_hd/index.m3u8");
                */
            }
        });
        myBGgrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        return BGChannelInf;
    }


    private void Save_BgLinks() {
        BGdata bGdata = new BGdata();
        bGdata.BgChannel_Link = "http://1.442244.info/bg_diema_sport/index.m3u8";
        Backendless.Persistence.of(BGdata.class).save(bGdata, new AsyncCallback<BGdata>() {
            @Override
            public void handleResponse(BGdata response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
    //getting my data from backendless
   private void GetBgData()
   {

       Backendless.Persistence.of(BGdata.class).find(new AsyncCallback<BackendlessCollection<BGdata>>() {
           @Override
           public void handleResponse(BackendlessCollection<BGdata> response) {

               for (BGdata item : response.getData()) {
                   BgLink.add(item.BgChannel_Link);
                   BgPics1.add(item.BgChannel_Pic);
                   Bgnames.add(item.BGChannel_Name);
               }
               myBGgrid.setAdapter(new MyCountryAdapter(context, BgPics1, Bgnames));
           }

           @Override
           public void handleFault(BackendlessFault fault) {

           }
       });
   }
}
