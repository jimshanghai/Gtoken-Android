package com.netban.edc.wallet.module.keystore.repwd;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.module.keystore.manager.KeyStoreShowActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/8/13.
 */

public class KeyPwdResetActivity extends MvpActivity<KeyPwdResetPresenter> implements KeyPwdResetContract.View {


    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_ensure)
    EditText etPwdEnsure;
    @BindView(R.id.btn_ensure)
    NoneButton btnEnsure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repwd_key);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        StatusBarUtil.setColor(this, Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
       // initEt();
        btnEnsure.addReleView(etPwd,etPwdEnsure);
    }

    private void initEt() {
        Drawable lock_icon = getResources().getDrawable(R.drawable.lock_icon);

        lock_icon.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_14), getResources().getDimensionPixelSize(R.dimen.dp_18));

        etPwd.setCompoundDrawables(lock_icon, null, null, null);
        etPwdEnsure.setCompoundDrawables(lock_icon, null, null, null);

    }

    @OnClick({R.id.btn_ensure})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ensure:
                mpresenter.newSetKeystore(etPwd.getText().toString(), etPwdEnsure.getText().toString());
                break;
        }
    }

    @Override
    public void onSuccess() {
        notifyDialog.setTitle(getString(R.string.title_ensure_dia));
        notifyDialog.setMsg(getString(R.string.msg_success_dia));
        notifyDialog.setListener(this);
        notifyDialog.show();
    }

    @Override
    public void onCancle() {
        super.onCancle();
        startActivity(new Intent(this, KeyStoreShowActivity.class));
        finish();
    }

    @Override
    public void onCall() {
        super.onCall();
        startActivity(new Intent(this, KeyStoreShowActivity.class));
        finish();
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.showShortToast(this, msg);
    }
}
