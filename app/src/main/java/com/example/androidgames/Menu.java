package com.example.androidgames;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void startGameModeSolo(View v){
        Intent intent = new Intent(this, GameMode.class);
        startActivity(intent);
    }

    public void startWifip2p(View v){
        Intent intent = new Intent(this, WifiDirectActivity.class);
        startActivity(intent);
    }
}