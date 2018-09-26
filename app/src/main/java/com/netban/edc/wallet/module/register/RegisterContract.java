package com.netban.edc.wallet.module.register;

import android.content.Context;

import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;

import retrofit2.http.Field;
import rx.Observable;


/**
 * Created by Evan on 2018/8/1.
 */

public interface RegisterContract {
    interface View{
        void onSuccess();
        void onFail(String msg);
        void onGetCode();
        void onGetECode();
    }
    interface Presenter{
        void register(String name, String areas, String mobile, String password, String password_confirmation, String sex, String verification);
        void smscodes(String areas,String mobile );
        void sendEmail(String email);
        void registeremail(String name,String email,String password,String password_confirmation,String sex,String verification);
    }
    interface Model{
        Observable<RequestBean<User.DataBean>> register(String name, String areas, String mobile, String password, String password_confirmation, String sex, String verification);
        Observable<RequestBean> smscodes(String areas,String mobile );
        Observable<RequestBean> sendEmail(String email);
        Observable<RequestBean<User.DataBean>> registeremail(String name,String email,String password,String password_confirmation,String sex,String verification);
        void saveUser(Context context, User.DataBean user);
    }
}
