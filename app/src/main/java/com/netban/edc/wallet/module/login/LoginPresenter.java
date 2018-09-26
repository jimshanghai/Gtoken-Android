package com.netban.edc.wallet.module.login;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;

import java.io.IOException;

/**
 * Created by Evan on 2018/7/31.
 */

public class LoginPresenter extends BasePresenter<LoginModel, LoginContract.View> implements LoginContract.Presenter {


    @Override
    public void login(String username, String password) {
        if (password.length() < 6) {
            mview.onFail(mcontext.getString(R.string.minnum_pwd));
            return;
        }

        rxManager.add(model.login(username, password)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean<User.DataBean>>() {
                    @Override
                    public void call(RequestBean<User.DataBean> bean) {
                        super.call(bean);
                        if (bean.getCode() == 200) {
                            try {
                                model.saveUser(mcontext,bean.getData());
                            } catch (IOException e) {
                                mview.onFail("error");
                            }
                            mview.onSuccess();
                        } else {
                            mview.onFail(bean.getMsg());
                        }

                    }
                }, new OnErrorAction(), new CompletedAction())
        );
    }

    @Override
    public void changeLanguage(String type) {
        rxManager.add(model.changeLanguage(type)
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
