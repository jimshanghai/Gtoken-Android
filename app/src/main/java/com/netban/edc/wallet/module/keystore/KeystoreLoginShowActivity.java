package com.netban.edc.wallet.module.keystore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.KeystoreBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.module.main.MainActivity;
import com.netban.edc.wallet.module.register.RegisterActivity;
import com.netban.edc.wallet.utils.ActivityManager;
import com.netban.edc.wallet.utils.FileUtils;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.utils.StringUtils;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.dialog.NotifyDialog;
import com.netban.edc.wallet.view.widget.NoneButton;

import org.json.JSONException;

import java.net.ConnectException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class KeystoreLoginShowActivity extends BaseActivity {

    @BindView(R.id.layout_toolbar)
    RelativeLayout layoutToolbar;
    @BindView(R.id.tv_keystore_address)
    TextView tvKeystoreAddress;
    @BindView(R.id.layout_keystore)
    LinearLayout layoutKeystore;
    @BindView(R.id.tv_keystore)
    TextView tvKeystore;
    @BindView(R.id.et_pwd_keystore)
    EditText etPwdKeystore;
    @BindView(R.id.btn_ensure)
    NoneButton btnEnsure;
    private String keystore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keystore_show);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        keystore = getIntent().getStringExtra("keystore");
        btnEnsure.addReleView(etPwdKeystore);
        notifyDialog.setListener(this);
        notifyDialog.setTitle(getString(R.string.title_ensure_dia_trade));
        notifyDialog.setTitle(getString(R.string.error_parse));
        notifyDialog.setCanceledOnTouchOutside(false);
        try {
            KeystoreBean keystoreBean = new Gson().fromJson(keystore, KeystoreBean.class);
            tvKeystoreAddress.setText(keystoreBean.getAddress());
            tvKeystore.setText(StringUtils.str2json(keystore));
        } catch (JsonParseException e) {
            notifyDialog.show();
        } catch (Exception e) {
            notifyDialog.show();
        }
        btnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        NetClient.getInstance().getNetApi().keysotreLogin(keystore,etPwdKeystore.getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RequestBean<User.DataBean>>() {
                    @Override
                    public void call(RequestBean<User.DataBean> bean) {
                        if (bean.getCode()==200){
                            bean.getData().setToken("Bearer "+bean.getData().getToken());
                            FileUtils.saveKeystore( bean.getData().getKeystore());
                            SharePreferencesHelper.getInstance(KeystoreLoginShowActivity.this).saveSerializableObject("user",bean.getData());
                            ActivityManager.getInstance().finishAllActivity();
                            startActivity(new Intent(KeystoreLoginShowActivity.this, MainActivity.class));
                            finish();
                        }else{
                            ToastUtils.showShortToast(KeystoreLoginShowActivity.this,bean.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof HttpException) {
                            if (((HttpException) throwable).code()==401){
                                notifyDialog.setTitle(getString(R.string.title_notify_camera_home));
                                notifyDialog.setMsg(getString(R.string.msg_token_dia));
                                notifyDialog.setCanceledOnTouchOutside(false);
                                notifyDialog.show();
                                return;
                            }
                            ToastUtils.showShortToast(KeystoreLoginShowActivity.this, KeystoreLoginShowActivity.this.getString(R.string.error_request));
                        } else if (throwable instanceof JsonParseException
                                || throwable instanceof JSONException) {
                            ToastUtils.showShortToast(KeystoreLoginShowActivity.this, KeystoreLoginShowActivity.this.getString(R.string.error_parse));

                        } else if (throwable instanceof ConnectException) {
                            ToastUtils.showShortToast(KeystoreLoginShowActivity.this, KeystoreLoginShowActivity.this.getString(R.string.error_connect_server));
                        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
                            ToastUtils.showShortToast(KeystoreLoginShowActivity.this, KeystoreLoginShowActivity.this.getString(R.string.error_validation));
                        } else {
                            ToastUtils.showShortToast(KeystoreLoginShowActivity.this, throwable.getMessage());

                        }
                    }
                });
    }


    @Override
    public void onCall() {
        super.onCall();
        finish();
    }

    @Override
    public void onCancle() {
        super.onCancle();
    }
}
