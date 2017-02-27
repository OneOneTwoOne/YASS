package com.oneonetwoone.yass;

import android.view.View;
import android.widget.TextView;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;

public class ScoreGameObject extends GameObject {
    private final TextView mText;
    private long mTotalMilis;

    public ScoreGameObject(View view, int viewResId){
        mText = (TextView) view.findViewById(viewResId);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine)
    {
     mTotalMilis += elapsedMillis;
    }

    @Override
    public void startGame(){
        mTotalMilis = 0;
    }

    @Override
    public void onDraw(){
        mText.setText(String.valueOf(mTotalMilis));
    }
}
