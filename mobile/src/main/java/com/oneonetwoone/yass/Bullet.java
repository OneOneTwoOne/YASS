package com.oneonetwoone.yass;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.oneonetwoone.yass.engine.GameObject;



public class Bullet extends GameObject {

    public Bullet(View view, double pixelFactor){
        Context c = view.getContext();

        mSpeedFactor= pixelFactor*-300d/1000d;

        mImageView= new ImageView(c);
        Drawable bulletDrawable=c.getResources().getDrawable(R.drawable.bullet);

        mImageHeight= bulletDrawable.getIntrinsicHeight()*pixelFactor;
        mImageWidth= bulletDrawable.getIntrinsicWidth()*pixelFactor;

        mImageView.setLayoutParams(new ViewGroup.LayoutParams((int) (mImageWidth),(int) (imageHeight)));
        mImageView.setImageDrawable(bulletDrawable);
        mImageView.setVisibility(View.GONE);
        ((FrameLayout) view).addView(mImageView);
    }

    public init(){}


}
