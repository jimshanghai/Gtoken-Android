package com.netban.edc.wallet.module.trade.out;

import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.TradeOutBean;

/**
 * Created by Evan on 2018/8/13.
 */

public class TradeOutPresenter extends BasePresenter<TradeOutModel,TradeOutContract.View>  implements TradeOutContract.Presenter{
   @Deprecated
    public void isTransferAccounts(double val, long numbers, String contract,String private_address) {
//        rxManager.add(model.isTransferAccounts(val,numbers,contract,private_address)
//        .doOnSubscribe(new DoOnSubscribe())
//                .subscribe(new OnNextAction<RequestBean<TradeOutBean.DataBean>>(){
//                    @Override
//                    public void call(RequestBean<TradeOutBean.DataBean> bean) {
//                        super.call(bean);
//                        if (bean.getCode()==200){
//                            mview.onSuccess(bean.getData());
//                        }else{
//                            mview.onFail(bean.getMsg());
//                        }
//                    }
//                },new OnErrorAction(),new CompletedAction())
//        );
    }

    @Override
    public void isTransferAccounts(double val, String numbers, String contract) {
        rxManager.add(model.isTransferAccounts(val,numbers,contract)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean<TradeOutBean.DataBean>>(){
                    @Override
                    public void call(RequestBean<TradeOutBean.DataBean> bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            mview.onSuccess(bean.getData());
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(),new CompletedAction())
        );
    }

    @Override
    public void isTransferAccountsByaddress(double val, String contract, String private_address) {
        rxManager.add(model.isTransferAccountsByaddress(val,contract,private_address)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean<TradeOutBean.DataBean>>(){
                    @Override
                    public void call(RequestBean<TradeOutBean.DataBean> bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            mview.onSuccess(bean.getData());
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(),new CompletedAction())
        );
    }
}
