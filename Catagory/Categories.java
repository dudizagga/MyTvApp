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
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
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
    private Context context;
    private ArrayList<String> getCatagoryPics = new ArrayList<>();
    private ArrayList<String> getCatagoryNames = new ArrayList<>();
    private GridView list;
    private Fragmentcontainer fragmentcontainer;

    public Categories() {
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        // savedata();
        getPic();
        View movInf = inflater.inflate(R.layout.activity_categories, container, false);
        list = (GridView) movInf.findViewById(R.id.TvShow);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Toast.makeText(context, "sports", Toast.LENGTH_SHORT).show();
                        fragmentcontainer.nextFragment(R.id.fcontainer, new SportChannels());
                        break;
                    case 1:
                        Toast.makeText(context, "News", Toast.LENGTH_SHORT).show();
                        fragmentcontainer.nextFragment(R.id.fcontainer, new NewsChannels());
                        break;
                    case 2:
                        Toast.makeText(context, "Music", Toast.LENGTH_SHORT).show();
                        fragmentcontainer.nextFragment(R.id.fcontainer, new MusicChannels());
                        break;
                    case 3:
                        Toast.makeText(context, "tv show", Toast.LENGTH_SHORT).show();
                        fragmentcontainer.nextFragment(R.id.fcontainer, new MoviesChannels());
                        break;
                }

            }

        });


        return movInf;

    }

    @Override
    public void onStop() {
        super.onStop();
        getCatagoryNames.clear();
        getCatagoryPics.clear();
    }

    private void savedata() {
        CatagoeryData cata = new CatagoeryData();
        cata.cataName = "fsfd";
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

    private void getPic() {
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
        fragmentcontainer.porgressdialog(context, "Getting Data");
        try {


            Backendless.Persistence.of(CatagoeryData.class).find(dataQuery,new AsyncCallback<BackendlessCollection<CatagoeryData>>() {
                @Override
                public void handleResponse(BackendlessCollection<CatagoeryData> response) {
                    for (CatagoeryData data : response.getData()) {
                        getCatagoryPics.add(data.cataImg);
                        getCatagoryNames.add(data.cataName);
                    }
                    list.setAdapter(new MyCountryAdapter(context, getCatagoryPics, getCatagoryNames));
                    fragmentcontainer.dismisprogress();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(context, "NetWork Error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}

