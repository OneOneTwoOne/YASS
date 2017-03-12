package com.oneonetwoone.yass;

import android.graphics.Canvas;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;
import com.oneonetwoone.yass.objects.Asteroid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Home on 12/03/2017.
 */

public class GameController extends GameObject {
    private long mCurrentMillis;
    private long mEnemiesSpawned;
    private long TIME_BETWEEN_ENEMIES=2000;
    private List<Asteroid> mAsteroidPool= new ArrayList<>();

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        mCurrentMillis += elapsedMillis;
        long waveTimestamp = mEnemiesSpawned*TIME_BETWEEN_ENEMIES;
        if (mCurrentMillis > waveTimestamp){
            //spawn enemy
            Asteroid a = mAsteroidPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a);
            mEnemiesSpawned++;
        }
    }

    public void returnToPool(){}

    public void startGame(){}
    public void onDraw(Canvas canvas){}
}
