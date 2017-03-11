package com.oneonetwoone.yass.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

public class FPSCounter extends GameObject {
    private final float mTextWidth;
    private final float mTextHeight;
    private final double mPixelFactor;

    private Paint mPaint;
    private long mTotalMillis;
    private int mDraws;
    private float mFps;

    private String mFpsText="";

    public FPSCounter(GameEngine gameEngine){
        mPaint=new Paint();
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPixelFactor= gameEngine.mPixelFactor;
        mTextHeight = (float) (25*mPixelFactor);
        mTextWidth = (float) (50*mPixelFactor);
        mPaint.setTextSize(mTextHeight/2);
    }

    @Override
    public void startGame(){
        mTotalMillis=0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        mTotalMillis += elapsedMillis;
        if(mTotalMillis>1000){
            mFps=mDraws*1000/mTotalMillis;
            mFpsText=mFps+" fps";
            mTotalMillis=0;
            mDraws = 0;
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0,(int)(canvas.getHeight()-mTextHeight),
                mTextWidth, canvas.getHeight(), mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawText(mFpsText, mTextWidth/2, (int)(canvas.getHeight()-mTextHeight/2), mPaint);
        mDraws++;
    }
}
