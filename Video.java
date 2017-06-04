package com.example.moree.mytvapp1;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaCodecInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;
import com.devbrackets.android.exomedia.ui.widget.VideoControls;
import com.devbrackets.android.exomedia.ui.widget.VideoControlsLeanback;


public class Video extends AppCompatActivity {
    private EMVideoView video;
    private Context context;
    private int vlcRequestCode = 42;
    // SimpleExoPlayer player;
    // ExoPlayerFactory exo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_video);
     this.context = this;
       video = (EMVideoView) findViewById(R.id.video_view);
  //uri();
//uri1();
       uri2();
    }

public void uri1()
{
    Bundle b = getIntent().getExtras();
    Intent intent=new Intent(Intent.ACTION_VIEW);
    intent.setDataAndType(Uri.parse(b.getString("link")),"video/*");
    try {


        startActivity(Intent.createChooser(intent, "org.videolan.vlc"));
    }catch (Exception e)
    {
        Toast.makeText(context, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
    }
        onBackPressed();

}
    public void uri() {

        Bundle b = getIntent().getExtras();
        Intent i = new Intent(Intent.ACTION_VIEW);
        Uri uri=Uri.parse(b.getString("link"));
       i.setPackage("org.videolan.vlc");
        i.setData(uri);
        i.putExtra("from_start", false);
        //i.putExtra("position", 90000l);
        //i.setComponent(new ComponentName("org.videolan.vlc","org.videolan.vlc.gui.video.VideoPlayerActivity"));
        i.setDataAndType(uri,"video/*");
        startActivityForResult(i,vlcRequestCode);

      onBackPressed();
        }
    @Override
    public void onBackPressed() {
        finish();
        onStop();
    }

    @Override
    protected void onStop() {
        super.onStop();
    if (video.isPlaying())
    {
        video.pause();
    }
    }

    private void uri2() {
        try {
            Bundle b = getIntent().getExtras();
            video.getVideoControls().setVerticalFadingEdgeEnabled(true);
            video.getVideoControls().setHorizontalFadingEdgeEnabled(true);
            video.getVideoControls().setHorizontalGravity(100);
            video.setMeasureBasedOnAspectRatioEnabled(true);
            video.requestFocus();
            video.setVideoURI(Uri.parse(b.getString("link")));
            video.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared() {


                    video.start();
                    video.getAvailableTracks();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}


