package com.netban.edc.wallet.utils;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;

import static android.os.Environment.DIRECTORY_DOCUMENTS;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static java.nio.charset.Charset.forName;

/**
 * Created by Evan on 2018/8/14.
 */

public class FileUtils {

    private static int max=1000;

    public static   String getRealPathFromURI(Context context, Uri contentUri) {
        String res = null;
        URI uri = URI.create(contentUri.getPath());
        String parent = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getParent();
        String[] split = contentUri.getPath().split(":");
        String path=parent+"/"+split[split.length-1];
        return  path;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public  static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }




    /** 获取文件
     * @param path 路径
     * @return
     */
    public static File getFile(String path){
        if (path == null || path.trim().equals(""))
            return null;
        File f = new File(path);
        return f;
    }

    /** 删除文件
     * @param path 文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(String path) {
        File f = getFile(path);
        if (!f.exists())
            return true;
        return f.delete();
    }

    /** 复制文件
     * @param fromFile 原文件
     * @param toFile 目标文件
     * @param rewrite 是否覆盖
     * @param isDeleteFromFile 复制完成是否删除原文件
     * @return 是否复制成功
     */
    public static boolean copyFile(File fromFile, File toFile, Boolean rewrite,
                                   Boolean isDeleteFromFile) {
        if (!fromFile.exists() || !fromFile.isFile() || !fromFile.canRead()) {
            return false;
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }
        FileInputStream from = null;
        FileOutputStream to = null;
        boolean isSuccess = true;
        try {
            from = new FileInputStream(fromFile);
            to = new FileOutputStream(toFile);
            byte[] content = new byte[1024];
            int reads = 0;
            while ((reads = from.read(content)) > 0) {
                to.write(content, 0, reads);
            }
            to.flush();
        } catch (Exception ex) {
            isSuccess = false;
        } finally {
            if (from != null) {
                try {
                    from.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (to != null) {
                try {
                    to.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isDeleteFromFile && isSuccess) {
                fromFile.delete();
            }
        }
        return isSuccess;
    }
    /**
     * 获取文件夹大小
     * @param file 文件夹
     * @return 文件夹大小，单位字节
     */
    public static long getFileSize(File file) {
        long size = 0;
        File flist[] = file.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }
    /**
     * 获取文件大小
     * @param path 文件
     * @return 文件大小，单位字节
     */
    public static long getFileSize(String path) {
        long size = 0;
        File file = getFile(path);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            size = fis.available();
        } catch (Exception e) {
            Log.e("e","获取文件大小失败");
            e.printStackTrace();
        }
        return size;
    }

    /**
     *  打开文件
     * @param context 上下文对象
     * @param path 路径
     */
    public static void openFile(Context context, String path) {
        try {
            if (TextUtils.isEmpty(path)) {
                return;
            }
            File file = new File(path);
            if (!file.exists()) {
                return;
            }
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // 设置intent的Action属性
            intent.setAction(Intent.ACTION_VIEW);
            // 获取文件file的MIME类型
            String type = getMIMEType(file);
            // 设置intent的data和Type属性。
            intent.setDataAndType(getFileUri(context,path), type);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "文件无法打开,请下载相关软件！", Toast.LENGTH_SHORT).show();
        }
    }
    private static String getMIMEType(File file) {
        String type = "*/*";
        if (file == null)
            return type;
        if (TextUtils.isEmpty(file.getName())) {
            return type;
        }
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        String ext = fName.substring(dotIndex, fName.length()).toLowerCase();
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIMEMapTable.length; i++) {
            if (ext.equals(MIMEMapTable[i][0]))
                type = MIMEMapTable[i][1];
        }
        return type;
    }
    /**
     * 使用文件管理器选择文件时获取文件路径
     * 注意：有的文件管理器返回的是content://协议，还有的返回的是file://协议
     * */
    public static String getFilePath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            //equalsIgnoreCase()方法忽略大小写比较两个字符串
            //判断协议是不是content开头，有的话就用ContentResolver去query查询文件真实位置
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {

            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
   public static String readKeystore(String path){
       File file = new File(path);
       if (!file.canRead()) {
           return null;
       }
        FileInputStream inputStream=null;
       try {
           inputStream=new FileInputStream(path);
           int length=0;
           byte[] bytes=new byte[1024];
           StringBuffer buffer=new StringBuffer();
           while ((length=inputStream.read(bytes))!=-1){
               buffer.append(new String(bytes,0,length));
           }
           return buffer.toString();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }finally {
           if (inputStream==null){
               try {
                   inputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
       return null;
   }
    public static String readFile(String path){
        File file = new File(path);
        if (!file.canRead()) {
           return null;
        }

        FileInputStream inputStream = null;
        InputStreamReader gbk=null;
        try {
            inputStream = new FileInputStream(path);
            gbk=new InputStreamReader(inputStream,"GBK");
            char[] tc=new char[1024];
            int tl = gbk.read(tc);

            if (StringUtils.isMessyCode(new String(tc,0,tl))){
                return null;
            }
            char[] chars=new char[1024];
            StringBuffer buffer = new StringBuffer();
            int length=0;
            buffer.append(tc);
            while ((length=gbk.read(chars))!=-1){
                buffer.append(chars,0,length);
            }
            String res = buffer.toString();
            boolean messyCode = StringUtils.isMessyCode(res);
            if (messyCode){
                return null;
            }else{
                return res;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if (inputStream!=null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (gbk!=null)
                try {
                    gbk.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private static File getAvatar(Context context) throws IOException {
        File file = new File(context.getExternalFilesDir("avatar"), System.currentTimeMillis()+".jpg");
        if (!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    public static File getPublicAvatar() throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "avatar.jpg");
        if (!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    public static File saveAvatar(Context context,Uri uri){
        Bitmap bitmap = BitmapUtils.compressAvatar(context, uri, max);
        byte[] bytes = BitmapUtils.Bitmap2Bytes(bitmap);
        FileOutputStream stream=null;
        File file=null;
        try {
            file=getAvatar(context);
            stream=new FileOutputStream(file);
            BufferedOutputStream outputStream=new BufferedOutputStream(stream);
            outputStream.write(bytes,0,bytes.length);
            outputStream.flush();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            file.delete();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            file.delete();
            return null;
        }
    }
    public static Uri getFileUri(Context context,File file){
        if (Build.VERSION.SDK_INT >= 24){
            return FileProvider.getUriForFile(context,"com.netban.edc.wallet.fileprovider",file);
        }else{
           return Uri.fromFile(file);
        }
    }

    public static Uri getFileUri(Context context,String file){
        return getFileUri(context,new File(file));
    }

    /*获取keystore路径*/
    public static String getKeystorePath(){
        File directory = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
        File file=new File(directory,"keystore");
        return file.getPath();
    }
    /*保存keystore路径*/
    public static void saveKeystore(String keystore){
        File directory = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS);
        File file=new File(directory,"keystore");
        FileWriter writer=null;
        try {
            writer=new FileWriter(file);
            writer.write(keystore);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    /**
     *
     * @return 安装包地址
     */
    public static String getApkPath(Context context){
        return getApkFile(context).getPath();
    }
    public static File getApkFile(Context context){
        File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), "Gtoken.apk");
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return file;
    }

    private static final String[][] MIMEMapTable = {
            // {文件扩展名,MIME类型}
            { ".3gp", "video/3gpp" },
            { ".apk", "application/vnd.android.package-archive" },
            { ".asf", "video/x-ms-asf" },
            { ".avi", "video/x-msvideo" },
            { ".bin", "application/octet-stream" },
            { ".bmp", "image/bmp" },
            { ".c", "text/plain" },
            { ".class", "application/octet-stream" },
            { ".conf", "text/plain" },
            { ".cpp", "text/plain" },
            { ".doc", "application/msword" },
            { ".docx",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document" },
            { ".xls", "application/vnd.ms-excel" },
            { ".xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" },
            { ".exe", "application/octet-stream" },
            { ".gif", "image/gif" },
            { ".gtar", "application/x-gtar" },
            { ".gz", "application/x-gzip" },
            { ".h", "text/plain" },
            { ".htm", "text/html" },
            { ".html", "text/html" },
            { ".jar", "application/java-archive" },
            { ".java", "text/plain" },
            { ".jpeg", "image/jpeg" },
            { ".jpg", "image/jpeg" },
            { ".js", "application/x-javascript" },
            { ".log", "text/plain" },
            { ".m3u", "audio/x-mpegurl" },
            { ".m4a", "audio/mp4a-latm" },
            { ".m4b", "audio/mp4a-latm" },
            { ".m4p", "audio/mp4a-latm" },
            { ".m4u", "video/vnd.mpegurl" },
            { ".m4v", "video/x-m4v" },
            { ".mov", "video/quicktime" },
            { ".mp2", "audio/x-mpeg" },
            { ".mp3", "audio/x-mpeg" },
            { ".mp4", "video/mp4" },
            { ".mpc", "application/vnd.mpohun.certificate" },
            { ".mpe", "video/mpeg" },
            { ".mpeg", "video/mpeg" },
            { ".mpg", "video/mpeg" },
            { ".mpg4", "video/mp4" },
            { ".mpga", "audio/mpeg" },
            { ".msg", "application/vnd.ms-outlook" },
            { ".ogg", "audio/ogg" },
            { ".pdf", "application/pdf" },
            { ".png", "image/png" },
            { ".pps", "application/vnd.ms-powerpoint" },
            { ".ppt", "application/vnd.ms-powerpoint" },
            { ".pptx",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation" },
            { ".prop", "text/plain" }, { ".rc", "text/plain" },
            { ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" },
            { ".sh", "text/plain" }, { ".tar", "application/x-tar" },
            { ".tgz", "application/x-compressed" }, { ".txt", "text/plain" },
            { ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" },
            { ".wmv", "audio/x-ms-wmv" },
            { ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" },
            { ".z", "application/x-compress" },
            { ".zip", "application/x-zip-compressed" }, { "", "*/*" } };






}
