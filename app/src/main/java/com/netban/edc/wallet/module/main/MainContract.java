package com.netban.edc.wallet.module.main;

import android.support.v4.app.Fragment;

import com.netban.edc.wallet.bean.ApkBean;
import com.netban.edc.wallet.bean.RequestBean;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Evan on 2018/8/3.
 */

public interface MainContract {
    public interface View{
        void onSuccess();
        void onFail(String msg);
        void downApk();
        void downApk(int type);
    }
    public interface Presenter{
        void addFragement(Fragment fragment);
        List<Fragment> getFragments();
        Fragment getCurFragment();
        void setFragment(Fragment fragment);
        void changeLanguage(String type);
        void getVersion();
    }
    public interface Model{
        void addFragement(Fragment fragment);
        List<Fragment> getFragments();
        Fragment getCurFragment();
        void setFragment(Fragment fragment);
        Observable<RequestBean> changeLanguage(String type);
        Observable<ApkBean> getVersion();

    }
}
