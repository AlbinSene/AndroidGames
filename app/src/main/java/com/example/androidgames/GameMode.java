package com.example.androidgames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GameMode extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemode);
    }

    public void startGameList(View v){
        Intent intent = new Intent(this, Questionnaire.class);
        startActivity(intent);
    }


}
