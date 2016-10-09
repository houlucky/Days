package com.houxy.days.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import com.houxy.days.base.BitmapHolder;
import com.houxy.days.base.StringHolder;
import com.houxy.days.common.ACache;
import com.houxy.days.common.Utils;
import com.orhanobut.logger.Logger;
import com.zzhoujay.richtext.ImageHolder;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Houxy on 2016/9/5.
 */
public class BitmapUtils {
    private static Pattern IMAGE_TAG_PATTERN = Pattern.compile("<img(.*?)>");
    private static int CONTENT_WIDTH = 660;

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

    public static Bitmap compressImage(Bitmap bitmap, float insertedImgWidth){

        int imgWidth = bitmap.getWidth();
        int imgHeight = bitmap.getHeight();
        // 只对大尺寸图片进行下面的压缩，小尺寸图片使用原图

        if (imgWidth >= insertedImgWidth) {
            float scale =  insertedImgWidth / imgWidth;
            Matrix mx = new Matrix();
            mx.setScale(scale, scale);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, imgWidth, imgHeight, mx, true);
        }
        return bitmap;
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

    public static Bitmap textAsBitmap(String text, float textSize) {

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);

        StaticLayout layout = new StaticLayout(text, textPaint, CONTENT_WIDTH,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        Bitmap bitmap = Bitmap.createBitmap(CONTENT_WIDTH, layout.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        layout.draw(canvas);
        return bitmap;
    }

    public static Bitmap richTextAsBitmap(String text){

        ArrayList<StringHolder> imageHolders = new ArrayList<>();
        //遍历查找
        Matcher imageMatcher;
        imageMatcher = IMAGE_TAG_PATTERN.matcher(text);
        while (imageMatcher.find()) {
            String image = imageMatcher.group().trim();
            int matchStringStartIndex = text.indexOf(image);
            int matchStringEndIndex = matchStringStartIndex + image.length();
            StringHolder imageHolder = new StringHolder(image, matchStringStartIndex, matchStringEndIndex);
            imageHolders.add(imageHolder);
        }

        ArrayList<BitmapHolder> bitmapHolders = new ArrayList<>();
        for (StringHolder imageHolder: imageHolders) {
            Bitmap bitmap = ACache.getDefault().getAsBitmap(imageHolder.getString());
            if(bitmap.getWidth() > CONTENT_WIDTH){
                bitmap = Bitmap.createScaledBitmap(bitmap, CONTENT_WIDTH, (int)(bitmap.getHeight()*1f/bitmap.getWidth()*CONTENT_WIDTH), false);
            }
            int pos = imageHolder.getStartIndex();
            bitmapHolders.add(new BitmapHolder(bitmap, pos));
        }

        Log.d("TAG", " size : " + bitmapHolders.size());
        String strings[] = text.split("<img(.*?)>");
        int w=0;
        for (String s: strings){
            if(!s.equals("")){
                Bitmap bitmap = textAsBitmap(s, 25);
                int pos = text.indexOf(s);
                bitmapHolders.add(new BitmapHolder(bitmap, pos));
                Log.d("TAG", w + "|"+s + "|");
            }
            w++;
        }
        Log.d("TAG", " size : " + bitmapHolders.size());


        for(int i=0; i<bitmapHolders.size(); i++){
            for (int j=i+1; j<bitmapHolders.size(); j++){
                if( bitmapHolders.get(i).getPosition() > bitmapHolders.get(j).getPosition()){
                    Collections.swap(bitmapHolders, i, j);
                }
            }
//            Log.d("TAG", "pos : " + bitmapHolders.get(i).getPosition());
        }
        Bitmap bitmap = bitmapHolders.get(0).getBitmap();
        for(int i=1; i<bitmapHolders.size(); i++){
            bitmap = mergeBitmap_TB(bitmap, bitmapHolders.get(i).getBitmap());
        }
        int margin = 30;
        Bitmap destBitmap = Bitmap.createBitmap(bitmap.getWidth() + margin + margin, bitmap.getHeight() + margin + margin, bitmap.getConfig());
        Canvas canvas = new Canvas(destBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, margin, margin, null );
        Log.d("TAG", "final : " + " h: " + bitmap.getHeight() + " w: " + bitmap.getWidth());

        return destBitmap;
    }

    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     * @param topBitmap x
     * @param bottomBitmap x
     */
    private static Bitmap mergeBitmap_TB(Bitmap topBitmap, Bitmap bottomBitmap) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {
            return null;
        }

        int height = topBitmap.getHeight() + bottomBitmap.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(CONTENT_WIDTH, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(topBitmap, 0, 0, null);
        canvas.translate(0, topBitmap.getHeight());
        canvas.drawBitmap(bottomBitmap, 0, 0, null);
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        canvas.drawText("123123123", 0, 0, paint);
//        Log.d("TAG", "height : " + height + " topHeight : " + topBitmap.getHeight() + " bottomH : " +  bottomBitmap.getHeight());
        return bitmap;
    }

}
