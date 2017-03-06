package com.oneonetwoone.yass;

import android.view.MotionEvent;
import android.view.View;

public class VJoystickTouchListener implements View.OnTouchListener {

    public double mStartingPositionX,mStartingPositionY,mHorizontalFactor, mVerticalFactor, mMaxDistance;
    @Override
    public boolean onTouch(View v, MotionEvent event){
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN){
            mStartingPositionX = event.getX(0);
            mStartingPositionY = event.getY(0);
        }
        else if (action == MotionEvent.ACTION_UP){
            mHorizontalFactor=0;
            mVerticalFactor=0;
        }
        else if(action == MotionEvent.ACTION_MOVE){
            mHorizontalFactor=(event.getX(0)-mStartingPositionX)/mMaxDistance;
        if(mHorizontalFactor>1){
            mHorizontalFactor=1;
        }
        else if(mHorizontalFactor < -1){
            mHorizontalFactor= -1;
        }
        mVerticalFactor=1;
        }
        else if(mVerticalFactor < -1){
            mVerticalFactor = -1;
        }
        return true;
    }
}
