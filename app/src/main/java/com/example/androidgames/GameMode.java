package com.example.androidgames;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class GameMode extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemode);


    }

    public void startGameList(View v){
        //Intent intent = new Intent(this, Light_sensor.class);
        //startActivity(intent);

        setContentView(R.layout.game_list);
        String[] list = {"Light_sensor","Mega_ball","Questionnaire"};
        ArrayList listGames = new ArrayList<String>(Arrays.asList(list));

        //instantiate custom adapter
        CustomAdapter adapter = new CustomAdapter(listGames, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.listGames);
        lView.setAdapter(adapter);
    }


}
