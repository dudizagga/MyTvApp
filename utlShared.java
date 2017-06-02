package com.example.moree.mytvapp1;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.exoplayer.C;

/**
 * Created by morees on 10/27/2016.
 */

public class utlShared {
    private Context context;
    private SharedPreferences MyFavoriteData ;
private SharedPreferences.Editor editor;
    private utlShared() {}


    public utlShared(Context context) {
        this.context = context;
        MyFavoriteData= context.getSharedPreferences("Favorites",Context.MODE_PRIVATE);
        editor=MyFavoriteData.edit();
    }
    public void AddUser(String Link,String Pic,String channelName) {
            editor.putString(Link,Pic);
 editor.putString("Name",channelName);
            editor.commit();

    }
    //if dosent exites
    public boolean checkKey(String link) {
        String checkuser = MyFavoriteData.getString(link, "na");
        return !checkuser.equals("na");
    }
    //pic equals link with the same value
    public boolean CheckUserPassword(String pic, String link) {
        String user = MyFavoriteData.getString(link, "na");
        return user.equals(pic);
    }
    public void DeleteKey(String key)
    {
        editor.remove(key);
        editor.commit();
    }
    public boolean putBol( boolean bol){
        editor.putBoolean("b",bol);
        editor.commit();

        return bol;
    }
    public boolean getBol( boolean bol){
        boolean b =  MyFavoriteData.getBoolean("b",bol);
        return b;
    }
    public void putId(String ownId){
        editor.putString("own",ownId);
        editor.commit();
    }
    public String getId( String ownId){
        String c =MyFavoriteData.getString("own", ownId);
        return c;
    }
}


