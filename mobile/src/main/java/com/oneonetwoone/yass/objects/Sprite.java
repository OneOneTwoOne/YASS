package com.oneonetwoone.yass.objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

public abstract class Sprite extends GameObject {

    protected double mPositionX;
    protected double mPositionY;

    protected final double mPixelFactor;
    private final Bitmap mBitmap;
    protected final int mImageHeight;
    protected final int mImageWidth;
    protected int mRotation;
    protected double mRotationSpeed;

    private final Matrix mMatrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int drawableRes){
        Resources r= gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);
        mPixelFactor = gameEngine.mPixelFactor;

        mImageHeight = (int)(spriteDrawable.getIntrinsicHeight()*mPixelFactor);
        mImageWidth = (int)(spriteDrawable.getIntrinsicWidth()*mPixelFactor);

        mBitmap=((BitmapDrawable)spriteDrawable).getBitmap();
    }

    @Override
    public void onDraw(Canvas canvas){
        if(mPositionX>canvas.getWidth()
                ||mPositionY>canvas.getWidth()
                ||mPositionX<-mImageWidth
                ||mPositionY<-mImageHeight){
            return;
        }
        mMatrix.reset();
        mMatrix.postScale((float)mPixelFactor, (float)mPixelFactor);

        mMatrix.postTranslate((float)mPositionX, (float)mPositionY);
        mMatrix.postRotate((float) mRotation,
                (float) (mPositionX + mImageWidth/2),
                (float) (mPositionY + mImageWidth/2));
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }
}
