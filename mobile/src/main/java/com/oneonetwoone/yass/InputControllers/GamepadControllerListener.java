package com.oneonetwoone.yass.InputControllers;

import android.hardware.input.InputManager.InputDeviceListener;
import android.view.KeyEvent;
import android.view.MotionEvent;

import static android.R.id.input;

public interface GamepadControllerListener {
    boolean dispatchGenericMotionEvent(MotionEvent event);
    boolean dispatchKeyEvent(KeyEvent event);


}
