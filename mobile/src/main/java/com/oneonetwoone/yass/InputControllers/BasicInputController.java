package com.oneonetwoone.yass.InputControllers;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import com.oneonetwoone.yass.R;

public class BasicInputController extends InputController implements View.OnTouchListener {
    private Activity mActivity;

    public BasicInputController(View view){
        view.findViewById(R.id.keypad_up).setOnTouchListener(this);
        view.findViewById(R.id.keypad_down).setOnTouchListener(this);
        view.findViewById(R.id.keypad_left).setOnTouchListener(this);
        view.findViewById(R.id.keypad_right).setOnTouchListener(this);
        view.findViewById(R.id.keypad_fire).setOnTouchListener(this);
    }
    @Override
    public boolean onTouch(View v,MotionEvent event){
        int action=event.getActionMasked();
        int id =v.getId();
        if (action == MotionEvent.ACTION_DOWN){
            if(id==R.id.keypad_up){
                mVerticalFactor -= 1;
            }
            else if(id==R.id.keypad_down){
                mVerticalFactor += 1;
            }
            else if(id==R.id.keypad_left){
                mHorizontalFactor -= 1;
            }
            else if(id==R.id.keypad_right){
                mHorizontalFactor += 1;
            }
            else if(id==R.id.keypad_fire){
                mIsFiring=true;
            }
        } else if(action==MotionEvent.ACTION_UP){
            if(id==R.id.keypad_up){
                mVerticalFactor += 1;
            }
            else if(id==R.id.keypad_down){
                mVerticalFactor -= 1;
            }
            else if(id==R.id.keypad_left){
                mHorizontalFactor += 1;
            }
            else if(id==R.id.keypad_right){
                mHorizontalFactor -= 1;
            }
            else if(id==R.id.keypad_fire){
                mIsFiring=false;
            }
        }
        return false;}

}
