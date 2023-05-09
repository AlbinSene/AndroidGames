package com.example.androidgames;
// Importations des bibliothèques nécessaires
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

/**
 * Activité qui affiche un compte à rebours (d'une durée de 10 secondes)
 * ainsi qu'un objectif (cacher un capteur pendant 4 secondes et 10 millisecondes).
 * Après 2 secondes, un rectangle noir apparaît sur l'écran et le joueur doit
 * cacher le capteur pendant la durée restante pour atteindre l'objectif.
 */
public class Decompte extends AppCompatActivity {
    TextView objectif; // TextView pour afficher l'objectif
    TextView timer; // TextView pour afficher le compte à rebours
    RelativeLayout myLayout; // Layout pour afficher le rectangle noir sur l'écran

    String passStatus; // Statut de réussite de l'utilisateur (passé/échoué)

    // Durée de départ du compte à rebours
    private static final long START_TIME_IN_MILLIS = 15000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer; // Compte à rebours
    private boolean mTimerRunning; // Booléen pour vérifier si le compte à rebours est en cours d'exécution

    private boolean rectangleDisplayed = false; // Booléen pour vérifier si le rectangle est déjà affiché

    // Définition de la durée à atteindre pour réussir l'objectif, avec une marge de 500 millisecondes
    private static int condVictoire = 4100;
    private static int marge = 1000;
    private int dureeAvantCache = 5000;
    private int score = 0;
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
        }, dureeAvantCache); // Délai en millisecondes

        // Vérification de l'objectif atteint après clic sur le bouton "Submit"
        Button submitBtn = findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i("DEBUG", "appui");
                if (mTimeLeftInMillis > condVictoire-marge && mTimeLeftInMillis < condVictoire+marge) { // Vérification de l'objectif atteint (entre 2 et 4 secondes restantes)
                    passStatus = "Passed";
                } else {
                    passStatus = "Failed";
                }
                String scoreText = compScore();
                // Affichage du score dans une boîte de dialogue
                showEndScreen(scoreText);


            }
        });
    }

    private void showEndScreen(String message){
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(message)
                .setPositiveButton("OK",(dialogInterface, i) ->actionSuite(score))
                .setCancelable(false)
                .show();
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
        if (passStatus.equals("Failed")){
            mTimeLeftInMillis=0;
        }
        score = (int)(calculateScore(mTimeLeftInMillis));
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

    void actionSuite(int score){
        Intent intent = new Intent();
        intent.putExtra("key_score", score);

        setResult(RESULT_OK, intent);

        finish();
    }

    private static double calculateScore(long playerTime) {
        if (playerTime >= condVictoire - marge && playerTime <= condVictoire + marge) {
            double ratio = 1.0 - ((double) Math.abs(playerTime - condVictoire) / marge);
            return ratio * 2000;
        }

        return 0;
    }

}