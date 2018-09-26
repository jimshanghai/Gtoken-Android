package com.netban.edc.wallet.module.register;

import android.text.TextUtils;

import com.netban.edc.wallet.R;
import com.netban.edc.wallet.base.mvp.BasePresenter;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.User;

import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action1;

/**
 * Created by Evan on 2018/8/1.
 */

public class RegisterPresenter extends BasePresenter<RegisterModel,RegisterContract.View> implements RegisterContract.Presenter {

    @Override
    public void register(String name, String areas, String mobile, String password, String password_confirmation, String sex, String verification) {
        if (!password.equals(password_confirmation)){
            mview.onFail(mcontext.getString(R.string.error_inconsistent_input_pwd));
            return;
        }
        if (password.length()<6||password_confirmation.length()<6){
            mview.onFail(mcontext.getString(R.string.minnum_pwd));
            return;
        }


        rxManager.add(model.register(name,areas,mobile,password,password_confirmation,sex,verification)
            .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new Action1<RequestBean<User.DataBean>>() {
                    @Override
                    public void call(RequestBean<User.DataBean> requestBean) {
                        if (requestBean.getCode()==200){
                            model.saveUser(mcontext,requestBean.getData());
                            mview.onSuccess();
                        }else{
                            mview.onFail(requestBean.getMsg());
                        }
                    }
                },new OnErrorAction(){
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof HttpException){
                            mview.onFail(mcontext.getString(R.string.fail_register));
                            dismiss();
                        }else{
                            super.call(throwable);
                        }
                    }
                },new CompletedAction())
        );
    }

    @Override
    public void smscodes(String areas, String mobile) {
        areas="00"+areas.substring(1);
        rxManager.add(model.smscodes(areas,mobile)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new Action1<RequestBean>() {
                    @Override
                    public void call(RequestBean requestBean) {
                        if (requestBean.getCode()==200){
                            mview.onGetCode();
                        }else{
                            mview.onFail(requestBean.getMsg());
                        }
                    }
                },new OnErrorAction(),new CompletedAction())
        );
    }

    @Override
    public void sendEmail(String email) {
        rxManager.add(model.sendEmail(email)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new Action1<RequestBean>() {
                    @Override
                    public void call(RequestBean requestBean) {
                        if (requestBean.getCode()==200){
                            mview.onGetECode();
                        }else{
                            mview.onFail(requestBean.getMsg());
                        }
                    }
                },new OnErrorAction(),new CompletedAction())
        );
    }

    @Override
    public void registeremail(String name, String email, String password, String password_confirmation, String sex, String verification) {
        if (!password.equals(password_confirmation)){
            mview.onFail(mcontext.getString(R.string.error_inconsistent_input_pwd));
            return;
        }
        if (password.length()<6||password_confirmation.length()<6){
            mview.onFail(mcontext.getString(R.string.minnum_pwd));
            return;
        }

        rxManager.add(model.registeremail(name,email,password,password_confirmation,sex,verification)
                .doOnSubscribe(new DoOnSubscribe())
                .subscribe(new Action1<RequestBean<User.DataBean>>() {
                    @Override
                    public void call(RequestBean<User.DataBean> requestBean) {
                        if (requestBean.getCode()==200){
                            model.saveUser(mcontext,requestBean.getData());
                            mview.onSuccess();
                        }else{
                            mview.onFail(requestBean.getMsg());
                        }
                    }
                },new OnErrorAction(){
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof HttpException){
                            mview.onFail(mcontext.getString(R.string.fail_register));
                            dismiss();
                        }else{
                            super.call(throwable);
                        }
                    }
                },new CompletedAction())
        );
    }
    public String getKeystore() {
        return model.getKeystore();
    }

    public void setKeystore(String keystore) {
        model.setKeystore(keystore);
    }
}
