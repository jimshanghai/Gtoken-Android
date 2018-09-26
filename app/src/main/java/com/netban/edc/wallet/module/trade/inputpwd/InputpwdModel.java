package com.netban.edc.wallet.module.trade.inputpwd;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.mvp.BaseModel;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.ToUser;
import com.netban.edc.wallet.bean.TradeOutBean;

import retrofit2.http.Field;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/13.
 */

public class InputpwdModel extends BaseModel implements InputpwdContract.Model {
    private ToUser.DataBean touser;
    public ToUser.DataBean getTouser() {
        return touser;
    }

    public void setTouser(ToUser.DataBean touser) {
        this.touser = touser;
    }

    @Override
    public Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccounts(double val, String numbers, String contract, String keystorepsd) {
        return NetClient.getInstance().getNetApi().isTransferAccounts(getAuthorization(),val,numbers,contract,keystorepsd).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<RequestBean<TradeOutBean.DataBean>> isTransferAccountsByaddress(double val, String contract, String keystorepsd, String private_address) {
        return NetClient.getInstance().getNetApi().isTransferAccountsByaddress(getAuthorization(),val,contract,keystorepsd,private_address).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

    }
}
