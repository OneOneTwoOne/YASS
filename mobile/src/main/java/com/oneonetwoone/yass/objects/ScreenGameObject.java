package com.oneonetwoone.yass.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

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
        return false;
    }

    public void onCollision(GameEngine gameEngine, ScreenGameObject sgo){

    }
    @Override
    public void onDraw(Canvas canvas){}
    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){}

    public void removeObject(){}
}
