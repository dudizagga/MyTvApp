package com.example.moree.mytvapp1.MyFavorite;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.moree.mytvapp1.Fragmentcontainer;
import com.example.moree.mytvapp1.MainActivity;
import com.example.moree.mytvapp1.MyCountries.MyCountries;
import com.example.moree.mytvapp1.MyCountries.MyCountryAdapter;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Video;
import com.example.moree.mytvapp1.utlShared;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.drakeet.materialdialog.MaterialDialog;

import static android.R.attr.drawable;
import static android.R.attr.id;
import static android.content.Context.MODE_PRIVATE;
import static com.backendless.servercode.services.codegen.ServiceCodeFormat.android;
import static com.example.moree.mytvapp1.R.id.myBookmarks;

/**
 * Created by moree on 2/2/2017.
 */

public class Favorite extends Fragment {
    private Context context;
    private utlShared utlShared;
    private ArrayList<String> Favorite_Links = new ArrayList<>();
    private ArrayList<String> Favorite_Names = new ArrayList<>();
    private ArrayList<String> Favorite_pic = new ArrayList<>();
    private ArrayList<String> Favorite_ob = new ArrayList<>();
    private MyCountries myCountries;
    private GridView FavoriteGrid;
    private Fragmentcontainer fragmentcontainer;
    private SharedPreferences sharedPreferences;

    public Favorite() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        myCountries = new MyCountries();
        utlShared = new utlShared(context);
        getFavoriteLinks();
        final View FavoriteInf = inflater.inflate(R.layout.my_grid_view, container, false);
        FavoriteGrid = (GridView) FavoriteInf.findViewById(R.id.MyGridView);
        FavoriteGrid.setAdapter(new MyCountryAdapter(context, Favorite_pic, Favorite_Links));
        //  SaveLinks();
        FavoriteGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final MaterialDialog materialDialog=new MaterialDialog(context);
                final View Favorite = LayoutInflater.from(context).inflate(R.layout.alert_favorite, null, false);
                final TextView bookmarks = (TextView) Favorite.findViewById(myBookmarks);
                final ImageView myImage=(ImageView)Favorite.findViewById(R.id.myimg);
                Picasso.with(context)
                        .load(Favorite_pic.get(position))
                        .into(myImage);
                bookmarks.setText("Delete Channel");
                bookmarks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure?")
                                .setContentText("Won't be able to recover this file!")
                                .setConfirmText("Yes,delete it!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        materialDialog.dismiss();
                                        sweetAlertDialog.setTitleText("Deleted!")
                                                .setContentText("Your imaginary file has been deleted!")
                                                .setConfirmText("OK")
                                                .setConfirmClickListener(null)
                                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        final FavoriteData fav = new FavoriteData();
                                        fav.objectId = Favorite_ob.get(position);
                                        fav.FavoriteLink = Favorite_Links.get(position);
                                        fav.FavoritePic=Favorite_pic.get(position);
                                        try {
                                            Backendless.Persistence.of(FavoriteData.class).remove(fav, new AsyncCallback<Long>() {
                                                @Override
                                                public void handleResponse(Long response) {
                                                    Toast.makeText(context, "item deleted", Toast.LENGTH_SHORT).show();
                                                    Favorite_Links.clear();
                                                    Favorite_Names.clear();
                                                    Favorite_pic.clear();
                                                    Favorite_ob.clear();
                                                    getFavoriteLinks();
                                                }

                                                @Override
                                                public void handleFault(BackendlessFault fault) {
                                                    Toast.makeText(context, "Error Saving Data", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        } catch (Exception e) {
                                            Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                })
                                .show();



                    }
                });
                materialDialog.setPositiveButton("play Channel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Video.class);
                        intent.putExtra("link", (Favorite_Links.get(position)));
                        context.startActivity(intent);

                    }
                });
               materialDialog.setNegativeButton("Cancel", new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                  materialDialog.dismiss();
                   }
               });
                materialDialog.setContentView(Favorite);
                materialDialog.show();

            }
        });

        FavoriteGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                return false;
            }
        });

        return FavoriteInf;

    }

    @Override
    public void onStop() {
        super.onStop();
        Favorite_Names.clear();
        Favorite_pic.clear();
        Favorite_ob.clear();
        Favorite_Links.clear();
    }

    private void getFavoriteLinks() {
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
        fragmentcontainer.porgressdialog(context, "Getting Data");
        try {
            Backendless.Data.of(FavoriteData.class).find(dataQuery,new AsyncCallback<BackendlessCollection<FavoriteData>>() {
                @Override
                public void handleResponse(BackendlessCollection<FavoriteData> response) {
                    Iterator<FavoriteData> favoritedata = response.getCurrentPage().iterator();
                    while (favoritedata.hasNext()) {
                        FavoriteData fav = favoritedata.next();
                        Favorite_Links.add(fav.FavoriteLink);
                        Favorite_pic.add(fav.FavoritePic);
                        Favorite_Names.add(fav.FavoriteName);
                        Favorite_ob.add(fav.objectId);
                    }
                    FavoriteGrid.setAdapter(new MyCountryAdapter(context, Favorite_pic, Favorite_Names));
                    fragmentcontainer.dismisprogress();
                    return;
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {

        }

    }

}
