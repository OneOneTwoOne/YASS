package com.oneonetwoone.yass.engine;

import android.app.Activity;
import android.graphics.Canvas;


import java.util.ArrayList;
import java.util.List;


public class GameEngine {

    private List<com.oneonetwoone.yass.engine.GameObject> mGameObjects= new ArrayList<>();

    private UpdateThread mUpdateThread;
    private DrawThread mDrawThread;


    public void startGame(){
        stopGame();//Stops game if already running

        int numGameObjects = mGameObjects.size();
        for(int i=0; i<numGameObjects; i++){
            mGameObjects.get(i).startGame();
        }

        //starts update thread
        mUpdateThread = new UpdateThread(this);
        mUpdateThread.start();

        //starts drawThread
        mDrawThread = new DrawThread(this);
        mDrawThread.start();
    }

    public void stopGame(){
        if (mUpdateThread != null){
            mUpdateThread.stopGame();
        }
        if(mDrawThread != null){
            mDrawThread.stopGame();
        }
    }

    public void addGameObject(final com.oneonetwoone.yass.engine.GameObject gameObject){
        if(isRunning()){
            mObjectsToAdd.add(gameObject);
        }
        else{
            mGameObjects.add(gameObject);
        }
        mActivity.runOnUiThread(gameObject.mOnAddedRunnable);
    }

    public void removeGameObject(final GameObject gameObject){
        mObjectsToRemove.add(gameObject);
        mActivity.runOnUiThread(gameObject.mOnRemovedRunnable);
    }

    public void onUpdate (long elaspsedMillis){
        int numGameObjects = mGameObjects.size();
        for(int i=0; i<numGameObjects; i++){
            mGameObjects.get(i).onUpdate(elapsedMillis,this);
        }
        synchronized(mGameObjects){
            while(!mObjectsToRemove.isEmpty()){
                mGameObjects.remove(mObjectsToRemove.remove(0));
            }
            while(!mObjectsToRemove.isEmpty()){
                mGameObjects.add(mObjectsToadd.remove(0));
            }
        }
    }

    private Runnable mDrawRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (mGameObjects) {
                int numGameObjects = mGameObjects.size();
                for (int i = 0; i < numGameObjects; i++) {
                    mGameObjects.get(i).onDraw();
                }
            }
        }
    };

    public void onDraw(Canvas canvas){
        mActivity.runOnUiThread(mDrawRunnable);
    }

    public boolean isRunning(){
    return mUpdateThread != null&& mUpdateThread.isGameRunning();
    }
}






