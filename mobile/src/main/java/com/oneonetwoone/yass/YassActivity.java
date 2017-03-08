package com.oneonetwoone.yass;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.oneonetwoone.yass.InputControllers.GamepadControllerListener;


public class YassActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT="Tag";
    public GamepadControllerListener mGamepadControllerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState==null){
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenuFragment(), TAG_FRAGMENT)
                    .commit();
        }
    }
    @Override
    public void onBackPressed(){
        final YassBaseFragment fragment = (YassBaseFragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if(!fragment.onBackPressed()){
            super.onBackPressed();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            View decorView= getWindow().getDecorView();
            if(Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT ){
                    decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                |View.SYSTEM_UI_FLAG_FULLSCREEN
                                |View.SYSTEM_UI_FLAG_LOW_PROFILE);}
            else{
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                |View.SYSTEM_UI_FLAG_FULLSCREEN
                                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

    public void navigateBack(){
        super.onBackPressed();
    }

    public void setGamepadControllerListener(GamepadControllerListener listener){
        mGamepadControllerListener=listener;
    }

    @Override
    public boolean dispatchGenericMotionEvent (MotionEvent event){
        if (mGamepadControllerListener !=null){
            if (mGamepadControllerListener.dispatchGenericMotionEvent(event)){
                return true;
            }
        }
        return super.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        if (mGamepadControllerListener != null){
            if(mGamepadControllerListener.dispatchKeyEvent(event)){
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }



}
