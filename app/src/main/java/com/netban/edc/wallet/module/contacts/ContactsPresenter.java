package com.netban.edc.wallet.module.contacts;

import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.ContractsListBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.RequestListBean;

import java.util.List;

/**
 * Created by Evan on 2018/8/15.
 */

public class ContactsPresenter extends BasePresenter<ContactsModel,ContactsContract.View> implements ContactsContract.Presenter {
    @Override
    public void getUserContact(String contract) {
        rxManager.add(model.getUserContact(contract)
            .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestListBean<ContractsListBean.DataBean>>(){
                    @Override
                    public void call(RequestListBean<ContractsListBean.DataBean> bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            model.setDatas(bean.getData());
                            mview.onSuccess();
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(),new CompletedAction())
        );
    }

    @Override
    public void getUserLately(String contract) {
        rxManager.add(model.getUserLately(contract)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestListBean<ContractsListBean.DataBean>>(){
                    @Override
                    public void call(RequestListBean<ContractsListBean.DataBean> bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            model.setDatas(bean.getData());
                            mview.onSuccess();
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(),new CompletedAction())
        );
    }

    @Override
    public String getAuthorization() {
        return model.getAuthorization();
    }

    @Override
    public void setAuthorization(String authorization) {
        model.setAuthorization(authorization);
    }

    @Override
    public List<ContractsListBean.DataBean> getDatas() {
        return model.getDatas();
    }

    @Override
    public void deltUserContact(String numbers, String name) {
        rxManager.add(model.deltUserContact(numbers,name)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean>(){
                    @Override
                    public void call(RequestBean bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            mview.deleteSuccess();
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(),new CompletedAction())
        );
    }
}
