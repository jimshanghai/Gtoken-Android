package com.netban.edc.wallet.module.main.home;

import com.netban.edc.wallet.base.mvp.BasePresenter;

import com.netban.edc.wallet.bean.CollageListBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.RequestListBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Evan on 2018/8/3.
 */

public class HomdPresenter extends BasePresenter<HomdModel,HomeContract.View> implements HomeContract.Presenter {
    @Override
    public void walletList() {
        rxManager.add(model.walletList()
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestListBean<CollageListBean.DataBean>>(){
                    @Override
                    public void call(RequestListBean<CollageListBean.DataBean>  bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            model.setDatas(bean.getData());
                            mview.onSuccess();
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(){
                    @Override
                    public void call(Throwable throwable) {
                        super.call(throwable);
                        mview.onFail(null);
                    }
                },new CompletedAction())

        );
    }

    @Override
    public List<CollageListBean.DataBean> getDatas() {
        return model.getDatas();
    }

    @Override
    public void refreshWalletList() {
        rxManager.add(model.refreshWalletList()
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean>(){
                    @Override
                    public void call(RequestBean  bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            walletList();
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(){
                    @Override
                    public void call(Throwable throwable) {
                        super.call(throwable);
                        mview.onFail(null);
                    }
                },new CompletedAction())

        );
    }
}
