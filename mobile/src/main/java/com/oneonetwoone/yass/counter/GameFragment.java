package com.oneonetwoone.yass.counter;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.oneonetwoone.yass.R;
import com.oneonetwoone.yass.ScoreGameObject;
import com.oneonetwoone.yass.YassBaseFragment;
import com.oneonetwoone.yass.engine.GameEngine;

public class GameFragment extends YassBaseFragment {
    private GameEngine mGameEngine;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
            super.onViewCreated(view, savedInstanceState);
            mGameEngine=new GameEngine(getActivity());
            mGameEngine.addGameObject(
                    new ScoreGameObject(view, R.id.txt_score));
            view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
            mGameEngine.startGame();
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.btn_play_pause){
            pauseGameAndShowPauseDialog();
        }
    }

    private void pauseGameAndShowPauseDialog(){
        mGameEngine.pauseGame();
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                        mGameEngine.resumeGame();
                    }
                })
                .setNegativeButton(R.string.stop,
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                dialog.dismiss();
                                mGameEngine.stopGame();
                                ((MainActivity)getActivity()).navigateBack();
                            }
                        })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mGameEngine.resumeGame();
                    }
                })
                .create()
                .show();
    }

    public void navigateBack(){
        super.onBackPressed();
    }

    @Override
    public boolean onBackPressed(){
        if(mGameEngine.isRunning()){
            pauseGameAndShowPauseDialog();
            return true;
        }
        return false;
    }
}
