package com.netban.edc.wallet.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.base.Constant;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.utils.BitmapUtils;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.http.UrlConverter;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Created by Evan on 2018/7/3.
 */

public class UploadManagerUtils {
    private static UploadManagerUtils instance;
    private UploadManager uploadManager;

    private UploadManagerUtils() {
        init();
    }

    private void init() {
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .urlConverter(new UrlConverter() {
                    @Override
                    public String convert(String url) {
                        return url + "/user/avatar/";
                    }
                })
                .build();
// 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    public static UploadManagerUtils getInstance() {
        if (instance == null) {
            synchronized (UploadManagerUtils.class) {
                if (instance == null) {
                    instance = new UploadManagerUtils();
                }
            }
        }
        return instance;
    }

    public String uploadAvatar(Context context, String token, File file,String numbers) {
        String key = "user/avatar/" + numbers + "/" + file.getName();
        try {
            ResponseInfo responseInfo = uploadManager.syncPut(file, key, token, null);
            if (responseInfo.isOK()) {
                return Constant.Uri.PATH_QINIU + key + "?imageView2/1/w/200/h/200";
            } else {
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }

}
