package com.netban.edc.wallet.module.keystore.repwd;

import android.content.Context;

import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;

import java.io.IOException;

import retrofit2.http.Field;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by Evan on 2018/8/13.
 */

public interface KeyPwdResetContract {
    interface View{
        void onSuccess();
        void onFail(String msg);
    }
    interface Presenter{
      void   newSetKeystore(String password, String password_confirmation);
    }

    interface Model{
        Observable<RequestBean> newSetKeystore(String password, String password_confirmation);
        Observable<RequestBean<User.DataBean>> userInfo();
        /**
         * 保存登录成功后的用户信息
         */
        void saveUser(Context context, User.DataBean user);
    }
}
