package com.oneonetwoone.yass;

import android.view.View;

/**
 * Created by Hugh on 06/03/2017.
 */

public class VirtualJoystickInputController extends InputController {
    private double mMaxDistance;
    public boolean mIsFiring;

    public VirtualJoystickInputController(View view){
        view.findViewById(R.id.vjoystick_main).setOnTouchListener(new VJoystickTouchListener());
        view.findViewById(R.id.vjoystick_touch).setOnTouchListener(new VFireButtonTouchListener());

        double pixelFactor = view.getHeight()/400d;
        mMaxDistance=50*pixelFactor;
    }
}
