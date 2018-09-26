package com.netban.edc.wallet.module.trade.out.scan;

import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.ToUser;
import com.netban.edc.wallet.bean.TradeOutBean;
import com.netban.edc.wallet.module.trade.out.TradeOutContract;

/**
 * Created by Evan on 2018/8/13.
 */

public class ScanOutPresenter extends BasePresenter<ScanOutModel,ScanOutContract.View>  implements ScanOutContract.Presenter{
    @Override
    public void isTransferAccounts(double val, String numbers, String contract) {
        if (val<=0) {
            mview.onFail("转入数量应大于0");
            return;
        }

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
    public void getUserInfo(String numbers) {
        rxManager.add(model.getUserInfo(numbers)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean<ToUser.DataBean>>(){
                    @Override
                    public void call(RequestBean<ToUser.DataBean> bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            model.setTouser(bean.getData());
                            mview.onGetUser();
                        }else{
                            mview.onGetUserFail("二维码可能存在问题，请确认二维码正确后，在进行转账");
                        }
                    }
                },new OnErrorAction(){
                    @Override
                    public void call(Throwable throwable) {
                        super.call(throwable);
                        mview.onGetUserFail("二维码可能存在问题，请确认二维码正确后，在进行转账");
                    }
                },new CompletedAction())
        );
    }

    @Override
    public ToUser.DataBean getTouser() {
        return model.getTouser();
    }
}
