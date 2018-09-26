package com.netban.edc.wallet.module.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/29.
 */

public class PhoneAmendActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.spinner_code_photo)
    Spinner spinnerCodePhoto;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_code_ver)
    EditText etCodeVer;
    @BindView(R.id.btn_code_ver)
    NoneButton btnCodeVer;
    @BindView(R.id.btn_subject)
    NoneButton btnsubject;
    private String phone;
    private String code;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend_phone);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        type = getIntent().getIntExtra("type", 0);
        btnCodeVer.addReleView(etPhone);
        btnsubject.addReleView(etPhone,etCodeVer);
        btnCodeVer.setOnClickListener(this);
        btnsubject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_code_ver:
                getSmsCode();


                break;
            case R.id.btn_subject:
                submit();
                break;
        }
    }

    private void submit() {
        phone = etPhone.getText().toString();
        code = etCodeVer.getText().toString();
        NetClient.getInstance().getNetApi().updateUsers(getUser().getToken(),  phone,code)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<RequestBean>() {
                        @Override
                        public void call(RequestBean bean) {
                            if (bean.getCode() == 200) {
                                ToastUtils.showShortToast(PhoneAmendActivity.this, getString(R.string.success_amend_pwd));
                                getUser().setName(phone);
                                SharePreferencesHelper.getInstance(PhoneAmendActivity.this).saveSerializableObject("user", getUser());
                            } else {
                                ToastUtils.showShortToast(PhoneAmendActivity.this, bean.getMsg());
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            ToastUtils.showShortToast(PhoneAmendActivity.this, throwable.getMessage());

                        }
                    });


    }

    public void getSmsCode() {
        String selectedItem = (String) spinnerCodePhoto.getSelectedItem();
        selectedItem="00"+selectedItem.substring(1,selectedItem.length());
        NetClient.getInstance().getNetApi().smscodes(selectedItem,etPhone.getText().toString(),"update")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RequestBean>() {
                    @Override
                    public void call(RequestBean bean) {
                        if (bean.getCode()==200){
                            ToastUtils.showShortToast(PhoneAmendActivity.this,getString(R.string.success_send_register));
                        }else{
                            ToastUtils.showShortToast(PhoneAmendActivity.this,bean.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShortToast(PhoneAmendActivity.this,throwable.getMessage());

                    }
                });
    }
}
