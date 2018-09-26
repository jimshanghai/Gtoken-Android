package com.netban.edc.wallet.bean;

import java.io.Serializable;

/**
 * Created by Evan on 2018/8/16.
 */

public class BaseBean implements Serializable {
    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
