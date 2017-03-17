package com.oneonetwoone.yass.objects;

import android.util.Log;

import com.oneonetwoone.yass.InputControllers.InputController;
import com.oneonetwoone.yass.R;
import com.oneonetwoone.yass.engine.GameEngine;

import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {
    private double  mMaxX, mMaxY;
    private double mSpeedFactor;
    private List<Bullet> mBullets;
    private int INITIAL_BULLET_POOL_AMOUNT=6;
    private long TIME_BETWEEN_BULLETS=300;
    private long mTimeSinceLastFire;
    private GameEngine mGameEngine;

    public Player(GameEngine gameEngine){
        super(gameEngine, R.drawable.ship);
        mGameEngine=gameEngine;
        mSpeedFactor=mPixelFactor*100d/1000d;

        mMaxX = gameEngine.mWidth-mWidth;
        mMaxY = gameEngine.mHeight- mHeight;

        initBulletPool();
    }

    @Override
    public void startGame(){
        mX = mMaxX/2;
        mY = mMaxY/2;

    }


    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        updatePosition(elapsedMillis, gameEngine.mInputController);
        checkFiring(elapsedMillis, gameEngine);
        InputController inputController = gameEngine.mInputController;
        mX +=
                mSpeedFactor*(inputController.mHorizontalFactor*elapsedMillis);
        if(mX <0){
            mX =0;
        }
        if(mX >mMaxX){
            mX =mMaxX;
        }
        mY+=
                mSpeedFactor*(inputController.mVerticalFactor*elapsedMillis);
        if(mY<0){
            mY=0;
        }
        if(mY>mMaxY){
            mY=mMaxY;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine){
        if (gameEngine.mInputController.mIsFiring && mTimeSinceLastFire > TIME_BETWEEN_BULLETS){
            Bullet b = getBullet();
            if (b == null){
                return;
            }
            b.init(this, mX + mWidth/2, mY); //put bullet at top of ship, halfway thru width
            gameEngine.addGameObject(b,2);
            mTimeSinceLastFire = 0;
        }
        else{
            mTimeSinceLastFire+=elapsedMillis;
        }
    }

    private void initBulletPool(){
        mBullets=new ArrayList<>();
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++){
            mBullets.add(new Bullet(mGameEngine));
        }
    }

    private Bullet getBullet(){
        if (mBullets.isEmpty()){
            return null;
        }
        return mBullets.remove(0);
    }

    public void releaseBullet(Bullet b){
        mBullets.add(b);
    }

    public void updatePosition(long ellapsedMillis, InputController inputController){}

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject){
        if(otherObject instanceof Asteroid){
            removeObject(gameEngine);
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
        }
    }
}
