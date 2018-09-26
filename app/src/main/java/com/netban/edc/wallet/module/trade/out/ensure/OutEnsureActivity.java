package com.netban.edc.wallet.module.trade.out.ensure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.bean.EventUpdate;
import com.netban.edc.wallet.bean.TradeOutBean;
import com.netban.edc.wallet.module.main.MainActivity;
import com.netban.edc.wallet.module.trade.TradeListActivity;
import com.netban.edc.wallet.utils.DateUtils;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.StringUtils;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.dialog.NotifyDialog;
import com.netban.edc.wallet.view.widget.RingImageView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/13.
 */

public class OutEnsureActivity extends MvpActivity<OutEnsurePresenter> implements View.OnClickListener,OutEnsureContract.View {


    @BindView(R.id.layout_head)
    RelativeLayout layoutHead;
    @BindView(R.id.img_out)
    RingImageView imgOut;
    @BindView(R.id.tv_name_out)
    TextView tvNameOut;
    @BindView(R.id.layout_out)
    LinearLayout layoutOut;
    @BindView(R.id.img_in)
    RingImageView imgIn;
    @BindView(R.id.tv_name_in)
    TextView tvNameIn;
    @BindView(R.id.layout_in)
    LinearLayout layoutIn;
    @BindView(R.id.tv_num_trade)
    TextView tvNumTrade;
    @BindView(R.id.tv_collage)
    TextView tvCollage;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.btn_ensure)
    Button btnEnsure;
    private TradeOutBean.DataBean dataBean;
    private NotifyDialog notifyDialog;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensure_out);
        ButterKnife.bind(this);

    }

    @Override
    protected void init() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        StatusBarUtil.setColor(this, Color.WHITE);
        dataBean = ((TradeOutBean.DataBean) getIntent().getParcelableExtra("data"));
        type = getIntent().getIntExtra("type", 0);
        if (dataBean==null)return;
        tvCollage.setText(dataBean.getTitle()+"\n("+dataBean.getAbbreviation()+")");
        tvNameIn.setText(dataBean.getTo_name());
        tvNameOut.setText(dataBean.getName());
        tvNumTrade.setText(StringUtils.doubleToString(dataBean.getNum()));
        tvNum.setText(StringUtils.doubleToString(dataBean.getNum()));
        tvTime.setText(DateUtils.getHms());
        tvRemark.setText(dataBean.getRemark());

        Glide.with(this).load(dataBean.getAvatar()).error(R.drawable.ic_launcher).into(imgOut);
        Glide.with(this).load(dataBean.getTo_avatar()).error(R.drawable.ic_launcher).into(imgIn);

        btnEnsure.setOnClickListener(this);

        notifyDialog = new NotifyDialog(this);
        notifyDialog.setListener(this);
    }

    @Override
    public void onClick(View v) {
        mpresenter.userTransferAccounts(dataBean.getNum(),dataBean.getNumber(),dataBean.getContract_id(),dataBean.getRemark(),dataBean.getTrade_pwd());
    }

    @Override
    public void onSuccess() {
        notifyDialog.setTitle(getString(R.string.title_ensure_dia_trade));
        notifyDialog.setMsg(getString(R.string.msg_ensure_dia_trade));
        notifyDialog.show();
    }

    @Override
    public void onFail(String msg) {
        notifyDialog.setTitle(getString(R.string.title_fail_dia_trade));
        notifyDialog.setMsg(getString(R.string.msg_fail_dia_trade));
        notifyDialog.show();
    }

    @Override
    public void onCall() {
        super.onCall();
        EventBus.getDefault().post(new EventUpdate());
        switch (type){
            case 1:
                startActivity(new Intent(this, TradeListActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, MainActivity.class));
                break;
            default:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

    }

    @Override
    public void onCancle() {
        super.onCancle();
        EventBus.getDefault().post(new EventUpdate());
        switch (type){
            case 1:
                startActivity(new Intent(this, TradeListActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, MainActivity.class));
                break;
            default:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
