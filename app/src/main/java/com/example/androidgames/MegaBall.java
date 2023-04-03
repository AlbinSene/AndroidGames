package com.example.androidgames;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MegaBall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mega_ball);

        //Verification de la presence d'un accelerometre
        /*SensorManager mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = mgr.getSensorList((Sensor.TYPE_ALL));
        for(Sensor sensor : sensors ){
            Log.i("DEBUG", sensor.getName() + "---" + sensor.getVendor());
        }*/
    }
}