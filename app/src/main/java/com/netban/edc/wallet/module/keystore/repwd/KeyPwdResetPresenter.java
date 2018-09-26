package com.netban.edc.wallet.module.keystore.repwd;

import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.BaseBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Evan on 2018/8/13.
 */

public class KeyPwdResetPresenter extends BasePresenter<KeyPwdResetModel, KeyPwdResetContract.View> implements KeyPwdResetContract.Presenter {


    @Override
    public void newSetKeystore(String password, String password_confirmation) {
        rxManager.add(model.newSetKeystore(password, password_confirmation)
                .flatMap(new Func1<RequestBean, Observable<RequestBean<User.DataBean>>>() {
                    @Override
                    public Observable<RequestBean<User.DataBean>> call(RequestBean bean) {
                        if (bean.getCode()==200){
                            return model.userInfo();
                        }else{
                            RequestBean<User.DataBean> b=new RequestBean<>();
                            b.setCode(bean.getCode());
                            b.setMsg(bean.getMsg());
                            return Observable.just(b);
                        }

                    }
                }).subscribe(new OnNextAction<RequestBean<User.DataBean>>() {
                    @Override
                    public void call(RequestBean<User.DataBean> bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            model.saveUser(mcontext,bean.getData());
                            mview.onSuccess();
                        }else{
                            mview.onFail(bean.getMsg());
                        }

                    }
                }, new OnErrorAction(), new CompletedAction())


        );
    }
}
