package com.example.moree.mytvapp1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Handler;
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

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class splashScreen extends AppCompatActivity implements Animation.AnimationListener {
    private Context context;
    private ImageView logo;
    private Animation logoFadein;
    private Animation WelcomeFadeIn;
    private TextView welcomeText;
    private ProgressBar progressLogin;
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setPointer();
    }

    private void setPointer() {
        this.context = this;
        logo = (ImageView) findViewById(R.id.Logo);
        welcomeText = (TextView) findViewById(R.id.Welcome);
        progressLogin = (ProgressBar) findViewById(R.id.loginProgress);
        progressLogin.getIndeterminateDrawable().setColorFilter(Color.parseColor("#91ffffff"), PorterDuff.Mode.SRC_IN);
        progressLogin.setVisibility(View.INVISIBLE);
        LogoAnim();
    }

    private void LogoAnim() {
        logo.setVisibility(View.VISIBLE);
        logoFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        logoFadein.setAnimationListener(this);
        logo.startAnimation(logoFadein);
        welcomeText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == logoFadein) {
            WelcomeFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
            welcomeText.startAnimation(WelcomeFadeIn);
            progressLogin.setVisibility(View.VISIBLE);
            Login();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
    private void Login() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent main = new Intent(context, MainActivity.class);
                    startActivity(main);
                    finish();

                }
        }, SPLASH_DISPLAY_LENGTH);


    }
}
