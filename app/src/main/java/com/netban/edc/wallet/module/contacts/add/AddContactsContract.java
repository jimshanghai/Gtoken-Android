package com.netban.edc.wallet.module.contacts.add;

import com.netban.edc.wallet.bean.RequestBean;

import retrofit2.http.Field;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by Evan on 2018/8/16.
 */

public interface AddContactsContract {
    interface View{
        void onSuccess();
        void onFail(String msg);
    }
    interface Presenter{
       void addtUserContact( String numbers, String  name);
    }
    interface Model{
        Observable<RequestBean> addtUserContact( String numbers, String  name);
    }
}
