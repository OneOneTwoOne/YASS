package com.oneonetwoone.yass.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

public class ScreenGameObject extends GameObject {
    protected double mX;
    protected double mY;
    protected int mHeight;
    protected int mWidth;
    protected double mRadius;

    public Rect mBoundingRect=new Rect(-1,-1,-1,-1);

    public boolean checkCollision(ScreenGameObject otherObject){
        return checkCircularCollision(otherObject);
    }

    private boolean checkRectangularCollision(ScreenGameObject other){
        return Rect.intersects(mBoundingRect, other.mBoundingRect);
    }

    private boolean checkCircularCollision(ScreenGameObject other){
        double distanceX = (mX+mWidth/2)-(other.mX+other.mWidth/2);
        double distanceY = (mY+mHeight/2)-(other.mY+other.mHeight/2);
        double squareDistance=distanceX*distanceX+distanceY*distanceY;
        double collisionDistance=(mRadius+other.mRadius);
        return squareDistance<= collisionDistance*collisionDistance;
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
