package com.oneonetwoone.yass.counter;

import android.app.AlertDialog;
import android.hardware.input.InputManager.InputDeviceListener;
import android.view.View;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.oneonetwoone.yass.GameController;
import com.oneonetwoone.yass.objects.FPSCounter;
import com.oneonetwoone.yass.GameView;
import com.oneonetwoone.yass.InputControllers.CompositeInputController;
import com.oneonetwoone.yass.objects.ParallaxBackground;
import com.oneonetwoone.yass.objects.Player;
import com.oneonetwoone.yass.R;
import com.oneonetwoone.yass.YassActivity;
import com.oneonetwoone.yass.YassBaseFragment;
import com.oneonetwoone.yass.engine.GameEngine;

public class GameFragment extends YassBaseFragment implements View.OnClickListener, InputDeviceListener {
    private GameEngine mGameEngine;
    private int SCROLLSPEED = 28;

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
                    GameView gameView=(GameView) getView().findViewById(R.id.gameView);
                    mGameEngine = new GameEngine(getActivity(), gameView,4);
                    mGameEngine.setInputController(new CompositeInputController(getView(), (YassActivity) getActivity()));
                    mGameEngine.addGameObject(new ParallaxBackground(mGameEngine,20,R.drawable.space),0);
                    mGameEngine.addGameObject(new ParallaxBackground(mGameEngine,SCROLLSPEED,R.drawable.space2),0);
                    mGameEngine.addGameObject(new Player(mGameEngine),3);
                    mGameEngine.addGameObject(new FPSCounter(mGameEngine),4);
                    mGameEngine.addGameObject(new GameController(mGameEngine),1);
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
                                ((YassActivity) getActivity()).navigateBack();
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
    @Override
    public void onInputDeviceChanged(int deviceId) {

    }
    @Override
    public void onInputDeviceAdded(int deviceId) {

    }
    @Override
    public void onInputDeviceRemoved(int deviceId) {
        if (!mGameEngine.isRunning()) {
            pauseGameAndShowPauseDialog();
        }
    }
}
