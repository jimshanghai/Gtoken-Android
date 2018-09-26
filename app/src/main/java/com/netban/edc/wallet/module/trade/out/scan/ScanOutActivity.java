package com.netban.edc.wallet.module.trade.out.scan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.bean.CollageListBean;
import com.netban.edc.wallet.bean.QrcodeBean;
import com.netban.edc.wallet.bean.TradeOutBean;
import com.netban.edc.wallet.module.trade.inputpwd.InputPwdActivity;
import com.netban.edc.wallet.module.trade.out.ensure.OutEnsureActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.NoneButton;
import com.netban.edc.wallet.view.widget.RingImageView;
import com.netban.edc.wallet.view.widget.RoundImageView;
import com.netban.edc.wallet.view.widget.SpinerPopWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/8/14.
 */

public class ScanOutActivity extends MvpActivity<ScanOutPresenter> implements ScanOutContract.View {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_avater_out)
    RingImageView imgAvaterOut;
    @BindView(R.id.tv_name_out)
    TextView tvNameOut;
    @BindView(R.id.layout_out)
    LinearLayout layoutOut;
    @BindView(R.id.img_avater_in)
    RingImageView imgAvaterIn;
    @BindView(R.id.tv_name_in)
    TextView tvNameIn;
    @BindView(R.id.layout_in)
    LinearLayout layoutIn;
    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.img_avater)
    RoundImageView imgAvater;
    @BindView(R.id.tv_collage)
    TextView tvCollage;
    @BindView(R.id.layout_collage)
    RelativeLayout layoutCollage;
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
    @BindView(R.id.btn_next)
    NoneButton btnNext;
    @BindView(R.id.btn_cancle)
    NoneButton btnCancle;
    private SpinerPopWindow<CollageListBean.DataBean> mSpinerPopWindow;
    private List<CollageListBean.DataBean> contract_list;
    private QrcodeBean qrcode;
    private String contract_id;
    private String to_numbaer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_out_trade);
        ButterKnife.bind(this);

    }

    @Override
    protected void init() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtil.setColor(this, Color.WHITE);
        tvTitle.setText(getString(R.string.title_details_trade));
        btnNext.addReleView(etNumOut);
        contract_list = getIntent().getParcelableArrayListExtra("contract_list");
        to_numbaer = getIntent().getStringExtra("number");
        initTv();
        initData();


        mSpinerPopWindow = new SpinerPopWindow<CollageListBean.DataBean>(this, contract_list, itemClickListener);
        mSpinerPopWindow.setOnDismissListener(dismissListener);
    }

    private void initData() {
        mpresenter.getUserInfo(to_numbaer);

        if (contract_list == null || contract_list.size() <= 0)
            return;
        tvCollage.setText(contract_list.get(0).getZh_name());
        Glide.with(ScanOutActivity.this).load(contract_list.get(0).getIcon()).error(R.drawable.ic_launcher).into(imgAvater);

        tvNameOut.setText(getUser().getName());

        Glide.with(ScanOutActivity.this).load(getUser().getAvatar()).error(R.drawable.ic_launcher).into(imgAvaterOut);

        contract_id = contract_list.get(0).getContract_id();
        tvNumHint.setText("可用余额："+contract_list.get(0).getBalance()+"个");
    }

    @OnClick({R.id.layout_collage, R.id.btn_next, R.id.btn_cancle})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.layout_collage:
                mSpinerPopWindow.setWidth(layoutCollage.getWidth());
                mSpinerPopWindow.showAsDropDown(layoutCollage, 0, -layoutCollage.getHeight());

                initTv();
                break;
            case R.id.btn_next:
                if (getUser().getType().equals("1")) {
                    mpresenter.isTransferAccounts(Double.valueOf(etNumOut.getText().toString()),mpresenter.getTouser().getNumbers(),contract_id);
                }else {
                    Intent intent = new Intent(this, InputPwdActivity.class);
//                    double val, long numbers, String contract
                    intent.putExtra("val",Double.valueOf(etNumOut.getText().toString()));
                    intent.putExtra("numbers",mpresenter.getTouser().getNumbers());
                    intent.putExtra("contract_id",contract_id);
                    intent.putExtra("remark",etRemark.getText().toString());
                    intent.putExtra("type",2);
                    startActivity(intent);
                }

                break;
            case R.id.btn_cancle:
                finish();
                break;
        }
    }

    /**
     * 初始化TextView
     */
    private void initTv() {
        Drawable drawable = getResources().getDrawable(R.drawable.pull_icon);
        drawable.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.dp_16), getResources().getDimensionPixelSize(R.dimen.dp_10));

        tvCollage.setCompoundDrawables(null, null, drawable, null);
    }


    /**
     * 监听popupwindow取消
     */
    private PopupWindow.OnDismissListener dismissListener = new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {

        }
    };

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            tvCollage.setText(contract_list.get(position).getZh_name());
            Glide.with(ScanOutActivity.this).load(contract_list.get(position).getIcon()).error(R.drawable.ic_launcher).into(imgAvater);
            contract_id = contract_list.get(position).getContract_id();
            tvNumHint.setText("可用余额："+contract_list.get(position).getBalance()+"个");
        }
    };

    @Override
    public void onSuccess(TradeOutBean.DataBean dataBean) {

            Intent intent = new Intent(this, OutEnsureActivity.class);
            dataBean.setRemark(etRemark.getText().toString());
            dataBean.setNum(Double.valueOf(etNumOut.getText().toString()));
            dataBean.setNumber(mpresenter.getTouser().getNumbers());
            dataBean.setContract_id(contract_id);
            intent.putExtra("type",2);
            intent.putExtra("data", dataBean);
            startActivity(intent);

    }

    @Override
    public void onFail(String msg) {
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    public void onGetUser() {
        tvNameIn.setText(mpresenter.getTouser().getName());
        Glide.with(this).load(mpresenter.getTouser().getAvatar()).error(R.drawable.ic_launcher).into(imgAvaterIn);
    }

    @Override
    public void onGetUserFail(String msg) {
        notifyDialog.setTitle("提示");
        notifyDialog.setMsg(msg);
        notifyDialog.setCanceledOnTouchOutside(false);
        notifyDialog.show();
    }

    @Override
    public void onCall() {
        super.onCall();
        notifyDialog.dismiss();
        finish();
    }
}
