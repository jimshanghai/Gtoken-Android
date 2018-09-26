package com.netban.edc.wallet.module.keystore.repwd;

import android.content.Context;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.mvp.BaseModel;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;
import com.netban.edc.wallet.utils.FileUtils;
import com.netban.edc.wallet.utils.SharePreferencesHelper;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/13.
 */

public class KeyPwdResetModel extends BaseModel implements KeyPwdResetContract.Model{
    @Override
    public Observable<RequestBean> newSetKeystore(String password, String password_confirmation) {
        return NetClient.getInstance().getNetApi().newSetKeystore(getAuthorization(),password,password_confirmation).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());

    }

    @Override
    public Observable<RequestBean<User.DataBean>> userInfo() {
        return NetClient.getInstance().getNetApi().userInfo(getAuthorization()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());

    }

    @Override
    public void saveUser(Context context, User.DataBean user)  {

        user.setToken(getAuthorization());
        FileUtils.saveKeystore(user.getKeystore());
        SharePreferencesHelper.getInstance(context).saveSerializableObject("user",user);

    }
}
