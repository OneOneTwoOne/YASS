package com.oneonetwoone.yass.engine;


import android.graphics.Canvas;

public abstract class GameObject {
    public int mLayer;
    public void startGame(){}
    public abstract void onUpdate(long elapsedMillis,
                                  GameEngine gameEngine);
    public abstract void onDraw(Canvas canvas);
    public final Runnable mOnAddedRunnable = new Runnable() {
        @Override
        public void run() {
            onAddedToGameUiThread();
        }
    };
    public final Runnable mOnRemovedRunnable = new Runnable() {
        @Override
        public void run() {
            onRemovedFromGameUiThread();
        }
    };
    public void onRemovedFromGameUiThread(){
    }
    public void onAddedToGameUiThread(){
    }
}