package com.oneonetwoone.yass.objects;

import com.oneonetwoone.yass.GameController;
import com.oneonetwoone.yass.R;
import com.oneonetwoone.yass.engine.GameEngine;

public class Asteroid extends Sprite {
    private final GameController mController;
    private final double mSpeed;
    private double mSpeedX, mSpeedY;

    public Asteroid(GameController gameController, GameEngine gameEngine){
        super(gameEngine, R.drawable.a10000);
        mSpeed = 200d*mPixelFactor/1000d;
        mController= gameController;
    }

    public void init(GameEngine gameEngine){
        double angle= gameEngine.mRandom.nextDouble()*Math.PI/3d-Math.PI/6d;
        mSpeedX = mSpeed*Math.sin(angle);
        mSpeedY = mSpeed*Math.cos(angle);

        mPositionX=gameEngine.mRandom.nextInt(gameEngine.mWidth/2)+gameEngine.mWidth/4;

        mPositionY =-mImageHeight;

        mRotation= gameEngine.mRandom.nextInt(360);

        mRotationSpeed = angle*(180d / Math.PI)/250d;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        mPositionX += mSpeedX * elapsedMillis;
        mPositionY += mSpeedY * elapsedMillis;
        if (mPositionY > gameEngine.mHeight) {
            gameEngine.removeGameObject(this);
            mController.returnToPool(this);
        }
        mRotation += mRotationSpeed * elapsedMillis;
        if (mRotation > 360){
            mRotation=0;
        }
        else if (mRotation<0){
            mRotation = 360;
        }
    }
}