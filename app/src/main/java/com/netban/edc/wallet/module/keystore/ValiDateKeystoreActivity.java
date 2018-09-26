package com.netban.edc.wallet.module.keystore;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.KeystoreBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.module.register.RegisterActivity;
import com.netban.edc.wallet.utils.FileUtils;
import com.netban.edc.wallet.utils.NetUtils;
import com.netban.edc.wallet.utils.PhoneSystemManager;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.dialog.ProgressDialog;
import com.netban.edc.wallet.view.widget.NoneButton;

import org.json.JSONException;

import java.net.ConnectException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.netban.edc.wallet.utils.FileUtils.getPath;
import static com.netban.edc.wallet.utils.FileUtils.getRealPathFromURI;

/**
 * Created by Evan on 2018/8/13.
 *
 * 选择KeyStore
 * 1.文件选择
 * 2.copy
 *
 */

public class ValiDateKeystoreActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btn_upload)
    NoneButton btnUpload;
    @BindView(R.id.img_back)
    LinearLayout imgBack;
    @BindView(R.id.btn_copy)
    NoneButton btnCopy;
    private String fpath;
    private int REQUEST_CODE = 110;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keystore_upload);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        StatusBarUtil.setColor(this, Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        btnUpload.setOnClickListener(this);
        btnCopy.setOnClickListener(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {
                fpath = uri.getPath();
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                fpath = getPath(this, uri);
            } else {//4.4以下下系统调用方法
                fpath = getRealPathFromURI(this, uri);
            }
            String res = FileUtils.readKeystore(fpath);

            if (TextUtils.isEmpty(res)){
                ToastUtils.showShortToast(ValiDateKeystoreActivity.this,getString(R.string.error_read));
            }

            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    String res = FileUtils.readKeystore(fpath);
                    if (TextUtils.isEmpty(res)){
                        subscriber.onError(new NullPointerException());
                        return;
                    }
                    if (!TextUtils.isEmpty(res)) {
                        try {
                            KeystoreBean keystoreBean = new Gson().fromJson(res, KeystoreBean.class);
                        } catch (Exception e) {
                            subscriber.onError(new JsonParseException(e.getMessage()));
                        }
                    }
                    subscriber.onNext(res);
                    subscriber.onCompleted();
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            progressDialog = new ProgressDialog(ValiDateKeystoreActivity.this,R.style.translationTheme);
                            progressDialog.show();
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String res) {
                           upload(res);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            if (throwable instanceof NullPointerException){
                                showToast(getString(R.string.error_file_type));
                            }else if (throwable instanceof JSONException){
                                showToast(getString(R.string.error_parse));
                            }else{
                                showToast(getString(R.string.error_file_type));
                            }
                        }
                    }, new Action0() {
                        @Override
                        public void call() {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                        }
                    });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_upload:
                PhoneSystemManager.openSystenFileManager(this, REQUEST_CODE, "*/*");
                break;
            case R.id.btn_copy:
                startActivity(new Intent(this,CopyShowActivity.class));
                break;
        }
    }
    public void showToast(String msg){
        ToastUtils.showShortToast(this,msg);
    }

    private void upload(String res) {
        NetClient.getInstance().getNetApi().isKeyStore(res)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RequestBean>() {
                    @Override
                    public void call(RequestBean bean) {
                        if (bean.getCode() == 200) {
                            Intent intent = new Intent(ValiDateKeystoreActivity.this, KeystoreLoginShowActivity.class);
                            intent.putExtra("keystore", res);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(ValiDateKeystoreActivity.this, KeyStoreBindActivity.class);
                            intent.putExtra("keystore", res);
                            startActivity(intent);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable==null){
                            if (progressDialog != null)
                                progressDialog.dismiss();
                        }
                        if (throwable instanceof HttpException) {
                            if (((HttpException) throwable).code() == 401) {
                                notifyDialog.setTitle(ValiDateKeystoreActivity.this.getString(R.string.title_notify_camera_home));
                                notifyDialog.setMsg(ValiDateKeystoreActivity.this.getString(R.string.msg_token_dia));
                                notifyDialog.setCanceledOnTouchOutside(false);
                                notifyDialog.show();
                                return;
                            }else if (((HttpException) throwable).code()==500){
                                notifyDialog.setTitle(ValiDateKeystoreActivity.this.getString(R.string.title_notify_camera_home));
                                notifyDialog.setMsg(((HttpException) throwable).message());
                                notifyDialog.setCanceledOnTouchOutside(false);
                                notifyDialog.show();
                            }else if (((HttpException) throwable).code()==302){
                                notifyDialog.setTitle(ValiDateKeystoreActivity.this.getString(R.string.title_notify_camera_home));
                                notifyDialog.setMsg(((HttpException) throwable).message());
                                notifyDialog.setCanceledOnTouchOutside(false);
                                notifyDialog.show();
                            }else if (((HttpException) throwable).code()==422){
                                notifyDialog.setTitle(ValiDateKeystoreActivity.this.getString(R.string.title_notify_camera_home));
                                notifyDialog.setMsg(((HttpException) throwable).message());
                                notifyDialog.setCanceledOnTouchOutside(false);
                                notifyDialog.show();
                            }else if (((HttpException) throwable).code()==403){
                                notifyDialog.setTitle(ValiDateKeystoreActivity.this.getString(R.string.title_notify_camera_home));
                                notifyDialog.setMsg(((HttpException) throwable).message());
                                notifyDialog.setCanceledOnTouchOutside(false);
                                notifyDialog.show();
                            }else{
                                notifyDialog.setTitle(ValiDateKeystoreActivity.this.getString(R.string.title_notify_camera_home));
                                notifyDialog.setMsg(ValiDateKeystoreActivity.this.getString(R.string.error_network));
                                notifyDialog.setCanceledOnTouchOutside(false);
                                notifyDialog.show();
                            }

                        } else if (throwable instanceof JsonParseException
                                || throwable instanceof JSONException) {
                            ToastUtils.showShortToast(ValiDateKeystoreActivity.this, ValiDateKeystoreActivity.this.getString(R.string.error_parse));

                        } else if (throwable instanceof ConnectException) {
                            ToastUtils.showShortToast(ValiDateKeystoreActivity.this, ValiDateKeystoreActivity.this.getString(R.string.error_connect_server));
                        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
                            ToastUtils.showShortToast(ValiDateKeystoreActivity.this, ValiDateKeystoreActivity.this.getString(R.string.error_validation));
                        } else {
                            if (!NetUtils.isConnected(ValiDateKeystoreActivity.this)) {
                                notifyDialog.setTitle(ValiDateKeystoreActivity.this.getString(R.string.title_notify_camera_home));
                                notifyDialog.setMsg(ValiDateKeystoreActivity.this.getString(R.string.error_network));
                                notifyDialog.setCanceledOnTouchOutside(false);
                                notifyDialog.show();
                                return;
                            }
                            ToastUtils.showShortToast(ValiDateKeystoreActivity.this, throwable.getMessage());
                        }
                    }
                });

    }
}
