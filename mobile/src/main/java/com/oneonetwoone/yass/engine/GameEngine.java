package com.oneonetwoone.yass.engine;

import android.app.Activity;
import android.content.Context;

import com.oneonetwoone.yass.GameView;
import com.oneonetwoone.yass.InputControllers.InputController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameEngine {
    private List<List<GameObject>> mLayers = new ArrayList<List<GameObject>>();
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
    public Random mRandom= new Random();


    public GameEngine(Activity activity, GameView gameView, int numLayers){
        mActivity=activity;
        mGameView = gameView;
        mGameView.setGameObjects(mGameObjects, mLayers);

        mWidth= gameView.getWidth()
                -gameView.getPaddingRight()-gameView.getPaddingRight();
        mHeight=gameView.getHeight()
                -gameView.getPaddingTop()-gameView.getPaddingBottom();

        mPixelFactor=mHeight/400d;

        for (int i=0; i<numLayers; i++){
            mLayers.add(new ArrayList<GameObject>());
        }
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



    public void removeGameObject(final GameObject gameObject){
        mObjectsToRemove.add(gameObject);
        mActivity.runOnUiThread(gameObject.mOnRemovedRunnable);
    }

    public void onUpdate (long elapsedMillis){
        mInputController.onPreUpdate();
        int numObjects = mGameObjects.size();
        for(int i=0; i<numObjects; i++){
            mGameObjects.get(i).onUpdate(elapsedMillis, this);
        }
        synchronized (mLayers){
            while(!mObjectsToRemove.isEmpty()){
                GameObject objectToRemove= mObjectsToRemove.remove(0);
                mGameObjects.remove(objectToRemove);
                mLayers.get(objectToRemove.mLayer).remove(objectToRemove);
            }
            while(!mObjectsToAdd.isEmpty()){
                GameObject objectToAdd=mObjectsToAdd.remove(0);
                addToLayerNow(objectToAdd);
            }
        }
    }

    public void addGameObject(final GameObject gameObject, int layer){
        gameObject.mLayer = layer;
        if (isRunning()){
            mObjectsToAdd.add(gameObject);
        }
        else{
            addToLayerNow(gameObject);
        }
        mActivity.runOnUiThread(gameObject.mOnAddedRunnable);
    }

    private void addToLayerNow(GameObject object){
        int layer= object.mLayer;
        while(mLayers.size()<= layer){
            mLayers.add(new ArrayList<GameObject>());
        }
        mLayers.get(layer).add(object);
        mGameObjects.add(object);
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






