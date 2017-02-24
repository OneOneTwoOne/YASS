package com.oneonetwoone.yass.engine;


public class UpdateThread extends Thread {
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
        stuper.start();
    }

    public void stopGame(){
        mGameIsRunning=false;
        resumeGame();
    }

}
