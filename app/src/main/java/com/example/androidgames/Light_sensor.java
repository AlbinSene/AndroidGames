package com.example.androidgames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Service;
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

    TextView textView;
    SensorManager sensorManager;
    Sensor sensor;
    String passStatus;
    //ajout du timer
    private static final long START_TIME_IN_MILLIS = 10000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);

        textView = (TextView) findViewById(R.id.textView);

        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

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
        Log.i("DEBUG", "temps" + mTimeLeftInMillis);
    if(sensorEvent.sensor.getType()==Sensor.TYPE_LIGHT){
        textView.setText(""+sensorEvent.values[0]);
    }
        if(sensorEvent.values[0]<10 && mTimeLeftInMillis>10){
            passStatus = "Passed";
        }else{
            passStatus = "Failed";
        }
        if(mTimeLeftInMillis<10 ||sensorEvent.values[0]<10){
            onPause();

            String timeLeftFormatted = compScore();
            textView.setText(timeLeftFormatted);
            new AlertDialog.Builder(this)
                    .setTitle(passStatus)
                    .setMessage(timeLeftFormatted)
                    .setPositiveButton("Restart",(dialogInterface, i) ->onPause())
                    .setCancelable(false)
                    .show();
        }



    }

    private void startTimer(){
        mCountDownTimer=new CountDownTimer(mTimeLeftInMillis,10) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis=millisUntilFinished;
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
    }

    /*
    Afficahge du score à partir du temps mis par le joueur pour cacher le capteur
     */
    private String compScore(){
        int seconds = (int) (mTimeLeftInMillis ) / 1000;
        int milliseconds = (int) (mTimeLeftInMillis ) % 1000;
        int score = 12*seconds + 6* milliseconds;
        if (passStatus=="Failed"){
            seconds=0;
            milliseconds=0;
            score=0;
        }
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", seconds, milliseconds);
    return ("You did : "+timeLeftFormatted + "\n Your score is : " + score);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    }
