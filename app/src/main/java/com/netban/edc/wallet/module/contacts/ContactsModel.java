package com.netban.edc.wallet.module.contacts;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.bean.ContractsListBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.RequestListBean;


import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/15.
 */

public class ContactsModel implements ContactsContract.Model {
    private String Authorization;
    private List<ContractsListBean.DataBean> datas;

    public String getAuthorization() {
        return Authorization;
    }

    public void setAuthorization(String authorization) {
        Authorization = authorization;
    }

    @Override
    public Observable<RequestListBean<ContractsListBean.DataBean>> getUserContact(String contract) {
        return NetClient.getInstance().getNetApi().getUserContact(Authorization,contract).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RequestListBean<ContractsListBean.DataBean>> getUserLately(String contract) {
        return NetClient.getInstance().getNetApi().getUserLately(Authorization,contract).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public List<ContractsListBean.DataBean> getDatas() {
        return datas;
    }

    @Override
    public void setDatas(List<ContractsListBean.DataBean> datas) {
        this.datas=datas;
    }

    @Override
    public Observable<RequestBean> deltUserContact(String numbers, String name) {
        return NetClient.getInstance().getNetApi().deltUserContact(Authorization,numbers,name).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

    }
}
