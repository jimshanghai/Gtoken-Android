package com.netban.edc.wallet.module.main.home;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.mvp.BaseModel;
import com.netban.edc.wallet.bean.CollageListBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.RequestListBean;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/3.
 */

public class HomdModel  extends BaseModel implements HomeContract.Model{
    private List<CollageListBean.DataBean> datas;
    @Override
    public Observable<RequestListBean<CollageListBean.DataBean>> walletList() {
        return NetClient.getInstance().getNetApi().walletList(getAuthorization()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    public List<CollageListBean.DataBean> getDatas() {
        return datas;
    }

    public void setDatas(List<CollageListBean.DataBean> datas) {
        this.datas = datas;
    }

    @Override
    public Observable<RequestBean> refreshWalletList() {
        return NetClient.getInstance().getNetApi().refreshWalletList(getAuthorization()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());

    }
}
