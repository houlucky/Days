package com.houxy.days.common;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.houxy.days.C;
import com.houxy.days.DaysApplication;
import com.houxy.days.base.i.UpLoadDoneListener;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.ProgressCallback;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Houxy on 2016/9/3.
 */
public class UploadPictureUtil {

    public static String cameraPath = DaysApplication.cacheDir + "/Data/cacheImage";
    public static String cropPath = DaysApplication.cacheDir + "/Data/cropImage";
    public static Uri cameraUri = Uri.fromFile(new File(cameraPath));
    public static Uri cropUri = Uri.fromFile(new File(cropPath));
    private static String imageUrl;

    public static void upLoadPicture(Context context, Uri uri, final Observer<String> observer){

        String imagePath = saveBitmapToSDCard(BitmapUtils.compressImage(uri, context));
        Log.d("TAG", imagePath);

        final BmobFile bmobFile = new BmobFile(new File(imagePath));

        bmobFile.uploadObservable(null).doOnNext(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                ToastUtils.show("上传文件成功");
            }
        }).concatMap(new Func1<Void, Observable<String>>() {
            @Override
            public Observable<String> call(Void aVoid) {
                return Observable.just(bmobFile.getFileUrl()).distinct();
            }
        }).subscribe(observer);

    }

    public static Intent getUploadIntent(int uploadType) {
        switch (uploadType) {
            case C.PICK_FROM_FILE:
                Intent intentFile = new Intent(Intent.ACTION_PICK);
                intentFile.setType("image/*");
                return intentFile;

            case C.PICK_FROM_CAMERA:
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                return intentCamera;
            default:
                return null;
        }
    }

    public static Intent cropPicture(Uri uri, int outputX, int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }

    public static String saveBitmapToSDCard(Bitmap bitmap){
        FileOutputStream out = null;

        File appDir = new File(Environment.getExternalStorageDirectory(), "days");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = TimeUtil.getNowYMDHMSTime()+ ".png";
        File file = new File(appDir, fileName);

        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.getAbsolutePath();
    }
}
