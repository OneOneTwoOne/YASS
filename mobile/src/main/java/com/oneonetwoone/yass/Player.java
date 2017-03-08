package com.oneonetwoone.yass;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oneonetwoone.yass.InputControllers.InputController;
import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    private double mPositionX,mPositionY, mMaxX, mMaxY;
    private double mSpeedFactor;
    public TextView mTextView;
    public ImageView mShip;
    public View mView;
    public List<Bullet> mBullets;
    public int INITIAL_BULLET_POOL_AMOUNT=6;
    public long TIME_BETWEEN_BULLETS=300;
    double mPixelFactor;
    long mTimeSinceLastFire;

    public Player(final View view){
        mView=view;
        mBullets = new ArrayList<>();
        mPixelFactor= mView.getHeight() /400d;
        mSpeedFactor=mPixelFactor*100d/1000d;
        mMaxX=mView.getWidth()-mView.getPaddingRight()-mView.getPaddingLeft();
        mMaxY=mView.getHeight() - mView.getPaddingTop()-mView.getPaddingBottom();
        mTextView=(TextView) mView.findViewById(R.id.txt_score);
        mShip= new ImageView(mView.getContext());
        Drawable shipDrawable = mView.getContext().getResources().getDrawable(R.drawable.ship);
        mShip.setLayoutParams(new ViewGroup.LayoutParams(
                (int) (shipDrawable.getIntrinsicWidth()*mPixelFactor),
                (int) (shipDrawable.getIntrinsicHeight()*mPixelFactor)));
        mShip.setImageDrawable(shipDrawable);

        mMaxX -= (shipDrawable.getIntrinsicWidth()*mPixelFactor);
        mMaxY -= (shipDrawable.getIntrinsicHeight()*mPixelFactor);

        ((FrameLayout) mView).addView(mShip);
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
                return;
            }
            b.init(this, mPositionX + mShip.getWidth()/2, mPositionY); //put bullet at top of ship, halfway thru width
            gameEngine.addGameObject(b);
            mTimeSinceLastFire = 0;
        }
        else{
            mTimeSinceLastFire+=elapsedMillis;
        }
    }

    @Override
    public void onDraw(){
        mTextView.setText("["+ mPositionX +","+mPositionY+"]");
        mShip.setTranslationX((int) mPositionX);
        mShip.setTranslationY((int) mPositionY);
    }

    private void initBulletPool(){
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++){
            mBullets.add(new Bullet(mView, mPixelFactor));
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
