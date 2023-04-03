package com.example.androidgames;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameViewBall extends View {
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
        currentY= (height-imageHeight)/2;
    }

    @Override
    protected void onDraw (Canvas canvas){
        super.onDraw(canvas);

        canvas.drawBitmap(ballBitMap, currentX, currentY, paint);
    }
}
