package com.netban.edc.wallet.module.login;


import android.content.Context;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.utils.FileUtils;
import com.netban.edc.wallet.utils.SharePreferencesHelper;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Evan on 2018/7/31.
 */

public class LoginModel implements LoginContract.Model {


    @Override
    public Observable<RequestBean<User.DataBean>> login(String username, String password) {
        return NetClient.getInstance().getNetApi().login(username,password).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    @Override
    public void saveUser(Context context,User.DataBean user) throws IOException {
        user.setToken("Bearer "+user.getToken());
        if (user.getType().equals("2")) {
            FileUtils.saveKeystore(user.getKeystore());
        }
        SharePreferencesHelper.getInstance(context).saveSerializableObject("user",user);
    }

    @Override
    public Observable<RequestBean> changeLanguage(String type) {
        return NetClient.getInstance().getNetApi().changeLanguage(type).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
