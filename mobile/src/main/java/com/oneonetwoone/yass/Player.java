package com.oneonetwoone.yass;

import android.widget.TextView;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

public class Player extends GameObject {
    public int mPositionX,mSpeedFactor,mPositionY, mMaxX, mMaxY;
    public TextView mTextView;

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
