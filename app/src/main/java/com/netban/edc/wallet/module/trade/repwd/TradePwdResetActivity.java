package com.netban.edc.wallet.module.trade.repwd;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/8/13.
 */

public class TradePwdResetActivity extends BaseActivity {

    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_ensure)
    EditText etPwdEnsure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repwd_trade);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        StatusBarUtil.setColor(this, Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initEt();
    }
    private void initEt(){
        Drawable lock_icon = getResources().getDrawable(R.drawable.lock_icon);

        lock_icon.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_14), getResources().getDimensionPixelSize(R.dimen.dp_18));

        etPwd.setCompoundDrawables(lock_icon, null, null, null);
        etPwdEnsure.setCompoundDrawables(lock_icon, null, null, null);
    }
}
