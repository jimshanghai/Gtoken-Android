package com.netban.edc.wallet.module.contacts.add;

import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.RequestBean;

/**
 * Created by Evan on 2018/8/16.
 */

public class AddContractsPresenter extends BasePresenter<AddContractsModel,AddContactsContract.View> implements AddContactsContract.Presenter {


    @Override
    public void addtUserContact(String numbers, String name) {
        rxManager.add(model.addtUserContact(numbers,name)
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
                },new OnErrorAction(),new CompletedAction())
        );
    }
}
