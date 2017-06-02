package com.example.moree.mytvapp1.Italy;

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

public class ItalyChannels extends Fragment {
    Context context;
    ArrayList<String> ITlink=new ArrayList<>();
    ArrayList<String> ITPics=new ArrayList<>();
    ArrayList<String> ITNames=new ArrayList<>();
    MyCountries myCountries;
    GridView myITList;
    public ItalyChannels() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
       // Save_ITLinks();
        ITlink.clear();
        ITPics.clear();
        Get_ITData();
        context = container.getContext();
        myCountries=new MyCountries();
        myCountries.Find(context);
        View ITChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
       myITList = (GridView) ITChannelInf.findViewById(R.id.MyGridView);
        myITList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           myCountries.MyAlertDialog1(context,ITlink.get(i),ITPics.get(i));



            }
        });
        myITList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        return ITChannelInf;
    }
    private void Save_ITLinks(){
        ItalyData italyData=new ItalyData();
        italyData.ITChannel_Link="";
        Backendless.Persistence.of(ItalyData.class).save(italyData, new AsyncCallback<ItalyData>() {
            @Override
            public void handleResponse(ItalyData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }



    private void Get_ITData(){
        Backendless.Persistence.of(ItalyData.class).find(new AsyncCallback<BackendlessCollection<ItalyData>>() {
            @Override
            public void handleResponse(BackendlessCollection<ItalyData> response) {
                for (ItalyData item:response.getData()){
                    ITlink.add(item.ITChannel_Link);
                    ITPics.add(item.ITChannel_Pic);
                    ITNames.add(item.ITChannel_Name);

                }
                myITList.setAdapter(new MyCountryAdapter(context,ITPics,ITNames));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
