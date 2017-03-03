package com.oneonetwoone.yass;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    public double mPositionX,mPositionY, mMaxX, mMaxY;
    public double mSpeedFactor;
    public TextView mTextView;
    public ImageView mShip;
    public List<Bullet> mBullets;
    public int INITIAL_BULLET_POOL_AMOUNT=0;
    public long TIME_BETWEEN_BULLETS;
    double mPixelFactor;
    long mTimeSinceLastFire;

    public Player(final View view){
        mBullets = new ArrayList<>();
        mPixelFactor= view.getHeight() /400d;
        mSpeedFactor=mPixelFactor*100d/1000d;
        mMaxX=view.getWidth()-view.getPaddingRight()-view.getPaddingLeft();
        mMaxY=view.getHeight() - view.getPaddingTop()-view.getPaddingBottom();
        mTextView=(TextView) view.findViewById(R.id.player);
        mShip= new ImageView(view.getContext());
        Drawable shipDrawable = view.getContext().getResources().getDrawable(R.drawable.ship);
        mShip.setLayoutParams(new ViewGroup.LayoutParams(
                (int) (shipDrawable.getIntrinsicWidth()*mPixelFactor),
                (int) (shipDrawable.getIntrinsicHeight()*mPixelFactor)));
        mShip.setImageDrawable(shipDrawable);

        mMaxX -= (shipDrawable.getIntrinsicWidth()*mPixelFactor);
        mMaxY -= (shipDrawable.getIntrinsicHeight()*mPixelFactor);

        ((FrameLayout) view).addView(mShip);
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
                mSpeedFactor*inputController.mHorizontalFactor*elapsedMillis;
        if(mPositionX<0){
            mPositionX=0;
        }
        if(mPositionX>mMaxX){
            mPositionX=mMaxX;
        }
        mPositionY+=
                mSpeedFactor*inputController.mVerticalFactor*elapsedMillis;
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
            b.init(mPositionX + mShip.getWidth()/2, mPositionY);
            gameEngine.addGameObject(b);
            mTimeSinceLastFire = 0;
        }
        else{
            mTimeSinceLastFire+=elapsedMillis;
        }
    }

    public void updatePosition(){}

    @Override
    public void onDraw(){
        mTextView.setText("["+ mPositionX +","+mPositionY+"]");
        mShip.setTranslationX((int) mPositionX);
        mShip.setTranslationY((int) mPositionY);
    }

    private void initBulletPool(){
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++){
            mBullets.add(new Bullet(mPixelFactor));
        }
    }

    private Bullet getBullet(){
        if (mBullets.isEmpty()){
            return null;
        }
        return mBullets.remove(0);
    }

    private void releaseBullet(Bullet b){
        mBullets.add(b);
    }
}
