package com.netban.edc.wallet.module.trade.inputpwd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.BaseActivity;

import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.bean.TradeOutBean;
import com.netban.edc.wallet.module.trade.out.ensure.OutEnsureActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/8/14.
 */

public class InputPwdActivity extends MvpActivity<InputpwdPresenter>  implements InputpwdContract.View{


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_pwd)
    TextView tvPwd;
    @BindView(R.id.et_pwd_trade)
    EditText etPwdTrade;
    @BindView(R.id.btn_next)
    NoneButton btnNext;
    @BindView(R.id.btn_cancle)
    NoneButton btnCancle;
    private int type;
    private double val;
    private String numbers;
    private String remark;
    private String contract_id;
    private String private_address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_input_trade);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtil.setColor(this, Color.WHITE);
        val = getIntent().getDoubleExtra("val", 0);
        numbers = getIntent().getStringExtra("numbers");
        contract_id = getIntent().getStringExtra("contract_id");
        remark = getIntent().getStringExtra("remark");
        type=getIntent().getIntExtra("type",0);
        tvTitle.setText(getString(R.string.title_pwd_input));
        btnNext.addReleView(etPwdTrade);
    }

    @OnClick({R.id.btn_next, R.id.btn_cancle, R.id.img_back})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
            case R.id.btn_next:
//                if(!TextUtils.isEmpty(private_address)&&private_address.startsWith("0x")){
//                    mpresenter.isTransferAccountsByaddress(val,contract_id,etPwdTrade.getText().toString(),private_address);
//                }else{
                    mpresenter.isTransferAccounts(val,numbers,contract_id,etPwdTrade.getText().toString());
               // }
                break;
            case R.id.btn_cancle:
                finish();
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess(TradeOutBean.DataBean dataBean) {
        Intent intent = new Intent(this, OutEnsureActivity.class);
        dataBean.setNum(val);
        dataBean.setContract_id(contract_id);
        dataBean.setNumber(numbers);
        dataBean.setRemark(remark);
        dataBean.setTrade_pwd(etPwdTrade.getText().toString());
        intent.putExtra("data",dataBean);
        intent.putExtra("type",type);
        startActivity(intent);
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.showShortToast(this,msg);
    }
}
