package com.oneonetwoone.yass;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oneonetwoone.yass.engine.GameEngine;

public class YassFragment extends YassBaseFragment {
    private GameEngine mGameEngine;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yass_main, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        mGameEngine = new GameEngine(getActivity());
        mGameEngine.addGameObject(new ScoreGameObject(view, R.id.txt_score));
        view.findViewById(R.id.btn_start_stop).setOnClickListener(this);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
    }


}
