package com.netban.edc.wallet.module.keystore.manager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.module.keystore.CopySecretActivity;
import com.netban.edc.wallet.module.keystore.download.KeyDownLoadActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/8/13.
 */

public class KeyManagerActivity extends BaseActivity {

    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.layout_download)
    LinearLayout layoutDownload;
    @BindView(R.id.layout_copy)
    LinearLayout layoutCopy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_key);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        StatusBarUtil.setColor(this, Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    @OnClick({R.id.layout_download,R.id.layout_copy})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.layout_download:
                startActivity(new Intent(this, KeyDownLoadActivity.class));
                break;
            case R.id.layout_copy:
                startActivity(new Intent(this, CopySecretActivity.class));
                break;
        }
    }
}
