package com.netban.edc.wallet.module.pwdreset;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.bean.RequestBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/2.
 */

public class PwdResetModel implements PwdResetContract.Model {
    private String type="reset";
    private boolean isemail;
    @Override
    public Observable<RequestBean> resetPassword(String mobile, String password, String password_confirmation, String verification) {
        if (!isemail){
            return NetClient.getInstance().getNetApi().resetPasswordbyMobile(mobile,password,password_confirmation,verification).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());

        }else{
            return NetClient.getInstance().getNetApi().resetPasswordbyEmail(mobile,password,password_confirmation,verification).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
        }
    }

    @Override
    public Observable<RequestBean> smscodes(String areas, String mobile) {
        return NetClient.getInstance().getNetApi().smscodes(areas,mobile,type).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<RequestBean> sendEmail(String email) {
        return NetClient.getInstance().getNetApi().sendEmail(email,type).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread());
    }

    public boolean isIsemail() {
        return isemail;
    }

    public void setIsemail(boolean isemail) {
        this.isemail = isemail;
    }
}

