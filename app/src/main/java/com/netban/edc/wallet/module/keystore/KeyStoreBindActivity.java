package com.netban.edc.wallet.module.keystore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.KeystoreBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.module.main.MainActivity;
import com.netban.edc.wallet.utils.ActivityManager;
import com.netban.edc.wallet.utils.FileUtils;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/9/7.
 */

public class KeyStoreBindActivity extends BaseActivity {
    @BindView(R.id.img_back)
    LinearLayout imgBack;
    @BindView(R.id.layout_toolbar)
    RelativeLayout layoutToolbar;
    @BindView(R.id.tv_keystore_address)
    TextView tvKeystoreAddress;
    @BindView(R.id.et_name_user)
    EditText etNameUser;
    @BindView(R.id.btn_submit)
    NoneButton btnSubmit;
    @BindView(R.id.cb_pact)
    CheckBox cbPact;
    @BindView(R.id.tv_pact)
    TextView tvPact;
    private String ketstore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_keystore);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        ketstore = getIntent().getStringExtra("keystore");
        if (!TextUtils.isEmpty(ketstore)) {
            try {
                KeystoreBean keystoreBean = new Gson().fromJson(ketstore, KeystoreBean.class);
                tvKeystoreAddress.setText(keystoreBean.getAddress());
            } catch (JsonParseException e) {
                ToastUtils.showShortToast(this, getString(R.string.error_parse));
            } catch (Exception e) {
                ToastUtils.showShortToast(this, getString(R.string.error_parse));
            }
        }
        btnSubmit.addReleView(etNameUser,cbPact);
    }

    @OnClick({R.id.btn_submit})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void submit() {
        NetClient.getInstance().getNetApi().bindRegister(etNameUser.getText().toString(),ketstore)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .throttleFirst(3000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<RequestBean<User.DataBean>>() {
                    @Override
                    public void call(RequestBean<User.DataBean> bean) {
                        if (bean.getCode()==200){
                            saveUser(bean.getData());
                            ActivityManager.getInstance().finishAllActivity();
                            Intent intent = new Intent(KeyStoreBindActivity.this, MainActivity.class);
                            intent.putExtra("isbind",true);
                            startActivity(intent);
                            finish();
                        }else{
                            ToastUtils.showShortToast(KeyStoreBindActivity.this,bean.getMsg());
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
                            ToastUtils.showShortToast(KeyStoreBindActivity.this, KeyStoreBindActivity.this.getString(R.string.error_request));
                        } else if (throwable instanceof JsonParseException
                                || throwable instanceof JSONException) {
                            ToastUtils.showShortToast(KeyStoreBindActivity.this, KeyStoreBindActivity.this.getString(R.string.error_parse));

                        } else if (throwable instanceof ConnectException) {
                            ToastUtils.showShortToast(KeyStoreBindActivity.this, KeyStoreBindActivity.this.getString(R.string.error_connect_server));
                        } else if (throwable instanceof javax.net.ssl.SSLHandshakeException) {
                            ToastUtils.showShortToast(KeyStoreBindActivity.this, KeyStoreBindActivity.this.getString(R.string.error_validation));
                        } else {
                            ToastUtils.showShortToast(KeyStoreBindActivity.this, throwable.getMessage());

                        }
                    }
                });
    }


    public void saveUser(User.DataBean user)  {
        user.setToken("Bearer "+user.getToken());
        FileUtils.saveKeystore(user.getKeystore());
        SharePreferencesHelper.getInstance(this).saveSerializableObject("user",user);
    }
}
