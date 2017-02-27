package com.oneonetwoone.yass;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.oneonetwoone.yass.engine.GameEngine;

import static android.R.attr.button;

public class YassFragment extends Fragment {
    private GameEngine mGameEngine;

    public YassFragment() {
    }

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
