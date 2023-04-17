package com.example.androidgames;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameViewBall extends View implements SensorEventListener {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap ballBitMap;

    private MegaBall megaBall;

    private int imageWidth;
    private int imageHeight;
    private int currentX;
    private int currentY;
    private static int timer;

    public static int getTimer() {
        return timer;
    }

    static int restart=0;

    public GameViewBall(Context context){
        super(context);
    }

    public GameViewBall(Context context, AttributeSet attrSet){
        super(context, attrSet);
    }



    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh){
        super.onSizeChanged(width,height,oldw,oldh);
        ballBitMap= BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        imageWidth= ballBitMap.getWidth();
        imageHeight = ballBitMap.getHeight();

        currentX= (width - imageWidth)/2;
        currentY= (height- imageHeight)/2;
    }

    @Override
    protected void onDraw (Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(ballBitMap, currentX, currentY, paint);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x= sensorEvent.values[0];
        float y= sensorEvent.values[1];

        //affichage des x et y
        //Log.i("DEBUG", x + " - " + y);
        this.moveImage(-x*4,y*4);

    }
    private void moveImage(float x, float y){
        timer++;
        currentX +=(int)x;
        currentY +=(int)y;
        //changement des coordonnées pour bouger la balle
        this.currentX += (int) x;
        this.currentY += (int) y;

        //délimitation de l'écran pour que la balle reste confinée
        //horizontalement
        if ( this.currentX <=0 ) { //bord gauche
            this.currentX = 0;
        } else if ( this.currentX + this.imageWidth >= this.getWidth() ){ //bord droit
            this.currentX = this.getWidth() - this.imageWidth;
        }
        //délimitation de l'écran pour que la balle reste confinée
        //verticalement
        if ( this.currentY <= 0 ) { //en haut
            this.currentY = 0;
        } else if ( this.currentY + this.imageHeight >= this.getHeight() ){ //en bas
            this.currentY = this.getHeight() - this.imageHeight;
        }

        Log.i("DEBUG", currentX +" , " + currentY + " --- " + timer); //affichage des coordonnées
        //condition de victoire
        if(this.currentX>500 && this.currentY>1450 && timer>= 500){
            Log.i("DEBUG", "cond victoire ");
                //megaBall.setPassStatus("Passed");
                //megaBall.setScore(700);
            }else {
                //megaBall.setPassStatus("Failed");
                //megaBall.setScore(0);
        }
            //Log.i("DEBUG", "victoire");
        //autoriser le mouvement sur la view
        this.invalidate();
    }


    public static void restartGame(){
        restart=1;
    }
}










