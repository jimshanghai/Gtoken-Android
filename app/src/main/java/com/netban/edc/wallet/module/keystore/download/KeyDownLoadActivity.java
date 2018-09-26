package com.netban.edc.wallet.module.keystore.download;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.module.keystore.KeyDownSuccessActivity;
import com.netban.edc.wallet.module.keystore.repwd.KeyPwdResetActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/8/13.
 */

public class KeyDownLoadActivity extends MvpActivity<KeyDownLoadPresenter> implements KeyDownLoadContract.View {

    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.img_down)
    ImageView imgDown;
    @BindView(R.id.layout_download)
    RelativeLayout layoutDownload;
    @BindView(R.id.img_down_set)
    ImageView imgDownSet;
    @BindView(R.id.layout_download_afset)
    RelativeLayout layoutDownloadAfset;
    @BindView(R.id.img_set)
    ImageView imgSet;
    @BindView(R.id.layout_setting)
    RelativeLayout layoutSetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_key);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        StatusBarUtil.setColor(this, Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }
    @OnClick({R.id.layout_download,R.id.layout_download_afset,R.id.layout_setting})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.layout_download:
                if (getUser().getType().equals("2")){
                    startActivity(new Intent(this, KeyPwdResetActivity.class));
                }else{

                }
                break;
            case R.id.layout_download_afset:
                startActivity(new Intent(this,KeyDownLoadAFActivity.class));
                break;
            case R.id.layout_setting:
                startActivity(new Intent(this,KeyPwdResetActivity.class));
                break;
        }
    }
    @Override
    public void downSuccess() {
        startActivity(new Intent(this, KeyDownSuccessActivity.class));
    }

    @Override
    public void downFail(String msg) {

    }
}
