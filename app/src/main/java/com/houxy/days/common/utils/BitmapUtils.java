package com.houxy.days.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;

/**
 * Created by Houxy on 2016/9/5.
 */
public class BitmapUtils {


    public static Bitmap decodeUriAsBitmap(Uri uri, Context context) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    public static Bitmap compressImage(Uri uri, Context context, float insertedImgWidth) {

        Bitmap pic = null;
        try{
            pic =  BitmapUtils.decodeUriAsBitmap(uri, context);
        }catch (Exception e){
            Log.e("TAG", e.toString());
        }
        int imgWidth = pic.getWidth();
        int imgHeight = pic.getHeight();
        // 只对大尺寸图片进行下面的压缩，小尺寸图片使用原图

        if (imgWidth >= insertedImgWidth) {
            float scale =  insertedImgWidth / imgWidth;
            Matrix mx = new Matrix();
            mx.setScale(scale, scale);
            pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true);
        }
        return pic;
    }


    public static Bitmap compressImage(Uri uri,Context context) {
        Bitmap pic = null;
        try{
            pic =  BitmapUtils.decodeUriAsBitmap(uri, context);
        }catch (Exception e){
            Log.e("TAG", e.toString());
        }
        if( null != pic){
            int imgWidth = pic.getWidth();
            int imgHeight = pic.getHeight();
            // 只对大尺寸图片进行下面的压缩，小尺寸图片使用原图
            if (imgWidth >= 360f) {
                float scale =  360f / imgWidth;
                Matrix mx = new Matrix();
                mx.setScale(scale, scale);
                pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true);
            }

            return pic;
        }
        return null;
    }
}
