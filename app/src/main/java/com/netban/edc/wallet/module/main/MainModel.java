package com.netban.edc.wallet.module.main;

import android.support.v4.app.Fragment;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.bean.ApkBean;
import com.netban.edc.wallet.bean.RequestBean;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/3.
 */

public class MainModel implements MainContract.Model{
    private Fragment curFragment;
    private List<Fragment> fragments;
    @Override
    public void addFragement(Fragment fragment) {
        if (fragments==null)
            fragments=new ArrayList<>();
        fragments.add(fragment);

    }

    @Override
    public List<Fragment> getFragments() {
        if (fragments==null)
            throw new NullPointerException("fragments is full");
        return fragments;
    }

    @Override
    public Fragment getCurFragment() {
        return curFragment;
    }

    @Override
    public void setFragment(Fragment fragment) {
        curFragment=fragment;
    }

    @Override
    public Observable<RequestBean> changeLanguage(String type) {
        return NetClient.getInstance().getNetApi().changeLanguage(type).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ApkBean> getVersion() {
        return NetClient.getInstance().getNetApi().getVersion().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
