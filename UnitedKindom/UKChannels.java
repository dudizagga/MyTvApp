package com.example.moree.mytvapp1.UnitedKindom;

import android.app.Fragment;
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
import com.backendless.exceptions.BackendlessException;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.example.moree.mytvapp1.Fragmentcontainer;
import com.example.moree.mytvapp1.MyCountries.MyCountries;
import com.example.moree.mytvapp1.MyCountries.MyCountryAdapter;
import com.example.moree.mytvapp1.R;

import java.util.ArrayList;

public class UKChannels extends Fragment {
    private Context context;
   private GridView UKchannels;
   private ArrayList<String> Uklink = new ArrayList<>();
   private ArrayList<String> UKpic = new ArrayList<>();
   private ArrayList<String> UKNames = new ArrayList<>();
   private MyCountries myCountries;
private Fragmentcontainer fragmentcontainer;
    public UKChannels() {

    }

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        //Save_UKLinks();
        context = container.getContext();
        myCountries = new MyCountries();
        fragmentcontainer=(Fragmentcontainer)getActivity();
        Get_UKData();
        View UKChannelInf = inflater.inflate(R.layout.my_grid_view, container, false);
        UKchannels = (GridView) UKChannelInf.findViewById(R.id.MyGridView);
        UKchannels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//sport
                myCountries.MyAlertDialog1(context, Uklink.get(i), UKpic.get(i),UKNames.get(i));
                /*
                link.add(" http://mcdn-play-358.5centscdn.net:80/truemovies1extv/stream1.stream/playlist.m3u8");
                link.add(" http://mcdn-play-358.5centscdn.net:80/sky1extv/stream1.stream/playlist.m3u8");
                link.add(" http://mcdn-play-358.5centscdn.net/e4extv/stream1.stream/index.m3u8");
                link.add(" http://mcdn-play-358.5centscdn.net/bbc2extv/stream1.stream/playlist.m3u8");
                link.add(" http://mcdn-play-358.5centscdn.net/skysports1extv/stream1.stream/playlist.m3u8");
                link.add("http://mcdn-play-358.5centscdn.net/bbc1scotextv/stream1.stream/playlist.m3u8");
                link.add("http://1.442244.info/uk_premier_sport/index.m3u8");
*/
//News
                //Music
                /*
                Intent intent = new Intent(context, Video.class);
                intent.putExtra("link", (Uklink.get(i)));
                context.startActivity(intent);
*/
            }

        });
        UKchannels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                return false;
            }
        });
        return UKchannels;
    }

    private void Save_UKLinks() {
        UKData ukData = new UKData();
        ukData.UKChannel_Link = "http://146.185.243.250:8000/play/rossija1";
        Backendless.Persistence.of(UKData.class).save(ukData, new AsyncCallback<UKData>() {
            @Override
            public void handleResponse(UKData response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Get_UKData() {
        fragmentcontainer.porgressdialog(context,"Getting Data");
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setPageSize(100);
        queryOptions.setOffset(0);
        dataQuery.setQueryOptions(queryOptions);
try {
    Backendless.Persistence.of(UKData.class).find(dataQuery, new AsyncCallback<BackendlessCollection<UKData>>() {
        @Override
        public void handleResponse(BackendlessCollection<UKData> response) {
            for (UKData item : response.getData()) {
                Uklink.add(item.UKChannel_Link);
                UKpic.add(item.UkChannel_Pic);
                UKNames.add(item.UkChannel_Name);

            }
            UKchannels.setAdapter(new MyCountryAdapter(context, UKpic, UKNames));
            fragmentcontainer.dismisprogress();
            return;
        }

        @Override
        public void handleFault(BackendlessFault fault) {
            Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
        }
    });

}catch (Exception e)
{
    Toast.makeText(context, "Error Network", Toast.LENGTH_SHORT).show();
}



    }

}