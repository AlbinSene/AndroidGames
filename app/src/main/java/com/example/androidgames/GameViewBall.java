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
        Log.i("DEBUG", x + " - " + y);
        this.moveImage(-x*4,y*4);

    }
    private void moveImage(float x, float y){
        timer++;
        currentX +=(int)x;
        currentY +=(int)y;
        this.currentX += (int) x;
        this.currentY += (int) y;

        if ( this.currentX <=0 ) {
            this.currentX = 0;
        } else if ( this.currentX + this.imageWidth >= this.getWidth() ){
            this.currentX = this.getWidth() - this.imageWidth;
        }

        if ( this.currentY <= 0 ) {
            this.currentY = 0;
        } else if ( this.currentY + this.imageHeight >= this.getHeight() ){
            this.currentY = this.getHeight() - this.imageHeight;
        }
        if(this.currentX<320 && this.currentY>1400 && timer== 50){
                megaBall.setPassStatus("Passed");
                megaBall.setScore(700);
            }else {
            megaBall.setPassStatus("Failed");
            megaBall.setScore(0);
        }
            Log.i("DEBUG", "victoire");
        this.invalidate();
    }


    public static void restartGame(){
        restart=1;
    }
}










