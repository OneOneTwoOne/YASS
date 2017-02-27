package com.oneonetwoone.yass.engine;

import android.graphics.Canvas;

import java.util.Timer;
import java.util.TimerTask;

public class DrawThread {
    private static int EXPECTED_FPS=30;
    private static final long TIME_BETWEEN_DRAWS=1000/EXPECTED_FPS;
    private GameEngine mGameEngine;
    private Timer mTimer;

    DrawThread(GameEngine gameEngine){mGameEngine=gameEngine;}
    public void start(){
        stopGame();
        mTimer=new Timer();
        mTimer.schedule(new TimerTask(){
            @Override
            public void run(){
                mGameEngine.onDraw(new Canvas());
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