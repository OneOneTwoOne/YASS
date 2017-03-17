package com.oneonetwoone.yass.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

/**
 * Created by Home on 14/03/2017.
 */

public class ScreenGameObject extends GameObject {
    protected double mX;
    protected double mY;
    protected int mHeight;
    protected int mWidth;

    public Rect mBoundingRect=new Rect(-1,-1,-1,-1);

    public boolean checkCollision(ScreenGameObject otherObject){

        return checkRectangularCollision(otherObject);
    }

    private boolean checkRectangularCollision(ScreenGameObject other){
        return Rect.intersects(mBoundingRect, other.mBoundingRect);


    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject sgo){
    }
    @Override
    public void onDraw(Canvas canvas){

    }
    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){}

    public void removeObject(GameEngine gameEngine){
        gameEngine.removeGameObject(this);
    }

    public void onPostUpdate(){
        mBoundingRect.set(
                (int)mX,
                (int)mY,
                (int)mX+mWidth,
                (int)mY+mHeight
        );
    }
}
