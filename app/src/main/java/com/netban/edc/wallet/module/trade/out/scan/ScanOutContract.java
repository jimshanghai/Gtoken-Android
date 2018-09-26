package com.netban.edc.wallet.module.trade.out.scan;

import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.ToUser;
import com.netban.edc.wallet.bean.TradeOutBean;

import retrofit2.http.Field;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by Evan on 2018/8/17.
 */

public interface ScanOutContract {
    interface View{
        void  onSuccess(TradeOutBean.DataBean dataBean);
        void onFail(String msg);
        void onGetUser();
        void onGetUserFail(String msg);
    }

    interface Presenter{
        void isTransferAccounts(double val, String numbers, String contract);
        void getUserInfo(String numbers);
        ToUser.DataBean getTouser();
    }

    interface Model{
        Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccounts(double val, String numbers, String contract);
        Observable<RequestBean<ToUser.DataBean>> getUserInfo(String numbers);
        public ToUser.DataBean getTouser();

        public void setTouser(ToUser.DataBean touser);
    }
}
