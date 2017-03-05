package com.oneonetwoone.yass.counter;

import android.view.View;

import com.oneonetwoone.yass.BasicInputController;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.oneonetwoone.yass.Player;
import com.oneonetwoone.yass.R;
import com.oneonetwoone.yass.YassActivity;
import com.oneonetwoone.yass.YassBaseFragment;
import com.oneonetwoone.yass.engine.GameEngine;

public class GameFragment extends YassBaseFragment implements View.OnClickListener{
    private GameEngine mGameEngine;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        return inflater.inflate(R.layout.fragment_yass_main,null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_play_pause).setOnClickListener(this);
        final ViewTreeObserver obs = view.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new
            ViewTreeObserver.OnGlobalLayoutListener(){
                @Override
                public void onGlobalLayout () {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        obs.removeGlobalOnLayoutListener(this);
                    } else {
                        obs.removeOnGlobalLayoutListener(this);
                    }
                    mGameEngine = new GameEngine(getActivity());
                    mGameEngine.setInputController(new BasicInputController(getView()));
                    mGameEngine.addGameObject(new Player(getView()));
                    mGameEngine.startGame();
                }
        });
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_play_pause) {
            pauseGameAndShowPauseDialog();
        }
    }

    private void pauseGameAndShowPauseDialog() {
        mGameEngine.pauseGame();
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.pause_dialog_title)
                .setMessage(R.string.pause_dialog_message)
                .setPositiveButton(R.string.resume,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mGameEngine.resumeGame();
                            }
                        })
                .setNegativeButton(R.string.stop,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mGameEngine.stopGame();
                                ((YassActivity)getActivity()).navigateBack();
                            }
                        })
                .create()
                .show();
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
