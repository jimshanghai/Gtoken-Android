package com.netban.edc.wallet.module.pwdreset;

import com.netban.edc.wallet.bean.RequestBean;

import rx.Observable;

/**
 * Created by Evan on 2018/8/2.
 */

public interface PwdResetContract {
    public interface View{
        void onSuccess(String msg);
        void onFail(String msg);
    }

    public interface Presenter{
        void resetPassword(String mobile,String password, String password_confirmation,String verification);
        void smscodes(String areas,String mobile );
    }

    public interface Model{
        Observable<RequestBean> resetPassword(String mobile,String password, String password_confirmation,String verification);
        Observable<RequestBean> smscodes(String areas,String mobile );
        Observable<RequestBean> sendEmail(String email);

    }
}
