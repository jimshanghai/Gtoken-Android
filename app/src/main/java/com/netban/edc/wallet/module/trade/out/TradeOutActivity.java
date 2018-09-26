package com.netban.edc.wallet.module.trade.out;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.bean.CollageListBean;
import com.netban.edc.wallet.bean.ContractsListBean;
import com.netban.edc.wallet.bean.TradeOutBean;
import com.netban.edc.wallet.module.contacts.ContactsActivity;
import com.netban.edc.wallet.module.trade.inputpwd.InputPwdActivity;
import com.netban.edc.wallet.module.trade.out.ensure.OutEnsureActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.StringUtils;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/8/13.
 */

public class TradeOutActivity extends MvpActivity<TradeOutPresenter> implements TradeOutContract.View {

    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_num_out)
    TextView tvNumOut;
    @BindView(R.id.et_num_out)
    EditText etNumOut;
    @BindView(R.id.tv_num_hint)
    TextView tvNumHint;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.line_head)
    View lineHead;
    @BindView(R.id.img_contract)
    ImageView imgContract;
    @BindView(R.id.layout_number)
    LinearLayout layoutNumber;
    @BindView(R.id.btn_next)
    NoneButton btnNext;
    @BindView(R.id.btn_cancle)
    NoneButton btnCancle;

    private int CODE_REQUEST=100;
    private CollageListBean.DataBean collage_data;
    private int CODE_SCAN=101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_trade);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {

        btnNext.addReleView(etNumber,etNumOut);
        collage_data = ((CollageListBean.DataBean) getIntent().getParcelableExtra("data"));
        tvNumHint.setText(getString(R.string.balance_your)+collage_data.getBalance());
    }


    @OnClick({R.id.btn_next, R.id.btn_cancle, R.id.img_back,R.id.img_contract,R.id.img_scan})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next://下一步
                String result = etNumber.getText().toString();
                if (getUser().getType().equals("1")) {//非keystore用户

                    mpresenter.isTransferAccounts(Double.valueOf(etNumOut.getText().toString()), etNumber.getText().toString(), collage_data.getContract_id());


//                    if (!TextUtils.isEmpty(result)&&result.startsWith("0x")){
//                        mpresenter.isTransferAccountsByaddress(0,collage_data.getContract_id(),etNumOut.getText().toString());
//
//                    }else{
                   // }
                }else {//keystore用户,进入密码输入界面
//                    double val, long numbers, String contract
//                    if (result.startsWith("0x")) {
//                        intent.putExtra("private_address",result.trim());
//                    }else{
//                        intent.putExtra("numbers",Long.valueOf(result.trim()));
//                    }
                    Intent intent = new Intent(this, InputPwdActivity.class);
                    intent.putExtra("val",Double.valueOf(etNumOut.getText().toString()));
                    intent.putExtra("numbers",result);
                    intent.putExtra("contract_id",collage_data.getContract_id());
                    intent.putExtra("remark",etRemark.getText().toString());
                    intent.putExtra("type",1);
                    startActivity(intent);
                }
                break;
            case R.id.btn_cancle:
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_contract:
                Intent intent = new Intent(this, ContactsActivity.class);
                intent.putExtra("type",1);
                startActivityForResult(intent,CODE_REQUEST);
                break;
            case R.id.img_scan:
                Intent scan_intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(scan_intent, CODE_SCAN);
                return;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CODE_REQUEST){
            if (data==null)return;
            ContractsListBean.DataBean result = (ContractsListBean.DataBean) data.getParcelableExtra("result");
            etNumber.setText(String.valueOf(result.getNumbers()));
        }else if (requestCode==CODE_SCAN) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    String res="";
                    try {
                        res= StringUtils.lastofsplit(result,"/");
                    }catch (IllegalArgumentException e){
                        ToastUtils.showShortToast(this,getString(R.string.error_qrcode));
                        return;
                    }
                    etNumber.setText(res);
                }
            }
        }
    }


    @Override
    public void onSuccess(TradeOutBean.DataBean dataBean) {
            Intent intent = new Intent(this, OutEnsureActivity.class);
            dataBean.setRemark(etRemark.getText().toString());
            dataBean.setNum(Double.valueOf(etNumOut.getText().toString()));
            dataBean.setContract_id(collage_data.getContract_id());
            dataBean.setNumber(etNumber.getText().toString());
//            if (etNumber.getText().toString().startsWith("0x")){
//                dataBean.setPrivate_address(etNumber.getText().toString());
//                dataBean.setNumber("");
//            }else{
//                dataBean.setPrivate_address("");
//                dataBean.setNumber(etNumber.getText().toString());
//            }
            intent.putExtra("data",dataBean);
            intent.putExtra("type",1);
            startActivity(intent);
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.showShortToast(this,msg);
    }
}
