package com.example.androidgames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

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

        view = (GameViewBall) findViewById(R.id.theview);
        //Verification de la presence d'un accelerometre
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        /*
        List<Sensor> sensors = mgr.getSensorList((Sensor.TYPE_ALL));
        for(Sensor sensor : sensors ){
            Log.i("DEBUG", sensor.getName() + "---" + sensor.getVendor());
        }*/

        /*if (GameViewBall.getTimer() >= 500) {
            new AlertDialog.Builder(this)
                    .setTitle(passStatus)
                    .setMessage("Your score is : " + score)
                    .setPositiveButton("Restart", (dialogInterface, i) -> GameViewBall.restartGame())
                    .setCancelable(false)
                    .show();
                    }*/


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(view, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(view);
    }

    public void traiterGameView(GameViewBall gameViewBall,int score,String passStatus) {
        //Log.i("DEBUG","fin du game");
        AlertDialog.Builder builder = new AlertDialog.Builder(MegaBall.this);
        builder.setTitle(passStatus);
        builder.setMessage("Your score is : "+score);
        builder.setPositiveButton("OK", null);
        builder.show();
        onPause();
        //setContentView(R.layout.activity_light_sensor);

    }
}
