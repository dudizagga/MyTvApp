package com.example.moree.mytvapp1.News;

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
 * Created by DudiZagga on 12/05/2017.
 */

public class NewsChannels extends Fragment {
    private Context context;
    private ArrayList<String> getNewsPics = new ArrayList<>();
    private ArrayList<String> getNewsLinks = new ArrayList<>();
    private ArrayList<String> getNewsNames = new ArrayList<>();
    private GridView listNews;
    private MyCountries myCountries;
    private Fragmentcontainer fragmentcontainer;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        myCountries = new MyCountries();
        fragmentcontainer=(Fragmentcontainer)getActivity();
        getNewsData();
        Toast.makeText(context, "News", Toast.LENGTH_SHORT).show();
        View movInf = inflater.inflate(R.layout.activity_categories, container, false);
        listNews = (GridView) movInf.findViewById(R.id.TvShow);
        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
myCountries.MyAlertDialog1(context,getNewsLinks.get(i),getNewsPics.get(i),getNewsNames.get(i));

            }

        });


        return movInf;

    }

    private void getNewsData() {
        fragmentcontainer.porgressdialog(context,"Getting Data");
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
        Backendless.Persistence.of(NewsData.class).find(dataQuery,new AsyncCallback<BackendlessCollection<NewsData>>() {
            @Override
            public void handleResponse(BackendlessCollection<NewsData> response) {
                for (NewsData item : response.getData()) {
                    getNewsLinks.add(item.NewsChannel_Link);
                    getNewsPics.add(item.NewsChannel_Pic);
                    getNewsNames.add(item.News_Names);
                }
                listNews.setAdapter(new MyCountryAdapter(context, getNewsPics, getNewsNames));
                fragmentcontainer.dismisprogress();
                return;
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
