package com.oneonetwoone.yass;

import android.view.View;
import android.widget.TextView;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

public class Player extends GameObject {
    public double mPositionX,mPositionY, mMaxX, mMaxY;
    public double mSpeedFactor;
    public TextView mTextView;

    public Player(final View view){
        double pixelFactor= view.getHeight() /400d;
        mSpeedFactor=pixelFactor*100d/1000d;
        mMaxX=view.getWidth()-view.getPaddingRight()-view.getPaddingLeft();
        mMaxY=view.getHeight() - view.getPaddingTop()-view.getPaddingBottom();
        mTextView=(TextView) view.findViewById(R.id.player);

    }

    @Override
    public void startGame(){
        mPositionX = mMaxX/2;
        mPositionY = mMaxY/2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
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

    @Override
    public void onDraw(){
        mTextView.setText("["+ mPositionX +","+mPositionY+"]");
    }
}
