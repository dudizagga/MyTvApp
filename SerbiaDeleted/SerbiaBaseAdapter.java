package com.example.moree.mytvapp1.SerbiaDeleted;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.moree.mytvapp1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moree on 1/31/2017.
 */

public class SerbiaBaseAdapter extends BaseAdapter {
    Context context;
    List<String> SerPics;
    List<String> SerChannel_Name;
    ImageView SerImg;

    public SerbiaBaseAdapter(Context context) {
        this.context = context;
        SerPics = new ArrayList<>();
        SerChannel_Name = new ArrayList<>();
        //Save_SerPics();
       // Get_SerPics();
    }

    @Override
    public int getCount() {
        return SerPics.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridInflateItem = LayoutInflater.from(context).inflate(R.layout.my_grid_item, null, false);
        ImageView myImg = (ImageView) gridInflateItem.findViewById(R.id.myItem_Img);
        gridInflateItem.setLayoutParams(new GridView.LayoutParams(350, 450));
        // gridInflateItem.setPadding(0,0,0,0);
        TextView myText = (TextView) gridInflateItem.findViewById(R.id.myItem_text);
myText.setText(SerChannel_Name.get(position));
        Picasso.with(context)
                    .load(SerPics.get(position))
                    .into(myImg);
        return gridInflateItem;
    }

    private void Save_SerPics() {
        SerbiaData serbiaData = new SerbiaData();
        serbiaData.SerbiaChannel_Name="Ser1";
        serbiaData.SerbiaChannel_Pic = "https://api.backendless.com/B69DDA46-E458-378B-FF0E-F5F182F4A800/v1/files/picSER/sky1.png";
        Backendless.Persistence.of(SerbiaData.class).save(serbiaData, new AsyncCallback<SerbiaData>() {
            @Override
            public void handleResponse(SerbiaData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private void Get_SerPics() {
        Backendless.Persistence.of(SerbiaData.class).find(new AsyncCallback<BackendlessCollection<SerbiaData>>() {
            @Override
            public void handleResponse(BackendlessCollection<SerbiaData> response) {
                for (SerbiaData item : response.getData()) {
                    SerPics.add(item.SerbiaChannel_Pic);
                }
                notifyDataSetChanged();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
