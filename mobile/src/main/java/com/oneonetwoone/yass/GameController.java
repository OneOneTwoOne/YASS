package com.oneonetwoone.yass;

import android.graphics.Canvas;

import com.oneonetwoone.yass.engine.GameEngine;
import com.oneonetwoone.yass.engine.GameObject;
import com.oneonetwoone.yass.objects.Asteroid;

import java.util.ArrayList;
import java.util.List;

public class GameController extends GameObject {
    private long mCurrentMillis;
    private long mEnemiesSpawned;
    private long TIME_BETWEEN_ENEMIES=500;
    private int INIT_ASTERIOD_POOL_SIZE=12;
    private List<Asteroid> mAsteroidPool= new ArrayList<>();
    private GameEngine mGameEngine;

    public GameController(GameEngine gameEngine){
        mGameEngine=gameEngine;

        for(int i=0;i<INIT_ASTERIOD_POOL_SIZE;i++){
            mAsteroidPool.add(new Asteroid(this,mGameEngine));
        }
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine){
        mCurrentMillis += elapsedMillis;
        long waveTimestamp = mEnemiesSpawned*TIME_BETWEEN_ENEMIES;
        if (mCurrentMillis > waveTimestamp){
            //spawn enemy
            Asteroid a = mAsteroidPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a,2);
            mEnemiesSpawned++;
        }
    }

    public void returnToPool(Asteroid asteroid){
        mAsteroidPool.add(asteroid);
    }

    public void startGame(){}
    public void onDraw(Canvas canvas){}
}
