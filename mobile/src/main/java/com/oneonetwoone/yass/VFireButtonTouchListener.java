package com.oneonetwoone.yass;

import android.view.MotionEvent;
import android.view.View;

public class VFireButtonTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event){
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN){
            mIsFiring = true;
        }
        else if (action == MotionEvent.ACTION_UP){
            mIsFiring = false;
        }
        return true;
    }
}
