package com.oneonetwoone.yass;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.oneonetwoone.yass.engine.GameObject;

import java.util.ArrayList;
import java.util.List;


public class StandardGameView extends View implements GameView{
    private List<List<GameObject>> mLayers;
    private List<GameObject> mGameObjects;


    public StandardGameView(Context context){
        super(context);
    }

    public StandardGameView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public StandardGameView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        synchronized (mLayers){
            int numLayers= mLayers.size();
            for (int i=0; i < numLayers; i++){
                List<GameObject> currentLayer = mLayers.get(i);
                int numObjects = currentLayer.size();
                for(int j=0; j<numObjects; j++){
                    currentLayer.get(j).onDraw(canvas);
                }
            }
        }
    }
    @Override
    public void draw(){
        postInvalidate();
    }

    @Override
    public void setGameObjects(List<GameObject> gameObjects, List<List<GameObject>> layers){
        mGameObjects=gameObjects;
        mLayers=layers;
    }
}
