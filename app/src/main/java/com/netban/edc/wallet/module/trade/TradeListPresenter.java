package com.netban.edc.wallet.module.trade;

import android.view.View;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.RequestListBean;
import com.netban.edc.wallet.bean.TradeListBean;
import com.netban.edc.wallet.utils.ToastUtils;

import java.util.List;

/**
 * Created by Evan on 2018/8/13.
 */

public class TradeListPresenter extends BasePresenter<TradeListModel,TradeListContract.View> implements TradeListContract.Presenter {
    @Override
    public void transactions_list(String token, String page, String limit) {
        rxManager.add(model.transactions_list(token,page,limit)
        .doOnSubscribe(new DoOnSubscribe())
        .subscribe(new OnNextAction<TradeListBean>(){
            @Override
            public void call(TradeListBean bean) {
                super.call(bean);
                if (bean.getCode()==200){
                    if (bean==null||bean.getCount()<=0){
                        mview.showNothing();
                        return;
                    }
                    model.setDataBeans(bean.getData());
                    model.setData(bean);
                    model.setBanlance(bean.getBalance());
                    mview.hindeNothing();
                }else{
                    mview.showNothing();
                }
            }
        },new OnErrorAction(){
            @Override
            public void call(Throwable throwable) {
                super.call(throwable);
                mview.showNothing();
            }
        },new CompletedAction()));
    }
    public List<TradeListBean.DataBean> getDataBeans() {
        return model.getDataBeans();
    }

    public double getBanlance() {
        return model.getBanlance();
    }

    @Override
    public TradeListBean getData() {
        return model.getData();
    }
}
