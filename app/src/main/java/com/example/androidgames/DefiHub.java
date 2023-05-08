package com.example.androidgames;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DefiHub extends AppCompatActivity {

    private List<Integer> activityToDo = new ArrayList<>();
    private int sommeScore = 0;
    MediaPlayer sound;
    private boolean win =false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defi_hub);

        activityToDo.add(0);
        activityToDo.add(1);
        activityToDo.add(2);
        activityToDo.add(3);
        activityToDo.add(4);
        activityToDo.add(5);
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
                Intent questionnaireIntent = new Intent(this, Questionnaire.class);
                startActivityForResult(questionnaireIntent,0);
                break;
            case 3:
                Intent speedQuestionnaireIntent = new Intent(this, SpeedQuestionnaire.class);
                startActivityForResult(speedQuestionnaireIntent,0);
                break;
            case 4:
                Intent decompteIntent = new Intent(this, Decompte.class);
                startActivityForResult(decompteIntent,0);
                break;
            case 5:
                Intent swipeGameIntent = new Intent(this, SwipeGame.class);
                startActivityForResult(swipeGameIntent,0);
                break;
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

                sommeScore += score;
                TextView textScore = (TextView) findViewById(R.id.score);
                textScore.setText("Score : " + sommeScore);

                if (activityToDo.size() == 0){
                    changeLayoutFinal();
                }

            }
        }
    }

    private void changeLayoutFinal(){
        setContentView(R.layout.fin_defi);
        if(win) {
            sound = MediaPlayer.create(DefiHub.this, R.raw.gagne);
            sound.start();
            //sound.release();
            TextView textScore = (TextView) findViewById(R.id.scoreFinal);
            textScore.setText("Bravo !\nVotre score est : " + sommeScore);
        } else {
            sound = MediaPlayer.create(DefiHub.this, R.raw.perdu);
            sound.start();
            //sound.release();
            TextView textScore = (TextView) findViewById(R.id.scoreFinal);
            textScore.setText("Dommage !\nVotre score est : " + sommeScore);
        }
    }

    public void finRetour(View v){
        finish();
    }
}