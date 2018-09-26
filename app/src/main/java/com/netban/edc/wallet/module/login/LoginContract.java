package com.netban.edc.wallet.module.login;

import android.content.Context;

import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;


import java.io.IOException;

import retrofit2.http.Field;
import rx.Observable;

/**
 * Created by Evan on 2018/7/31.
 */

public interface LoginContract {
    interface View {
        /**
         * 登陆成功的界面回调
         */
        void onSuccess();

        /**
         * 登陆失败的界面回调
         * @param msg
         */
        void onFail(String msg);



    }

    interface Presenter{
        /**
         * 网络请求登录及登陆结果的处理
         * @param account
         * @param pwd
         */
        void login(String username, String password);
        void changeLanguage(String type);
    }

    interface Model {
        /**
         * 网络请求登录
         * @param account
         * @param pwd
         */
        Observable<RequestBean<User.DataBean>> login(String username, String password);

        /**
         * 保存登录成功后的用户信息
         */
        void saveUser(Context context,User.DataBean user) throws IOException;

        Observable<RequestBean> changeLanguage(String type);
    }

}
