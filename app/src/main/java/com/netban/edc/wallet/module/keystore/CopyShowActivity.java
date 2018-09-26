package com.netban.edc.wallet.module.keystore;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonParseException;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.utils.StringUtils;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import org.json.JSONException;

import java.net.ConnectException;

import javax.net.ssl.SSLHandshakeException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CopyShowActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_back)
    LinearLayout imgBack;
    @BindView(R.id.layout_toolbar)
    RelativeLayout layoutToolbar;
    @BindView(R.id.tv_keystore)
    EditText tvKeystore;
    @BindView(R.id.btn_ensure)
    NoneButton btnEnsure;
    @BindView(R.id.tv_format)
    TextView tvFormat;
    private String res;
    private boolean isformat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_show);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        btnEnsure.addReleView(tvKeystore);
        btnEnsure.setOnClickListener(this);
        tvFormat.setOnClickListener(this);
//        tvKeystore.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (TextUtils.isEmpty(s.toString())){
//                    isformat=false;
//                }
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ensure:
                upload();
                break;
            case R.id.tv_format:
                if (TextUtils.isEmpty(tvKeystore.getText().toString())){
                    return;
                }
                if (isformat){
                    return;
                }
                res=tvKeystore.getText().toString();
                tvKeystore.setText(StringUtils.str2json(res));
                isformat=true;
                break;
        }


    }

    private void upload(){
        res=tvKeystore.getText().toString();
        NetClient.getInstance().getNetApi().isKeyStore(res)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RequestBean>() {
                    @Override
                    public void call(RequestBean bean) {
                        if (bean.getCode() == 200) {
                            Intent intent = new Intent(CopyShowActivity.this, KeystoreLoginShowActivity.class);
                            intent.putExtra("keystore", res);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(CopyShowActivity.this, KeyStoreBindActivity.class);
                            intent.putExtra("keystore", res);
                            startActivity(intent);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof HttpException) {
                            if (((HttpException) throwable).code() == 401) {
                                notifyDialog.setTitle(getString(R.string.title_notify_camera_home));
                                notifyDialog.setMsg(getString(R.string.msg_token_dia));
                                notifyDialog.setCanceledOnTouchOutside(false);
                                notifyDialog.show();
                                return;
                            }
                            ToastUtils.showShortToast(CopyShowActivity.this, CopyShowActivity.this.getString(R.string.error_request));
                        } else if (throwable instanceof JsonParseException
                                || throwable instanceof JSONException) {
                            ToastUtils.showShortToast(CopyShowActivity.this, CopyShowActivity.this.getString(R.string.error_parse));

                        } else if (throwable instanceof ConnectException) {
                            ToastUtils.showShortToast(CopyShowActivity.this, CopyShowActivity.this.getString(R.string.error_connect_server));
                        } else if (throwable instanceof SSLHandshakeException) {
                            ToastUtils.showShortToast(CopyShowActivity.this, CopyShowActivity.this.getString(R.string.error_validation));
                        } else {
                            ToastUtils.showShortToast(CopyShowActivity.this, throwable.getMessage());

                        }
                    }
                });
    }

    @Override
    public void onActionModeStarted(android.view.ActionMode mode) {
        super.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(android.view.ActionMode mode) {
        super.onActionModeFinished(mode);
//        if (TextUtils.isEmpty(tvKeystore.getText().toString())){
//            return;
//        }
//        if (isformat){
//            return;
//        }else{
//            res = tvKeystore.getText().toString();
//        }
//        tvKeystore.setText(StringUtils.str2json(tvKeystore.getText().toString()));
//        isformat=true;
    }

    //    public void onActionModeStarted(ActionMode mode) {
//        System.out.println("----------onActionModeStarted----------");
//    };
//
//
//    public void onActionModeFinished(ActionMode mode) {
//        super.onActionModeFinished(mode);
//    }
}
