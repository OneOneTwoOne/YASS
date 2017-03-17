package com.oneonetwoone.yass.objects;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.oneonetwoone.yass.engine.GameEngine;

public abstract class Sprite extends ScreenGameObject {

    protected final double mPixelFactor;
    private final Bitmap mBitmap;
    protected int mRotation;
    protected double mRotationSpeed;
    private Paint mPaint=new Paint();

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
    public void onDraw(Canvas canvas) {
        if (mX > canvas.getWidth()
                || mY > canvas.getHeight()
                || mX < -mWidth
                || mY < -mHeight) {
            return;
        }

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(mBoundingRect, mPaint);
        mMatrix.reset();
        mMatrix.postScale((float)mPixelFactor, (float)mPixelFactor);
        mMatrix.postTranslate((float) mX, (float)mY);
        mMatrix.postRotate((float) mRotation,
                (float) (mX + mWidth/2),
                (float) (mY + mWidth/2));
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }
}
