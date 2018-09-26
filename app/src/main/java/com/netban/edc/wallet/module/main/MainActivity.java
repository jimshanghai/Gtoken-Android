package com.netban.edc.wallet.module.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.MvpActivity;

import com.netban.edc.wallet.module.main.home.HomeFragment;
import com.netban.edc.wallet.module.main.personal.PersonalFragment;
import com.netban.edc.wallet.module.main.search.SearchFragment;
import com.netban.edc.wallet.module.personal.PersonalActivity;
import com.netban.edc.wallet.service.DownApkService;
import com.netban.edc.wallet.utils.PhoneSystemManager;
import com.netban.edc.wallet.utils.StatusBarUtil;
import com.netban.edc.wallet.view.dialog.NotifyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Evan on 2018/8/3.
 */

public class MainActivity extends MvpActivity<MainPresenter> implements MainContract.View, BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bottom_view)
    BottomNavigationView bottomView;
    private MainAdapter mainAdapter;
    private boolean isbind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void init() {
        isbind = getIntent().getBooleanExtra("isbind", false);
        if (language.equals("en")){
            mpresenter.changeLanguage("en");
        }else{
            mpresenter.changeLanguage("zh");
        }



        bottomView.setOnNavigationItemSelectedListener(this);
        mpresenter.addFragement(new HomeFragment());
        mpresenter.addFragement(new SearchFragment());
        mpresenter.addFragement(new PersonalFragment());
        mainAdapter=new MainAdapter(getSupportFragmentManager());
        mainAdapter.setMlist(mpresenter.getFragments());
        viewPager.setAdapter(mainAdapter);
        viewPager.addOnPageChangeListener(this);


//        if (isbind&&TextUtils.isEmpty(getUser().getEmail())&&TextUtils.isEmpty(getUser().getMobile())){
//            notifyDialog.setTitle(getString(R.string.title_bind_dia));
//            notifyDialog.setMsg(getString(R.string.msg_bind_dia));
//            notifyDialog.setListener(this);
//            notifyDialog.show();
//        }




    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_menu:
                viewPager.setCurrentItem(0);
                return true;
            case R.id.search_menu:
                viewPager.setCurrentItem(1);
                return true;
            case R.id.personal_menu:
                viewPager.setCurrentItem(2);
                return true;
            default:
                viewPager.setCurrentItem(0);
                return true;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                bottomView.setSelectedItemId(R.id.home_menu);
                break;
            case 1:
                bottomView.setSelectedItemId(R.id.search_menu);
                break;
            case 2:
                bottomView.setSelectedItemId(R.id.personal_menu);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail(String msg) {

    }
    @Deprecated
    @Override
    public void downApk() {
        NotifyDialog notifyDialog = new NotifyDialog(this);
        notifyDialog.setTitle(getString(R.string.title_download));
        notifyDialog.setMsg(getString(R.string.msg_download));
        notifyDialog.setOk(getString(R.string.ok_download));
        notifyDialog.setListener(new NotifyDialog.OnCallListener() {
            @Override
            public void onCall() {
                startService(new Intent(MainActivity.this, DownApkService.class));
            }

            @Override
            public void onCancle() {
                getUser().setIsdown(true);
            }
        });
        notifyDialog.show();
    }

    @Override
    public void downApk(int type) {
        if (type==0){
            downApk();
        }else{
            NotifyDialog notifyDialog = new NotifyDialog(this);
            notifyDialog.setTitle(getString(R.string.title_download));
            notifyDialog.setMsg(getString(R.string.force_msg_download));
            notifyDialog.setOk(getString(R.string.ok_download));
            notifyDialog.setCanceledOnTouchOutside(false);
            notifyDialog.setListener(new NotifyDialog.OnCallListener() {
                @Override
                public void onCall() {
                    startService(new Intent(MainActivity.this, DownApkService.class));
                }

                @Override
                public void onCancle() {
                    getUser().setIsdown(true);
                }
            });
            notifyDialog.show();
        }
    }

    @Override
    public void onCall() {
        super.onCall();
        startActivity(new Intent(this, PersonalActivity.class));
    }


}
