package com.example.androidgames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameViewBall extends View implements SensorEventListener {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap ballBitMap;

    private int imageWidth;
    private int imageHeight;
    private int currentX;
    private int currentY;

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
        // Log.i("DEBUG", x + " - " + y);
        this.moveImage(-x*4,y*4);
    }
    private void moveImage(float x, float y){
        currentX +=(int)x;
        currentY +=(int)y;
        this.currentX += (int) x;
        this.currentY += (int) y;

        if ( this.currentX < 1 ) {
            this.currentX = 0;
        } else if ( this.currentX + this.imageWidth > this.getWidth() ){
            this.currentX = this.getWidth() - this.imageWidth;
        }

        if ( this.currentY < 0 ) {
            this.currentY = 0;
        } else if ( this.currentY + this.imageHeight > this.getHeight() ){
            this.currentY = this.getHeight() - this.imageHeight;
        }
        this.invalidate();
    }
}










