package com.example.androidgames;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SwipeGame extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_game);

        ArrayList<String> listPlayerMoves = new ArrayList<String>();

        TextView swipeView = (TextView) findViewById(R.id.swipeView);
        swipeView.setOnTouchListener(new OnSwipeTouchListener(SwipeGame.this) {
            public void onSwipeTop() {
                //Toast.makeText(SwipeGame.this, "top", Toast.LENGTH_SHORT).show();
                listPlayerMoves.add("top");
                Log.d("DEBUG",listPlayerMoves.toString());
            }
            public void onSwipeRight() {
                //Toast.makeText(SwipeGame.this, "right", Toast.LENGTH_SHORT).show();
                listPlayerMoves.add("right");
            }
            public void onSwipeLeft() {
                //Toast.makeText(SwipeGame.this, "left", Toast.LENGTH_SHORT).show();
                listPlayerMoves.add("left");
            }
            public void onSwipeBottom() {
                //Toast.makeText(SwipeGame.this, "bottom", Toast.LENGTH_SHORT).show();
                listPlayerMoves.add("bottom");
            }

        });
    }









}
