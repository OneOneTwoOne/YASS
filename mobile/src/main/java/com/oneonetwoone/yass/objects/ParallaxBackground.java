package com.oneonetwoone.yass.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

public class ParallaxBackground extends GameObject {

    protected double mHeight, mWidth, mScreenHeight, mScreenWidth, mTargetWidth, mSpeedY, mPixelFactor, mY;
    private final Bitmap mBitmap;
    private Rect mSrcRect =new Rect(), mDstRect=new Rect();


    public ParallaxBackground(GameEngine gameEngine, int speed, int drawableResId){
        Drawable spriteDrawable = gameEngine.getContext().getResources().getDrawable(drawableResId);
        mBitmap= ((BitmapDrawable) spriteDrawable).getBitmap();

        mPixelFactor= gameEngine.mPixelFactor;
        mSpeedY = speed*mPixelFactor/1000d;

        mHeight =spriteDrawable.getIntrinsicHeight()*mPixelFactor;
        mWidth =spriteDrawable.getIntrinsicWidth()*mPixelFactor;

        mScreenHeight = gameEngine.mHeight;
        mScreenWidth = gameEngine.mWidth;

        mTargetWidth = Math.min(mWidth, mScreenWidth);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        mY+=mSpeedY*elapsedMillis;
    }

    @Override
    public void onDraw(Canvas canvas){
        efficientDraw(canvas);
    }

    private void efficientDraw(Canvas canvas){
        if (mY<0){
            mSrcRect.set(  // has scale of image...
                    0,
                    (int)(-mY/mPixelFactor/*...hence this.*/),
                    (int)(mTargetWidth/mPixelFactor),
                    (int)((mScreenHeight - mY)/mPixelFactor ));
            mDstRect.set(  // has scale of GameView ...
                    0,
                    0,
                    (int)mTargetWidth,/*...hence this.*/
                    (int)mScreenHeight);
            canvas.drawBitmap(mBitmap,mSrcRect, mDstRect, null);
        }
        else {
            mSrcRect.set(
                    0,
                    0,
                    (int) (mTargetWidth / mPixelFactor),
                    (int) ((mScreenHeight - mY) / mPixelFactor));
            mDstRect.set(
                    0,
                    (int) mY,
                    (int) mTargetWidth,
                    (int) mScreenHeight);
            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
            //we need to draw previous block
            mSrcRect.set(
                    0,
                    (int) ((mHeight - mY) / mPixelFactor),
                    (int) (mTargetWidth / mPixelFactor),
                    (int) (mHeight / mPixelFactor));
            mDstRect.set(
                    0,
                    0,
                    (int) mTargetWidth,
                    (int) mY);
            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
        }
        if (mY > mScreenHeight) {
            mY-= mHeight;
        }
    }
}
