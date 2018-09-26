package com.netban.edc.wallet.module.trade.out.scan;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.mvp.BaseModel;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.ToUser;
import com.netban.edc.wallet.bean.TradeOutBean;
import com.netban.edc.wallet.module.trade.out.TradeOutContract;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/13.
 */

public class ScanOutModel extends BaseModel implements ScanOutContract.Model {
    private ToUser.DataBean touser;
    @Override
    public Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccounts(double val, String numbers, String contract) {
        return NetClient.getInstance().getNetApi().isTransferAccounts(getAuthorization(),val,numbers,contract,"").subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RequestBean<ToUser.DataBean>> getUserInfo(String numbers) {
        return NetClient.getInstance().getNetApi().getUserInfo(getAuthorization(),numbers).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public ToUser.DataBean getTouser() {
        return touser;
    }

    public void setTouser(ToUser.DataBean touser) {
        this.touser = touser;
    }
}
