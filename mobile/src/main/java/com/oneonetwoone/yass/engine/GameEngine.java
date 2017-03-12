package com.oneonetwoone.yass.engine;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.oneonetwoone.yass.GameView;
import com.oneonetwoone.yass.InputControllers.InputController;

import java.util.ArrayList;
import java.util.List;


public class GameEngine {
    public List<GameObject> mGameObjects= new ArrayList<>();
    private List<GameObject> mObjectsToAdd= new ArrayList<>();
    private List<GameObject> mObjectsToRemove= new ArrayList<>();
    private UpdateThread mUpdateThread;
    private DrawThread mDrawThread;
    private Activity mActivity;
    public InputController mInputController;
    public GameView mGameView;
    public double mPixelFactor;
    public int mHeight,mWidth;


    public GameEngine(Activity activity, GameView gameView){
        mActivity=activity;
        mGameView = gameView;
        mGameView.setGameObjects(mGameObjects);

        mWidth= gameView.getWidth()
                -gameView.getPaddingRight()-gameView.getPaddingRight();
        mHeight=gameView.getHeight()
                -gameView.getPaddingTop()-gameView.getPaddingBottom();

        mPixelFactor=mHeight/400d;
    }


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
        if(mInputController!=null){
            mInputController.onStart();
        }
    }

    public void stopGame(){
        if (mUpdateThread != null){
            mUpdateThread.stopGame();
        }
        if(mDrawThread != null){
            mDrawThread.stopGame();
        }
        if(mInputController!=null){
            mInputController.onStop();
        }
    }

    public void pauseGame(){
        if (mUpdateThread != null){
            mUpdateThread.pauseGame();
        }
        if(mDrawThread != null){
            mDrawThread.pauseGame();
        }
        if(mInputController!=null){
            mInputController.onPause();
        }
    }

    public void resumeGame(){
        if (mUpdateThread != null){
            mUpdateThread.resumeGame();
        }
        if(mDrawThread != null){
            mDrawThread.resumeGame();
        }
        if(mInputController!=null){
            mInputController.onResume();
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
        mInputController.onPreUpdate();
        int numGameObjects = mGameObjects.size();
        for(int i=0; i<numGameObjects; i++){
            mGameObjects.get(i).onUpdate(elapsedMillis,this);
        }
        synchronized(mGameObjects){
            while(!mObjectsToRemove.isEmpty()){
                mGameObjects.remove(mObjectsToRemove.remove(0));
            }
            while(!mObjectsToAdd.isEmpty()){
                mGameObjects.add(mObjectsToAdd.remove(0));
            }
        }
    }


    public void onDraw() {mGameView.draw();}

    public boolean isRunning(){
    return mUpdateThread != null&& mUpdateThread.isGameRunning();
    }

    public void setInputController(InputController controller){
        mInputController=controller;
    }

    public Context getContext(){
        return mActivity.getApplicationContext();
    }
}






