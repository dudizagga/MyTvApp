package com.example.moree.mytvapp1.MyFavorite;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.example.moree.mytvapp1.Fragmentcontainer;
import com.example.moree.mytvapp1.MainActivity;
import com.example.moree.mytvapp1.MyCountries.MyCountries;
import com.example.moree.mytvapp1.MyCountries.MyCountryAdapter;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Video;
import com.example.moree.mytvapp1.utlShared;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.R.attr.id;
import static android.content.Context.MODE_PRIVATE;
import static com.backendless.servercode.services.codegen.ServiceCodeFormat.android;
import static com.example.moree.mytvapp1.R.id.myBookmarks;

/**
 * Created by moree on 2/2/2017.
 */

public class Favorite extends Fragment {
    Context context;
    utlShared utlShared;
    public ArrayList<String> Favorite_Links = new ArrayList<>();
    public ArrayList<String> Favorite_pic = new ArrayList<>();
    public ArrayList<String> Favorite_ob = new ArrayList<>();
    MyCountries myCountries;
    GridView FavoriteGrid;
    MainActivity mainActivity;
    Fragmentcontainer fragmentcontainer;
    SharedPreferences sharedPreferences;
    ProgressDialog pd;

    public Favorite() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        mainActivity = new MainActivity();
        myCountries = new MyCountries();
        Favorite_pic.clear();
        Favorite_Links.clear();
        sharedPreferences = context.getSharedPreferences("Favorites", MODE_PRIVATE);
        utlShared = new utlShared(context);
        getFavoriteLinks();
        View FavoriteInf = inflater.inflate(R.layout.my_grid_view, container, false);
        FavoriteGrid = (GridView) FavoriteInf.findViewById(R.id.MyGridView);
        FavoriteGrid.setAdapter(new MyCountryAdapter(context, Favorite_pic, Favorite_Links));

        //  SaveLinks();
        FavoriteGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder mydi = new AlertDialog.Builder(context);
                View Favorite=LayoutInflater.from(context).inflate(R.layout.alert_favorite,null,false);
                final TextView bookmarks = (TextView) Favorite.findViewById(myBookmarks);
bookmarks.setText("");
                //  mydi.setMessage("Are you use you want to delete\n"+Favorite_OBid.get(position));
                mydi.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mydi.setPositiveButton("Play Video", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(context, Video.class);
                        intent.putExtra("link", (Favorite_Links.get(position)));
                        context.startActivity(intent);

                        pd = new ProgressDialog(context);
                        pd.setMessage("Loading");
                        pd.show();
                        final FavoriteData fav = new FavoriteData();
                        fav.objectId = Favorite_ob.get(position);
                        fav.FavoriteLink = Favorite_Links.get(position);
                        myCountries.Find(context);
                        Backendless.Persistence.of(FavoriteData.class).remove(fav, new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                Toast.makeText(context, "item deleted", Toast.LENGTH_SHORT).show();
                                Favorite_Links.clear();
                                Favorite_pic.clear();
                                Favorite_ob.clear();
                                getFavoriteLinks();
                                pd.dismiss();

                            }


                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });
                    }
                });

                mydi.show();

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


    private void getFavoriteLinks() {
        Backendless.Data.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
            @Override
            public void handleResponse(BackendlessCollection<FavoriteData> response) {
                Iterator<FavoriteData> favoritedata = response.getCurrentPage().iterator();
                while (favoritedata.hasNext()) {
                    FavoriteData fav = favoritedata.next();
                    Favorite_Links.add(fav.FavoriteLink);
                    Favorite_pic.add(fav.FavoritePic);
                    Favorite_ob.add(fav.objectId);
                }
                FavoriteGrid.setAdapter(new MyCountryAdapter(context, Favorite_pic, Favorite_Links));
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

    }

}
