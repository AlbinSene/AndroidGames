package com.example.androidgames;

import android.app.Activity;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.graphics.Color;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


import java.util.Locale;

public class GameViewBall extends View implements SensorEventListener {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap ballBitMap;

    private MegaBall megaBall;

    private int imageWidth;
    private int imageHeight;
    private int currentX;
    private int currentY;

    TextView timer = new TextView(getContext()); // TextView pour afficher le compte à rebours

    // Durée de départ du compte à rebours
    private static final long START_TIME_IN_MILLIS = 15000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private CountDownTimer mCountDownTimer; // Compte à rebours
    private boolean mTimerRunning; // Booléen pour vérifier si le compte à rebours est en cours d'exécution


    private static int victoireX = 60;
    private static int victoireY = 60;
    private static int marge = 50;
    private int score=0;
    private String passStatus= "Failed";




    static int restart=0;

    public GameViewBall(Context context){
        super(context);
        startTimer();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        victoireX = screenWidth * victoireX / 100;
        victoireY = screenHeight * victoireY / 100;

    }

    public GameViewBall(Context context, AttributeSet attrSet){
        super(context, attrSet);
        startTimer();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        victoireX = screenWidth * victoireX / 100;
        victoireY = screenHeight * victoireY / 100;

    }



    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh){
        super.onSizeChanged(width,height,oldw,oldh);
        ballBitMap= BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        imageWidth= ballBitMap.getWidth();
        imageHeight = ballBitMap.getHeight();

        currentX= (width - imageWidth)/2;
        currentY= (height- imageHeight)/2;
        timer = (TextView) ((Activity) getContext()).findViewById(R.id.timer);

    }


    @Override
    protected void onDraw (Canvas canvas){
        super.onDraw(canvas);
        canvas.drawBitmap(ballBitMap, currentX, currentY, paint);

        paint.setColor(Color.RED);

        // Dessiner un cercle rouge de 1 pixel de diamètre aux coordonnées de victoire
        canvas.drawCircle(victoireX, victoireY, 14f, paint);
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
        invalidate();

    }
    private void moveImage(float x, float y){
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
        //Log.i("DEBUG", currentX +" , " + currentY + " --- " + timer);
        int seconds = (int) (mTimeLeftInMillis ) / 1000;
        int milliseconds = (int) (mTimeLeftInMillis ) % 1000;
        if(this.currentX>victoireX //&& this.currentX>victoireX+marge
                && this.currentY>victoireY //&& this.currentY>victoireY+marge
               && seconds>1 && milliseconds>10
        ){
            Log.i("DEBUG", "cond victoire ");
            score =1000;
            passStatus="Passed";
            ((MegaBall) getContext()).traiterGameView(this,score,passStatus);
        }
            //Log.i("DEBUG", "victoire");
        invalidate();
    }


    public static void restartGame(){
        restart=1;
    }


    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                timer.setText((int) (mTimeLeftInMillis / 1000) + " s " + (int) (mTimeLeftInMillis % 1000) + " ms");
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;

            }
        }.start();
    }

        private String compScore(){
            int seconds = (int) (mTimeLeftInMillis ) / 1000;
            int milliseconds = (int) (mTimeLeftInMillis ) % 1000;
            int score = 6*seconds + 12* milliseconds;
            if (passStatus=="Failed"){
                seconds=0;
                milliseconds=0;
                score=0;
            }
            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", seconds, milliseconds);
            return ("You did : "+timeLeftFormatted + "\n Your score is : " + score);
    }
}










