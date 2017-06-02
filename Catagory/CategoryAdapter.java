package com.example.moree.mytvapp1.Catagory;

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
import com.example.moree.mytvapp1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moree on 1/2/2017.
 */

public class CategoryAdapter extends BaseAdapter{
    Context context;
    ImageView imageView1;
    List<String> getCatagoryDPics;
    List<String> getCatagoryDNames;
    public CategoryAdapter(Context context) {
        getCatagoryDPics=new ArrayList<>();
        this.context = context;
        //SavePics();
        //getPic();
    }

    @Override
    public int getCount() {
        return getCatagoryDPics.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridInflateItem = LayoutInflater.from(context).inflate(R.layout.my_grid_item, null, false);
        ImageView myImg = (ImageView) gridInflateItem.findViewById(R.id.myItem_Img);
        gridInflateItem.setLayoutParams(new GridView.LayoutParams(350,450));
        LinearLayout myGrid=(LinearLayout)gridInflateItem.findViewById(R.id.MyGridItem);
        // gridInflateItem.setPadding(0,0,0,0);
        TextView myText=(TextView)gridInflateItem.findViewById(R.id.myItem_text);
        Picasso.with(context)
                .load(getCatagoryDPics.get(position))
                .into(myImg);
        myText.setText(getCatagoryDPics.get(position));
        return myGrid;
    }


 private void SavePics()
 {
     CatagoeryData catagoeryData=new CatagoeryData();
     catagoeryData.cataImg="dudi";
     Backendless.Persistence.of(CatagoeryData.class).save(catagoeryData, new AsyncCallback<CatagoeryData>() {
         @Override
         public void handleResponse(CatagoeryData response) {

         }

         @Override
         public void handleFault(BackendlessFault fault) {

         }
     });
 }
    private void getPic()
    {
        Backendless.Persistence.of(CatagoeryData.class).find(new AsyncCallback<BackendlessCollection<CatagoeryData>>() {
            @Override
            public void handleResponse(BackendlessCollection<CatagoeryData> response) {
                for(CatagoeryData item:response.getData())
                {
                    getCatagoryDPics.add(item.cataImg);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

}

