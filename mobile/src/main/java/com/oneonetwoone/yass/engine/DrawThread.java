package com.oneonetwoone.yass.engine;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DrawThread extends Thread {
    private static int EXPECTED_FPS=50;
    private static final long TIME_BETWEEN_DRAWS=1000/EXPECTED_FPS;
    private GameEngine mGameEngine;
    public boolean mGameIsRunning, mPauseGame;
    Lock mLock=new ReentrantLock();

    DrawThread(GameEngine gameEngine){
        mGameEngine=gameEngine;
    }

    @Override
    public void run() {
        long previousTimeMillis=System.currentTimeMillis();
        long currentTimeMillis;
        long elapsedMillis;

        while (mGameIsRunning) {
            currentTimeMillis = System.currentTimeMillis();
            elapsedMillis = currentTimeMillis - previousTimeMillis;
            if (mPauseGame) {
                while (mPauseGame) {
                    try {
                        synchronized (mLock) {
                            mLock.wait();
                        }
                    } catch (InterruptedException e) {

                    }
                }
                currentTimeMillis = System.currentTimeMillis();
            }
            if (elapsedMillis <TIME_BETWEEN_DRAWS){
                try{
                    Thread.sleep(TIME_BETWEEN_DRAWS-elapsedMillis);
                }catch(InterruptedException e){

                }

            }
            mGameEngine.onDraw();
            previousTimeMillis=currentTimeMillis;
        }
    }

    public void pauseGame(){
        mPauseGame=true;
    }

    public void resumeGame(){
        if(mPauseGame==true){
            mPauseGame=false;
            synchronized (mLock){
                mLock.notify();
            }
        }
    }

    public void start(){
        mGameIsRunning=true;
        mPauseGame=false;
        super.start();
    }

    public void stopGame(){
        mGameIsRunning=false;
        resumeGame();
    }

    public boolean isGameRunning(){return mGameIsRunning;}
}
