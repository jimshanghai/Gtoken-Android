package com.netban.edc.wallet.module.main.home;

import com.netban.edc.wallet.bean.CollageListBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.RequestListBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Evan on 2018/8/3.
 */

public interface HomeContract {
    public interface View{
        void onSuccess();
        void onFail(String msg);
    }
    public interface Presenter{
       void walletList();
        List<CollageListBean.DataBean> getDatas();
        void refreshWalletList();
    }
    public interface Model{
        Observable<RequestListBean<CollageListBean.DataBean>> walletList();
        List<CollageListBean.DataBean> getDatas();
        void setDatas(List<CollageListBean.DataBean> datas);
        Observable<RequestBean> refreshWalletList();
    }
}
