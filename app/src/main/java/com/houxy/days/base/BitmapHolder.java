package com.houxy.days.base;

import android.graphics.Bitmap;

/**
 * Created by Houxy on 2016/10/5.
 */

public class BitmapHolder {

    private Bitmap bitmap;
    private int position;


    public BitmapHolder(Bitmap bitmap, int position){
        this.bitmap = bitmap;
        this.position = position;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
