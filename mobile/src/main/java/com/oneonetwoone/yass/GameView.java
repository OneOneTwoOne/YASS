package com.oneonetwoone.yass;

import android.content.Context;

import com.oneonetwoone.yass.engine.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugh on 10/03/2017.
 */

public interface GameView {
    void draw();
    void setGameObjects(ArrayList<GameObject> gameObjects);
    //Generic methods from View
    int getWidth();
    int getHeight();
    int getPaddingLeft();
    int getPaddingRight();
    int getPaddingTop();
    int getPaddingBottom();
    Context getContext();
}
