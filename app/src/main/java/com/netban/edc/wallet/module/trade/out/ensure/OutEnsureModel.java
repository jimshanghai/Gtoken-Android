package com.netban.edc.wallet.module.trade.out.ensure;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.mvp.BaseModel;
import com.netban.edc.wallet.bean.RequestBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/17.
 */

public class OutEnsureModel extends BaseModel implements OutEnsureContract.Model {

    @Override
    public Observable<RequestBean> userTransferAccounts(double val, String numbers, String contract, String remarks, String keystorepsd) {
        return NetClient.getInstance().getNetApi().userTransferAccounts(getAuthorization(),val,numbers,contract,remarks,keystorepsd).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<RequestBean> userTransferAccountsByaddress(double val, String contract, String remarks, String keystorepsd, String private_address) {
        return NetClient.getInstance().getNetApi().userTransferAccountsByaddress(getAuthorization(),val,contract,remarks,keystorepsd,private_address).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

    }
}
