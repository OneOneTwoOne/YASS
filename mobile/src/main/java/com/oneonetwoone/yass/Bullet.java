package com.oneonetwoone.yass;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;



public class Bullet extends GameObject {
    public ImageView mImageView;
    public double mSpeedFactor, mImageHeight, mImageWidth, mPositionX, mPositionY;
    public Player mParent;

    public Bullet(View view, double pixelFactor){
        Context c = view.getContext();

        mSpeedFactor= pixelFactor*-300d/1000d;

        mImageView= new ImageView(c);
        Drawable bulletDrawable=c.getResources().getDrawable(R.drawable.ship);

        mImageHeight= bulletDrawable.getIntrinsicHeight()*pixelFactor;
        mImageWidth= bulletDrawable.getIntrinsicWidth()*pixelFactor;

        mImageView.setLayoutParams(new ViewGroup.LayoutParams((int) (mImageWidth),(int) (mImageHeight)));
        mImageView.setImageDrawable(bulletDrawable);
        mImageView.setVisibility(View.GONE);
        ((FrameLayout) view).addView(mImageView);
    }

    public void init(Player parent, double positionX, double positionY){
        mPositionX= positionX-mImageWidth/2;
        mPositionY=positionY-mImageHeight/2;
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
        mPositionY += mSpeedFactor * elapsedMillis;
        if(mPositionY < -mImageHeight){
            gameEngine.removeGameObject(this);
            mParent.releaseBullet(this);
        }
    }
    @Override
    public void onDraw(){
        mImageView.setTranslationX((int) mPositionX);
        mImageView.setTranslationY((int) mPositionY);
    }


}
