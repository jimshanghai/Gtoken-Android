package com.netban.edc.wallet.module.trade;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.bean.CollageListBean;
import com.netban.edc.wallet.bean.EventUpdate;
import com.netban.edc.wallet.module.trade.in.TradeInActivity;
import com.netban.edc.wallet.module.trade.out.TradeOutActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.StringUtils;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.RingImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/8/13.
 */

public class TradeListActivity extends MvpActivity<TradeListPresenter> implements TradeListContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.tv_nothing)
    TextView tvNothing;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_view)
    SmartRefreshLayout refreshView;
    @BindView(R.id.out_trade)
    LinearLayout outTrade;
    @BindView(R.id.in_trade)
    LinearLayout inTrade;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_avater)
    RingImageView imgAvater;
    @BindView(R.id.tv_surplus)
    TextView tvSurplus;
    @BindView(R.id.tv_hint_suplus)
    TextView tvHintSuplus;
    private BodyAdaper bodyAdaper;
    private String token;
    private String title;
    private String avater;
    private CollageListBean.DataBean collage_data;
    private String page = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_trade);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
       EventBus.getDefault().register(this);
        //处理来自主页的数据
        collage_data = ((CollageListBean.DataBean) getIntent().getParcelableExtra("collage"));
        title = collage_data.getZh_name() + getString(R.string.title_details_trade);
        tvTitle.setText(title);
        tvHintSuplus.setText(collage_data.getSymbol()+" "+getString(R.string.balance_trade));
        tvSurplus.setText(String.valueOf(collage_data.getBalance() + getString(R.string.unit_trade)));
        Glide.with(this).load(collage_data.getIcon()).error(R.drawable.ic_launcher).into(imgAvater);


        refreshView.setOnRefreshLoadMoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bodyAdaper = new BodyAdaper(this);
        recyclerView.setAdapter(bodyAdaper);


        //请求列表数据
        mpresenter.transactions_list(collage_data.getContract_id(), page, "3");

    }

    @OnClick({R.id.out_trade, R.id.in_trade})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.out_trade:
                Intent intent = new Intent(this, TradeOutActivity.class);
                intent.putExtra("data", collage_data);
                startActivity(intent);
                break;
            case R.id.in_trade:
                startActivity(new Intent(this, TradeInActivity.class));
                break;
        }
    }

    /**
     * 无数据
     */
    @Override
    public void showNothing() {
        refreshView.finishLoadMore();
        refreshView.finishRefresh();
        tvNothing.setVisibility(View.VISIBLE);
        bodyAdaper.setMlist(null);
    }

    /**
     * 存在数据
     */
    @Override
    public void hindeNothing() {
        refreshView.finishLoadMore();
        refreshView.finishRefresh();
        tvNothing.setVisibility(View.INVISIBLE);
        if (page.equals("1")) {
            bodyAdaper.setMlist(mpresenter.getDataBeans());
        } else {
            bodyAdaper.addMlist(mpresenter.getDataBeans());
        }
        tvSurplus.setText(StringUtils.doubleToString(mpresenter.getBanlance()));
        page = String.valueOf(Integer.valueOf(page) + 1);
    }

    /**
     * 错误情况的回调显示
     *
     * @param msg
     */
    @Override
    public void onFail(String msg) {
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (!collage_data.getSymbol().contains("EDC")){
            mpresenter.transactions_list(collage_data.getContract_id(), page, "3");
            return;
        }
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = "1";
        mpresenter.transactions_list(collage_data.getContract_id(), page, "3");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventUpdate(EventUpdate bean){
        onRefresh(refreshView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
