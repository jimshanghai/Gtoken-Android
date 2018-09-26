package com.netban.edc.wallet.module.main.home;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpFragment;
import com.netban.edc.wallet.bean.CollageListBean;
import com.netban.edc.wallet.bean.ContractsListBean;
import com.netban.edc.wallet.bean.EventUpdate;
import com.netban.edc.wallet.bean.QrcodeBean;
import com.netban.edc.wallet.module.personal.PersonalActivity;
import com.netban.edc.wallet.module.trade.TradeListActivity;
import com.netban.edc.wallet.module.trade.in.TradeInActivity;
import com.netban.edc.wallet.module.trade.out.scan.ScanOutActivity;
import com.netban.edc.wallet.utils.DateUtils;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.utils.StringUtils;
import com.netban.edc.wallet.utils.ToastUtils;
import com.netban.edc.wallet.view.dialog.NotifyDialog;
import com.netban.edc.wallet.view.widget.RingImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by Evan on 2018/8/3.
 */

public class HomeFragment extends MvpFragment<HomdPresenter> implements HomeContract.View, OnRefreshListener, OnLoadMoreListener, HomeAdapter.OnItemClick<CollageListBean.DataBean>, View.OnClickListener {

    @BindView(R.id.img_avater)
    RingImageView imgAvater;
    @BindView(R.id.tv_name_user)
    TextView tvNameUser;
    @BindView(R.id.tv_number_user)
    TextView tvNumberUser;
    @BindView(R.id.img_qrcode)
    ImageView imgQrcode;
    @BindView(R.id.layout_head)
    LinearLayout layoutHead;
    @BindView(R.id.img_scan)
    ImageView imgScan;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_view)
    SmartRefreshLayout refreshView;
    Unbinder unbinder;
    private HomeAdapter mainAdapter;
    private int CODE_ZXING=100;
    private long starttime=0;
    private int count;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        StatusBarUtil.setColor(getActivity(), Color.parseColor("#FFFFFF"));
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, null);


        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void init() {
        boolean registered = EventBus.getDefault().isRegistered(this);
        if (!registered)
            EventBus.getDefault().register(this);
        toolbarLayout.setTitle(getUser().getName());
        toolbarLayout.setExpandedTitleColor(Color.WHITE);
        toolbarLayout.setCollapsedTitleTextColor(Color.BLACK);

        //初始化界面数据
        tvNumberUser.setText(getString(R.string.number_home)+getUser().getNumbers());


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mainAdapter = new HomeAdapter(pactivity);
        mainAdapter.setOnItemClick(this);
        recyclerView.setAdapter(mainAdapter);


        refreshView.setOnRefreshListener(this);
        refreshView.setOnLoadMoreListener(this);

        imgScan.setOnClickListener(this);
        imgQrcode.setOnClickListener(this);
        imgAvater.setOnClickListener(this);
        imgScan.setClickable(false);

    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (starttime==0){
            starttime=System.currentTimeMillis();
        }else{
            long curtime = System.currentTimeMillis();
            long dt = curtime - starttime;
            if (dt/1000<3){
                count++;
                if (count==3){
                    mpresenter.refreshWalletList();
                    count=0;
                    starttime=0;
                }else{
                    starttime=curtime;
                }
            }else{
                count=0;
                starttime=0;
            }
        }
        mpresenter.walletList();

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mpresenter.walletList();

    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this).load(getFUser().getAvatar()).error(R.drawable.ic_launcher).skipMemoryCache(true).into(imgAvater);
        tvNameUser.setText(getFUser().getName());
        mpresenter.walletList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(CollageListBean.DataBean dataBean, int pos) {
        Intent intent = new Intent(getContext(), TradeListActivity.class);
        intent.putExtra("collage",dataBean);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_scan:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(getContext().checkSelfPermission(Manifest.permission.CAMERA)!=PERMISSION_GRANTED) {
                        NotifyDialog notifyDialog = new NotifyDialog(getContext());
                        notifyDialog.setTitle(getString(R.string.title_notify_camera_home));
                        notifyDialog.setMsg(getString(R.string.msg_notify_camera_home));
                        notifyDialog.setListener(new NotifyDialog.OnCallListener(){
                            @Override
                            public void onCall() {
                                checkPermission(Manifest.permission.CAMERA);
                            }

                            @Override
                            public void onCancle() {
                                checkPermission(Manifest.permission.CAMERA);
                            }
                        });
                        notifyDialog.show();
                        return;
                    }
                }
                startActivityForResult(new Intent(pactivity,CaptureActivity.class),CODE_ZXING);
                break;
            case R.id.img_qrcode:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent(pactivity, TradeInActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity(),v,"qrcode").toBundle());
                }else{
                    startActivity(new Intent(pactivity, TradeInActivity.class));

                }
                break;
            case R.id.img_avater:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent(getContext(), PersonalActivity.class), ActivityOptions.makeSceneTransitionAnimation(getActivity(),imgAvater,"imgavatar").toBundle());
                }else{
                    startActivity(new Intent(getContext(), PersonalActivity.class));
                }
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==CODE_ZXING) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    QrcodeBean qrcodeBean = new Gson().fromJson(result, QrcodeBean.class);
//                    if (qrcodeBean==null){
//                        ToastUtils.showShortToast(getContext(),"错误的二维码");
//                        return;
//                    }
//                    if (mpresenter.getDatas()==null||mpresenter.getDatas().size()<=0){
//                        ToastUtils.showShortToast(getContext(),"您暂时无法转出任何学币");
//                        return;
//                    }

                    Intent intent = new Intent(getContext(), ScanOutActivity.class);
                    intent.putParcelableArrayListExtra("contract_list", (ArrayList<? extends Parcelable>) mpresenter.getDatas());
                    //intent.putExtra("qrcode",qrcodeBean);
                    try {
                        intent.putExtra("number", StringUtils.lastofsplit(result,"/"));
                    }catch (IllegalArgumentException e){
                        ToastUtils.showShortToast(getContext(),getString(R.string.error_qrcode));
                        return;
                    }
                    startActivity(intent);

                }
            }
        }
    }


    @Override
    public void onSuccess() {
        refreshView.finishRefresh();
        refreshView.finishLoadMore();
        imgScan.setClickable(true);
        if (mpresenter.getDatas()==null||mpresenter.getDatas().size()<1)
            return;
        mainAdapter.setMlist(mpresenter.getDatas());
    }

    @Override
    public void onFail(String msg) {
        refreshView.finishRefresh();
        refreshView.finishLoadMore();
        imgScan.setClickable(true);
        if (msg!=null)
            ToastUtils.showShortToast(pactivity,msg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventUpdate(EventUpdate bean){
        onRefresh(refreshView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
