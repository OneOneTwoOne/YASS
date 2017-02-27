package com.oneonetwoone.yass.engine;

import java.util.Timer;
import java.util.TimerTask;

public class DrawThread {
    private static int EXPECTED_FPS=30;
    private static final long TIME_BETWEEN_DRAWS=1000/EXPECTED_FPS;
    private GameEngine mGameEngine;
    private Timer mTimer;

    public void start(){
        stopGame();
        mTimer=new Timer();
        mTimer.schedule(new TimerTask(){
            @Override
            public void run(){
                mGameEngine.onDraw();
            }
        },0,TIME_BETWEEN_DRAWS);
    }

    public void stopGame(){
        if(mTimer!=null){
            mTimer.cancel();
            mTimer.purge();
        }
    }

    public void pauseGame(){
        stopGame();
    }

    public void resumeGame(){
        start();
    }
}