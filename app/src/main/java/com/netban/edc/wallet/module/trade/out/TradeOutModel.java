package com.netban.edc.wallet.module.trade.out;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.mvp.BaseModel;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.TradeOutBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/13.
 */

public class TradeOutModel extends BaseModel implements TradeOutContract.Model {

    @Override
    public Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccounts(double val, String numbers, String contract) {
        return NetClient.getInstance().getNetApi().isTransferAccounts(getAuthorization(),val,numbers,contract,"").subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccountsByaddress(double val, String contract, String private_address) {
        return NetClient.getInstance().getNetApi().isTransferAccountsByaddress(getAuthorization(),val,contract,"",private_address).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

    }
}
