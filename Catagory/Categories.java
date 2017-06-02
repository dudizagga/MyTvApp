package com.example.moree.mytvapp1.Catagory;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.moree.mytvapp1.Fragmentcontainer;
import com.example.moree.mytvapp1.Movies.MoviesChannels;
import com.example.moree.mytvapp1.Music.MusicChannels;
import com.example.moree.mytvapp1.MyCountries.MyCountryAdapter;
import com.example.moree.mytvapp1.News.NewsChannels;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Sport.SportChannels;

import java.util.ArrayList;

/**
 * Created by moree on 1/2/2017.
 */

public class Categories extends Fragment {
    Context context;
    ArrayList<String> getCatagoryPics=new ArrayList<>();
    ArrayList<String> getCatagoryNames=new ArrayList<>();
    public GridView list;
    public Categories() {
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        final Fragmentcontainer first=(Fragmentcontainer)getActivity();
       // savedata();
        getCatagoryNames.clear();
        getCatagoryPics.clear();
         getPic();
        View movInf = inflater.inflate(R.layout.activity_categories, container, false);
       list = (GridView) movInf.findViewById(R.id.TvShow);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
switch (i)
{
    case 0:
        Toast.makeText(context, "sports", Toast.LENGTH_SHORT).show();
        first.nextFragment(R.id.fcontainer,new SportChannels());
       break;
    case 1:
        Toast.makeText(context, "News", Toast.LENGTH_SHORT).show();
        first.nextFragment(R.id.fcontainer,new NewsChannels());
        break;
    case 2:
        Toast.makeText(context, "Music", Toast.LENGTH_SHORT).show();
        first.nextFragment(R.id.fcontainer,new MusicChannels());
        break;
    case 3:
        Toast.makeText(context, "tv show", Toast.LENGTH_SHORT).show();
        first.nextFragment(R.id.fcontainer,new MoviesChannels());
        break;
}

            }

        });


        return movInf;

    }


    private void savedata()
    {
       CatagoeryData cata=new CatagoeryData();
        cata.cataName="fsfd";
        Backendless.Persistence.of(CatagoeryData.class).save(cata, new AsyncCallback<CatagoeryData>() {
            @Override
            public void handleResponse(CatagoeryData response) {
                Toast.makeText(context, "name WAs saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
    private void getPic()
    {
        final ProgressDialog progressDialog1 = new ProgressDialog(context);
        progressDialog1.setTitle("Getting Data");
        progressDialog1.setMessage("Please Wait");
        progressDialog1.show();
        try {


            Backendless.Persistence.of(CatagoeryData.class).find(new AsyncCallback<BackendlessCollection<CatagoeryData>>() {
                @Override
                public void handleResponse(BackendlessCollection<CatagoeryData> response) {
                    for (CatagoeryData data : response.getData()) {
                        getCatagoryPics.add(data.cataImg);
                        getCatagoryNames.add(data.cataName);
                    progressDialog1.dismiss();
                    }
                    list.setAdapter(new MyCountryAdapter(context, getCatagoryPics, getCatagoryNames));
                    return;
                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });
        }catch (Exception e)
        {
            Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        }
}

