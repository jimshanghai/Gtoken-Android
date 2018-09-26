package com.netban.edc.wallet.module.trade;

import com.netban.edc.wallet.bean.RequestListBean;
import com.netban.edc.wallet.bean.TradeListBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by Evan on 2018/8/13.
 */

public interface TradeListContract {


    interface View{

        void showNothing();
        void hindeNothing();
        void onFail(String msg);
    }

    interface Presenter{
        void transactions_list(String token, String page, String limit);
        List<TradeListBean.DataBean> getDataBeans();
        TradeListBean getData();
        double getBanlance();
    }

    interface Model{
        Observable<TradeListBean> transactions_list(String token, String page, String limit);
        List<TradeListBean.DataBean> getDataBeans();
        void setDataBeans(List<TradeListBean.DataBean> dataBeans);
        TradeListBean getData();
        void setData(TradeListBean data);
        double getBanlance();
        void setBanlance(double banlance);

    }
}
