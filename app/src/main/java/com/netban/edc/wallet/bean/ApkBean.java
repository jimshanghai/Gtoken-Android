package com.netban.edc.wallet.bean;

/**
 * Created by Evan on 2018/9/12.
 */

public class ApkBean extends RequestBean {

    /**
     * data : 1.0
     * url :
     */

    private String url;
    private String upgrade;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }
}
