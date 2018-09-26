package com.netban.edc.wallet.module.pwdreset;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.RequestBean;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Evan on 2018/8/2.
 */

public class PwdResetPresenter extends BasePresenter<PwdResetModel, PwdResetContract.View> implements PwdResetContract.Presenter {

    @Override
    public void resetPassword(String mobile, String password, String password_confirmation, String verification) {
        if (!password.equals(password_confirmation)){
            mview.onFail(mcontext.getString(R.string.error_inconsistent_input_pwd));
            return;
        }

        rxManager.add(model.resetPassword(mobile,password,password_confirmation,verification)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean>() {
                    @Override
                    public void call(RequestBean bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            mview.onSuccess(mcontext.getString(R.string.success_amend_pwd));
                        }else{
                            mview.onFail(bean.getMsg());
                        }

                    }
                }, new OnErrorAction(), new CompletedAction()));
    }

    @Override
    public void smscodes(String areas, String mobile) {
        rxManager.add(model.smscodes(areas,mobile)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean>() {
                    @Override
                    public void call(RequestBean bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            mview.onSuccess(mcontext.getString(R.string.success_send_register));
                        }else{
                            mview.onFail(bean.getMsg());
                        }

                    }
                }, new OnErrorAction(), new CompletedAction()));
    }

    public void sendEmail(String email) {
        rxManager.add(model.sendEmail(email)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new OnNextAction<RequestBean>() {
                    @Override
                    public void call(RequestBean bean) {
                        super.call(bean);
                        if (bean.getCode()==200){
                            mview.onSuccess(mcontext.getString(R.string.success_send_register));
                        }else{
                            mview.onFail(bean.getMsg());
                        }

                    }
                }, new OnErrorAction(), new CompletedAction()));
    }
    public boolean isIsemail() {
        return model.isIsemail();
    }

    public void setIsemail(boolean isemail) {
        model.setIsemail(isemail);
    }
}
