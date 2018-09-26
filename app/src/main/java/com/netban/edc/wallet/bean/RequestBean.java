package com.netban.edc.wallet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Evan on 2018/8/14.
 */

public class RequestBean<T> extends BaseBean implements Serializable {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
