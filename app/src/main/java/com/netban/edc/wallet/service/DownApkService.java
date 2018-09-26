package com.netban.edc.wallet.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import com.netban.edc.wallet.receiver.DownloadFinishReceiver;
import com.netban.edc.wallet.utils.FileUtils;

import java.io.File;

import static com.netban.edc.wallet.base.Constant.Uri.URL_APK;

public class DownApkService extends Service {

    private DownloadManager download;
    private long downloadId;
    private DownloadFinishReceiver mReceiver;

    public DownApkService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        download = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        mReceiver = new DownloadFinishReceiver();
        registerReceiver(mReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        FileUtils.deleteFile(FileUtils.getApkPath(this));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL_APK));
        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Gtoken.apk");
        downloadId = download.enqueue(request);
        return super.onStartCommand(intent, flags, startId);
    }
}
