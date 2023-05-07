package com.example.androidgames;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SwipeGame extends AppCompatActivity  {


    private ArrayList<String> listWin = new ArrayList<>();

    private ArrayList<String> listPlayerMoves = new ArrayList<>();

    private String passStatus = "Failed";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_game);

        listWin.add("top");
        listWin.add("bottom");
        listWin.add("right");
        listWin.add("left");

        TextView swipeView = (TextView) findViewById(R.id.swipeView);
        swipeView.setOnTouchListener(new OnSwipeTouchListener(SwipeGame.this) {
            public void onSwipeTop() {
                //Toast.makeText(SwipeGame.this, "top", Toast.LENGTH_SHORT).show();
                listPlayerMoves.add("top");
                checkWin();
            }
            public void onSwipeRight() {
                //Toast.makeText(SwipeGame.this, "right", Toast.LENGTH_SHORT).show();
                listPlayerMoves.add("right");
                checkWin();
            }
            public void onSwipeLeft() {
                //Toast.makeText(SwipeGame.this, "left", Toast.LENGTH_SHORT).show();
                listPlayerMoves.add("left");
                checkWin();
            }
            public void onSwipeBottom() {
                //Toast.makeText(SwipeGame.this, "bottom", Toast.LENGTH_SHORT).show();
                listPlayerMoves.add("bottom");
                checkWin();
            }

        });
    }

    private void checkWin(){
        if (listPlayerMoves.size() < 4){

        }
        else {
            if (listPlayerMoves.equals(listWin)){
                //Toast.makeText(SwipeGame.this, "gagne", Toast.LENGTH_SHORT).show();
                passStatus = "Passed";
                showEndScreen("score");
            }
            else {
                showEndScreen("bite");
                Toast.makeText(SwipeGame.this, "perdu", Toast.LENGTH_SHORT).show();
                listPlayerMoves.clear();
            }
        }
    }


    private void showEndScreen(String message){
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(message)
                .setPositiveButton("OK",(dialogInterface, i) ->actionSuite())
                .setCancelable(false)
                .show();
    }

    void actionSuite(){

    }








}
