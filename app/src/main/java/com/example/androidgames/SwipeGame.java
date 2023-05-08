package com.example.androidgames;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SwipeGame extends AppCompatActivity {

    private ArrayList<String> listWin = new ArrayList<>();
    private ArrayList<String> listPlayerMoves = new ArrayList<>();
    private String passStatus = "Failed";
    private int score=0;

    TextView timer; // TextView pour afficher le compte à rebours

    // Durée de départ du compte à rebours
    private static final long START_TIME_IN_MILLIS = 15000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer; // Compte à rebours
    private boolean mTimerRunning; // Booléen pour vérifier si le compte à rebours est en cours d'exécution

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_game);
        timer = (TextView) findViewById(R.id.timer);
        listWin.add("top");
        listWin.add("bottom");
        listWin.add("right");
        listWin.add("left");
        startTimer();

        TextView swipeView = (TextView) findViewById(R.id.swipeView);
        swipeView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                Log.i("DEBUG","swipe top");
                listPlayerMoves.add("top");
                checkWin();
            }

            public void onSwipeRight() {
                listPlayerMoves.add("right");
                checkWin();
            }

            public void onSwipeLeft() {
                listPlayerMoves.add("left");
                checkWin();
            }

            public void onSwipeBottom() {
                listPlayerMoves.add("bottom");
                checkWin();
            }
        });
    }


    private void checkWin() {
        int seconds = (int) (mTimeLeftInMillis) / 1000;
        int milliseconds = (int) (mTimeLeftInMillis) % 1000;
        //Log.i("DEBUG", "checkwin");
        if (listPlayerMoves.size() < 4) {
            // Nombre de mouvements insuffisant
        } else {
            if (listPlayerMoves.equals(listWin)) {
                // Mouvements corrects, gagne
                //Toast.makeText(SwipeGame.this, "Gagne", Toast.LENGTH_SHORT).show();
                passStatus = "Passed";
                //Log.i("DEBUG","win");
                score =6 * seconds + 12 * milliseconds;
               showEndScreen("Your score is: " + seconds + "s " + milliseconds + "ms\nYour score is " + score);
            } else {
                Log.i("DEBUG","loose");
                // Mouvements incorrects, perd
                //Toast.makeText(SwipeGame.this, "PERD", Toast.LENGTH_SHORT).show();
                showEndScreen("You did: 0s 0ms\nYour score is: 0");
                listPlayerMoves.clear();
            }
        }
    }

    private void showEndScreen(String message) {
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(message)
                .setPositiveButton("OK", (dialogInterface, i) -> actionSuite(score))
                .setCancelable(false)
                .show();
        onPause();
    }

    void actionSuite(int score) {
        Intent intent = new Intent();
        intent.putExtra("key_score", score);

        setResult(RESULT_OK, intent);

        finish();
    }

    private void startTimer() {

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                timer.setText((int) (mTimeLeftInMillis / 1000) + " s " + (int) (mTimeLeftInMillis % 1000) + " ms");
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                showEndScreen("You did : 0s 0ms \nYour score is : 0");
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        View swipeView = findViewById(R.id.swipeView);
        swipeView.setOnTouchListener(new OnSwipeTouchListener(this) {
            public void onSwipeTop() {
                listPlayerMoves.add("top");
                checkWin();
            }

            public void onSwipeRight() {
                listPlayerMoves.add("right");
                checkWin();
            }

            public void onSwipeLeft() {
                listPlayerMoves.add("left");
                checkWin();
            }

            public void onSwipeBottom() {
                listPlayerMoves.add("bottom");
                checkWin();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        View swipeView = findViewById(R.id.swipeView);
        swipeView.setOnTouchListener(null);
    }
}
