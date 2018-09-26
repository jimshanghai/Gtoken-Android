package com.netban.edc.wallet.module.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.utils.SharePreferencesHelper;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class EmailAmendActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.img_back)
    LinearLayout imgBack;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_code_ver)
    EditText etCodeVer;
    @BindView(R.id.btn_code_ver)
    NoneButton btnCodeVer;
    @BindView(R.id.btn_subject)
    NoneButton btnSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_amend);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        btnCodeVer.addReleView(etEmail);
        btnSubject.addReleView(etEmail,etCodeVer);
        btnCodeVer.setOnClickListener(this);
        btnSubject.setOnClickListener(this);
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
        NetClient.getInstance().getNetApi().updateUsersEmail(getUser().getToken(),  etEmail.getText().toString(),etCodeVer.getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RequestBean>() {
                    @Override
                    public void call(RequestBean bean) {
                        if (bean.getCode() == 200) {
                            ToastUtils.showShortToast(EmailAmendActivity.this, getString(R.string.success_amend_pwd));
                            getUser().setEmail(etEmail.getText().toString());
                            SharePreferencesHelper.getInstance(EmailAmendActivity.this).saveSerializableObject("user", getUser());
                        } else {
                            ToastUtils.showShortToast(EmailAmendActivity.this, bean.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShortToast(EmailAmendActivity.this, throwable.getMessage());

                    }
                });


    }

    public void getSmsCode() {

        NetClient.getInstance().getNetApi().sendEmail(etEmail.getText().toString(),"update")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RequestBean>() {
                    @Override
                    public void call(RequestBean bean) {
                        if (bean.getCode()==200){
                            ToastUtils.showShortToast(EmailAmendActivity.this,getString(R.string.success_send_register));
                        }else{
                            ToastUtils.showShortToast(EmailAmendActivity.this,bean.getMsg());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShortToast(EmailAmendActivity.this,throwable.getMessage());

                    }
                });
    }

}
