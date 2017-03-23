package com.oneonetwoone.yass.objects;

import android.util.Log;
import android.view.View;

import com.oneonetwoone.yass.R;
import com.oneonetwoone.yass.engine.GameEngine;


public class Bullet extends Sprite {
    public double mSpeedFactor;
    public Player mParent;

    public Bullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.bullet);
        mSpeedFactor= mPixelFactor*-300d/1000d;
    }

    public void init(Player parent, double positionX, double positionY){
        mX = positionX-(mWidth/2);
        mY=positionY-(mHeight /2);
        mParent = parent;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        mY += (mSpeedFactor * elapsedMillis);
        if(mY < -mHeight){
            mParent.releaseBullet(this);
            removeObject(gameEngine);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject){
        if(otherObject instanceof Asteroid){
            removeObject(gameEngine);
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            a.returnToPool();
        }
    }

    @Override
    public void removeObject(GameEngine gameEngine){
        super.removeObject(gameEngine);
        mParent.releaseBullet(this);
    }
}
