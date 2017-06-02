package com.example.moree.mytvapp1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class splashScreen extends AppCompatActivity implements Animation.AnimationListener {
    Context context;
MainActivity mainActivity;
ImageView logo;
    Animation logoAnim,welcomeAnim;
    TextView welcomeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    setPointer();
    }

    private void setPointer()
    {
        this.context=this;
        mainActivity=new MainActivity();
logo=(ImageView)findViewById(R.id.Logo);
        welcomeText=(TextView)findViewById(R.id.Welcome);
        welcomeText.setVisibility(View.INVISIBLE);
logoAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        welcomeAnim=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        logoAnim.setAnimationListener(this);
logo.setVisibility(View.VISIBLE);
        logo.startAnimation(logoAnim);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
welcomeText.setVisibility(View.VISIBLE);
        welcomeText.startAnimation(welcomeAnim);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
