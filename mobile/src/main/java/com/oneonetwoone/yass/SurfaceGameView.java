package com.oneonetwoone.yass;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.oneonetwoone.yass.engine.GameObject;

import java.util.List;



public class SurfaceGameView extends SurfaceView implements SurfaceHolder.Callback, GameView{

    private static List<GameObject> mGameObjects;
    private boolean mReady;

    public SurfaceGameView(Context c){
        super(c);
        getHolder().addCallback(this);
    }

    public SurfaceGameView(Context c, AttributeSet attrs){
        super(c, attrs);
        getHolder().addCallback(this);
    }

    public SurfaceGameView(Context c, AttributeSet attrs, int defStyleAttr){
        super(c, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        mReady=true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        mReady=false;
    }

    @Override
    public void setGameObjects(List<GameObject> gameObjects){
        mGameObjects=gameObjects;
    }

    @Override
    public void draw(){
        if(!mReady){
            return;
        }
        Canvas canvas = getHolder().lockCanvas();
        if(canvas == null){
            return;
        }
        canvas.drawRGB(0,0,0);
        synchronized (mGameObjects){
            int numObjects= mGameObjects.size();
            for(int i=0; i <numObjects; i++){
               mGameObjects.get(i).onDraw(canvas);
            }
        }
        getHolder().unlockCanvasAndPost(canvas);
    }

}
