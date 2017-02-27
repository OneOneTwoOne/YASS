package com.oneonetwoone.yass.engine;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;


import java.util.ArrayList;
import java.util.List;


public class GameEngine {

    private List<GameObject> mGameObjects= new ArrayList<>();
    private List<GameObject> mObjectsToAdd= new ArrayList<>();
    private List<GameObject> mObjectsToRemove= new ArrayList<>();
    private UpdateThread mUpdateThread;
    private DrawThread mDrawThread;
    private Activity mActivity;


    public GameEngine(Activity activity){
        mActivity=activity;
    };
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

    public void pauseGame(){
        if (mUpdateThread != null){
            mUpdateThread.pauseGame();
        }
        if(mDrawThread != null){
            mDrawThread.pauseGame();
        }
    }

    public void resumeGame(){
        if (mUpdateThread != null){
            mUpdateThread.resumeGame();
        }
        if(mDrawThread != null){
            mDrawThread.resumeGame();
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

    public void onUpdate (long elapsedMillis){
        int numGameObjects = mGameObjects.size();
        for(int i=0; i<numGameObjects; i++){
            mGameObjects.get(i).onUpdate(elapsedMillis,this);
        }
        synchronized(mGameObjects){
            while(!mObjectsToRemove.isEmpty()){
                mGameObjects.remove(mObjectsToRemove.remove(0));
            }
            while(!mObjectsToRemove.isEmpty()){
                mGameObjects.add(mObjectsToAdd.remove(0));
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






