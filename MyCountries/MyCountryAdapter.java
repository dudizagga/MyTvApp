package com.example.moree.mytvapp1.MyCountries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.moree.mytvapp1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moree on 1/13/2017.
 */

public class MyCountryAdapter extends BaseAdapter {
    Context context;
    //my ArrayLists for my BaseApter
    List<String> getCountrynames;
    List<String> getCountrypics;
/// my Constructor for my Base adapter has context and to arrays
    public MyCountryAdapter(Context context,List getpics,List getCountrynames) {
        this.context = context;
        //this. my array that i am passing data to=from my getPics
        this.getCountrypics=getpics;
        this.getCountrynames=getCountrynames;
        notifyDataSetChanged();
        //GetCountryData_Pics();
    }


//you need to set Array of pics


    @Override
    public int getCount() {
        return getCountrypics.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View gridInflateItem = LayoutInflater.from(context).inflate(R.layout.my_grid_item, null, false);
        ImageView myImg = (ImageView) gridInflateItem.findViewById(R.id.myItem_Img);
        gridInflateItem.setLayoutParams(new GridView.LayoutParams(350,450));
        LinearLayout myGrid=(LinearLayout)gridInflateItem.findViewById(R.id.MyGridItem);
       // gridInflateItem.setPadding(0,0,0,0);
        TextView myText=(TextView)gridInflateItem.findViewById(R.id.myItem_text);
       Picasso.with(context)
                .load(getCountrypics.get(i))
                .into(myImg);
       myText.setText(getCountrynames.get(i));
        return myGrid;


    }


    private void GetCountryData_Pics() {
        getCountrynames=new ArrayList<>();
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
        Backendless.Persistence.of(MyCountryData.class).find(dataQuery, new AsyncCallback<BackendlessCollection<MyCountryData>>() {
            @Override
            public void handleResponse(BackendlessCollection<MyCountryData> response) {
                for (MyCountryData item : response.getData()) {
                    getCountrypics.add(item.CountryPic);
                    getCountrynames.add(item.CountryName);


                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });


    }


}
