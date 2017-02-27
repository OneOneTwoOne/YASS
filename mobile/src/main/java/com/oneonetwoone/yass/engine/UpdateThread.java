package com.oneonetwoone.yass.engine;


import java.util.concurrent.locks.Lock;

public class UpdateThread extends Thread {
    boolean mPauseGame, mGameIsRunning;
    Lock mLock;
    GameEngine mGameEngine;
    @Override
    public void run() {
        long previousTimeMillis;
        long currentTimeMillis;
        long elapsedMillis;
        previousTimeMillis = System.currentTimeMillis();

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
            mGameEngine.onUpdate(elapsedMillis);
            previousTimeMillis = currentTimeMillis;
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
