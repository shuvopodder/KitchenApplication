package com.example.kitchenapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class About_Us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about__us);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(About_Us.this, MainActivity.class));
    }
}
