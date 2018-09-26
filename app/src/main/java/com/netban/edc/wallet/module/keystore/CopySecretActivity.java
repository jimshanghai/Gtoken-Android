package com.netban.edc.wallet.module.keystore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.utils.PhoneSystemManager;
import com.netban.edc.wallet.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/8/13.
 */

public class CopySecretActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.tv_secret)
    TextView tvSecret;
    @BindView(R.id.btn_copy)
    Button btnCopy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_copy);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        StatusBarUtil.setColor(this, Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        btnCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        PhoneSystemManager.copy(this, tvSecret.getText().toString());
        finish();
    }
}
