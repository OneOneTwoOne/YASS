package com.oneonetwoone.yass.objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

/**
 * Created by Hugh on 10/03/2017.
 */

public abstract class Sprite extends GameObject {

    protected double mPositionX;
    protected double mPositionY;

    protected final double mPixelFactor;
    private final Bitmap mBitmap;
    protected final int mImageHeight;
    protected final int mImageWidth;

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
        mMatrix.reset();
        mMatrix.postScale((float)mPixelFactor, (float)mPixelFactor);

        mMatrix.postTranslate((float)mPositionX, (float)mPositionY);
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }
}
