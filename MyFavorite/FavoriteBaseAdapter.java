package com.example.moree.mytvapp1.MyFavorite;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moree on 2/2/2017.
 */

public class FavoriteBaseAdapter extends BaseAdapter {
    Context context;
    List<String> favorite_Pics=new ArrayList<>();
    List<String> favorite_Links=new ArrayList<>();
    public ImageView FavoritePic;

    public FavoriteBaseAdapter(Context context,List getpic,List getLink) {
        this.favorite_Links=getLink;
        this.context = context;
        this.favorite_Pics=getpic;
        //SavePics();
       // GetPic();
    }

    @Override
    public int getCount() {
        return favorite_Pics.size();
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
        if (convertView == null) {
            FavoritePic = new ImageView(context);
            FavoritePic.setLayoutParams(new GridView.LayoutParams(250, 200));
            FavoritePic.setPadding(10, 10, 10, 10);
            Picasso.with(context)
                    .load(favorite_Pics.get(position))
                    .into(FavoritePic);
        } else {
            return convertView;
        }
        return FavoritePic;

    }
    private void SavePics(){
        FavoriteData favoriteData= new FavoriteData();
        favoriteData.FavoritePic="https://api.backendless.com/B69DDA46-E458-378B-FF0E-F5F182F4A800/v1/files/picSER/sky1.png";
        Backendless.Persistence.of(FavoriteData.class).save(favoriteData, new AsyncCallback<FavoriteData>() {
            @Override
            public void handleResponse(FavoriteData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
    public void GetPic(){
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
        Backendless.Persistence.of(FavoriteData.class).find(dataQuery,new AsyncCallback<BackendlessCollection<FavoriteData>>() {
            @Override
            public void handleResponse(BackendlessCollection<FavoriteData> response) {
                for (FavoriteData item:response.getData()){
                    favorite_Pics.add(item.FavoritePic);
                    notifyDataSetChanged();
                }
                notifyDataSetChanged();
            }


            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}
