package com.netban.edc.wallet.module.common;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.utils.CameraUtils;
import com.netban.edc.wallet.utils.MeasureUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;

import static com.netban.edc.wallet.base.Constant.Uri.URL_PACT;


/**
 * Created by Evan on 2018/6/27.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.webview)
    WebView webview;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    /** 视频全屏参数 */
    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private View customView;
    private FrameLayout fullscreenContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private WebSettings settings;
    private int type;

    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int pro = msg.arg1;

            progressBar.setProgress(pro);
            if (pro == 100)
                progressBar.setVisibility(View.GONE);
        }
    };
    private File uploadfile;
    private Dialog alertDialog;
    //图片
    private final static int FILE_CHOOSER_RESULT_CODE = 128;
    //拍照
    private final static int FILE_CAMERA_RESULT_CODE = 129;
    //拍照图片路径
    private String cameraFielPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        initweb();
        progressBar.setMax(100);
        type = getIntent().getIntExtra("type", 0);
        switch (type){
            case 1:
                webview.loadUrl("https://www.gonggawallet.com/#/Terms");
                break;
            default:
                    break;
        }
    }

    private void initweb() {

        WebSettings webSettings = webview.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
            }
        });
//        WebViewClient wvc = new WebViewClient() {
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                Log.e("czy","shouldOverrideUrlLoading:"+url);
////                webview.loadUrl(url);
////                return true;
////            }
////            @Override
////            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
////                super.onReceivedSslError(view,handler,error);
////                handler.proceed();
////            }
//
//
//
//        };
//        webview.setWebViewClient(wvc);
        webSettings.setJavaScriptEnabled(true);

        webview.setDownloadListener(new MyWebViewDownLoadListener());
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                // TODO Auto-generated method stub
                return true;
            }
            /*** 视频播放相关的方法 **/

            @Override
            public View getVideoLoadingProgressView() {
                FrameLayout frameLayout = new FrameLayout(WebViewActivity.this);
                frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                return frameLayout;
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                showCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                hideCustomView();
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
               openImageChooserActivity();
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }



            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Message message = Message.obtain();
                message.arg1 = newProgress;
                handler.sendMessage(message);
            }
        });


    }
//    private void revise() {
//        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_avatar_select, null);
//        TextView tv_take_photo = (TextView) view.findViewById(R.id.tv_take_photo);
//        TextView tv_gallery = (TextView) view.findViewById(R.id.tv_gallery);
//
//        tv_take_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadfile = new File(getExternalFilesDir("web"), System.currentTimeMillis() + ".jpg");
//                CameraUtils.startCamera(WebViewActivity.this, TAKEPHOTO_CODE, uploadfile.getPath());
//                if (alertDialog != null) {
//                    alertDialog.dismiss();
//                }
//            }
//        });
//        tv_gallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CameraUtils.startGallery(WebViewActivity.this, FILE_CHOOSER_RESULT_CODE);
//                if (alertDialog != null) {
//                    alertDialog.dismiss();
//                }
//            }
//        });
//
//        builder.setView(view);
//
//        alertDialog = builder.create();
//        alertDialog.show();
//    }

    /** 视频播放全屏 **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (customView != null) {
            callback.onCustomViewHidden();
            return;
        }

        this.getWindow().getDecorView();

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        fullscreenContainer = new FullscreenHolder(this);
        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);
        customView = view;
        setStatusBarVisibility(false);
        customViewCallback = callback;
    }

    /** 隐藏视频全屏 */
    private void hideCustomView() {
        if (customView == null) {
            return;
        }

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(fullscreenContainer);
        fullscreenContainer = null;
        customView = null;
        customViewCallback.onCustomViewHidden();
        webview.setVisibility(View.VISIBLE);
    }
    private void setStatusBarVisibility(boolean visible) {
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    /** 全屏容器界面 */
    static class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode== KeyEvent.KEYCODE_BACK&&webview.canGoBack()){
            webview.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    private void openImageChooserActivity() {
        new MaterialDialog.Builder(this)
                .items(R.array.photo)
                .positiveText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (uploadMessageAboveL != null) {
                            uploadMessageAboveL.onReceiveValue(null);
                            uploadMessageAboveL = null;
                        }
                        if (uploadMessage != null) {
                            uploadMessage.onReceiveValue(null);
                            uploadMessage = null;
                        }
                        dialog.dismiss();
                    }
                })
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position == 0) {
                            takeCamera();
                        } else if (position == 1) {
                            takePhoto();
                        }
                    }
                }).show();
    }

    //选择图片
    private void takePhoto() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    //拍照
    private void takeCamera() {
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // if (CommonUtil.hasSdcard()) {
            cameraFielPath = new File(getExternalFilesDir("avatar"), System.currentTimeMillis() + ".jpg").getPath();
//            File outputImage = new File(cameraFielPath);
////            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputImage));
////            startActivityForResult(intent, FILE_CAMERA_RESULT_CODE);
        CameraUtils.startCamera(this,FILE_CAMERA_RESULT_CODE,cameraFielPath);

       // }
    }
    protected  class MyWebViewDownLoadListener implements DownloadListener {

        @Override

        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,

                                    long contentLength) {

            Log.i("tag", "url="+url);

            Log.i("tag", "userAgent="+userAgent);

            Log.i("tag", "contentDisposition="+contentDisposition);

            Log.i("tag", "mimetype="+mimetype);

            Log.i("tag", "contentLength="+contentLength);

            Uri uri = Uri.parse(url);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            startActivity(intent);

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == uploadMessage && null == uploadMessageAboveL) return;
        if (resultCode != RESULT_OK) {//同上所说需要回调onReceiveValue方法防止下次无法响应js方法
            if (uploadMessageAboveL != null) {
                uploadMessageAboveL.onReceiveValue(null);
                uploadMessageAboveL = null;
            }
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }
            return;
        }
        Uri result = null;
        if (requestCode == FILE_CAMERA_RESULT_CODE) {
            if (null != data && null != data.getData()) {
                result = data.getData();
            }
            if (result == null && hasFile(cameraFielPath)) {
                result = Uri.fromFile(new File(cameraFielPath));
            }
            if (uploadMessageAboveL != null) {
                uploadMessageAboveL.onReceiveValue(new Uri[]{result});
                uploadMessageAboveL = null;
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        } else if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (data != null) {
                result = data.getData();
            }
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    /**
     * 判断文件是否存在
     */
    public static boolean hasFile(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(Intent intent) {
        Uri[] results = null;
        if (intent != null) {
            String dataString = intent.getDataString();
            ClipData clipData = intent.getClipData();
            if (clipData != null) {
                results = new Uri[clipData.getItemCount()];
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    results[i] = item.getUri();
                }
            }
            if (dataString != null)
                results = new Uri[]{Uri.parse(dataString)};
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }




}
