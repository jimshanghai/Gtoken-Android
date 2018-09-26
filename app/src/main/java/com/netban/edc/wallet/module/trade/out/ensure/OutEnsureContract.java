package com.netban.edc.wallet.module.trade.out.ensure;

import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.TradeOutBean;

import rx.Observable;

/**
 * Created by Evan on 2018/8/17.
 */

public interface OutEnsureContract {
    interface View{
        void  onSuccess();
        void onFail(String msg);
    }

    interface Presenter{
        void userTransferAccounts(double val, String numbers, String contract,String remarks,String keystorepsd);
        void userTransferAccountsByaddress(double val, String contract,String remarks,String keystorepsd,String private_address);

    }

    interface Model{
        Observable<RequestBean> userTransferAccounts(double val, String numbers, String contract,String remarks,String keystorepsd);
        Observable<RequestBean> userTransferAccountsByaddress(double val, String contract,String remarks,String keystorepsd,String private_address);

    }
}
