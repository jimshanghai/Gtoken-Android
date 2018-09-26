package com.netban.edc.wallet.module.contacts;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;
import com.netban.edc.wallet.bean.ContractsListBean;
import com.netban.edc.wallet.module.contacts.add.AddContactsActivity;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.widget.RxItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Evan on 2018/8/15.
 */

public class ContactsActivity extends MvpActivity<ContactsPresenter> implements ContactsContract.View, OnRefreshLoadMoreListener, ContractsListAdapter.OnItemClick {



    @BindView(R.id.rb_contracts)
    RadioButton rbContracts;
    @BindView(R.id.rb_contracts_recent)
    RadioButton rbContractsRecent;
    @BindView(R.id.img_add_contacts)
    ImageView imgAddContacts;
    @BindView(R.id.layout_none_contacts)
    LinearLayout layoutNoneContacts;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_view)
    SmartRefreshLayout refreshView;
    private ContractsListAdapter adapter;
    private RecentListAdapter tadapter;
    private int type;
    private int REQUEST_CODE = 100;
    private boolean isRecent;//是否是最近联系人
    private RecentListAdapter radapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        type = getIntent().getIntExtra("type", 0);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RxItemDecoration(this, Color.parseColor("#ffe5e5e5")));

        /*最近联系人*/
        radapter=new RecentListAdapter(this);
        radapter.setOnItemClick(this);
        /*联系人*/
        adapter = new ContractsListAdapter(this);
        adapter.setOnItemClick(this);
        /*转账跳转 联系人*/
        tadapter=new RecentListAdapter(this);
        tadapter.setOnItemClick(this);
        /*
        * 1:转账过来
        * */
        if (type==1){
            recyclerView.setAdapter(tadapter);
            imgAddContacts.setVisibility(View.GONE);
        }else{
            recyclerView.setAdapter(adapter);
        }
        refreshView.setOnRefreshLoadMoreListener(this);
        rbContracts.setTextColor(Color.BLACK);
        rbContractsRecent.setTextColor(Color.GRAY);
        rbContracts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    isRecent = false;
                    mpresenter.getUserContact(null);
                    rbContracts.setTextColor(Color.BLACK);
                    rbContractsRecent.setTextColor(Color.GRAY);
                }
            }
        });
        rbContractsRecent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    isRecent=true;
                    mpresenter.getUserLately(null);
                    rbContractsRecent.setTextColor(Color.BLACK);
                    rbContracts.setTextColor(Color.GRAY);
                }

            }
        });
        mpresenter.setAuthorization(getUser().getToken());
        mpresenter.getUserContact(null);
    }

    @OnClick({R.id.img_add_contacts})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.img_add_contacts:
                skipAdd();
                break;
        }
    }

    /**
     * 跳转到添加联系人
     */
    private void skipAdd() {
        Intent intent = new Intent(this, AddContactsActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            rbContracts.setChecked(true);
            rbContractsRecent.setChecked(false);
            onRefresh(refreshView);
        }
    }

    @Override
    public void onSuccess() {
        if (mpresenter.getDatas() == null || mpresenter.getDatas().size() <= 0) {
            if (isRecent) {
                radapter.setMlist(mpresenter.getDatas());
                recyclerView.setAdapter(radapter);
            }else{
                if (type==1){
                    tadapter.setMlist(mpresenter.getDatas());
                    recyclerView.setAdapter(tadapter);
                }else{
                    adapter.setMlist(mpresenter.getDatas());
                    recyclerView.setAdapter(adapter);
                }
            }
            layoutNoneContacts.setVisibility(View.VISIBLE);
            return;
        }

        layoutNoneContacts.setVisibility(View.GONE);

        if (isRecent) {
            radapter.setMlist(mpresenter.getDatas());
            recyclerView.setAdapter(radapter);
        }else{
            if (type==1){
                tadapter.setMlist(mpresenter.getDatas());
                recyclerView.setAdapter(tadapter);
            }else{
                adapter.setMlist(mpresenter.getDatas());
                recyclerView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onFail(String msg) {
        layoutNoneContacts.setVisibility(View.VISIBLE);
        ToastUtils.showShortToast(this, msg);
    }

    @Override
    public void deleteSuccess() {
        mpresenter.getUserContact(null);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore();
        if (isRecent){
            mpresenter.getUserLately(null);
        }else{
            mpresenter.getUserContact(null);
        }

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        if (isRecent){
            mpresenter.getUserLately(null);
        }else{
            mpresenter.getUserContact(null);
        }
    }

    @Override
    public void onItemClick(ContractsListBean.DataBean dataBean, int pos) {
        if (type != 1) return;
        Intent intent = new Intent();
        intent.putExtra("result", dataBean);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDelete(ContractsListBean.DataBean dataBean, int pos) {
        mpresenter.deltUserContact(dataBean.getNumbers(), "");
    }
}
