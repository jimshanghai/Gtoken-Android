package com.netban.edc.wallet.module.keystore.secret;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/8/10.
 */

public class SecretInputActivity extends MvpActivity<SecretPresenter> implements SecretContract.View{

    @BindView(R.id.et_secret)
    EditText etSecret;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_input);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        StatusBarUtil.setColor(this, Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initEt();
    }
    /**
     * 控件点击事件
     */
    @OnClick({R.id.img_back})
    protected void OnViewClick(View view){
        switch (view.getId()){
            case R.id.img_back:
                finish();
                break;
        }
    }
    /**
     * 初始化输入框icon
     */
    private void initEt() {
        Drawable lock_icon = getResources().getDrawable(R.drawable.key_icon);

        lock_icon.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_18), getResources().getDimensionPixelSize(R.dimen.dp_18));

        etSecret.setCompoundDrawables(lock_icon, null, null, null);
    }
}
