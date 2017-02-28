package com.oneonetwoone.yass;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class YassActivity extends AppCompatActivity {
    private static final String TAG_FRAGMENT="Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yass);
        if (savedInstanceState==null){
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainMenuFragment(), TAG_FRAGMENT)
                    .commit();
        }
    }
    @Override
    public void onBackPressed(){
        final YassFragment fragment = (YassFragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if(!fragment.onBackPressed()){
            super.onBackPressed();
        }
    }

    public void navigateBack(){
        super.onBackPressed();
    }
}
