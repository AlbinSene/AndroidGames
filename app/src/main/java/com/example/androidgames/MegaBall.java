package com.example.androidgames;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MegaBall extends AppCompatActivity {

    private GameViewBall view;
    private SensorManager mgr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mega_ball);

        view = (GameViewBall) findViewById(R.id.theview);
        //Verification de la presence d'un accelerometre
        mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        /*
        List<Sensor> sensors = mgr.getSensorList((Sensor.TYPE_ALL));
        for(Sensor sensor : sensors ){
            Log.i("DEBUG", sensor.getName() + "---" + sensor.getVendor());
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mgr.registerListener(view,mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),mgr.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mgr.unregisterListener(view);
    }
}
