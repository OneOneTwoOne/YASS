package com.oneonetwoone.yass.objects;

import com.oneonetwoone.yass.GameController;
import com.oneonetwoone.yass.R;
import com.oneonetwoone.yass.engine.GameEngine;

import static com.oneonetwoone.yass.objects.ScreenGameObject.BodyType.Circular;

public class Asteroid extends Sprite {
    private final GameController mController;
    private final double mSpeed;
    private double mSpeedX, mSpeedY;

    public Asteroid(GameController gameController, GameEngine gameEngine, int num){
        super(gameEngine, R.drawable.a10000);
        mBodyType=Circular;
        mSpeed = 200d*mPixelFactor/1000d;
        mController= gameController;
    }

    public void init(GameEngine gameEngine){

        double angle= gameEngine.mRandom.nextDouble()*Math.PI/3d-Math.PI/6d;
        mSpeedX = mSpeed*Math.sin(angle);
        mSpeedY = mSpeed*Math.cos(angle);

        mX = gameEngine.mRandom.nextInt(gameEngine.mWidth/2)+gameEngine.mWidth/4;

        mY = -mHeight;

        mRotation= (gameEngine.mRandom.nextInt(360));

        mRotationSpeed = angle*(180d / Math.PI)/250d;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        mX += mSpeedX * elapsedMillis;
        mY += mSpeedY * elapsedMillis;
        if (mY > gameEngine.mHeight) {
            removeObject(gameEngine);
            returnToPool();
        }
        mRotation += mRotationSpeed * elapsedMillis;
        if (mRotation > 360){
            mRotation=0;
        }
        else if (mRotation<0){
            mRotation = 360;
        }
    }
    public void returnToPool(){
        mController.returnToPool(this);

    }
    @Override
    public void removeObject(GameEngine gameEngine){
        super.removeObject(gameEngine);
        returnToPool();
    }
}
