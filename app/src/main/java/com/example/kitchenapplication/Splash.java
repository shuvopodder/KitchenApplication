package com.example.kitchenapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    TextView t;
    Animation topAnim,bottomANim;
    ProgressBar pr;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        t= findViewById(R.id.textView);
        pr=findViewById(R.id.progressBar);

        pr.setProgress(50);
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomANim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        t.setAnimation(topAnim);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i<=100){
                    pr.setProgress(i);
                    i++;
                    handler.postDelayed(this,20);
                }else{
                    //handler.removeCallbacks(this);
                    startActivity(new Intent(Splash.this,MainActivity.class));
                }
            }
        },20);
    }
}