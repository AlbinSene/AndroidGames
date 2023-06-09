package com.example.androidgames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class Light_sensor extends AppCompatActivity implements SensorEventListener {

    //declaration des attributs de manipulation des layouts et capteurs
    TextView textView;
    TextView timer;
    SensorManager sensorManager;
    Sensor sensor;
    //declaration du statut de reussite du jeu
    String passStatus;

    //attributs pour letimer
    private static final long START_TIME_IN_MILLIS = 10000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    private int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);

        //initialisation des attributs
        textView = (TextView) findViewById(R.id.textView);
        timer = (TextView)findViewById(R.id.timer);
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        //démarrage du timer
        startTimer();

    }


    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
       // Log.i("DEBUG", "temps" + mTimeLeftInMillis);
    if(sensorEvent.sensor.getType()==Sensor.TYPE_LIGHT){
        textView.setText(""+sensorEvent.values[0]);
    }
    //si le timer est supérieur à 10 et que l'on capte moins de 10 lux
        if(sensorEvent.values[0]<10 && mTimeLeftInMillis>1){
            passStatus = "Passed"; //alors victoire
        }else{
            passStatus = "Failed"; //sinon defaire
        }
        //fin du minijeu si le temps est inférieur à 10 ou que le jeu est reussi
        if(mTimeLeftInMillis<10 || sensorEvent.values[0]<10){
            //arret
            onPause();
            //arret du temps
            mCountDownTimer.cancel();
            //utilisation du compt score pour
            String timeLeftFormatted = compScore();
            //textView.setText(timeLeftFormatted);
            //affichage de l'écran de fin
            showEndScreen(timeLeftFormatted);
            onPause();
        }


    }

    //demarrage du timer
    private void startTimer(){
        mCountDownTimer=new CountDownTimer(mTimeLeftInMillis,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis=millisUntilFinished;
                timer.setText("Timer : " + (int) (mTimeLeftInMillis / 1000) + "s "+ (int) (mTimeLeftInMillis % 1000)+"ms" );
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                //affichage de l'ecran de fin
                String timeLeftFormatted = compScore();
                showEndScreen(timeLeftFormatted);
                onPause();
            }
        }.start();

    }

    private void showEndScreen(String message) {
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage(message)
                .setPositiveButton("OK",(dialogInterface, i) ->actionSuite(score))
                .setCancelable(false)
                .show();
        onPause();
        mCountDownTimer.cancel();
        //mCountDownTimer.onFinish();
    }
    /*
    Affichage du score à partir du temps mis par le joueur pour cacher le capteur
     */
    private String compScore(){
        int seconds = (int) (mTimeLeftInMillis ) / 1000;
        int milliseconds = (int) (mTimeLeftInMillis ) % 1000;
        if (passStatus=="Failed"){
            mTimeLeftInMillis=0;
        }
        score = (int)(2000*mTimeLeftInMillis/START_TIME_IN_MILLIS);
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02ds %02dms", seconds, milliseconds);
    return ("You did : "+timeLeftFormatted + "\n Your score is : " + score);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    void actionSuite(int score){
        Intent intent = new Intent();
        intent.putExtra("key_score", score);

        setResult(RESULT_OK, intent);

        finish();

    }
}


