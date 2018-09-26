package com.netban.edc.wallet.module.groom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.BaseActivity;
import com.netban.edc.wallet.base.rx.ErrorAction;
import com.netban.edc.wallet.bean.GroomBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.RequestListBean;
import com.netban.edc.wallet.view.dialog.NotifyDialog;
import com.netban.edc.wallet.view.widget.RxItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class GroomActivity extends BaseActivity implements OnRefreshListener {

    @BindView(R.id.img_back)
    LinearLayout imgBack;
    @BindView(R.id.layout_toolbar)
    RelativeLayout layoutToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    private GroomAdapter groomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groom);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RxItemDecoration(this, Color.parseColor("#ffe5e5e5")));
        groomAdapter = new GroomAdapter(this);
        recyclerView.setAdapter(groomAdapter);
        smartRefresh.setOnRefreshListener(this);
        onRefresh(smartRefresh);
    }


    /**
     * 刷新操作
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        NetClient.getInstance().getNetApi().userReferee(getUser().getToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<RequestListBean<GroomBean.DataBean>>() {
                    @Override
                    public void call(RequestListBean<GroomBean.DataBean> bean) {
                        refreshLayout.finishRefresh();
                        if (bean.getCode()==200){
                            refreshView(bean.getData());
                        }else{
                            showToast(bean.getMsg());
                        }
                    }
                },new ErrorAction(new NotifyDialog(this)){
                    @Override
                    public void call(Throwable throwable) {
                        super.call(throwable);
                        refreshLayout.finishRefresh();
                    }
                });
    }

    private void refreshView(List<GroomBean.DataBean> list){
        if (list==null||list.size()<=0) {
            smartRefresh.setNoMoreData(true);
            return;
        }
        if (groomAdapter!=null)
            groomAdapter.setMlist(list);
    }

}
