package com.netban.edc.wallet.module.personal;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/24.
 */

public class NameSetActivity extends BaseActivity {


    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.et_name_user)
    EditText etNameUser;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.btn_save)
    NoneButton btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_personal);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        StatusBarUtil.setColor(this, Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNameUser.setText(null);
            }
        });
        btnSave.addReleView(etNameUser);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etNameUser.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showShortToast(NameSetActivity.this, getString(R.string.notify_names_update));
                    return;
                }
                NetClient.getInstance().getNetApi().updateUser(getUser().getToken(), name, "", "","")
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<RequestBean>() {
                            @Override
                            public void call(RequestBean bean) {
                                if (bean.getCode() == 200) {
                                    ToastUtils.showShortToast(NameSetActivity.this, getString(R.string.success_amend_pwd));
                                    getUser().setName(name);
                                    SharePreferencesHelper.getInstance(NameSetActivity.this).saveSerializableObject("user", getUser());
                                } else {
                                    ToastUtils.showShortToast(NameSetActivity.this, bean.getMsg());
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                ToastUtils.showShortToast(NameSetActivity.this, throwable.getMessage());

                            }
                        });

            }
        });
    }
}
