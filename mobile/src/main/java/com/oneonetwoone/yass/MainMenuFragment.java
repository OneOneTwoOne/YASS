package com.oneonetwoone.yass;

import com.oneonetwoone.yass.counter.GameFragment;

public class MainMenuFragment extends YassBaseFragment {
    public void startGame(){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new GameFragment(), TAG_FRAGMENT)
                .addToBackStack(null)
                .commit();
    }
}
