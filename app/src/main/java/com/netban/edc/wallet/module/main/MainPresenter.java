package com.netban.edc.wallet.module.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.ApkBean;
import com.netban.edc.wallet.bean.BaseBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.service.DownApkService;
import com.netban.edc.wallet.utils.PhoneSystemManager;
import com.netban.edc.wallet.utils.SharePreferencesHelper;

import java.util.List;

/**
 * Created by Evan on 2018/8/3.
 */

public class MainPresenter extends BasePresenter<MainModel,MainContract.View> implements MainContract.Presenter {
    @Override
    public void addFragement(Fragment fragment) {
        model.addFragement(fragment);
    }

    @Override
    public List<Fragment> getFragments() {
        return model.getFragments();
    }

    @Override
    public Fragment getCurFragment() {
        return model.getCurFragment();
    }

    @Override
    public void setFragment(Fragment fragment) {
        model.setFragment(fragment);
    }

    @Override
    public void changeLanguage(String type) {
        rxManager.add(model.changeLanguage(type)
        .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean>(){
                    @Override
                    public void call(RequestBean bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            mview.onSuccess();
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(),new CompletedAction())

        );
    }

    @Override
    public void attach(MainContract.View view, Context context) {
        super.attach(view, context);

        getVersion();
    }

    @Override
    public void getVersion() {
        model.getVersion().doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<ApkBean>(){
                    @Override
                    public void call(ApkBean bean) {
                        super.call(bean);

                        if (bean.getCode()==200) {
                            String data = (String) bean.getData();
                            if (!data.equals(PhoneSystemManager.getVerName(mcontext))) {
                                if (bean.getUpgrade().equals("0")) {
                                    mview.downApk(0);
                                }else{
                                    mview.downApk(1);
                                }
                            }
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(),new CompletedAction());
    }
}
