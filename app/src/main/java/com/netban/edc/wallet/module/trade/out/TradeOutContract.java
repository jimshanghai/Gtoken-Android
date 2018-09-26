package com.netban.edc.wallet.module.trade.out;

import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.TradeOutBean;

import retrofit2.http.Field;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by Evan on 2018/8/13.
 */

public interface TradeOutContract {
    interface View{
        void  onSuccess(TradeOutBean.DataBean dataBean);
        void onFail(String msg);
    }

    interface Presenter{
        void isTransferAccounts(double val, String numbers, String contract);
        void isTransferAccountsByaddress(double val, String contract,String private_address);
    }

    interface Model{
        Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccounts(double val, String numbers, String contract);
        Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccountsByaddress(double val, String contract,String private_address);

    }
}
