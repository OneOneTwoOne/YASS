package com.oneonetwoone.yass.objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

public abstract class Sprite extends ScreenGameObject {

    protected double mX;
    protected double mY;

    protected final double mPixelFactor;
    private final Bitmap mBitmap;
    protected final int mHeight;
    protected final int mWidth;
    protected int mRotation;
    protected double mRotationSpeed;

    private final Matrix mMatrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int drawableRes){
        Resources r= gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);
        mPixelFactor = gameEngine.mPixelFactor;

        mHeight = (int)(spriteDrawable.getIntrinsicHeight()*mPixelFactor);
        mWidth = (int)(spriteDrawable.getIntrinsicWidth()*mPixelFactor);

        mBitmap=((BitmapDrawable)spriteDrawable).getBitmap();
    }

    @Override
    public void onDraw(Canvas canvas){
        if(mX >canvas.getWidth()
                ||mY>canvas.getWidth()
                || mX <-mWidth
                ||mY<-mHeight){
            return;
        }
        mMatrix.reset();
        mMatrix.postScale((float)mPixelFactor, (float)mPixelFactor);

        mMatrix.postTranslate((float) mX, (float)mY);
        mMatrix.postRotate((float) mRotation,
                (float) (mX + mWidth/2),
                (float) (mY + mWidth/2));
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }
}
