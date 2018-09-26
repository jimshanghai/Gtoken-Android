package com.netban.edc.wallet.base.mvp;

import com.netban.edc.wallet.api.NetClient;
import com.netban.edc.wallet.bean.RequestListBean;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Evan on 2018/7/31.
 */

public class BaseModel implements BaseContract.Model {
    String Authorization;


    public void setAuthorization(String Authorization) {
        this.Authorization=Authorization;
    }

    public String getAuthorization() {
        return Authorization;
    }
}
