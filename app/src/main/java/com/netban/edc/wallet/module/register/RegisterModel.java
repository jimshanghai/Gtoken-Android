package com.netban.edc.wallet.module.register;

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
 * Created by Evan on 2018/8/1.
 */

public class RegisterModel implements RegisterContract.Model {
    private String keystore;
    private String type="register";
    @Override
    public Observable<RequestBean<User.DataBean>> register(String name, String areas, String mobile, String password, String password_confirmation, String sex, String verification) {
        return NetClient.getInstance().getNetApi().register(name,areas,mobile,password,password_confirmation,sex,verification,keystore).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<RequestBean> smscodes(String areas, String mobile) {
        return NetClient.getInstance().getNetApi().smscodes(areas,mobile,type).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());

    }

    @Override
    public Observable<RequestBean> sendEmail(String email) {
        return NetClient.getInstance().getNetApi().sendEmail(email,this.type).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());

    }

    @Override
    public Observable<RequestBean<User.DataBean>> registeremail(String name, String email, String password, String password_confirmation, String sex, String verification) {
        return NetClient.getInstance().getNetApi().registeremail(name,email,password,password_confirmation,sex,verification,keystore).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }
    @Override
    public void saveUser(Context context,User.DataBean user) {
        user.setToken("Bearer "+user.getToken());
        FileUtils.saveKeystore(user.getKeystore());

        SharePreferencesHelper.getInstance(context).saveSerializableObject("user",user);
    }

    public String getKeystore() {
        return keystore;
    }

    public void setKeystore(String keystore) {
        this.keystore = keystore;
    }
}
