package com.netban.edc.wallet.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.netban.edc.wallet.utils.FileUtils;
import com.netban.edc.wallet.utils.PhoneSystemManager;

import java.io.File;

public class DownloadFinishReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
        Uri uriForDownloadedFile = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).getUriForDownloadedFile(completeDownloadId);
        PhoneSystemManager.installApk(context,uriForDownloadedFile);
//        PhoneSystemManager.installApk(context, FileUtils.getApkPath(context));
    }
}
