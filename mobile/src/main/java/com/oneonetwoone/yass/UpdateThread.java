package com.oneonetwoone.yass;


public class UpdateThread extends Thread {
    @Override
    public void run(){
        long previousTimeMillis;
        long currentTimeMillis;
        long elapsedMillis;
        previousTimeMillis=System.currentTimeMillis();

        while(mGameIsRunning){
            currentTimeMillis=System.currentTimeMillis();
            elapsedMillis=currentTimeMillis-previousTimeMillis;
            if(mPauseGame){
                while(mPauseGame){
                    try{
                        synchronized (mLock){
                            mLock.wait();
                        }
                    }catch(InterruptedException e){

                    }
                    currentTimeMillis=System.currentTimeMillis();
            }
            mGameEngine.onUpdate(elapsedMillis);
            previousTimeMillis=currentTimeMillis;
        }
    }
}
