package com.netban.edc.wallet.module.trade.in;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.QrcodeBean;
import com.netban.edc.wallet.utils.PhoneSystemManager;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/8/13.
 */

public class TradeInActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_qrcode)
    ImageView imgQrcode;
    @BindView(R.id.tv_number_user)
    TextView tvNumberUser;
    @BindView(R.id.btn_copy)
    Button btnCopy;
    private QrcodeBean qrcodeBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_trade);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtil.setColor(this, Color.WHITE);
        String numbers = getUser().getNumbers();
        tvTitle.setText(getString(R.string.title_recieve_trade));
        tvNumberUser.setText(getUser().getNumbers());
//        qrcodeBean = new QrcodeBean();
//        qrcodeBean.setNumber(numbers);
//        qrcodeBean.setName(getUser().getName());
//        qrcodeBean.setAvater(getUser().getAvatar());
        String res="https://wallet.edc.org.cn/"+getUser().getNumbers();

        Bitmap image = CodeUtils.createImage(res, 400, 400, null);
        imgQrcode.setImageBitmap(image);
        btnCopy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        PhoneSystemManager.copy(this,tvNumberUser.getText().toString());
        ToastUtils.showShortToast(this,getString(R.string.success_copy_recieve_trade));
    }
}
