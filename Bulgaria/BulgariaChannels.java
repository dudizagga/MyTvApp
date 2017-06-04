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

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by moree on 1/9/2017.
 */

public class BulgariaChannels extends Fragment {
    private Context context;
   private ArrayList<String> BgLink = new ArrayList<>();
   private ArrayList<String> BgPics1 = new ArrayList<>();
   private ArrayList<String> Bgnames = new ArrayList<>();
    private GridView myBGgrid;
   private Fragmentcontainer fragmentcontainer;
   private MyCountries myCountries;

    public BulgariaChannels() {
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        myCountries = new MyCountries();
        fragmentcontainer=(Fragmentcontainer)getActivity();
        // Save_BgLinks();
        GetBgData();
        View BGChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        myBGgrid = (GridView) BGChannelInf.findViewById(R.id.MyGridView);
        myBGgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context,BgLink.get(i),BgPics1.get(i),Bgnames.get(i));
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
       BackendlessDataQuery dataQuery = new BackendlessDataQuery();
       QueryOptions queryOptions = new QueryOptions();
       queryOptions.setPageSize(100);
       queryOptions.setOffset(0);
       dataQuery.setQueryOptions(queryOptions);
       fragmentcontainer.porgressdialog(context,"Getting Data");

       try {


           Backendless.Persistence.of(BGdata.class).find(dataQuery,new AsyncCallback<BackendlessCollection<BGdata>>() {
               @Override
               public void handleResponse(BackendlessCollection<BGdata> response) {

                   for (BGdata item : response.getData()) {
                       BgLink.add(item.BgChannel_Link);
                       BgPics1.add(item.BgChannel_Pic);
                       Bgnames.add(item.BGChannel_Name);
                   }
                   myBGgrid.setAdapter(new MyCountryAdapter(context, BgPics1, Bgnames));
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
