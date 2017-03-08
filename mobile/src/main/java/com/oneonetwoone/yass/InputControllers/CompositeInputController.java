package com.oneonetwoone.yass.InputControllers;

import android.view.View;

import com.oneonetwoone.yass.R;
import com.oneonetwoone.yass.YassActivity;

public class CompositeInputController extends InputController {
    public GamepadInputController mGamepadInputController;
    public VirtualJoystickInputController mVJoystickInputController;

    public CompositeInputController(View view, YassActivity activity){
        mGamepadInputController= new GamepadInputController(activity);
        mVJoystickInputController= new VirtualJoystickInputController(view);
    }

    public void onPreUpdate(){
        mIsFiring=mGamepadInputController.mIsFiring || mVJoystickInputController.mIsFiring;
        mHorizontalFactor=mGamepadInputController.mHorizontalFactor + mVJoystickInputController.mHorizontalFactor;
        mVerticalFactor=mGamepadInputController.mVerticalFactor + mVJoystickInputController.mVerticalFactor;
    }
}
