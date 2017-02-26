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

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.btn_play_pause){
            playOrPause();
        }
        if(v.getId() == R.id.btn_start_stop){
            startOrStop();
        }
    }

    private void playOrPause(){
        Button button = (Button) getView().findViewById(R.id.btn_play_pause);
        if(mGameEngine.isPaused()){
            mGameEngine.resumeGame();
            button.setText(R.string.pause);
        }
        else{
            mGameEngine.pauseGame();
            button.setText(R.string.resume);
        }
    }

    public boolean isPaused(){
        return mUpdateThread != null && mUpdateThread.isGamePaused();
    }

    private void startOrStop(){
        Button button=(Button)getView().findViewById(R.id.btn_play_pause);
    }
    Button playPauseButton = (Button) getView().findViewById(R.id.btn_play_pause);

    if(mGameEngine.isRunning()){
        mGameEngine.stopGame();
        button.setText(R.string.start);
        playPauseButton.setEnable(true);
    }
    else{
        mGameEngine.startGame();
        button.setText(R.string.stop);
        playPauseButton.setEnabled(true);
        playPauseButton.setText(R.string.pause);
    }
}
