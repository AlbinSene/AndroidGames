package com.example.androidgames;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.app.AlertDialog;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.util.Log;






import java.util.Locale;

public class Decompte extends AppCompatActivity {

    TextView objectif;
    TextView timer;
    RelativeLayout myLayout; // Ajout de l'objet layout pour afficher le rectangle


    String passStatus;

    //ajout du timer
    private static final long START_TIME_IN_MILLIS = 10000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    private boolean rectangleDisplayed = false; // Un booléen pour vérifier si le rectangle est déjà affiché


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decompte);

        objectif = (TextView) findViewById(R.id.objectif);
        timer = (TextView)findViewById(R.id.timer);
        myLayout = (RelativeLayout) findViewById(R.id.my_layout); // Récupération de l'objet layout
        objectif.setText("Objectif : 4 s 10 ms");
        startTimer();

        // Affichage du rectangle après 2 secondes
        myLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayRectangle();
            }
        }, 2000); // Délai de 2 secondes en millisecondes

        // Vérification de l'objectif atteint après clic sur le bouton "Submit"
        Button submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("DEBUG", "appui");
                if (mTimeLeftInMillis > 3700 && mTimeLeftInMillis < 4500) { // Vérification de l'objectif atteint (entre 2 et 4 secondes restantes)
                    passStatus = "Passed";
                } else {
                    passStatus = "Failed";
                }
                String scoreText = compScore();
                    // Affichage du score dans une boîte de dialogue
                    AlertDialog.Builder builder = new AlertDialog.Builder(Decompte.this);
                    builder.setTitle("Score");
                    builder.setMessage(scoreText);
                    builder.setPositiveButton("OK", null);
                    builder.show();

            }
        });
    }

    private void startTimer(){
        mCountDownTimer=new CountDownTimer(mTimeLeftInMillis,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis=millisUntilFinished;
                timer.setText((int) (mTimeLeftInMillis / 1000) + " s "+ (int) (mTimeLeftInMillis % 1000)+" ms" );
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
    }

    /*
    Affichage du score à partir du temps mis par le joueur pour cacher le capteur
     */
    private String compScore(){
        int seconds = (int) (mTimeLeftInMillis ) / 1000;
        int milliseconds = (int) (mTimeLeftInMillis ) % 1000;
        int score = 6*seconds + 12* milliseconds;
        if (passStatus=="Failed"){
            seconds=0;
            milliseconds=0;
            score=0;
        }
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", seconds, milliseconds);
        return ("You did : "+timeLeftFormatted + "\n Your score is : " + score);
    }

    private void displayRectangle() {
        // Get a reference to the parent layout
        RelativeLayout parentLayout = findViewById(R.id.my_layout);

        // Create a new rectangle view
        View rectangleView = new View(this);
        rectangleView.setBackgroundColor(Color.BLACK);

        // Set the layout params for the rectangle view
        int width = 500; // Change the width as needed
        int height = 300; // Change the height as needed
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.timer);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        // Add the rectangle view to the parent layout
        parentLayout.addView(rectangleView, layoutParams);
    }

}