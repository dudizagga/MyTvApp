package com.example.moree.mytvapp1.Music;

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
import com.example.moree.mytvapp1.MyCountries.MyCountries;
import com.example.moree.mytvapp1.MyCountries.MyCountryAdapter;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Video;

import java.util.ArrayList;

/**
 * Created by DudiZagga on 12/05/2017.
 */

public class MusicChannels extends Fragment {

    Context context;
    ArrayList<String> getMusicPics = new ArrayList<>();
    ArrayList<String> getMusicLinks = new ArrayList<>();
    ArrayList<String> getMusicNames = new ArrayList<>();
    MyCountries myCountries;
    public GridView listMusic;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        Toast.makeText(context, "Music", Toast.LENGTH_SHORT).show();
        getMusicData();
        // savedata();
        myCountries = new MyCountries();
        View movInf = inflater.inflate(R.layout.activity_categories, container, false);
        listMusic = (GridView) movInf.findViewById(R.id.TvShow);
        listMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myCountries.MyAlertDialog1(context,getMusicLinks.get(i),getMusicPics.get(i));
            }

        });
        listMusic.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        return movInf;

    }

    private void getMusicData() {
        Backendless.Persistence.of(MusicData.class).find(new AsyncCallback<BackendlessCollection<MusicData>>() {
            @Override
            public void handleResponse(BackendlessCollection<MusicData> response) {
                for (MusicData item : response.getData()) {
                    getMusicLinks.add(item.MusicChannel_Link);
                    getMusicPics.add(item.MusicChannel_Pic);
                    getMusicNames.add(item.Music_Name);
                }

                listMusic.setAdapter(new MyCountryAdapter(context, getMusicPics, getMusicNames));
                return;
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
