package com.example.androidgames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MegaBall extends AppCompatActivity {

    private GameViewBall view ;
    private SensorManager sensorManager ;


    @Override
    public Context getBaseContext() {
        return super.getBaseContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mega_ball);
        view = findViewById(R.id.theview); // Utilisation de findViewById pour récupérer GameViewBall
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(view, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(view);

    }

    public void traiterGameView(GameViewBall gameViewBall,int score,String passStatus) {
        //Log.i("DEBUG","fin du game");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(passStatus);
        builder.setMessage("Your score is : "+score);
        builder.setPositiveButton("OK", (dialogInterface, i) ->actionSuite());
        builder.show();
        onPause();
        //setContentView(R.layout.activity_light_sensor);

    }

    void actionSuite(){
        Intent intent = new Intent();
        intent.putExtra("key_score", 666);

        setResult(RESULT_OK, intent);

        finish();
    }
}
