package com.netban.edc.wallet.module.trade.out.ensure;

import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.TradeOutBean;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Evan on 2018/8/17.
 */

public class OutEnsurePresenter extends BasePresenter<OutEnsureModel,OutEnsureContract.View> implements OutEnsureContract.Presenter {
    @Override
    public void userTransferAccounts(double val, String numbers, String contract, String remarks, String keystorepsd) {
        rxManager.add(model.userTransferAccounts(val,numbers,contract,remarks,keystorepsd)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean>(){
                    @Override
                    public void call(RequestBean bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            mview.onSuccess();
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(){
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof HttpException){
                            mview.onFail("");
                            dismiss();
                        }else{
                            super.call(throwable);
                        }

                    }
                },new CompletedAction())

        );
    }

    @Override
    public void userTransferAccountsByaddress(double val, String contract, String remarks, String keystorepsd,String private_address) {
        rxManager.add(model.userTransferAccountsByaddress(val,contract,remarks,keystorepsd,private_address)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean>(){
                    @Override
                    public void call(RequestBean bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            mview.onSuccess();
                        }else{
                            mview.onFail(bean.getMsg());
                        }
                    }
                },new OnErrorAction(){
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof HttpException){
                            mview.onFail("");
                            dismiss();
                        }else{
                            super.call(throwable);
                        }

                    }
                },new CompletedAction())

        );
    }
}
