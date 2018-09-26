package com.netban.edc.wallet.module.login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.module.keystore.ValiDateKeystoreActivity;
import com.netban.edc.wallet.module.keystore.secret.SecretInputActivity;
import com.netban.edc.wallet.module.main.MainActivity;
import com.netban.edc.wallet.module.pwdreset.PwdResetActivity;
import com.netban.edc.wallet.module.register.RegisterActivity;

import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/7/31.
 */

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    NoneButton btnLogin;
    @BindView(R.id.tv_pwd_reset)
    TextView tvPwdReset;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    @BindView(R.id.tv_keystore)
    TextView tvKeystore;
    @BindView(R.id.tv_secret)
    TextView tvSecret;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtil.setColor(this, Color.parseColor("#F0F3F9"));

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @Override
    protected void init() {
        boolean token = getIntent().getBooleanExtra("token", false);
        if (token) {
            showToken();
        }
        initEt();
        if (!language.equals("en")) {
            //initTv();
        }
        checkPermission(getResources().getStringArray(R.array.persissions));
        btnLogin.addReleView(etAccount, etPwd);
//        etAccount.setText("17717870940");
//        etPwd.setText("111111");
    }

    public void showToken(){
        notifyDialog.setTitle(getString(R.string.title_notify_camera_home));
        notifyDialog.setMsg(getString(R.string.msg_token_dia));
        notifyDialog.setCanceledOnTouchOutside(false);
        notifyDialog.show();
    }

    /**
     * 初始化带有下划线的TextView
     */
    private void initTv() {
        SpannableStringBuilder sp = new SpannableStringBuilder(getResources().getString(R.string.keystore_login));
        sp.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
            }
        }, 0, 5, 0);
        sp.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
            }
        }, 6, 11, 0);
        sp.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
            }
        }, 12, 15, 0);
        tvKeystore.setText(sp);

        SpannableStringBuilder sp2 = new SpannableStringBuilder(getResources().getString(R.string.secret_key_login));
        sp2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
            }
        }, 0, 5, 0);
        sp2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(true);
            }
        }, 6, 9, 0);
        tvSecret.setText(sp2);
    }

    /**
     * 初始化输入框icon
     */
    private void initEt() {
        Drawable photo_icon = getResources().getDrawable(R.drawable.photo_icon);

        photo_icon.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_14), getResources().getDimensionPixelSize(R.dimen.dp_15));
        etAccount.setCompoundDrawables(photo_icon, null, null, null);

        Drawable lock_icon = getResources().getDrawable(R.drawable.lock_icon);

        lock_icon.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_14), getResources().getDimensionPixelSize(R.dimen.dp_18));
        etPwd.setCompoundDrawables(lock_icon, null, null, null);


    }

    /**
     * 控件点击事件处理
     */
    @OnClick({R.id.btn_login, R.id.tv_pwd_reset, R.id.tv_regist,R.id.tv_secret,R.id.tv_keystore})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //ToastUtils.showShortToast(this,"点击了");
                mpresenter.login(etAccount.getText().toString(), etPwd.getText().toString());
                break;
            case R.id.tv_pwd_reset:
                skipResetPwd();
                break;
            case R.id.tv_regist:
                skipRegist();
                break;
            case R.id.tv_secret:
                skipSecret();
                break;
            case R.id.tv_keystore:
                skipkeystore();
                break;
        }
    }

    private void skipkeystore() {
        startActivity(new Intent(this,ValiDateKeystoreActivity.class));
    }


    @Override
    public void onSuccess() {
        skipMain();
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.showShortToast(this, msg);
    }

    /**
     * 跳转密码重置界面
     */
    private void skipResetPwd() {
        startActivity(new Intent(this, PwdResetActivity.class));
    }

    /**
     * 跳转注册界面
     */
    private void skipRegist() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     * 跳转主界面
     */
    private void skipMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void skipSecret(){
        startActivity(new Intent(this, SecretInputActivity.class));
    }
}
