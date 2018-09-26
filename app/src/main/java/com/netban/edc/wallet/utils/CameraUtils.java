package com.netban.edc.wallet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by Evan on 2018/7/3.
 */

public class CameraUtils {
    public static void startCamera(Activity activity, int requestCode, String filePath){

        Intent mOpenCameraIntent = new Intent();
        mOpenCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri desUri = getFileUri(activity,filePath);

        mOpenCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,desUri);
        activity.startActivityForResult(mOpenCameraIntent,requestCode);

    }

    private static Uri getFileUri(Context context, String filePath){
        Uri mUri = null;
        File mFile = new File(filePath);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            mUri = FileProvider.getUriForFile(context,"com.netban.edc.picture_camera",mFile);
        }else{
            mUri = Uri.fromFile(mFile);
        }
        return mUri;
    }

    public static void startGallery(Activity activity,int requestCode){
        Intent mOpenGalleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mOpenGalleryIntent.setType("image/*");
        activity.startActivityForResult(mOpenGalleryIntent,requestCode);
    }
}
