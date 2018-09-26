package com.netban.edc.wallet.module.contacts;

import com.netban.edc.wallet.bean.ContractsListBean;
import com.netban.edc.wallet.bean.RequestBean;
import com.netban.edc.wallet.bean.RequestListBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.Header;
import rx.Observable;


/**
 * Created by Evan on 2018/8/15.
 */

public interface ContactsContract {
    interface View{
        void onSuccess();
        void onFail(String msg);
        void deleteSuccess();
    }
    interface Presenter{
        void getUserContact(String contract);
        void getUserLately(String contract);
        String getAuthorization();
        void setAuthorization(String authorization);
        List<ContractsListBean.DataBean> getDatas();
        void deltUserContact( String numbers,String name);
    }
    interface Model{
        String getAuthorization();
        void setAuthorization(String authorization);
        Observable<RequestListBean<ContractsListBean.DataBean>> getUserContact(String contract);
        Observable<RequestListBean<ContractsListBean.DataBean>> getUserLately(String contract);

        List<ContractsListBean.DataBean> getDatas();
        void setDatas(List<ContractsListBean.DataBean> datas);
        Observable<RequestBean> deltUserContact( String numbers,String name);


    }
}
