package com.oneonetwoone.yass;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class YassActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT="Tag";

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
}
