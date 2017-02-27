package com.oneonetwoone.yass;

import android.support.v4.app.Fragment;

import com.oneonetwoone.yass.counter.GameFragment;

public class  MainMenuFragment extends YassBaseFragment {
    private static final String TAG_FRAGMENT="Tag";
    public void startGame(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new GameFragment(), TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
}
