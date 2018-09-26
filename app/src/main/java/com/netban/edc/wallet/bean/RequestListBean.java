package com.netban.edc.wallet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Evan on 2018/8/16.
 */

public class RequestListBean<T> extends BaseBean implements Serializable {

    private List<T> data;


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
