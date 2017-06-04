package com.example.moree.mytvapp1.MyCountries;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.moree.mytvapp1.Bulgaria.BulgariaChannels;
import com.example.moree.mytvapp1.EuropGoingToDelete.EUChannels;
import com.example.moree.mytvapp1.Fragmentcontainer;
import com.example.moree.mytvapp1.Israel.IsraelChannels;
import com.example.moree.mytvapp1.Italy.ItalyChannels;
import com.example.moree.mytvapp1.MyFavorite.FavoriteData;
import com.example.moree.mytvapp1.Panel;
import com.example.moree.mytvapp1.R;
import com.example.moree.mytvapp1.Russia.RusChannels;
import com.example.moree.mytvapp1.Spain.SpainChannels;
import com.example.moree.mytvapp1.Turky.TurkyChannels;
import com.example.moree.mytvapp1.UnitedKindom.UKChannels;

import com.example.moree.mytvapp1.Video;
import com.squareup.picasso.Picasso;
import com.example.moree.mytvapp1.utlShared;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.drakeet.materialdialog.MaterialDialog;

import static com.example.moree.mytvapp1.R.id.myBookmarks;

public class MyCountries extends Fragment {
    private Context context;
    private utlShared utlShared;
    private GridView myCountryGist;
    private List<String> CountryFlags = new ArrayList<>();
    private List<String> CountryNames = new ArrayList<>();
    private ArrayList<String> Flink = new ArrayList<>();
    private Fragmentcontainer fragmentcontainer;
    private Integer isdone = 0;

    public MyCountries() {

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        context = container.getContext();
        fragmentcontainer = (Fragmentcontainer) getActivity();
        getData();
        //we call class that hold sharedpreferenes
        utlShared = new utlShared(context);
        final View MyCountryInf = inflater.inflate(R.layout.my_grid_view, container, false);
        myCountryGist = (GridView) MyCountryInf.findViewById(R.id.MyGridView);
        myCountryGist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    //checked
                    case 0:
                        fragmentcontainer.nextFragment(R.id.fcontainer, new UKChannels());
                        break;
                    case 1:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new EUChannels());
                        break;
                    case 2:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new IsraelChannels());
                        break;
                    case 3:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new SpainChannels());

                        break;
                    case 4:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new RusChannels());
                        break;
                    case 5:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new BulgariaChannels());
                        break;
                    case 6:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new ItalyChannels());
                        break;
                    case 7:
                        //checked
                        fragmentcontainer.nextFragment(R.id.fcontainer, new TurkyChannels());
                        break;


                }


            }
        });
        return MyCountryInf;

    }

//getting data from backendless

    @Override
    public void onStop() {
        super.onStop();
        CountryFlags.clear();
        CountryNames.clear();
    }


    private void getData() {
        //progress dailog
        fragmentcontainer.porgressdialog(context, "Getting Data");
        /////////////////////////////////
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
//mycountrydata is name of the table also name of the class that takes each colume
        Backendless.Persistence.of(MyCountryData.class).find(dataQuery, new AsyncCallback<BackendlessCollection<MyCountryData>>() {
            @Override
            public void handleResponse(BackendlessCollection<MyCountryData> response) {
                //for each data from our table
                for (MyCountryData data : response.getData()) {
                    //my Arraylist CountryFlags,CountryNames
                    //get each data and put it in array list
                    CountryFlags.add(data.CountryPic);
                    CountryNames.add(data.CountryName);
                    Flink.add(data.CountryName);
                    Find(context);
                    fragmentcontainer.dismisprogress();
                }
                myCountryGist.setAdapter(new MyCountryAdapter(context, CountryFlags, CountryNames));
                return;
            }

            //if there was error it will show
            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
            }
        });


    }


    //get data and add it to an array

    //my main alert


    public void Find(final Context context) {

       /* final ProgressDialog pd = new ProgressDialog(context);
        pd.setProgressDrawable(ContextCompat.getDrawable(context,R.drawable.roundedbutton_black));
        pd.setMessage("Loading");
        pd.setCancelable(false);
        pd.show();*/
      /*
        final SweetAlertDialog checkData = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        checkData.setTitleText("Loading");
        checkData.setCancelable(false);
        checkData.show();*/
            Backendless.Persistence.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
                @Override
                public void handleResponse(BackendlessCollection<FavoriteData> response) {
                    for (FavoriteData item : response.getData()) {
                        Flink.add(item.FavoriteLink);

                      /*  checkData.setCancelable(true);
                        checkData.dismiss();*/
                    }
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                }
            });

    }
//quick find to the save links
    ///////////////////////////////////////////////////

    public void MyAlertDialog1(final Context context, final String SaveLink, final String SavePic, final String SaveName) {
        this.context = context;
        Toast.makeText(context, "My Alert", Toast.LENGTH_SHORT).show();
        //my alert
        Backendless.Persistence.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
            @Override
            public void handleResponse(BackendlessCollection<FavoriteData> response) {
                for (FavoriteData item : response.getData()) {
                    Flink.add(item.FavoriteLink);
                      /*  checkData.setCancelable(true);
                        checkData.dismiss();*/
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });
        final MaterialDialog mysingleAlert = new MaterialDialog(context);
        //final MaterialDialog mysingleAlert = new MaterialDialog(context);
        // final AlertDialog.Builder mysingleAlert = new AlertDialog.Builder(context);
        // my inflate for my alert
        final View FavoriteIn = LayoutInflater.from(context).inflate(R.layout.alert_favorite, null, false);
        //my bookmark sign
        final TextView bookmarks = (TextView) FavoriteIn.findViewById(myBookmarks);
        //my image
        final ImageView Image = (ImageView) FavoriteIn.findViewById(R.id.myimg);
        Picasso.with(context)
                .load(SavePic)
                .into(Image);
        mysingleAlert.setPositiveButton("play Channel", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Video.class);
                intent.putExtra("link", (SaveLink));
                context.startActivity(intent);
            }
        });
        mysingleAlert.setNegativeButton("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mysingleAlert.dismiss();
            }
        });
        if (Flink.isEmpty()) {
            bookmarks.setTextColor(Color.BLACK);
        }
        if (Flink.contains(SaveLink)) {
            bookmarks.setTextColor(ContextCompat.getColor(context, R.color.yellow));
            Toast.makeText(context, "Channel Exists", Toast.LENGTH_SHORT).show();
            mysingleAlert.setView(FavoriteIn);
            mysingleAlert.show();
            return;
        } else {
            bookmarks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Backendless.Persistence.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
                        @Override
                        public void handleResponse(BackendlessCollection<FavoriteData> response) {
                            for (final FavoriteData item : response.getData()) {
                                Flink.add(item.FavoriteLink);
                            }

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });

                    if (Flink.contains(SaveLink)) {
                        bookmarks.setTextColor(ContextCompat.getColor(context,R.color.yellow));
                        Toast.makeText(context, "Channel Exists", Toast.LENGTH_SHORT).show();
                        Backendless.Persistence.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
                            @Override
                            public void handleResponse(BackendlessCollection<FavoriteData> response) {
                                for (FavoriteData item : response.getData()) {
                                    Flink.add(item.FavoriteLink);
                                bookmarks.setTextColor(ContextCompat.getColor(context,R.color.yellow));
                                }
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        });
                        return;
                    } else {
                        AsyncCallback<FavoriteData> favoriteDataAsyncCallback = new AsyncCallback<FavoriteData>() {
                            @Override
                            public void handleResponse(FavoriteData response) {

                                Toast.makeText(context, "Toas1", Toast.LENGTH_SHORT).show();
                                Backendless.Persistence.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
                                    @Override
                                    public void handleResponse(BackendlessCollection<FavoriteData> response) {
                                        for (FavoriteData item : response.getData()) {
                                            Flink.add(item.FavoriteLink);
                                            bookmarks.setTextColor(ContextCompat.getColor(context,R.color.yellow));

                      /*  checkData.setCancelable(true);
                        checkData.dismiss();*/
                                        }
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                    }
                                });
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {

                            }
                        };
                        FavoriteData fData = new FavoriteData();
                        utlShared = new utlShared(context);
                        fData.FavoriteLink = SaveLink;
                        fData.FavoritePic = SavePic;
                        fData.FavoriteName = SaveName;
                        fData.ownerId = utlShared.getId("");
                        Backendless.Data.of(FavoriteData.class).save(fData, favoriteDataAsyncCallback);
                        bookmarks.setTextColor(ContextCompat.getColor(context, R.color.yellow));
                        Backendless.Persistence.of(FavoriteData.class).find(new AsyncCallback<BackendlessCollection<FavoriteData>>() {
                            @Override
                            public void handleResponse(BackendlessCollection<FavoriteData> response) {
                                for (FavoriteData item : response.getData()) {
                                    Flink.add(item.FavoriteLink);

                      /*  checkData.setCancelable(true);
                        checkData.dismiss();*/
                                }
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                            }
                        });
                        return;

                    }

                }
            });
        }
        mysingleAlert.setContentView(FavoriteIn);
        mysingleAlert.show();
    }

    public void MyAlertDialog2(final Context context, final String SaveLink, final String SavePic) {
        this.context = context;
        //utlShared = new utlShared(context);
        Toast.makeText(context, "My Alert2", Toast.LENGTH_SHORT).show();
        //my alert
        final AlertDialog.Builder mysingleAlert = new AlertDialog.Builder(context);
        // my inflate for my alert
        final View FavoriteIn = LayoutInflater.from(context).inflate(R.layout.alert_favorite, null, false);
        //my bookmark sign
        final TextView bookmarks = (TextView) FavoriteIn.findViewById(myBookmarks);
        //my image
        final ImageView Image = (ImageView) FavoriteIn.findViewById(R.id.myimg);
        Picasso.with(context)
                .load(SavePic)
                .into(Image);
        if (utlShared.checkKey(SaveLink)) {
            Toast.makeText(context, "Channel Exists", Toast.LENGTH_SHORT).show();
            bookmarks.setTextColor(Color.YELLOW);
            mysingleAlert.setView(FavoriteIn);
            mysingleAlert.show();
            return;
        } else {
            mysingleAlert.setPositiveButton("Save Channel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final ProgressDialog pd1 = new ProgressDialog(context);
                    pd1.setMessage("Saving Channel");
                    pd1.setCancelable(false);
                    pd1.show();
                    if (utlShared.checkKey(SaveLink)) {
                        bookmarks.setTextColor(Color.YELLOW);
                        pd1.dismiss();
                        return;
                    } else {
                        //utlShared.AddUser(SaveLink, SavePic);
                        bookmarks.setTextColor(Color.YELLOW);
                        Toast.makeText(context, "Channel Saved", Toast.LENGTH_SHORT).show();
                        pd1.dismiss();
                        return;
                    }
                }
            });
        }
        /*

        if (Flink.isEmpty()) {
            bookmarks.setTextColor(Color.WHITE);
        }
        */


        mysingleAlert.setView(FavoriteIn);
        mysingleAlert.show();
    }

    //not useable now
    public void SharedAler(final Context context, final String Link, final String Pic, final String ChannelName) {
        this.context = context;
        utlShared = new utlShared(context);
        Toast.makeText(context, "My Alert", Toast.LENGTH_SHORT).show();
        //my alert
        AlertDialog.Builder mysingleAlert = new AlertDialog.Builder(context);
        // my inflate for my alert
        final View FavoriteIn = LayoutInflater.from(context).inflate(R.layout.alert_favorite, null, false);
        //my bookmark sign
        final TextView bookmarks = (TextView) FavoriteIn.findViewById(myBookmarks);
        //my image
        final ImageView Image = (ImageView) FavoriteIn.findViewById(R.id.myimg);
        Picasso.with(context)
                .load(Pic)
                .into(Image);
        bookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utlShared.checkKey(Link)) {
                    Toast.makeText(context, "Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                utlShared.AddUser(Pic, Link, ChannelName);
                Toast.makeText(context, "Pic" + Link + "\t" + "Link\n" + Pic, Toast.LENGTH_SHORT).show();
                bookmarks.setTextColor(Color.YELLOW);
                Toast.makeText(context, "Data was Saved", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        mysingleAlert.setView(FavoriteIn);
        mysingleAlert.show();

    }


    public void DeleteData(String ojectId) {
        FavoriteData fav = new FavoriteData();
        fav.objectId = ojectId;
        Backendless.Persistence.of(FavoriteData.class).remove(fav, new AsyncCallback<Long>() {
            @Override
            public void handleResponse(Long response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}








