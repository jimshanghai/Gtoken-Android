package com.netban.edc.wallet.module.pwdreset;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.module.register.RegisterActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/8/2.
 */

public class PwdResetActivity extends MvpActivity<PwdResetPresenter> implements PwdResetContract.View {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.layout_email)
    LinearLayout layoutEmail;
    @BindView(R.id.et_code_ver)
    EditText etCodeVer;
    @BindView(R.id.btn_code_ver)
    NoneButton btnCodeVer;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_ensure)
    EditText etPwdEnsure;
    @BindView(R.id.btn_reset)
    NoneButton btnReset;
    @BindView(R.id.tv_regist)
    TextView tvRegist;
    @BindView(R.id.layout_phone)
    LinearLayout layoutPhone;
    @BindView(R.id.tv_type)
    TextView tvType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.activity_pwd_reset);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        StatusBarUtil.setColor(this, Color.WHITE);
        btnReset.addReleView(etCodeVer, etPwd, etPwdEnsure);
        User.DataBean user = getUser();
        //判断是否是登录状态
        if (user == null) {
            layoutEmail.setVisibility(View.GONE);
            layoutPhone.setVisibility(View.VISIBLE);
            return;
        }
        tvRegist.setVisibility(View.GONE);
        etPhone.setText(user.getMobile());
        etEmail.setText(user.getEmail());
        tvType.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(user.getEmail())&&TextUtils.isEmpty(user.getMobile())){
            etPhone.setText(getUser().getMobile());
            tvType.setText(getString(R.string.phone_usered_reset));
            layoutEmail.setVisibility(View.VISIBLE);
            layoutEmail.setFocusable(false);
            layoutEmail.setFocusableInTouchMode(false);
            layoutPhone.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(user.getMobile())&&TextUtils.isEmpty(user.getEmail())){
            etEmail.setText(getUser().getEmail());
            tvType.setText(getString(R.string.email_usered_reset));
            layoutEmail.setVisibility(View.GONE);
            layoutPhone.setVisibility(View.VISIBLE);
            layoutPhone.setFocusable(false);
            layoutPhone.setFocusableInTouchMode(false);
        }
        if (!TextUtils.isEmpty(user.getMobile())&&!TextUtils.isEmpty(user.getEmail())){
            tvType.setVisibility(View.VISIBLE);
            etEmail.setFocusable(false);
            etEmail.setFocusableInTouchMode(false);
            etPhone.setFocusable(false);
            etPhone.setFocusableInTouchMode(false);
        }
    }

    @OnClick({R.id.img_back,R.id.tv_type, R.id.tv_regist, R.id.btn_code_ver, R.id.btn_reset})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_type:
                if (layoutPhone.getVisibility() == View.VISIBLE) {
                    etPhone.setText(getUser()==null?"":getUser().getMobile());
                    tvType.setText(getString(R.string.phone_usered_reset));
                    layoutEmail.setVisibility(View.VISIBLE);
                    layoutPhone.setVisibility(View.GONE);
                } else {
                    etEmail.setText(getUser()==null?"":getUser().getEmail());
                    tvType.setText(getString(R.string.email_usered_reset));
                    layoutEmail.setVisibility(View.GONE);
                    layoutPhone.setVisibility(View.VISIBLE);
                }
                etCodeVer.setText(null);
                etPwd.setText(null);
                etPwdEnsure.setText(null);
                break;
            case R.id.tv_regist:
               startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_code_ver:
                if (layoutPhone.getVisibility() == View.VISIBLE) {
                    mpresenter.smscodes("", etPhone.getText().toString());

                }else {
                    mpresenter.sendEmail(etEmail.getText().toString());

                }
                break;
            case R.id.btn_reset:
                if (layoutPhone.getVisibility() == View.VISIBLE) {
                    mpresenter.setIsemail(false);
                    mpresenter.resetPassword(etPhone.getText().toString(), etPwd.getText().toString(), etPwdEnsure.getText().toString(), etCodeVer.getText().toString());
                }else {
                    mpresenter.setIsemail(true);
                    mpresenter.resetPassword(etEmail.getText().toString(), etPwd.getText().toString(), etPwdEnsure.getText().toString(), etCodeVer.getText().toString());
                }
                break;
        }
    }

    @Override
    public void onSuccess(String msg) {
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.showShortToast(this, msg);
    }
}
