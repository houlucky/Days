package com.houxy.days.common;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;

import com.houxy.days.C;
import com.houxy.days.DaysApplication;

import java.io.File;

/**
 * Created by Houxy on 2016/9/3.
 */
public class UploadPictureUtil {

    public static String cameraPath = DaysApplication.cacheDir + "/Data/cacheImage";
    public static String cropPath = DaysApplication.cacheDir + "/Data/cropImage";
    public static Uri cameraUri = Uri.fromFile(new File(cameraPath));
    public static Uri cropUri = Uri.fromFile(new File(cropPath));

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

    public static void showSelectPicDialog(final Activity activity){
        new AlertDialog.Builder(activity).setItems(new String[]{"相册", "照相机", "取消"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        activity.startActivityForResult(UploadPictureUtil.getUploadIntent(C.PICK_FROM_FILE), C.PICK_FROM_FILE);
                        break;
                    case 1:
                        activity.startActivityForResult(UploadPictureUtil.getUploadIntent(C.PICK_FROM_CAMERA), C.PICK_FROM_CAMERA);
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        }).create().show();
    }
}
