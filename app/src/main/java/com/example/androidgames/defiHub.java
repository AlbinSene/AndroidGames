package com.example.androidgames;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class defiHub extends AppCompatActivity {

    private List<Integer> activityToDo = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defi_hub);

        activityToDo.add(0);
        activityToDo.add(1);
        activityToDo.add(2);
    }

    public void nextGame(View v){
        switch(activityToDo.get(0)){
            case 0:
                Intent lightSensorIntent = new Intent(this, Light_sensor.class);
                startActivityForResult(lightSensorIntent,0);
                break;
            case 1:
                Intent megaBallIntent = new Intent(this, MegaBall.class);
                startActivityForResult(megaBallIntent,0);
                break;
            case 2:
                //pareil
                break;
            //autres case
            default:
                throw new IllegalStateException("Unexpected value: " + activityToDo.get(0));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                activityToDo.remove(0);
                Log.d("DEBUG",activityToDo + "");
                int score = data.getIntExtra("key_score", 0);
                Log.d("DEBUG","Fin du jeu " + score);
            }
        }
    }

}
