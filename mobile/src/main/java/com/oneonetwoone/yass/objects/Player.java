package com.oneonetwoone.yass.objects;

import android.util.Log;
import android.widget.ImageView;

import com.oneonetwoone.yass.InputControllers.InputController;
import com.oneonetwoone.yass.R;
import com.oneonetwoone.yass.engine.GameEngine;

import java.util.ArrayList;
import java.util.List;

public class Player extends Sprite {
    private double  mMaxX, mMaxY;
    private double mSpeedFactor;
    public ImageView mShip;
    public List<Bullet> mBullets;
    public int INITIAL_BULLET_POOL_AMOUNT=6;
    public long TIME_BETWEEN_BULLETS=300;
    long mTimeSinceLastFire;
    private GameEngine mGameEngine;

    public Player(GameEngine gameEngine){
        super(gameEngine, R.drawable.ship);
        mGameEngine=gameEngine;
        mSpeedFactor=mPixelFactor*100d/1000d;

        mMaxX = gameEngine.mWidth-mImageWidth;
        mMaxY = gameEngine.mHeight-mImageHeight;

        initBulletPool();
    }

    @Override
    public void startGame(){
        mPositionX = mMaxX/2;
        mPositionY = mMaxY/2;

    }


    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        updatePosition(elapsedMillis, gameEngine.mInputController);
        checkFiring(elapsedMillis, gameEngine);
        InputController inputController = gameEngine.mInputController;
        mPositionX +=
                mSpeedFactor*(inputController.mHorizontalFactor*elapsedMillis);
        if(mPositionX<0){
            mPositionX=0;
        }
        if(mPositionX>mMaxX){
            mPositionX=mMaxX;
        }
        mPositionY+=
                mSpeedFactor*(inputController.mVerticalFactor*elapsedMillis);
        if(mPositionY<0){
            mPositionY=0;
        }
        if(mPositionY>mMaxY){
            mPositionY=mMaxY;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine){
        if (gameEngine.mInputController.mIsFiring && mTimeSinceLastFire > TIME_BETWEEN_BULLETS){
            Bullet b = getBullet();
            if (b == null){
                Log.i("WH", "huuh");
                return;
            }
            b.init(this, mPositionX + mImageWidth/2, mPositionY); //put bullet at top of ship, halfway thru width
            gameEngine.addGameObject(b);
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

}
