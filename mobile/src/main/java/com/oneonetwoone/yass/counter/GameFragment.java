package com.oneonetwoone.yass.counter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.oneonetwoone.yass.R;
import com.oneonetwoone.yass.engine.GameEngine;

/**
 * Created by Hugh on 24/02/2017.
 */

public class GameFragment extends Fragment {
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState){
            super.onViewCreated(view, savedInstanceState);
            mGameEngine=new GameEngine(getActivity());
            mGameEngine.addGameObject(
                    new ScoreGameObject(view, R.id.txt_score));
            view.findViewById(R.id.btn_start_stop).setOnClickListener(this);
            view.findViewById(R.id.btn_play_pause).setOnClickListener(this);

        }
    }


}
