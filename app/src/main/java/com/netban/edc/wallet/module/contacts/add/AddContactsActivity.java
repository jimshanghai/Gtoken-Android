package com.netban.edc.wallet.module.contacts.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/8/16.
 */

public class AddContactsActivity extends MvpActivity<AddContractsPresenter> implements AddContactsContract.View, View.OnClickListener {

    @BindView(R.id.layout_toolbar)
    RelativeLayout layoutToolbar;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_add)
    NoneButton btnAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        btnAdd.addReleView(etAccount);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onSuccess() {
        ToastUtils.showShortToast(this, getString(R.string.success_add));
        finish();
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    public void onClick(View v) {
        mpresenter.addtUserContact(etAccount.getText().toString(),etRemark.getText().toString());
    }
}
