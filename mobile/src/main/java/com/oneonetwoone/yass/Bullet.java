package com.oneonetwoone.yass;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;



public class Bullet extends Sprite {
    public ImageView mImageView;
    private double mSpeedFactor, mPositionX, mPositionY;
    public Player mParent;

    public Bullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.bullet);
        mSpeedFactor= gameEngine.mPixelFactor*-300d/1000d;
    }

    public void init(Player parent, double positionX, double positionY){
        mPositionX= positionX-(mImageWidth/2);
        mPositionY=positionY-(mImageHeight/2);
        mParent = parent;
    }

    @Override
    public void onRemovedFromGameUiThread(){
        mImageView.setVisibility(View.GONE);
    }
    @Override
    public void onAddedToGameUiThread(){
        mImageView.setVisibility(View.VISIBLE);
    }


    @Override
    public void startGame(){}
    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        mPositionY += (mSpeedFactor * elapsedMillis);
        if(mPositionY < -mImageHeight){
            gameEngine.removeGameObject(this);
            mParent.releaseBullet(this);
        }
    }
}
