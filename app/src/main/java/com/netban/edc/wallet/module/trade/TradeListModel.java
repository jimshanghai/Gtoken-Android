package com.netban.edc.wallet.module.trade;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.mvp.BaseModel;
import com.netban.edc.wallet.bean.RequestListBean;
import com.netban.edc.wallet.bean.TradeListBean;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/13.
 */

public class TradeListModel extends BaseModel implements TradeListContract.Model {
    private List<TradeListBean.DataBean> dataBeans;
    private TradeListBean data;
    private double banlance;
    @Override
    public Observable<TradeListBean> transactions_list(String token, String page, String limit) {
        return NetClient.getInstance().getNetApi().transactions_list(getAuthorization(),token,page,limit).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public List<TradeListBean.DataBean> getDataBeans() {
        return dataBeans;
    }

    public void setDataBeans(List<TradeListBean.DataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    public double getBanlance() {
        return banlance;
    }

    public void setBanlance(double banlance) {
        this.banlance = banlance;
    }

    @Override
    public TradeListBean getData() {
        return data;
    }

    @Override
    public void setData(TradeListBean data) {
        this.data=data;
    }
}
