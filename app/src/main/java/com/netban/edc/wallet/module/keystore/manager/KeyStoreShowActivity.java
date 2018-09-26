package com.netban.edc.wallet.module.keystore.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.KeystoreBean;
import com.netban.edc.wallet.utils.FileUtils;
import com.netban.edc.wallet.utils.StringUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeyStoreShowActivity extends BaseActivity implements View.OnLongClickListener {

    @BindView(R.id.img_back)
    LinearLayout imgBack;
    @BindView(R.id.layout_toolbar)
    RelativeLayout layoutToolbar;
    @BindView(R.id.tv_keystore_address)
    TextView tvKeystoreAddress;
    @BindView(R.id.layout_keystore)
    LinearLayout layoutKeystore;
    @BindView(R.id.tv_keystore)
    TextView tvKeystore;
    @BindView(R.id.tv_path)
    TextView tvPath;
    @BindView(R.id.btn_ensure)
    NoneButton btnEnsure;
    private String keystore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_store_show);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        tvPath.setText(getString(R.string.storage_address_keystore)+ FileUtils.getKeystorePath());
        keystore = getUser().getKeystore();

        KeystoreBean keystoreBean = new Gson().fromJson(keystore, KeystoreBean.class);
        tvKeystoreAddress.setText(keystoreBean.getAddress());
        tvKeystore.setText(StringUtils.str2json(keystore));

        tvKeystoreAddress.setOnLongClickListener(this);
        tvPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtils.openFile(KeyStoreShowActivity.this,FileUtils.getKeystorePath());
            }
        });
        btnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onLongClick(View v) {
        return true;
    }
}
