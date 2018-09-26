package com.netban.edc.wallet.module.contacts.add;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.base.mvp.BaseModel;
import com.netban.edc.wallet.bean.RequestBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/8/16.
 */

public class AddContractsModel extends BaseModel implements AddContactsContract.Model {
    @Override
    public Observable<RequestBean> addtUserContact(String numbers, String name) {
        return NetClient.getInstance().getNetApi().addtUserContact(getAuthorization(),numbers,name).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }
}
