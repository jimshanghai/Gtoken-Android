package com.netban.edc.wallet.module.trade.inputpwd;

import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.ToUser;
import com.netban.edc.wallet.bean.TradeOutBean;

import rx.Observable;

/**
 * Created by Evan on 2018/8/17.
 */

public interface InputpwdContract {
    interface View{
        void  onSuccess(TradeOutBean.DataBean dataBean);
        void onFail(String msg);
    }

    interface Presenter{
        void isTransferAccounts(double val, String numbers, String contract,String keystorepsd);
        void isTransferAccountsByaddress(double val, String contract,String keystorepsd,String private_address);
    }

    interface Model{
        Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccounts(double val, String numbers, String contract,String keystorepsd);
        Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccountsByaddress(double val, String contract,String keystorepsd,String private_address);

    }
}
